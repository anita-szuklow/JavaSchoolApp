/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolapp;

/**
 *
 * @author anita
 */
public class Course {
    int course_id;
    String name;
    int teacher_id;
    int class_id;
    
    public Course(int id, String name, int teacher, int class_id){
        this.course_id = id;
        this.name = name;
        this.teacher_id = teacher;
        this.class_id = class_id;
    }
 }
