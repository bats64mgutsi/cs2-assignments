package data_structures;

import java.util.NoSuchElementException;

/**
 * A collection that stores its items in a simple java java array.
 */
public class SimpleArrayCollection<T> implements SimpleCollection<T> {
  T[] dataSource = null;

  @Override
  public T firstItemWhere(CollectionItemPicker<T> picker) throws Exception {
    if (dataSource == null)
      throw new NoSuchElementException("No data source was set.");

    for (T item : dataSource) {
      if (picker.shouldPick(item))
        return item;
    }

    throw new NoSuchElementException("The given picker returned false for all elements.");
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
