import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Student {

  private int id;
  private String name;
  private String enrollment;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEnrollment() {
    return enrollment;
  }


  public Student(String name, String enrollment) {
  this.name = name;
  this.enrollment = enrollment;
}

@Override
public boolean equals(Object otherStudent){
  if (!(otherStudent instanceof Student)) {
    return false;
  } else {
    Student newStudent = (Student) otherStudent;
    return this.getName().equals(newStudent.getName()) &&
           this.getId() == newStudent.getId() &&
           this.getEnrollment().equals(newStudent.getEnrollment());
  }
}

  public static List<Student> all() {
    String sql = "SELECT * FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(name, enrollment) VALUES (:name, :enrollment)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .addParameter("enrollment", enrollment)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students where id=:id";
      Student student = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Student.class);
      return student;
    }
  }

}
