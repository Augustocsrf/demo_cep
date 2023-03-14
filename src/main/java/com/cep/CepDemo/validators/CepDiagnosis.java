package com.cep.CepDemo.validators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class CepDiagnosis {
    @Getter
    private final boolean isCepValid;
    @Getter
    private String errorMessage;
}
