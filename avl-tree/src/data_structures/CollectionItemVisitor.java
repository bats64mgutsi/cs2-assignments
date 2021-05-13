package data_structures;

public interface CollectionItemVisitor<T> {
  public void visit(T item);
}
