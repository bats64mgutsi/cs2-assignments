import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A service for reading in the contents of the oklist.txt and return them as a
 * collection of students.
 */
abstract public class StudentDataReaderService {
  abstract Student[] readAllStudents();

  public static StudentDataReaderService makeInstance() {
    return new StudentDataReaderServiceImpl();
  }

  static class StudentDataReaderServiceImpl extends StudentDataReaderService {
    final FileSystemService fileSystemService = (FileSystemService) Locator.instance.get(FileSystemService.class);

    @Override
    Student[] readAllStudents() {
      try {
        return unsafeReadAllStudents();
      } catch (IOException e) {
        System.err.println(e);
        return new Student[0];
      }
    }

    private Student[] unsafeReadAllStudents() throws IOException {
      final InputStream inputStream = fileSystemService.openOkListStudentsFile();
      final Scanner scanner = new Scanner(inputStream);

      Student[] out = new Student[0];
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        final String[] studentData = line.split(" ");

        final Student[] temp = new Student[out.length + 1];
        System.arraycopy(out, 0, temp, 0, out.length);
        temp[out.length] = new Student(studentData[0], studentData[1], studentData[2]);
        out = temp;
      }

      scanner.close();
      return out;
    }

  }
}
