/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolapp;

import java.time.LocalDate;

/**
 *
 * @author anita
 */
public class Project {
    int project_id;
    String name;
    String description;
    int teacher_id;
    int class_id;
    LocalDate date;
    
    public Project(int project_id, String name, String description, int teacher_id, int clas_id, LocalDate date){
        this.project_id = project_id;
        this.name = name;
        this.description = description;
        this.teacher_id = teacher_id;
        this.class_id = class_id;
        this.date = date;
    }
    public Project(int project_id, String name, String description, int teacher_id, int clas_id){
        this.project_id = project_id;
        this.name = name;
        this.description = description;
        this.teacher_id = teacher_id;
        this.class_id = class_id;
        this.date = LocalDate.now();
    }
}
