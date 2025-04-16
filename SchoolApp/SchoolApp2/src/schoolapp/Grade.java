/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolapp;

import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author anita
 */
public class Grade {
    int student_id;
    int teacher_id;
    int course;
    float grade;
    String description;
    LocalDate date;
    
    public Grade(int student_id, int teacher_id, int course, float grade, String description, LocalDate date){
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.course = course;
        this.grade = grade;
        this.description = description;
        this.date = date;
    }
    public Grade(int student_id, int teacher_id, int course, float grade, String description){
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.course = course;
        this.grade = grade;
        this.description = description;
        this.date = LocalDate.now();
    }
}
