package com.example.rentmyride;

import java.sql.Connection;
import java.sql.DriverManager;


public class database2 {

    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            // NOTE!! MAKE YOUR OWN DATABASE
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrentdb", "root", ""); // root is the default username while "" or empty is for the password
            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}