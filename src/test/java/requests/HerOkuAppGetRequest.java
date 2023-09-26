package requests;

import baseUrl.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;
import testData.HerOkuAppTestData;

import static io.restassured.RestAssured.given;


public class HerOkuAppGetRequest extends HerOkuAppBaseUrl {
    @Test
    public void test(){
        HerOkuAppTestData expected=new HerOkuAppTestData();
        HerOkuAppPostRequest.test();
        String bookingId=HerOkuAppPostRequest.bookingId;
        System.out.println("bookingId = " + bookingId);
        spec.pathParams("bir","booking","iki",bookingId);
        Response response=given()
                .contentType(ContentType.JSON)
                .spec(spec)
                .when()
                .get("/{bir}/{iki}");
        response.prettyPrint();

        BookingPojo actual=response.as(BookingPojo.class);
        System.out.println("actual = " + actual);

        Assert.assertEquals(200,response.getStatusCode());
        if (actual.getTotalprice()>1)
            System.out.println("Pahalı Tatil");
        else
            System.out.println("Uygun Tatil");
    }


}
