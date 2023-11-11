package de.turing85.mediatype;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path(Resource.HELLO_PATH)
@Getter
public class Resource {
  public static final String HELLO_PATH = "hello";
  public static final String HELLO_FOO_PATH = "foo";
  public static final String HTML_TEMPLATE = """
      <html>
        <head></head>
        <body>
          <h3>%s</h3>
        </body>
      </html>""";

  private final String hello;
  private final String foo;

  public Resource(@ConfigProperty(name = "application.response.hello") String hello,
      @ConfigProperty(name = "application.response.foo") String foo) {
    this.hello = hello;
    this.foo = foo;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<Response> getHelloText() {
    // @formatter:off
    return getHelloUni()
        .onItem().transform(Response::ok).onItem()
        .transform(Response.ResponseBuilder::build);
    // @formatter:on
  }

  private Uni<String> getHelloUni() {
    return Uni.createFrom().item(getHello());
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public Uni<Response> getHelloHtml() {
    // @formatter:off
    return getHelloUni()
        .onItem().transform(HTML_TEMPLATE::formatted)
        .onItem().transform(Response::ok).onItem()
        .transform(Response.ResponseBuilder::build);
    // @formatter:on
  }

  private Uni<String> getFooUni() {
    return Uni.createFrom().item(getFoo());
  }

  @GET
  @Path(HELLO_FOO_PATH)
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<Response> getFooText() {
    // @formatter:off
    return getFooUni()
        .onItem().transform(Response::ok).onItem()
        .transform(Response.ResponseBuilder::build);
    // @formatter:on
  }

  @GET
  @Path(HELLO_FOO_PATH)
  @Produces(MediaType.TEXT_HTML)
  public Uni<Response> getFooHtml() {
    // @formatter:off
    return getFooUni()
        .onItem().transform(HTML_TEMPLATE::formatted)
        .onItem().transform(Response::ok).onItem()
        .transform(Response.ResponseBuilder::build);
    // @formatter:on
  }
}
