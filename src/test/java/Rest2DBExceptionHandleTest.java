import com.apache.learncamel.routes.Rest2DBExceptionHandle;
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

public class Rest2DBExceptionHandleTest extends CamelTestSupport {

    @Override
    public RouteBuilder createRouteBuilder(){
        return new Rest2DBExceptionHandle();
    }

    @Override
    public CamelContext createCamelContext(){
        String dbHostUrl="jdbc:postgresql://localhost:5432/localdb";
        DataSource dataSource = setupDataSource(dbHostUrl);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);

        CamelContext context = new DefaultCamelContext(registry);

        return context;
    }

    private static DataSource setupDataSource(String dbHostUrl) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbHostUrl);
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");

        return basicDataSource;
    }

    @Test
    public void exceptionRouteTest(){

        ArrayList responseList = (ArrayList) consumer.receiveBody("timer:startTimer");
        //System.out.println("Test: ResponseList size : "+responseList.size());
        //assertEquals(5,responseList.size());

        assertNull(responseList);
    }


}
