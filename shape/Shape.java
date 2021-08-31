import java.io.BufferedReader;
import java.io.FileReader;

class Line {
  public static char DOT = 'X';
  public static char SPACE = ' ';
  public final char[] lineData;

  public Line(char[] lineData) {
    this.lineData = lineData;
  }

  public int countLeadingSpaces() {
    int count = 0;
    for (int iii = 0; iii < lineData.length && lineData[iii] != DOT; iii++) {
      count++;
    }

    return count;
  }

  public int countTrailingSpaces() {
    int count = 0;
    for (int iii = lineData.length - 1; iii >= 0 && lineData[iii] != DOT; iii--) {
      count++;
    }

    return count;
  }

  public int absDifferenceBeweenLeadingAndTrailingSpaces() {
    return Math.abs(countLeadingSpaces() - countTrailingSpaces());
  }

  public boolean isSymmetric(){
    return true;
  }

  public static void test() {
    final Line line1 = new Line(new char[] { SPACE, SPACE, DOT, SPACE });
    final Line line2 = new Line(new char[] { DOT, SPACE, SPACE, DOT });

    assert (line1.countLeadingSpaces() == 2);
    assert (line1.countTrailingSpaces() == 1);

    assert (line2.countLeadingSpaces() == 0);
    assert (line2.countTrailingSpaces() == 0);

    final Line line3 = new Line(new char[] { DOT, SPACE, DOT, DOT });
    final Line line4 = new Line(new char[] { DOT, SPACE, DOT });
    final Line line5 = new Line(new char[] { DOT, SPACE, DOT });

  }
}

public abstract class Shape {
  public abstract boolean isSymmetric();

  public static void main(String[] args) throws Exception {
    Line.test();
    System.out.println("Line Tests DONE!\n-----------------------");

    assert (Shape.fromDefFile("triangle.txt").isSymmetric() == true);
    assert (Shape.fromDefFile("parallelogram.txt").isSymmetric() == false);
  }

  public static Shape fromDefFile(String pathToDefFile) throws Exception {
    final BufferedReader reader = new BufferedReader(new FileReader(pathToDefFile));
    final Line[] lines = (Line[]) reader.lines().map(line -> new Line(line.toCharArray())).toArray(Line[]::new);
    reader.close();
    return new ShapeImpl(lines);
  }
}

class ShapeImpl extends Shape {
  public final Line[] data;

  public ShapeImpl(Line[] data) {
    this.data = data;
  }

  @Override
  public boolean isSymmetric() {
    final Line firstLine = data[0];

    if (data.length > 1) {
      for (int iii = 1; iii < data.length; iii++)
        if (firstLine.absDifferenceBeweenLeadingAndTrailingSpaces() != data[iii]
            .absDifferenceBeweenLeadingAndTrailingSpaces())
          return false;
    }

    return true;
  }

}
