import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ClassTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Category.all().size(), 0);
  }
}
