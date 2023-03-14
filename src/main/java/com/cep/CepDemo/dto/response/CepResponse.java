package com.cep.CepDemo.dto.response;

import com.cep.CepDemo.util.Fare;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class CepResponse {
    @Getter
    @ApiModelProperty(notes = "CEP encontrado na busca.", example = "01001000", required = true) 
    private String cep;
    @Getter
    @ApiModelProperty(notes = "Rua do CEP buscado.", required = true) 
    private String rua;
    @Getter
    @ApiModelProperty(notes = "Complemento do CEP buscado.", required = true) 
    private String complemento;
    @Getter
    @ApiModelProperty(notes = "Bairro do CEP buscado.", required = true) 
    private String bairro;
    @Getter
    @ApiModelProperty(notes = "Cidade do CEP buscado.", required = true) 
    private String cidade;
    @Getter
    @ApiModelProperty(notes = "Estado do CEP buscado.", required = true) 
    private String estado;
    @Getter
    @ApiModelProperty(notes = "Frete para entregas na Região do CEP buscado.", required = true) 
    private float frete;

    public CepResponse(JsonNode responseNode) {
        this.cep = responseNode.get("cep").asText();
        this.rua = responseNode.get("logradouro").asText();
        this.complemento = responseNode.get("complemento").asText();
        this.bairro = responseNode.get("bairro").asText();
        this.cidade = responseNode.get("localidade").asText();
        this.estado = responseNode.get("uf").asText();
        this.setFrete();
    }

    private void setFrete() {
        switch (this.getEstado()) {
            // Estados do norte
            case "AC":
            case "AP":
            case "AM":
            case "PA":
            case "RO":
            case "RR":
            case "TO":
                this.frete = Fare.freteNorte;
                break;

            // Estados do Nordeste
            case "MA":
            case "PI":
            case "CE":
            case "RN":
            case "PB":
            case "PE":
            case "AL":
            case "SE":
            case "BA":
                this.frete = Fare.freteNordeste;
                break;

            // Estados do Centro-Oeste
            case "GO":
            case "MT":
            case "MS":
            case "DF":
                this.frete = Fare.freteCentroOeste;
                break;

            // Estados do Sul
            case "PR":
            case "SC":
            case "RS":
                this.frete = Fare.freteSul;
                break;

            // Estados do Sudeste
            default:
                this.frete = Fare.freteSudeste;
                break;
        }
    }
}