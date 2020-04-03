package populacao;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ProjecaoResourceTest {
  
    @Inject 
    IbgeService IbgeService;

    @Test
    public void testIbgeService() {
        ProjecaoIbge populacao = IbgeService.populacao();
        Assertions.assertNotNull(populacao);
    }

    @Test
    public void testProjecaoEndpoint() {
        //2030
        given()
          .when().get("/projecao/1911772800")
          .then()
             .statusCode(200);
    }

    @Test
    public void testLogEndpoint() {
        given()
          .when().get("/projecao/log")
          .then()
             .statusCode(200);
    }

}