package data_structures;

import java.util.NoSuchElementException;

public interface SimpleCollection<T> {
  /**
   * Returns the first item for which the picker returns true.
   * 
   * @throws NoSuchElementException when the data source is exhausted but picker
   *                                has not yet returned true.
   */
  public T firstItemWhere(CollectionItemPicker<T> picker) throws Exception;

  /**
   * Calls the given visitor for each item in the data source.
   */
  public void forEachItemDo(CollectionItemVisitor<T> visitor);

  /**
   * Sets the given array as the data source.
   * 
   * the SimpleArrayCollection may keep a reference to the array while the BST
   * implementation may pick out the items.
   */
  public void setDataSource(T[] items);
}
