package com.mycompany.schoolapp;
import java.sql.*;
/**
 *
 * @author anita
 */
public class SchoolApp {
    
    public static void main(String[] args) {
        String url;
    Connection con;
    String command;
    Statement stmt;

        try{
            url="jdbc:mysql//127.0.01/schoolappdb";
            con = DriverManager.getConnection(url, "root", "");
            
            stmt = con.createStatement();
            
            command =
                "create table GRADES "
                + "(STUDENT_ID int, "
                + "TEACHER_ID int, "
                + "COURSE int,"
                + "GRADE float, "
                + "DESCRIPTION varchar(64),"
                + "DATE date)";
            stmt.executeUpdate(command);
            stmt.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println("SQL exception: " + ex.getMessage());
            
        }
    }    
}
