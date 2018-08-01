package com.apache.learncamel.processors;

import org.apache.camel.Exchange;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class InsertAppDBProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String inputMessage = exchange.getIn().getBody(String.class);
        System.out.println("InsertAppProcessor: Received input message is : "+inputMessage);

        //Create JSONParser object and Parse the JSON response
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(inputMessage);

        //Convert object to JSON object
        JSONObject jsonObject = (JSONObject) obj;

        //Retrieve the Country and Capital from JSON response
        String countryName = (String) jsonObject.get("name");
        String capitalName = (String) jsonObject.get("capital");

        //Form/prepared the insert query
        String insertQuery = "INSERT INTO country_capital values ('"+countryName+"','"+capitalName+"')";
        System.out.println("InsertAppProcessor: Prepared Insert Query is : "+insertQuery);


        //Set the insert query back to the exchange body
        exchange.getIn().setBody(insertQuery);





    }
}
