package com.apache.learncamel.routes;

import com.apache.learncamel.processors.ExceptionProcessor;
import com.apache.learncamel.processors.InsertProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

public class Rest2DBExceptionHandle extends RouteBuilder {

    public void configure() throws Exception {


        onException(PSQLException.class, Exception.class)
                .handled(true)
                .log("Exception: Exception In Route")
                .process(new ExceptionProcessor());

        from("timer:startTimer?period=10s")
                .to("log:?level=INFO&showBody=true")
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .setHeader(Exchange.HTTP_URI,simple("https://restcountries.eu/rest/v2/alpha/invalidurl"))
                .to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                .process(new InsertProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from country_capital?dataSource=myDataSource")
                .to("direct:outputListFromDB");
    }
}
