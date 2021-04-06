package data_structures;

public interface CollectionItemPicker<T> {
  /**
   * Returns true only, and only, when item should be picked.
   */
  public boolean shouldPick(T item);
}
