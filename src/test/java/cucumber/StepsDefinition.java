package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.cep.CepDemo.CepDemoApplication;
import com.cep.CepDemo.controllers.CepController;
import com.cep.CepDemo.dto.request.CepRequest;
import com.cep.CepDemo.dto.response.CepResponse;

@SpringBootTest
@ContextConfiguration(classes = CepDemoApplication.class, loader = SpringBootContextLoader.class)
public class StepsDefinition {
    private String cep;
    private float fare;

    @Autowired
    private CepController cepController;

    @Given("the CEP is {string}")
    public void the_cep_is(String cep) {
        this.cep = cep;
    }
    @When("I ask what the fare is")
    public void i_ask_what_the_fare_is() {
        try {
            CepRequest cepRequest = new CepRequest(this.cep);

            ResponseEntity<Object> response = cepController.findCepData(cepRequest);

            CepResponse cepResponse = (CepResponse) response.getBody();

            this.fare = cepResponse.getFrete();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        System.out.println("------ EXPECTED RESPONSES");
        System.out.println("expectedAnswer: " + expectedAnswer);
        System.out.println("this.fare: " + Float.toString(this.fare));

        assertEquals(expectedAnswer, Float.toString(this.fare));
    }
}

