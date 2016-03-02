import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  //Integration testing
  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to the Enrollment Center");
  }

  @Test
    public void courseIsCreatedTest() {
      goTo("http://localhost:4567/");
      Course myCourse = new Course("eco101");
      myCourse.save();

      goTo("http://localhost:4567/courses");
      assertThat(pageSource()).contains("eco101");
  }

  @Test
  public void courseIsDisplayedTest() {
    Course myCourse = new Course("eco101");
    myCourse.save();
    String categoryPath = String.format("http://localhost:4567/courses/%d", myCourse.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("eco101");
  }

  @Test
  public void allStudentsNameOnCoursePage() {
    Course myCourse = new Course("eco101");
    myCourse.save();
    Student firstStudent = new Student("Jimmy", "01.01.2016");
    firstStudent.save();
    Student secondStudent = new Student("Jim Bob", "01.01.2016");
    secondStudent.save();
    String coursePath = String.format("http://localhost:4567/courses/%d", myCourse.getId());
    goTo(coursePath);
    assertThat(pageSource()).contains("Jimmy");
    assertThat(pageSource()).contains("Jim Bob");
  }
}
