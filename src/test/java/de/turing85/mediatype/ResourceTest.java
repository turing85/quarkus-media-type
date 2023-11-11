package de.turing85.mediatype;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
@DisplayName("Resources /hello")
class ResourceTest {
  @Nested
  @TestHTTPEndpoint(Resource.class)
  @DisplayName("/hello GET")
  class GetRootTest {
    private static final String EXPECTED = "hello";

    @Test
    @DisplayName("TEXT_PLAIN")
    void testGetPlainText() {
      // @formatter:off
      RestAssured
          .given().header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN)
          .when().get()
          .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .header(HttpHeaders.CONTENT_TYPE, startsWith(MediaType.TEXT_PLAIN))
              .body(is(EXPECTED));
      // @formatter:on
    }

    @Test
    @DisplayName("TEXT_HTML")
    void testGetHtml() {
      // @formatter:off
      RestAssured
          .given().header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
          .when().get()
          .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .header(HttpHeaders.CONTENT_TYPE, startsWith(MediaType.TEXT_HTML))
              .body(is("""
                  <html>
                    <head></head>
                    <body>
                      <h3>%s</h3>
                    </body>
                  </html>""".formatted(EXPECTED)));
      // @formatter:on
    }
  }

  @Nested
  @TestHTTPEndpoint(Resource.class)
  @DisplayName("/hello/foo GET")
  class GetFooTest {
    private static final String EXPECTED = "foo";

    @Test
    @DisplayName("TEXT_PLAIN")
    void testGetPlainText() {
      // @formatter:off
      RestAssured
          .given().header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN)
          .when().get("/foo")
          .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .header(HttpHeaders.CONTENT_TYPE, startsWith(MediaType.TEXT_PLAIN))
              .body(is(EXPECTED));
      // @formatter:on
    }

    @Test
    @DisplayName("TEXT_HTML")
    void testGetHtml() {
      // @formatter:off
      RestAssured
          .given().header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
          .when().get("/foo")
          .then()
              .statusCode(Response.Status.OK.getStatusCode())
              .header(HttpHeaders.CONTENT_TYPE, startsWith(MediaType.TEXT_HTML))
              .body(is("""
                  <html>
                    <head></head>
                    <body>
                      <h3>%s</h3>
                    </body>
                  </html>""".formatted(EXPECTED)));
      // @formatter:on
    }
  }
}
