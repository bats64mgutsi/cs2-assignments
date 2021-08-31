import java.util.Scanner;

public class Symmetry {

  public static void main(String[] args) throws Exception {
    System.out.println("Enter the name of the text file:");
    final Scanner keyboard = new Scanner(System.in);
    final String fileName = keyboard.nextLine();
    keyboard.close();

    final Shape shape = Shape.fromDefFile(fileName);
    if (shape.isSymmetric()) {
      System.out.println("Symmetric");
    } else {
      System.out.println("Asymmetric");
    }
  }
}
