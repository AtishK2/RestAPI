package RestAPI.RestAPI;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ReqResAPITests {
	private RequestSpecification httpRequest;
	private Response response;
	private JSONObject requestParams;

	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "https://reqres.in/";
		httpRequest = RestAssured.given();
		requestParams = new JSONObject();
	}

	@Test
	public void GetRequestWithPathParameter() {

		response = httpRequest.request(Method.GET, "/api/users/2");
		System.out.println("GetRequestWithPathParameter=>" + response.getBody().asString());
		int statusCode = response.getStatusCode();
		assertEquals(statusCode, 200, "StatusCode mismatch");
		String email = response.jsonPath().get("data.email");
		System.out.println(email);
		assertEquals(email, "janet.weaver@reqres.in");
	}

	@Test
	public void GetRequestWithQueryParameter() {
		requestParams.put("page", 2);
		response = httpRequest.request(Method.GET, "/api/users/");
		System.out.println("GetRequestWithQueryParameter => " + response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(statusCode, 200, "StatusCode mismatch");
	}

	@Test
	public void GetRequestWithPathParameter_SingleUserNotFound() {
		response = httpRequest.request(Method.GET, "/api/users/23");
		System.out.println("GetRequestWithPathParameter_SingleUserNotFound=> " + response.getBody().asString());
		assertEquals(response.getStatusCode(), 404);
	}

	@Test
	public void createUser_PostRequest() {
		requestParams.put("name", "Nikhil");
		requestParams.put("job", "Automation Tester");

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/api/users");
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode() + " " + response.getStatusLine());

		assertEquals(response.getStatusCode(), 201);
		assertNotNull(response.jsonPath().get("id"));
	}

//	@Test
//	public void getListOfResources() {
//		httpRequest.
//	}
	
	
	@AfterMethod
	public void tearDown() {
		httpRequest = null;
		response = null;
	}
}
