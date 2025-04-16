package schoolapp;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author anita
 */
public class SchoolAppDbCreate {
    
    public static void main(String[] args) {
    String url;
    Connection con;
    String command;
    Statement stmt;

        try{
            url="jdbc:mysql://127.0.01/schoolappdb";
            con = DriverManager.getConnection(url, "root", "");
            
            stmt = con.createStatement();
            
//            command = //tworzenie tabeli ocen
//                "create table GRADES "
//                + "(STUDENT_ID int, "
//                + "TEACHER_ID int, "
//                + "COURSE int,"
//                + "GRADE float, "
//                + "DESCRIPTION varchar(64),"
//                + "DATE date)";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli uczniow
//                "create table STUDENTS "
//                + "(STUDENT_ID int, "
//                + "FIRT_NAME varchar(32), "
//                + "LAST_NAME varchar(32), "
//                + "CLASS_ID int)";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli nauczycieli
//                "create table TEACHERS "
//                + "(TEACHER_ID int, "
//                + "FIRT_NAME varchar(32), "
//                + "LAST_NAME varchar(32), "
//                + "AUTHORISATION varchar(32))";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli klas
//                "create table CLASSES "
//                + "(CLASS_ID int, "
//                + "NAME varchar(32), "
//                + "LEAD_TEACHER_ID int)";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli przedmiotow
//                "create table COURSES "
//                + "(COURSE_ID int, "
//                + "NAME varchar(32), "
//                + "TEACHER_ID int, "
//                + "CLASS_ID int) ";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli wiadomosci do uczniow
//                "create table STUDENT_INBOX "
//                + "(STUDENT_ID int, "
//                + "TEACHER_ID int, "
//                + "SUBJECT varchar(64), "
//                + "CONTENT varchar(1000), "
//                + "DATE date)";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli wiadomosci do nauczycieli
//                "create table TEACHER_INBOX "
//                + "(TEACHER_ID int, "
//                + "STUDENT_ID int, "
//                + "SUBJECT varchar(64), "
//                + "CONTENT varchar(1000), "
//                + "DATE date)";
//            stmt.executeUpdate(command);
//            
//            command = //tworzenie tabeli projektow
//                "create table PROJECTS "
//                + "(PROJECT_ID int, "
//                + "NAME varchar(32), "
//                + "DESCRIPTION varchar(128), "
//                + "TEACHER_ID int, "
//                + "CLASS_ID int,"
//                + "DATE date)";
//            stmt.executeUpdate(command);
//
//                command = 
//                "ALTER TABLE `courses` ADD PRIMARY KEY(`COURSE_ID`); ";
//            stmt.executeUpdate(command);
//            
//            command = 
//                "ALTER TABLE `projects` ADD PRIMARY KEY(`PROJECT_ID`); ";
//            stmt.executeUpdate(command);
//            
//            command = 
//                "ALTER TABLE `classes` ADD PRIMARY KEY(`CLASS_ID`); ";
//            stmt.executeUpdate(command);
//            
//            command = 
//                "ALTER TABLE `students` ADD PRIMARY KEY(`STUDENT_ID`); ";
//            stmt.executeUpdate(command);
//            
//            command = 
//                "ALTER TABLE `teachers` ADD PRIMARY KEY(`TEACHER_ID`); ";
//            stmt.executeUpdate(command);
//            
//            command = 
//                "create table LOGIN "
//                + "(LOGIN_STRING varchar(32), "
//                + "PASSWORD varchar(32), "
//                + "ROLE varchar(32))";
            
//              command = 
//                "ALTER TABLE `teachers` ADD COLUMN(`LOGIN`); ";
//                stmt.executeUpdate(command);
//
//            stmt.executeUpdate(command);
            
            stmt.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println("SQL exception: " + ex.getMessage());
            
        }
    }    
}

