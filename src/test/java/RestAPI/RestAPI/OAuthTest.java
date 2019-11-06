package RestAPI.RestAPI;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class OAuthTest {

	@Test
	public void basicAuthentication() {
		given().
			auth().
			preemptive().
			basic("postman", "password").
		when().
			get("https://postman-echo.com/basic-auth").
		then().
			assertThat().
			statusCode(200).
		and().
			contentType(ContentType.JSON).
		and().
			assertThat().
			body("authenticated",equalTo(true));			
	}
	
	
	
	
	@Test
	public void HandleOAuthRequest() {
		given().auth().oauth("RKCGzna7bv9YD57c", "D+EdQ-gs$-%@2Nu7", null, null).
		when().
		get("https://postman-echo.com/oauth1").
		then().
		assertThat().
		statusCode(200).
		and().
		body("status",equalTo("pass"));
		
			
	}
}
