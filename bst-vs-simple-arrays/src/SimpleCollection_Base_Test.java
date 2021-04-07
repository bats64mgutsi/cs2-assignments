import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import data_structures.*;

public class SimpleCollection_Base_Test {
  class LogCapturingCollectionItemVisitor<T> implements CollectionItemVisitor<T> {
    final List<T> items = new ArrayList<>();

    @Override
    public void visit(T item) {
      items.add(item);
    }
  }

  public void shouldInitiallyBeEmpty(SimpleCollection<Integer> instance) {
    final LogCapturingCollectionItemVisitor<Integer> collectionItemVisitor = new LogCapturingCollectionItemVisitor<>();
    instance.forEachItemDo(collectionItemVisitor);

    boolean doesNotHaveItems = collectionItemVisitor.items.isEmpty();
    assertTrue(doesNotHaveItems);
  }

  public void firstItemMatching_shouldReturnTheFirstItemThatComparesToZeroWithTheGivenItem(
      SimpleCollection<Integer> instance) {
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    try {
      final Integer returnedItem = instance.firstItemMatching(3);
      assertEquals(3, returnedItem);
    } catch (Exception e) {
    }
  }

  public void firstItemMatching_shouldThrowWhenTheSearchedForItemIsnotInTheDataSource(
      SimpleCollection<Integer> instance) {
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    assertThrows(NoSuchElementException.class, () -> instance.firstItemMatching(10));
  }

  public void forEachItemDo_shouldBeCalledForAllItems(SimpleCollection<Integer> instance) {
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    final LogCapturingCollectionItemVisitor<Integer> collectionItemVisitor = new LogCapturingCollectionItemVisitor<>();
    instance.forEachItemDo(collectionItemVisitor);

    assertEquals(3, collectionItemVisitor.items.size());
    assertEquals(1, collectionItemVisitor.items.get(0));
    assertEquals(3, collectionItemVisitor.items.get(1));
    assertEquals(7, collectionItemVisitor.items.get(2));
  }
}
