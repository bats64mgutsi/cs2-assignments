import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data_structures.*;

@DisplayName("SimpleArrayCollection Tests")
public class SimpleArrayCollection_Test {
  final SimpleCollection_Base_Test simpleCollectionTests = new SimpleCollection_Base_Test();

  @Test
  public void shouldInitiallyBeEmpty() {
    simpleCollectionTests.shouldInitiallyBeEmpty(new SimpleArrayCollection<>());
  }

  @Test
  public void firstItemWhere_shouldReturnTheFirstItemForWhichThePickerReturnsTrue() {
    simpleCollectionTests
        .firstItemMatching_shouldReturnTheFirstItemThatComparesToZeroWithTheGivenItem(new SimpleArrayCollection<>());
  }

  @Test
  public void firstItemWhere_shouldThrowWhenThePickerReturnsFalseForAllItems() {
    simpleCollectionTests
        .firstItemMatching_shouldThrowWhenTheSearchedForItemIsnotInTheDataSource(new SimpleArrayCollection<>());
  }

  @Test
  public void forEachItemDo_shouldBeCalledForAllItems() {
    simpleCollectionTests.forEachItemDo_shouldBeCalledForAllItems(new SimpleArrayCollection<>());
  }
}
