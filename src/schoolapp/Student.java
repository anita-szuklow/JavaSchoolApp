/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolapp;

/**
 *
 * @author anita
 */
public class Student {
    int student_id;
    String first_name;
    String last_name;
    int class_id;
    String login;
    
    public Student(int id, String firstName, String lastName, int classId, String login){
        this.student_id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.class_id = classId;
        this.login = login;
    }
    public String getName(){
        String name = this.first_name + " " + this.last_name;
        return name;
    }
}
