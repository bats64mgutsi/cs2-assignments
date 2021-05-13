import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import data_structures.AVLTree;

@DisplayName("BinarySearchTree Tests")
public class AVLTree_Test {
  final SimpleCollection_Base_Test simpleCollectionTests = new SimpleCollection_Base_Test();

  @Test
  public void shouldInitiallyBeEmpty() {
    simpleCollectionTests.shouldInitiallyBeEmpty(new AVLTree<>());
  }

  @Test
  public void firstItemWhere_shouldReturnTheFirstItemForWhichThePickerReturnsTrue() {
    simpleCollectionTests
        .firstItemMatching_shouldReturnTheFirstItemThatComparesToZeroWithTheGivenItem(new AVLTree<>());
  }

  @Test
  public void firstItemWhere_shouldThrowWhenThePickerReturnsFalseForAllItems() {
    simpleCollectionTests
        .firstItemMatching_shouldThrowWhenTheSearchedForItemIsnotInTheDataSource(new AVLTree<>());
  }

  @Test
  public void forEachItemDo_shouldBeCalledForAllItems() {
    simpleCollectionTests.forEachItemDo_shouldBeCalledForAllItems(new AVLTree<>());
  }
}
