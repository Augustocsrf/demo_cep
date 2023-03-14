package com.cep.CepDemo.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CepValidator {
    private String cep;
    private final String regexFor8DigitCep = "[0-9]{8}";
    private final String regexFor9DigitCep = "[0-9]{5}-[0-9]{3}";

    private final static String ERRORMESSAGE_NULL_CEP = "Valor do CEP informado está vazio";
    private final static String ERRORMESSAGE_INVALID_CEP = "Valor do CEP foi informado, porem está em um formato inválido.";

    public CepValidator(String cep) {
        this.cep = cep;
    }
    
    public CepDiagnosis isCepValid() {
        if(cep == null) {
            return new CepDiagnosis(false, ERRORMESSAGE_NULL_CEP);
        }

        boolean isCepValid = validateCepByAmountOfDigits();
        return generateDiagnosisAfterValidation(isCepValid);
    }

    private boolean validateCepByAmountOfDigits() {
        if(cep.length() == 8) {
            return validateFormattedCep(regexFor8DigitCep);
        } else if (cep.length() == 9) {
            return validateFormattedCep(regexFor9DigitCep);
        } else {
            return false;
        }
    }

    private boolean validateFormattedCep(String regex){
        Pattern patternOf8DigitCep = Pattern.compile(regex);
        Matcher matcher = patternOf8DigitCep.matcher(cep);
        return matcher.matches();
    }    

    private CepDiagnosis generateDiagnosisAfterValidation(boolean isCepValid){
        if(isCepValid){
            return new CepDiagnosis(isCepValid);
        } else {
            return new CepDiagnosis(isCepValid, ERRORMESSAGE_INVALID_CEP);
        }
    }
}
