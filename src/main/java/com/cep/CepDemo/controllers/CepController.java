package com.cep.CepDemo.controllers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cep.CepDemo.dto.request.CepRequest;
import com.cep.CepDemo.dto.response.CepResponse;
import com.cep.CepDemo.services.CepService;
import com.cep.CepDemo.validators.CepDiagnosis;
import com.cep.CepDemo.validators.CepValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(
  tags="API de CEP",
  description = "Obtem fretes e dados baseados em CEP."
)
@RestController
@RequestMapping("/v1")
public class CepController {
    private CepService cepService = new CepService();

    @ApiOperation("Encontre o frete e dados para um CEP informado")
    @ApiResponses({
        @ApiResponse(code=200, message="OK\nCEP foi encontrado com sucesso e dados foram retornados", response=CepResponse.class),
        @ApiResponse(code=400, message="Bad Request\nCEP enviado para busca não estava em formato válido ou não foi informado", response=String.class),
        @ApiResponse(code=404, message="Not Found\nCEP informado não foi encontrado no sistema", response=String.class),
    })
    @PostMapping("/consulta-endereco")
    public ResponseEntity<Object> findCepData(@RequestBody CepRequest cepRequest) throws ClientProtocolException, IOException {      
        CepDiagnosis cepDiagnosis = validateCep(cepRequest);

        if(!cepDiagnosis.isCepValid()){
            return ResponseEntity.badRequest().body(cepDiagnosis.getErrorMessage());
        }

        CepResponse cepResponse = cepService.obtainCepData(cepRequest);

        if(cepResponse != null) {
            return ResponseEntity.ok().body(cepResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private CepDiagnosis validateCep(CepRequest cepRequest) {
        CepValidator cepValidator = new CepValidator(cepRequest.getCep());

        return cepValidator.isCepValid();
    }
}
