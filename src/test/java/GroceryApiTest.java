import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GroceryApiTest {


    public static final String BASE_URL = "https://hepsiburada.free.beeceptor.com";

    @Before
    public void setUp(){
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void getAllGroceryTest()
    {
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/allGrocery");

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        Assert.assertEquals(HttpStatus.SC_OK, statusCode);

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray jsonArray = jsonResponse.getJSONArray("data");
        Assert.assertEquals(2,jsonArray.length());

    }

    @Test
    public void getGroceryByNameTest()
    {
        RequestSpecification request = RestAssured.given();
        Response response = request.get("/allGrocery"+"/apple");

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        Assert.assertEquals(HttpStatus.SC_OK, statusCode);

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray jsonArray = jsonResponse.getJSONArray("data");

        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Assert.assertEquals("apple",jsonObject.get("name"));

    }


    @Test
    public void addGroceryTest()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","4");
        jsonObject.put("name","orange");
        jsonObject.put("price",12.3);
        jsonObject.put("stock",3);

        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);
        request.body(jsonObject.toString());

        Response response = request.post("/add");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(HttpStatus.SC_OK, statusCode);
    }


}