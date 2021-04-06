import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data_structures.*;

@DisplayName("SimpleArrayCollection Tests")
public class SimpleArrayCollection_Test {
  class LogCapturingCollectionItemVisitor<T> implements CollectionItemVisitor<T> {
    final List<T> items = new ArrayList<>();

    @Override
    public void visit(T item) {
      items.add(item);
    }
  }

  @Test
  public void shouldInitiallyBeEmpty() {
    final SimpleArrayCollection<Integer> instance = new SimpleArrayCollection<>();
    final LogCapturingCollectionItemVisitor<Integer> collectionItemVisitor = new LogCapturingCollectionItemVisitor<>();
    instance.forEachItemDo(collectionItemVisitor);

    boolean doesNotHaveItems = collectionItemVisitor.items.isEmpty();
    assertTrue(doesNotHaveItems);
  }

  @Test
  public void firstItemWhere_shouldReturnTheFirstItemForWhichThePickerReturnsTrue() {
    final SimpleArrayCollection<Integer> instance = new SimpleArrayCollection<>();
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    try {
      final Integer returnedItem = instance.firstItemWhere((item) -> item == 3);
      assertEquals(3, returnedItem);
    } catch (Exception e) {
    }
  }

  @Test
  public void firstItemWhere_shouldThrowWhenThePickerReturnsFalseForAllItems() {
    final SimpleArrayCollection<Integer> instance = new SimpleArrayCollection<>();
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    assertThrows(NoSuchElementException.class, () -> instance.firstItemWhere((item) -> false));
  }

  @Test
  public void forEachItemDo_shouldBeCalledForAllItems() {
    final SimpleArrayCollection<Integer> instance = new SimpleArrayCollection<>();
    instance.setDataSource(new Integer[] { 1, 3, 7 });

    final LogCapturingCollectionItemVisitor<Integer> collectionItemVisitor = new LogCapturingCollectionItemVisitor<>();
    instance.forEachItemDo(collectionItemVisitor);

    assertEquals(3, collectionItemVisitor.items.size());
    assertEquals(1, collectionItemVisitor.items.get(0));
    assertEquals(3, collectionItemVisitor.items.get(1));
    assertEquals(7, collectionItemVisitor.items.get(2));
  }
}
