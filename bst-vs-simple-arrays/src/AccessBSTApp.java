import data_structures.BinarySearchTree;

public class AccessBSTApp {
  public static void main(String[] args) {
    final Application application = new Application();
    application.run(new BinarySearchTree<>(), args);
  }
}
