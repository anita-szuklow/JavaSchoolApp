package schoolapp;

/**
 *
 * @author anita
 */
public class Teacher {
    int teacher_id;
    String first_name;
    String last_name;
    String authorisation;
    String login;
    
    public Teacher(int id, String firstName, String lastName, String authorisation, String login){
        this.teacher_id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.authorisation = authorisation;
        this.login = login;
    }
    public String getName(){
        String name = this.first_name + " " + this.last_name;
        return name;
    }
}
