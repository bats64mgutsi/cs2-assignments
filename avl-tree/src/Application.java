import data_structures.SimpleCollection;

public class Application {
  private void initLocator() {
    Locator.instance.registerSingleton(FileSystemService.makeInstance());
    Locator.instance.registerSingleton(StudentDataReaderService.makeInstance());
  }

  private void loadStudentsData(SimpleCollection<Student> studentsCollection) {
    StudentDataReaderService studentDataReaderService = (StudentDataReaderService) Locator.instance
        .get(StudentDataReaderService.class);
    Student[] students = studentDataReaderService.readAllStudents();
    studentsCollection.setDataSource(students);
  }

  private void printAllStudentDetails(Student student) {
    System.out.printf("%s, %s %s\n", student.studentNumber, student.name, student.surname);
  }

  private void printStudentName(Student student) {
    System.out.printf("%s %s\n", student.name, student.surname);
  }

  public void run(SimpleCollection<Student> studentsCollection, String[] args) {
    initLocator();
    loadStudentsData(studentsCollection);

    if (args.length > 0) {
      // Search student by student id
      try {
        Student student = studentsCollection.firstItemMatching(Student.withStudentNumber(args[0]));
        printStudentName(student);
      } catch (Exception e) {
        System.out.println("Access denied!");
      }
    } else {
      // Print all students
      studentsCollection.forEachItemDo((student) -> printAllStudentDetails(student));
    }
  }
}
