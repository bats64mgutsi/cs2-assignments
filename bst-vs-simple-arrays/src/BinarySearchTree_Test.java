import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import data_structures.BinarySearchTree;

@DisplayName("BinarySearchTree Tests")
public class BinarySearchTree_Test {
  final SimpleCollection_Base_Test simpleCollectionTests = new SimpleCollection_Base_Test();

  @Test
  public void shouldInitiallyBeEmpty() {
    simpleCollectionTests.shouldInitiallyBeEmpty(new BinarySearchTree<>());
  }

  @Test
  public void firstItemWhere_shouldReturnTheFirstItemForWhichThePickerReturnsTrue() {
    simpleCollectionTests
        .firstItemMatching_shouldReturnTheFirstItemThatComparesToZeroWithTheGivenItem(new BinarySearchTree<>());
  }

  @Test
  public void firstItemWhere_shouldThrowWhenThePickerReturnsFalseForAllItems() {
    simpleCollectionTests
        .firstItemMatching_shouldThrowWhenTheSearchedForItemIsnotInTheDataSource(new BinarySearchTree<>());
  }

  @Test
  public void forEachItemDo_shouldBeCalledForAllItems() {
    simpleCollectionTests.forEachItemDo_shouldBeCalledForAllItems(new BinarySearchTree<>());
  }
}
