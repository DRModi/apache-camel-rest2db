package com.apache.learncamel.routes;

import com.apache.learncamel.processors.InsertProcessor;
import org.apache.camel.Route;
import org.apache.camel.builder.RouteBuilder;

public class DBPostgresRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("direct:dbInputMessage")
                .to("log:?level=INFO&showBody=true")
                .process(new InsertProcessor())
                .log("After Processor: Body is :  ${body}")
                .to("jdbc:myDataSource")
                .to("sql:select * from country_capital?dataSource=myDataSource")
                .to("log:?level=INFO&showBody=true");

    }
}
