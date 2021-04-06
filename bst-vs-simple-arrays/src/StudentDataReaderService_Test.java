import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StudentDataReader Tests")
public class StudentDataReaderService_Test {
  static class StubbedFileSystemService extends FileSystemService {
    final InputStream okListInputStream;

    public StubbedFileSystemService(InputStream okListInputStream) {
      this.okListInputStream = okListInputStream;
    }

    @Override
    InputStream openOkListStudentsFile() throws IOException {
      return okListInputStream;
    }
  }

  @AfterEach
  public void afterEach() {
    Locator.instance.reset();
  }

  @Test
  public void shouldReadInStudentDataTillANewline() {
    setOkListFileContents("abc def ghi\n");
    final StudentDataReaderService instance = StudentDataReaderService.makeInstance();
    final Student[] returnedStudents = instance.readAllStudents();

    assertEquals(1, returnedStudents.length);
  }

  @Test
  public void shouldParseALineSuchThatTheFirstWordIsTheStudentNumberThenStudentNameThenStudentSurname() {
    setOkListFileContents("abc def ghi\n");
    final StudentDataReaderService instance = StudentDataReaderService.makeInstance();
    final Student student = instance.readAllStudents()[0];

    assertEquals("abc", student.studentNumber);
    assertEquals("def", student.name);
    assertEquals("ghi", student.surname);
  }

  private void setOkListFileContents(String contents) {
    ByteArrayInputStream inputStream = new ByteArrayInputStream(contents.getBytes());
    Locator.instance.registerSingleton(new StubbedFileSystemService(inputStream));
  }
}
