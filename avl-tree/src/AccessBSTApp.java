import data_structures.AVLTree;

public class AccessBSTApp {
  public static void main(String[] args) {
    final Application application = new Application();
    application.run(new AVLTree<>(), args);
  }
}
