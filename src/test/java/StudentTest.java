import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Student firstStudent = new Student("Jimmy", "01.01.2016");
    Student secondStudent = new Student("Jimmy", "01.01.2016");
    assertTrue(firstStudent.equals(secondStudent));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertTrue(savedStudent.equals(myStudent));
  }

  @Test
  public void save_assignsIdToObject() {
    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();
    Student savedStudent = Student.all().get(0);
    assertEquals(myStudent.getId(), savedStudent.getId());
  }
  @Test
  public void find_findsStudentInDatabase_true() {
    Student myStudent = new Student("Jimmy", "01.01.2016");
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }
}
