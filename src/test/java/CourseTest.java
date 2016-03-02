import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCourseesAreEqual() {
    Course firstCourse = new Course("eco101");
    Course secondCourse = new Course("eco101");
    assertTrue(firstCourse.equals(secondCourse));
  }

  @Test
  public void save_savesIntoDatabase() {
    Course myCourse = new Course("eco101");
    myCourse.save();
    assertTrue(Course.all().get(0).equals(myCourse));
  }

  @Test
  public void find_findCourseInDatabase_true() {
    Course myCourse = new Course("eco101");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course myCourse = new Course("eco101");
    myCourse.save();

    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();

    myCourse.addStudent(myStudent);
    Student savedStudent = myCourse.getStudents().get(0);
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Course myCourse = new Course("eco101");
    myCourse.save();

    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();

    myCourse.addStudent(myStudent);
    List savedStudents = myCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void delete_deletesAllTasksAndListsAssoicationes() {
    Course myCourse = new Course("eco101");
    myCourse.save();

    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();

    myCourse.addStudent(myStudent);
    myCourse.delete();
    assertEquals(myStudent.getCourses().size(), 0);
  }
}
