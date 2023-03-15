package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.ResponseEntity;

import com.cep.CepDemo.controllers.CepController;
import com.cep.CepDemo.dto.request.CepRequest;
import com.cep.CepDemo.dto.response.CepResponse;

public class StepsDefinition {
    private CepRequest cepRequest;
    private CepResponse cepResponse;

    private CepController cepController = new CepController();

    @Given("the CEP is {string}")
    public void the_cep_is(String cep) {
        this.cepRequest = new CepRequest(cep);
    }

    @When("I ask what the fare is")
    public void i_ask_what_the_fare_is() {
        try {
            ResponseEntity<Object> response = cepController.findCepData(this.cepRequest);
            CepResponse responseBody = (CepResponse) response.getBody();
            this.cepResponse = responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } 
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(Float.parseFloat(expectedAnswer), this.cepResponse.getFrete());
    }
}

