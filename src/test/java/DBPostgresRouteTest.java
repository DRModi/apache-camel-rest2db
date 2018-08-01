import com.apache.learncamel.routes.DBPostgresRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;

public class DBPostgresRouteTest extends CamelTestSupport {

    @Override
    public RouteBuilder createRouteBuilder(){
        return new DBPostgresRoute();
    }


    @Override
    public CamelContext createCamelContext(){

        String dbhostUrl = "jdbc:postgresql://localhost:5432/localdb";
        DataSource dataSource =  setupDataSource(dbhostUrl);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);

        CamelContext context = new DefaultCamelContext(registry);

        return context;
    }

    private static DataSource setupDataSource(String dbhostUrl) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbhostUrl);
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");

        return basicDataSource;
    }

    @Test
    public void insertCountryCapitalInDB(){

        String inputMessageString = " {\"name\":\"India\",\"topLevelDomain\":[\".in\"],\"alpha2Code\":\"IN\"," +
                "\"alpha3Code\":\"IND\",\"callingCodes\":[\"91\"],\"capital\":\"New Delhi\",\"altSpellings\":[\"IN\"" +
                ",\"Bhārat\",\"Republic of India\",\"Bharat Ganrajya\"],\"region\":\"Asia\",\"subregion\":\"Southern Asia\"," +
                "\"population\":1295210000,\"latlng\":[20.0,77.0],\"demonym\":\"Indian\",\"area\":3287590.0,\"gini\":33.4," +
                "\"timezones\":[\"UTC+05:30\"],\"borders\":[\"AFG\",\"BGD\",\"BTN\",\"MMR\",\"CHN\",\"NPL\",\"PAK\",\"LKA\"]," +
                "\"nativeName\":\"भारत\",\"numericCode\":\"356\",\"currencies\":[{\"code\":\"INR\",\"name\":\"Indian rupee\"," +
                "\"symbol\":\"₹\"}],\"languages\":[{\"iso639_1\":\"hi\",\"iso639_2\":\"hin\",\"name\":\"Hindi\",\"nativeName\":\"हिन्दी\"}," +
                "{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Indien\"," +
                "\"es\":\"India\",\"fr\":\"Inde\",\"ja\":\"インド\",\"it\":\"India\",\"br\":\"Índia\",\"pt\":\"Índia\",\"nl\":\"India\",\"hr\":\"Indija\",\"fa\":\"هند\"}," +
                "\"flag\":\"https://restcountries.eu/data/ind.svg\",\"regionalBlocs\":[{\"acronym\":\"SAARC\",\"name\":\"South Asian Association for Regional Cooperation\",\"otherAcronyms\":[]," +
                "\"otherNames\":[]}],\"cioc\":\"IND\"}";

        ArrayList responseList = template.requestBody("direct:dbInputMessage",inputMessageString, ArrayList.class);

        assertNotEquals(0,responseList.size());
    }
}
