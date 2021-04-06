import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class for opening files.
 */
public abstract class FileSystemService {

  /**
   * Returns an InputStream for reading in the contents of the oklist.txt file.
   * 
   * The oklist.txt file should be in the same folder as the compiled .class file
   * for this file.
   */
  abstract InputStream openOkListStudentsFile() throws IOException;

  public static FileSystemService makeInstance() {
    return new FileSystemServiceImpl();
  }

  static class FileSystemServiceImpl extends FileSystemService {

    @Override
    InputStream openOkListStudentsFile() throws IOException {
      return new FileInputStream("oklist.txt");
    }
  }
}