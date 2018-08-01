package com.apache.learncamel.routes;

import com.apache.learncamel.processors.InsertProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class Rest2DBRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("timer:useTimer?period=5s")
                .to("log:?level=INFO&showBody=true")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI, simple("https://restcountries.eu/rest/v2/alpha/in"))
                .to("https://restcountries.eu/rest/v2/alpha/in").convertBodyTo(String.class)
                .log("******* Before Calling DB Routes : ")
                .to("log:?level=INFO&showBody=true")
                .process(new InsertProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from country_capital?dataSource=myDataSource")
                .to("direct:outputListSize");
    }
}
