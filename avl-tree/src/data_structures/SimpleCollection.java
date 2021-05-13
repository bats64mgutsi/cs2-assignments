package data_structures;

import java.util.NoSuchElementException;

public interface SimpleCollection<T extends Comparable<T>> {

  /**
   * Returns the number of times it compared a given item to another when
   * searching for items.
   * 
   * This is incremented by the firstItemMatching method.
   */
  public int getNumberOfKeyComparisonsWhenSearchingForItem();

  /**
   * Returns the first item whose compareTo method returns zero when compared with
   * other.
   * 
   * If the items have keys, then only the keys should be compared in the
   * compareTo method.
   * 
   * @throws NoSuchElementException when the data source is exhausted but no match
   *                                has been found yet.
   */
  public T firstItemMatching(T other) throws Exception;

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
