package requests;

import baseUrl.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojos.BookingDatesPojo;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;

import static io.restassured.RestAssured.given;

public class HerOkuAppPostRequest extends HerOkuAppBaseUrl {
    public static BookingResponsePojo actualData;

    /*
          {
         "bookingid": 11,
                "booking": {
                        "firstname": "Ali",
                        "lastname": "Can",
                        "totalprice": 500,
                        "depositpaid": true,
                        "bookingdates": {
                                        "checkin": "2022-03-01",
                                        "checkout": "2022-03-11"
                                  }
                              }
                          }
         */
    static String bookingId;

    @Test
    public static void test() {
        spec.pathParam("bir", "booking");

        //2) EXPECTED DATA OLUSTUR
        BookingDatesPojo bokingDates = new BookingDatesPojo("2022-03-01", "2022-03-11");
        System.out.println("bokingDates = " + bokingDates);

        BookingPojo bookingPojo = new BookingPojo("Ali", "Can", 500, true, bokingDates);
        System.out.println("bookingPojo = " + bookingPojo);

        //3) RESPONSE AND REQUEST
        Response response = given().contentType(ContentType.JSON)
                .spec(spec)
                .body(bookingPojo)
                .when()
                .post("/{bir}");
        response.prettyPrint();

        //4) DOĞRULAMA
        actualData = response.as(BookingResponsePojo.class);
        System.out.println("actualData = " + actualData);

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(bookingPojo.getFirstname(), actualData.getBooking().getFirstname());
        Assert.assertEquals(bookingPojo.getLastname(), actualData.getBooking().getLastname());
        Assert.assertEquals(bookingPojo.getTotalprice(), actualData.getBooking().getTotalprice());
        Assert.assertEquals(bookingPojo.isDepositpaid(), actualData.getBooking().isDepositpaid());

        Assert.assertEquals(bookingPojo.getBookingdates().getCheckin(),
                actualData.getBooking().getBookingdates().getCheckin());

        Assert.assertEquals(bookingPojo.getBookingdates().getCheckout(),
                actualData.getBooking().getBookingdates().getCheckout());
        bookingId=response.prettyPrint().substring(18,23).replaceAll(" ","");
        Assert.assertNotNull(bookingId);
        System.out.println("bookingId = " + bookingId);

    }
}
