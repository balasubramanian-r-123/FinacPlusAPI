import APIBase.BaseClass;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APIOperations extends BaseClass {
    SoftAssert softAssert = new SoftAssert();
    private static String userId;
    private static final String initialName = "Balasubramanian";
    private static final String updatedName = "Sathish Kumar";

    @Test
    public void userCreation() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", initialName);
        jsonObject.put("job", "Software QA Engineer");
        String payload = jsonObject.toJSONString();
        Response response = given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(payload).
                when().
                post("/users").
                then().
                statusCode(201).
                body("name", equalTo(initialName)).extract().response();
        String userId = response.jsonPath().getString("id");
        Assert.assertNotNull(userId);

        // Since this is not a live API, the post request doesn't actually create the user.
        // So, we can't retrieve the user details through userId. It'll give null as user name
        Response getResponse = given()
                .when()
                .get("/users/" + userId);
        String name = getResponse.jsonPath().getString("name");
        softAssert.assertEquals(name, initialName);
    }

    @Test
    public void userUpdation() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", updatedName);
        jsonObject.put("job", "Software Developer");
        String updatedPayload = jsonObject.toJSONString();
        Response response = given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(updatedPayload).
                when().
                put("/users/" + userId).
                then().
                statusCode(200).
                body("name", equalTo(updatedName)).
                extract().response();
        String userId = response.jsonPath().getString("id");


        // Since this is not a live API, the put request doesn't actually update the user.
        // So, we can't retrieve the user details through userId. It'll give null as user name
        Response getResponse = given()
                .when()
                .get("/users/" + userId);
        String name = getResponse.jsonPath().getString("name");
        softAssert.assertEquals(name, updatedName);
    }
}