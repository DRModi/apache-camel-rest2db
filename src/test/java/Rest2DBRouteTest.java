import com.apache.learncamel.routes.Rest2DBRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;

public class Rest2DBRouteTest extends CamelTestSupport {

    @Override
    public RouteBuilder createRouteBuilder(){

        return new Rest2DBRoute();
    }


    @Override
    public CamelContext createCamelContext(){

        String hostURL = "jdbc:postgresql://localhost:5432/localdb";
        DataSource dataSource = setupDataSource(hostURL);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);

        CamelContext context = new DefaultCamelContext(registry);

        return context;

    }

    private static DataSource setupDataSource(String hostURL) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(hostURL);
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");
        basicDataSource.setDriverClassName("org.postgresql.Driver");

        return basicDataSource;
    }

    @Test
    public void rest2dbTest(){

        ArrayList responseList = (ArrayList) consumer.receiveBody("direct:outputListSize");
        System.out.println("*** Response List Size : " + responseList.size());

        assertNotEquals(0,responseList);
    }
}
