package com.apache.learncamel.processors;

import org.apache.camel.Exchange;

public class ExceptionProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        Exception ex = exchange.getProperty(exchange.EXCEPTION_CAUGHT, Exception.class);
        System.out.println("Exception Processor: Actual Exception Message : "+ex.getMessage());
        System.out.println("Exception Processor: Actual Exception Class: "+ex.getClass());

        String failureEndPoint = exchange.getProperty(exchange.FAILURE_ENDPOINT, String.class);
        System.out.println("Exception Processor: Failure Endpoint : "+failureEndPoint);

        exchange.getIn().setBody("Exception Processor : Received Exception in route!!");
    }
}
