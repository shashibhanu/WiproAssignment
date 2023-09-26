package apiTestPackage;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.PostRandomData;
public class ApiTests {
	public static HashMap regData = new HashMap();
	public static HashMap loginData = new HashMap();
	public static HashMap putData = new HashMap();
	public static String token = null;
	public static int userId = 0;
	@BeforeClass
	void postData()
	{
		regData.put("name",PostRandomData.getName());
		regData.put("email",PostRandomData.getEmail());
		regData.put("Password",PostRandomData.getPassword());		


		RestAssured.baseURI="http://restapi.adequateshop.com";
		RestAssured.basePath="/api";
		
	}
	  
	  @Test(priority=1)
	  public void testRegistration () {
	    
			given()
			.contentType("application/json")
			.body(regData)
			.when()
			.post("/authaccount/registration")
			.then()
			.statusCode(200)
			.body("message", equalTo("success"))
			.log().body()
			.and()
			.body("data.Name", equalTo(regData.get("name")))
			.and()
			.body("data.Email", equalTo(regData.get("email")))
			.header("Content-Type", equalTo("application/json; charset=utf-8"));
	  }
	  
	  @Test(priority=2)
	  public void testLogin () {
		  
			loginData.put("email",regData.get("email"));
			loginData.put("Password",regData.get("Password"));
			
			Response resp = 
			given()
			.contentType("application/json")
			.body(loginData)
			.when()
			.post("/authaccount/login");
			
			resp.then()
			.log().body()
			.statusCode(200)
			.body("message", equalTo("success"))
			.and()
			.body("data.Name", equalTo(regData.get("name")))
			.and()
			.body("data.Email", equalTo(loginData.get("email")))
			.header("Content-Type", equalTo("application/json; charset=utf-8"));
		    
			//JsonPath jsonPath = resp.jsonPath();
			//or
			JsonPath jsonPath = new JsonPath(resp.getBody().asString());

			// Then simply query the JsonPath object to get a String value of the node
			
			token = jsonPath.get("data.Token");
			//token = resp.getBody("data.Token");
			//this can't be done as body() method doesn't accept any parameter
			
			// Print the token to see what we got
			System.out.println("token id is: " + token);
	  }
	  
	  @Test(priority=3)
	  public void testGetUsers () {
			
			Response resp = 
			given()
			.contentType("application/json")
			.header("Authorization","Bearer "+token)
			.when()
			.get("users?page=1");
			
			resp.then()
			.log().body()
			.statusCode(200)
			.header("Content-Type", equalTo("application/json; charset=utf-8"));
		    
			JsonPath jsonPathEvaluator = resp.jsonPath();

			// Then simply query the JsonPath object to get a String value of the node
			
			userId = jsonPathEvaluator.get("data[0].id");

			// Print the userid to see what we got
			System.out.println("user id is: " + userId);
	  }
	  
	  @Test(priority=4)
	  public void testGetUserDetails () {
			
			Response resp = 
			given()
			.contentType("application/json")
			.header("Authorization","Bearer "+token)
			.when()
			.get("users?id="+userId);
			
			resp.then()
			.log().body()
			.statusCode(200)
			.header("Content-Type", equalTo("application/json; charset=utf-8"));
	  }
	  
	  @Test(priority=5)
	  public void testUpdateUserDetails () {
		  putData.put("id",userId);
		  putData.put("name",regData.get("name"));
		  putData.put("email",regData.get("email"));
		  
			
			Response resp = 
			given()
			.contentType("application/json")
			.header("Authorization","Bearer "+token)
			.body(putData)
			.when()
			.put("users?id="+userId);
			
			resp.then()
			.log().body()
			.statusCode(405)
			.statusLine("HTTP/1.1 405 Method Not Allowed");
	  }
	  
	  @Test(priority=6)
	  public void testDeleteUserDetails () {			
			Response resp = 
			given()
			.contentType("application/json")
			.header("Authorization","Bearer "+token)
			.when()
			.delete("users?id="+userId);
			
			resp.then()
			.log().body()
			.statusCode(405)
			.statusLine("HTTP/1.1 405 Method Not Allowed");
	  }

}
