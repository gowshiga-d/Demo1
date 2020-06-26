package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ExpenseAPIResources;
import resources.InputData;

public class StepDefinition {

	RequestSpecification addRequest;
	RequestSpecification deleteRequest;

	Response response;
	InputData data = new InputData();

	String transactionId;
	int dataCount;
	String transactionId_delete;
	String requestAPImethod;

	RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://logu-expenz-mern.herokuapp.com")
			.setContentType(ContentType.JSON).build();

	@Given("Add expense or Income with {string} and {string}")
	public void add_expense_or_Income_with_and(String transaction, String amount) {

		addRequest = given().log().all().spec(reqSpec).body(data.addTransactionPayload(transaction, amount));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String APIName, String method) {

		ExpenseAPIResources resourceAPI = ExpenseAPIResources.valueOf(APIName);
		if (method.equalsIgnoreCase("POST"))
			response = addRequest.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = given().spec(reqSpec).when().get(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("DELETE"))
			response = given().spec(reqSpec).when().delete(resourceAPI.getResource() + transactionId_delete);
		requestAPImethod = method;

	}

	@Then("it returns status code {int}")
	public void it_returns_status_code(Object statusCode) {

		assertEquals(response.getStatusCode(), statusCode);
		System.out.println(response.getStatusCode());

	}

	@Then("verify transaction created maps to {string} using {string}")
	public void verify_transaction_created_maps_to_using(String transactionName, String APIName) {

		JsonPath js = new JsonPath(response.asString());
		transactionId = js.getString("data._id");
		System.out.println(transactionId);

		user_calls_with_http_request(APIName, "GET");

		JsonPath js1 = new JsonPath(response.asString());
		dataCount = js1.getInt("data.size()");
		System.out.println(dataCount);

		for (int i = 0; i < dataCount; i++) {

			String responseId = js1.get("data[" + i + "]._id");
			if (responseId.equals(transactionId)) {

				String actualName = js1.get("data[" + i + "].text");
				System.out.println(actualName);
				assertEquals(actualName, transactionName);
				break;
			}
		}
	}

	@Given("Get transactions using {string} and delete expense or Income with {string}")
	public void get_transactions_using_and_delete_expense_or_Income_with(String APIName, String transactionName) {

		user_calls_with_http_request(APIName, "GET");
		JsonPath js2 = new JsonPath(response.asString());
		dataCount = js2.getInt("data.size()");
		System.out.println(dataCount);

		for (int i = 0; i < dataCount; i++) {

			String tempName = js2.get("data[" + i + "].text");
			if (tempName.equals(transactionName)) {

				transactionId_delete = js2.get("data[" + i + "]._id");
				System.out.println(transactionId_delete);
				break;
			}
		}
	}

	@Then("verify transaction is deleted with {string} message")
	public void verify_transaction_is_deleted_with_message(String expectedDeleteMessage) {

		JsonPath js3 = new JsonPath(response.asString());
		String deleteMessage = js3.get("data.message");
		System.out.println(deleteMessage);
		assertEquals(deleteMessage, expectedDeleteMessage);

	}

	@Then("verify the error message {string}")
	public void verify_the_error_message(String expectedErrorMessage) {

		String transactionErrorMessage = null;

		if (requestAPImethod.equalsIgnoreCase("POST")) {
			JsonPath js4 = new JsonPath(response.asString());
			transactionErrorMessage = js4.get("error[0]");
		}
		else if (requestAPImethod.equalsIgnoreCase("DELETE")) {
			JsonPath js5 = new JsonPath(response.asString());
			transactionErrorMessage = js5.get("error");
		}

		assertEquals(transactionErrorMessage, expectedErrorMessage);
		System.out.println(transactionErrorMessage);

	}

	@Given("Delete expense or Income with invalid transaction Id {string}")
	public void delete_expense_or_Income_with_invalid_transaction_Id(String invalidTransactionId) {

		transactionId_delete = invalidTransactionId;
	}

}
