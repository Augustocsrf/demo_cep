package com.cep.CepDemo.services;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.cep.CepDemo.dto.request.CepRequest;
import com.cep.CepDemo.dto.response.CepResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CepService {
    private final ObjectMapper mapper = new ObjectMapper();
    
    public CepResponse obtainCepData(CepRequest cepRequest) throws ClientProtocolException, IOException {
        // Criar Client HTTP
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Criar método post para ser enviado ao endpoint de obtenção de dados de CEP
        HttpGet getRequest = new HttpGet("https://viacep.com.br/ws/" + cepRequest.getCep() + "/json/");

        CloseableHttpResponse response = httpClient.execute(getRequest);

        String result = EntityUtils.toString(response.getEntity());
        StatusLine statusLine = response.getStatusLine();
        JsonNode responseJson = this.mapper.readTree(result);

        httpClient.close();

        // Verificar o status da resposta
        // Caso o código consiga com "OK" (200), prosseguir normalmente, se não,
        // criar um objeto de erro para esse caso e lançar um erro esperado
        if (cepWasFound(statusLine, responseJson)) {
            return new CepResponse(responseJson);
        } else {
            return null;
        }        
    }

    private boolean cepWasFound(StatusLine statusLine, JsonNode responseObject){
        return
            statusLine.getStatusCode() == HttpStatus.SC_OK &&
            responseObject.get("erro") == null;
    }
}
