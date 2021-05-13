public class Student implements Comparable<Student> {
  public final String studentNumber;
  public final String name;
  public final String surname;

  public Student(String studentNumber, String name, String surname) {
    this.studentNumber = studentNumber;
    this.name = name;
    this.surname = surname;
  }

  @Override
  public int compareTo(Student other) {
    return studentNumber.compareTo(other.studentNumber);
  }

  public static Student withStudentNumber(String studentNumber) {
    return new Student(studentNumber, null, null);
  }
}
