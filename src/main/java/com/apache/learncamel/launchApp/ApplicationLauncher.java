package com.apache.learncamel.launchApp;

import com.apache.learncamel.routes.Rest2DBAppRoute;
import org.apache.camel.main.Main;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ApplicationLauncher {

    public static void main(String[] args){

        //create apache.camel.main
        Main main = new Main();

        //database host url
        String dbHostURL = "jdbc:postgresql://localhost:5432/appdb";

        //Using bind method, populate/map registry to camel context
        main.bind("myAppDataSource", setupDataSource(dbHostURL));

        //add route to main
        //main.addRouteBuilder(new Rest2DBAppRoute());

        main.addRouteBuilder(new Rest2DBAppRoute());

        System.out.println("Main Class: Starting Camel Rest to DB Route!!");



        try {
            //running the main method
            main.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataSource setupDataSource(String datasourceUrl) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(datasourceUrl);
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }
}
