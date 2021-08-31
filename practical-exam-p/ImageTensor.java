import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImageTensor {
  final int width;
  final int height;
  final int[] buffer;

  public ImageTensor(int height, int width, int[] buffer) {
    this.width = width;
    this.height = height;
    this.buffer = buffer;
  }

  public ImageTensor downscaleToHalfResolution() {
    final int outHeight = height / 2;
    final int outWidth = width / 2;
    final int[] outBuffer = new int[outWidth * outHeight];

    for (int outRow = 0; outRow < outHeight; outRow++) {
      for (int outCol = 0; outCol < outWidth; outCol++) {
        final int[] quad = sampleFromBufferQuadAt(outCol, outRow);
        // System.out.printf("Sample for %d %d is {%d, %d, %d, %d}\n", outRow, outCol,
        // quad[0], quad[1], quad[2], quad[3]);
        final double quadAvg = computeSampleAverage(quad);
        outBuffer[toIndex(outRow, outCol, outWidth)] = (int) Math.round(quadAvg);
      }
    }

    return new ImageTensor(outHeight, outWidth, outBuffer);
  }

  public void debugPrint() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        final int value = buffer[toIndex(row, col, width)];
        if (col == width - 1)
          System.out.printf("%3d", value);
        else
          System.out.printf("%3d ", value);
      }

      System.out.println();
    }
  }

  private int[] sampleFromBufferQuadAt(int qX, int qY) {
    final int tlRow = qY * 2;
    final int tlCol = qX * 2;

    final int trRow = tlRow;
    final int trCol = tlCol + 1;

    final int blRow = tlRow + 1;
    final int blCol = tlCol;

    final int brRow = tlRow + 1;
    final int brCol = tlCol + 1;

    return new int[] { buffer[toIndex(tlRow, tlCol, width)], buffer[toIndex(trRow, trCol, width)],
        buffer[toIndex(blRow, blCol, width)], buffer[toIndex(brRow, brCol, width)], };
  }

  private double computeSampleAverage(int[] sample) {
    double sum = 0;
    for (int value : sample)
      sum += value;

    return sum / sample.length;
  }

  private int toIndex(int row, int col, int width) {
    return row * width + col;
  }

  public static ImageTensor loadFromFile(String fileName) throws FileNotFoundException {
    final Scanner file = new Scanner(new FileInputStream(fileName));
    final String heightAndWidthLine = file.nextLine();
    final int width = parseWidthFromLine(heightAndWidthLine);
    final int height = parseHeightFromLine(heightAndWidthLine);

    final int[] pixels = new int[width * height];
    int writeTo = 0;
    while (file.hasNextLine()) {
      final int[] lineValues = getLineValues(file.nextLine());
      for (int value : lineValues) {
        pixels[writeTo] = value;
        writeTo++;
      }
    }

    final ImageTensor out = new ImageTensor(height, width, pixels);
    file.close();
    return out;
  }

  private static int parseWidthFromLine(String line) {
    return Integer.valueOf(line.split(" ")[0]);
  }

  private static int parseHeightFromLine(String line) {
    return Integer.valueOf(line.split(" ")[1]);
  }

  private static int[] getLineValues(String line) {
    final String[] valuesAsStrings = line.split(" ");
    final int[] out = new int[valuesAsStrings.length];

    for (int iii = 0; iii < valuesAsStrings.length; iii++) {
      out[iii] = Integer.valueOf(valuesAsStrings[iii]);
    }

    return out;
  }
}