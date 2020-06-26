package expenseAPI;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AllAPIs {

	public static void main(String[] args) {
		
//		RestAssured.baseURI = "https://logu-expenz-mern.herokuapp.com";
//		Response addresponse = given().log().all().header("Content-type","application/json").body("{\r\n" + 
//				"	\"amount\": -400,\r\n" + 
//				"	\"text\": \"Rent2\"\r\n" + 
//				"}")
//		.when().post("/api/v1/transactions/")
//		.then().log().all().assertThat().statusCode(201).extract().response();
//		
//		JsonPath js = new JsonPath(addresponse.asString());
//		String transactionId = js.getString("data._id");
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://logu-expenz-mern.herokuapp.com")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(201).build();
		
		RequestSpecification addrequest = given().log().all().spec(req).body("{\r\n" + 
				"	\"amount\": -400,\r\n" + 
				"	\"text\": \"Rentx\"\r\n" + 
				"}");
		
		Response response = addrequest.when().post("/api/v1/transactions/").then().spec(resp).extract().response();
				
				
	System.out.println(response.getStatusCode());								
//	Response response2 = response.then().log().all().assertThat().statusCode(201).extract().response();

		JsonPath js1 = new JsonPath(response.asString());
		String transactionId = js1.getString("data._id");
		System.out.println(transactionId);
//		given().log().all()
//		.when().get("/api/v1/transactions/")
//		.then().log().all().assertThat().statusCode(200);
		
//		given().log().all()
//		.when().delete("/api/v1/transactions/"+transactionId)
//		.then().log().all().assertThat().statusCode(200);
		
	}

	

}
