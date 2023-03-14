package com.cep.CepDemo.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CepRequest {
    @Getter
    @Setter
    @ApiModelProperty(notes = "CEP a ser buscado.", example = "01001000", required = true) 
    private String cep;
}
