import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Course {
  private int id;
  private String course_name;


  public int getId() {
    return id;
  }

  public String getName() {
    return course_name;
  }

  public Course(String course_name) {
    this.course_name = course_name;
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse){
    if(!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Courses(course_name) VALUES (:course_name)";
      this.id= (int) con.createQuery(sql, true).addParameter("course_name", this.course_name).executeUpdate().getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Courses WHERE id=:id";
      Course Course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return Course;
    }
  }

  public void update(String name) {
    this.course_name = course_name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_name = :name WHERE id= :id";
      con.createQuery(sql).addParameter("name", name).addParameter("id", id).executeUpdate();
    }
  }

  public void addStudent(Student student) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO students_courses (student_id, course_id) VALUES (:student_id, :course_id)";
    con.createQuery(sql)
      .addParameter("course_id", this.getId())
      .addParameter("student_id", student.getId())
      .executeUpdate();
    }
  }

  public List<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT students.* FROM courses JOIN students_courses ON (courses.id = students_courses.course_id) JOIN students ON (students_courses.student_id = students.id) WHERE courses.id = :course_id;";
        return con.createQuery(sql)
        .addParameter("course_id", this.getId())
        .executeAndFetch(Student.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();
      // "DELETE FROM students_courses WHERE student_id NOT IN (SELECT student_id FROM students)";
    }
  }
}
