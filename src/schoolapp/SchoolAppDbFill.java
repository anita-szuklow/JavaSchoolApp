package schoolapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author anita
 */
public class SchoolAppDbFill {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String url;
    Connection con;
    String command;
    Statement stmt;

        try{
            url="jdbc:mysql://127.0.01/schoolappdb";
            con = DriverManager.getConnection(url, "root", "");
            
            stmt = con.createStatement(); //wypelniam tebele poczatkowymi danymi
            
//            stmt.executeUpdate("INSERT INTO TEACHERS values(1, 'Anna', 'Smith', 'Teacher', 'asmith')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(2, 'John', 'Smith', 'Teacher', 'jsmith')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(3, 'Pamela', 'Anderson', 'Teacher', 'panderson')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(4, 'Stephen', 'Hawking', 'Teacher', 'shawking')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(5, 'Albert', 'Einstein', 'Teacher', 'aeinstein')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(6, 'Pablo', 'Picasso', 'Teacher', 'ppicasso')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(7, 'Marco', 'Polo', 'Teacher', 'mpolo')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(8, 'Lionel', 'Messi', 'Teacher', 'lmessi')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(9, 'Adam', 'Malysz', 'Teacher', 'amalysz')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(10, 'Charles', 'Darwin', 'Teacher', 'cdarwin')");
//            stmt.executeUpdate("INSERT INTO TEACHERS values(11, 'Sofia', 'Vergara', 'Teacher', 'svergara')");
//                       
//              stmt.executeUpdate("INSERT INTO COURSES values(1, 'English language', 1, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(2, 'English language', 1, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(3, 'English language', 3, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(4, 'Spanish language', 11, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(5, 'Spanish language', 11, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(6, 'Spanish language', 11, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(7, 'PE', 8, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(8, 'PE', 8, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(9, 'PE', 9, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(10, 'Biology', 10, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(11, 'Biology', 10, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(12, 'Biology', 10, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(13, 'Geography', 7, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(14, 'Geography', 7, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(15, 'Geography', 7, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(16, 'Arts', 6, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(17, 'Arts', 6, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(18, 'Arts', 6, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(19, 'Mathematics', 5, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(20, 'Mathematics', 5, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(21, 'Mathematics', 2, 3)");
//              stmt.executeUpdate("INSERT INTO COURSES values(22, 'Physics', 4, 1)");
//              stmt.executeUpdate("INSERT INTO COURSES values(23, 'Physics', 4, 2)");
//              stmt.executeUpdate("INSERT INTO COURSES values(24, 'Physics', 4, 3)");
//
//            stmt.executeUpdate("INSERT INTO CLASSES values(1, 'IA', 3)");
//            stmt.executeUpdate("INSERT INTO CLASSES values(2, 'IIA', 8)");
//            stmt.executeUpdate("INSERT INTO CLASSES values(3, 'IIB', 5)");
//
//              stmt.executeUpdate("INSERT INTO STUDENTS values(1, 'Anna', 'Abercrombie', 1, 'aabercrombie')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(2, 'Ben', 'Bumblebee', 1, 'bbumblebee')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(3, 'Claire', 'Cunning', 1, 'ccunning')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(4, 'David', 'Donovan', 1, 'ddonovan')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(5, 'Ellen', 'Epson', 1, 'eepson')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(6, 'Fred', 'Fontaine', 1, 'ffontaine')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(7, 'Gregory', 'Gillock', 1, 'ggillock')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(8, 'Hillary', 'Hammock', 2, 'hhammock')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(9, 'Ingrid', 'Ibsen', 2, 'iibsen')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(10, 'Jasper', 'Juul', 2, 'jjuul')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(11, 'Ken', 'Kellog', 2, 'kkellog')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(12, 'Monica', 'Martens', 2, 'mmartens')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(13, 'Natalie', 'Nomadini', 2, 'nnomadini')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(14, 'Omar', 'Obama', 2, 'oobama')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(15, 'Patrick', 'Paddington', 2, 'ppaddington')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(16, 'Rose', 'Remington', 3, 'rremington')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(17, 'Sandra', 'Sanjibh', 3, 'ssanjibh')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(18, 'Tobias', 'Thompson', 3, 'tthompson')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(19, 'Ursula', 'Uganda', 3, 'uuganda')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(20, 'Victor', 'VanPelt', 3, 'vvanpelt')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(21, 'William', 'Wonderfurst', 3, 'wwonderfurst')");
//              stmt.executeUpdate("INSERT INTO STUDENTS values(22, 'Zed', 'Zelman', 3, 'zzelman')");
//    
//                stmt.executeUpdate("INSERT INTO GRADES values(1, 1, 1, 1, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(2, 1, 1, 2, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(3, 1, 1, 3, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(4, 1, 1, 4, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(5, 1, 1, 3.5, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(6, 1, 1, 2.5, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(7, 1, 1, 1, 'Spelling test', '2024-02-06')");
//                stmt.executeUpdate("INSERT INTO GRADES values(1, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(2, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(3, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(4, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(5, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(6, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(7, 5, 19, 2, 'Multiplying to 30', '2024-02-15')");
//                stmt.executeUpdate("INSERT INTO GRADES values(8, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(9, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(10, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(11, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(12, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(13, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(14, 6, 17, 2, 'Aquarelle: My Dream Holidays', '2024-01-22')");
//                stmt.executeUpdate("INSERT INTO GRADES values(15, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(16, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(17, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(18, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(19, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(20, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(21, 7, 15, 2, 'Reading the map', '2024-03-01')");
//                stmt.executeUpdate("INSERT INTO GRADES values(22, 7, 15, 2, 'Reading the map', '2024-03-01')");
//
//            stmt.executeUpdate("INSERT INTO LOGIN values('asmith', '1', 'teacher')");
//            stmt.executeUpdate("INSERT INTO LOGIN values('aabercrombie', '1', 'student')");
//            stmt.executeUpdate("INSERT INTO LOGIN values('aszuklow', 'pass123', 'admin')");
//            stmt.executeUpdate("UPDATE `teachers` SET `LOGIN`='asmith' WHERE 'teacher_id'=1");
//              stmt.executeUpdate("INSERT INTO LOGIN values('jsmith', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('panderson', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('shawking', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('aeinstein', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('ppicasso', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('mpolo', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('lmessi', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('amalysz', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('cdarwin', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('svergara', '1', 'teacher')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('bbumblebee', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('ccunning', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('ddonovan', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('eepson', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('ffontaine', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('zzelman', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('wwonderfurst', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('vvanpelt', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('uuganda', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('tthompson', '1', 'student')");
//              stmt.executeUpdate("INSERT INTO LOGIN values('ssanjibh', '1', 'student')");

            stmt.close();
            con.close();
        }
        catch(SQLException ex){
            System.err.println("SQL exception: " + ex.getMessage());
            
        }
    }
}
