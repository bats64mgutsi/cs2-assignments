import data_structures.SimpleArrayCollection;

public class AccessArrayApp {
  public static void main(String[] args) {
    final Application application = new Application();
    application.run(new SimpleArrayCollection<>(), args);
  }
}
