package com.cep.CepDemo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cep.CepDemo.dto.request.CepRequest;
import com.cep.CepDemo.dto.response.CepResponse;

@SpringBootTest
public class CepControllerTests {
    @Autowired
    private CepController cepController;

    // Valores de testes
    private CepRequest validCep_noLine = new CepRequest("01001000");
    private CepRequest validCep_Line = new CepRequest("66615-850");
    private CepRequest validCep_Line_South = new CepRequest("89848-000");
    private CepRequest invalidCep_Empty = new CepRequest("");
    private CepRequest invalidCep_Malformed = new CepRequest("98469-7777");
    private CepRequest invalidCep_NonExistent = new CepRequest("01001002");

    float freteNorte = 20.83f;
    float freteNordeste = 15.98f;
    float freteCentroOeste = 12.50f;
    float freteSul = 17.30f;
    float freteSudeste = 7.85f;

    @Test
    public void test_getValidSoutheastCepWithNoLine() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP VALIDO. CEP ESCRITO SEM MASCARA.");
        ResponseEntity<Object> response = cepController.findCepData(validCep_noLine);
        CepResponse responseBody = (CepResponse) response.getBody();
        assertEquals(freteSudeste, responseBody.getFrete());
    }

    @Test
    public void test_getValidNorthCepWithLine() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP VALIDO. CEP ESCRITO COM MASCARA.");
        ResponseEntity<Object> response = cepController.findCepData(validCep_Line);
        CepResponse responseBody = (CepResponse) response.getBody();
        assertEquals(freteNorte, responseBody.getFrete());
    }

    @Test
    public void test_getValidSouthCepWithLine() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP VALIDO. CEP ESCRITO COM MASCARA.");
        ResponseEntity<Object> response = cepController.findCepData(validCep_Line_South);
        CepResponse responseBody = (CepResponse) response.getBody();
        assertEquals(freteSul, responseBody.getFrete());
    }

    @Test
    public void test_getEmptyCep_Fail() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP INVALIDO. CEP VAZIO.");
        ResponseEntity<Object> response = cepController.findCepData(invalidCep_Empty);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_getInvalidCep_Fail() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP INVALIDO. CEP MAL FORMATADO.");
        ResponseEntity<Object> response = cepController.findCepData(invalidCep_Malformed);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_getInexistentCep_Fail() throws ClientProtocolException, IOException {
        System.out.println("TESTE: OBTENÇÃO DE DADOS COM CEP INVALIDO. CEP INEXISTENTE.");
        ResponseEntity<Object> response = cepController.findCepData(invalidCep_NonExistent);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
