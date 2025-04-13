package APIBase;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;

public class BaseClass {
    @BeforeClass
    public void setup() {
        baseURI = "https://reqres.in/api";
    }
}
