import com.apache.learncamel.routes.RestCallRoute;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RestCallRouteTest extends CamelTestSupport {

    @Override
    public RouteBuilder createRouteBuilder(){
        return new RestCallRoute();
    }

    @Test
    public void RestCall_India(){

        String jsonOutput = template.requestBody("direct:restCallInitiate","in", String.class);
        System.out.println("Received Output Message : "+jsonOutput);

        assertTrue(jsonOutput.contains("\"alpha3Code\":\"IND\""));
    }


}
