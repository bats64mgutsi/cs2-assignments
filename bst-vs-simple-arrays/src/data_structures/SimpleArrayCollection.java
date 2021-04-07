package data_structures;

import java.util.NoSuchElementException;

/**
 * A collection that stores its items in a simple java java array.
 */
public class SimpleArrayCollection<T extends Comparable<T>> implements SimpleCollection<T> {
  T[] dataSource = null;

  @Override
  public T firstItemMatching(T other) throws Exception {
    if (dataSource == null)
      throw new NoSuchElementException("No data source was set.");

    for (T item : dataSource) {
      if (item == other)
        return item;
    }

    throw new NoSuchElementException("No element found for the given data.");
  }

  @Override
  public void forEachItemDo(CollectionItemVisitor<T> visitor) {
    if (dataSource == null)
      return;

    for (T item : dataSource)
      visitor.visit(item);
  }

  @Override
  public void setDataSource(T[] items) {
    dataSource = items;
  }
}
