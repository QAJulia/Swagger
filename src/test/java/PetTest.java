import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import model.Pet;
import model.ResponseBodyForDelete;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetTest {

    @Test
    public void addPet() throws FileNotFoundException {
        String petData = "{\"category\": {\"id\": 0, \"name\": \"string\"}, \"name\": \"doggie\", \"photoUrls\": [\"string\"], \"tags\": [ { \"id\": 0, \"name\": \"string\" }],\"status\": \"available\"}";//Gson gson = new Gson();

        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body(petData)
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Pet dog = gson.fromJson(new FileReader(new File("src/test/resources/newdog.json")), Pet.class);
        Pet petObject = gson.fromJson(petData, Pet.class);
        assertEquals(dog, petObject);

        String petName = response.jsonPath().getString("name");
        assertEquals(petName, petObject.getName());

        Pet dog1 = gson.fromJson(response.body().asString(), Pet.class);
        assertEquals(dog1, petObject);
    }

    @Test
    public void findPetById() {
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/pet/15435006002293")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

        String petName = response.jsonPath().getString("name");
        assertEquals(petName, "doggie");
    }

    @Test
    public void findPetsByStatus() throws FileNotFoundException {
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=sold")
                .then()
                //.log().body()
                .statusCode(200)
                .extract().response();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Pet[] pets = gson.fromJson(response.body().asString(), Pet[].class);
        Pet[] jsonPets = gson.fromJson(new FileReader(new File("src/test/resources/soldPets.json")), Pet[].class);
        assertEquals(pets, jsonPets, "Список изменился");
    }

    @Test
    public void PetByIdNotFound() {
        Response response = given()
                .when()
                .get("https://petstore.swagger.io/v2/pet/0")
                .then()
                .log().body()
                .statusCode(404)
                .extract().response();
        String errorMessage = response.jsonPath().getString("message");
        assertEquals(errorMessage, "Pet not found");
    }

    @Test
    public void deletePet() throws FileNotFoundException {
        Response response = given()
                .when()
                .delete("https://petstore.swagger.io/v2/pet/5")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    ResponseBodyForDelete body = gson.fromJson(response.body().asString(), ResponseBodyForDelete.class);
    ResponseBodyForDelete json = gson.fromJson(new FileReader(new File("src/test/resources/jsonForDelete.json")), ResponseBodyForDelete.class);
    assertEquals(body, json);
    }

}
