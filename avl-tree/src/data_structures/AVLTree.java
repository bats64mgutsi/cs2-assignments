package data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import data_structures.forks.BinaryTreeNode;
 
/**
 * A simple binary search tree collection.
 */
public class AVLTree<T extends Comparable<T>> implements SimpleCollection<T> {
  BinaryTreeNode<T> rootNode = null;

  // Adding nodes using recursion causes a StackOverflow error when there many
  // nodes (+1000).
  //
  // We will therefore use an iterative solution that schedules every add node
  // operation so that it can be ran by runAllAddNodeRunnables.
  List<Runnable> addNodeRunnables = new ArrayList<>();

  private int numberOfKeyComparisonsWhenSearchingForItem = 0;

  @Override
  public int getNumberOfKeyComparisonsWhenSearchingForItem() {
    return numberOfKeyComparisonsWhenSearchingForItem;
  }

  @Override
  public T firstItemMatching(T other) throws Exception {
    final BinaryTreeNode<T> node = findNodeWithData(rootNode, other);

    if (node == null)
      throw new NoSuchElementException("No element found for the given data.");

    return node.data;
  }

  @Override
  public void forEachItemDo(CollectionItemVisitor<T> visitor) {
    visitNodeInOrder(rootNode, visitor);
  }

  @Override
  public void setDataSource(T[] items) {
    if (items.length > 0) {
      rootNode = new BinaryTreeNode<T>(items[0], null, null);
      addNewAddNodeRunnable(rootNode, rootNode, 1, items);
      runAllAddNodeRunnables();
    }
  }

  private void visitNodeInOrder(BinaryTreeNode<T> node, CollectionItemVisitor<T> visitor) {
    if (node != null) {
      visitNodeInOrder(node.left, visitor);
      visitor.visit(node.data);
      visitNodeInOrder(node.right, visitor);
    }
  }

  private void addNode(BinaryTreeNode<T> rootNode, BinaryTreeNode<T> nodeToAddTo, int itemIndexToAdd, T[] items) {
    if (itemIndexToAdd == items.length)
      return;

    final BinaryTreeNode<T> nextNode = new BinaryTreeNode<T>(items[itemIndexToAdd], null, null);
    if (nextNode.data.compareTo(nodeToAddTo.data) < 0) {
      // Add the node to the left or follow the left

      if (nodeToAddTo.left == null) {
        nodeToAddTo.left = nextNode;
        addNewAddNodeRunnable(rootNode, rootNode, itemIndexToAdd + 1, items);
      } else {
        // Follow the left child
        addNewAddNodeRunnable(rootNode, nodeToAddTo.left, itemIndexToAdd, items);
      }
    } else {
      // Add the node to the right or follow the right

      if (nodeToAddTo.right == null) {
        nodeToAddTo.right = nextNode;
        addNewAddNodeRunnable(rootNode, rootNode, itemIndexToAdd + 1, items);
      } else {
        // Follow the right child
        addNewAddNodeRunnable(rootNode, nodeToAddTo.right, itemIndexToAdd, items);
      }
    }
  }

  private void addNewAddNodeRunnable(BinaryTreeNode<T> rootNode, BinaryTreeNode<T> nodeToAddTo, int itemIndexToAdd,
      T[] items) {
    addNodeRunnables.add(() -> addNode(rootNode, nodeToAddTo, itemIndexToAdd, items));
  }

  private void runAllAddNodeRunnables() {
    int size = addNodeRunnables.size();
    for (int iii = 0; iii < size; iii++) {
      addNodeRunnables.get(iii).run();
      size = addNodeRunnables.size();
    }
  }

  private BinaryTreeNode<T> findNodeWithData(BinaryTreeNode<T> nodeToSearch, T dataToSearchFor) {
    if (nodeToSearch == null)
      return null;

    int compare = dataToSearchFor.compareTo(nodeToSearch.data);
    if (compare == 0) {
      numberOfKeyComparisonsWhenSearchingForItem += 1;

      // We found the node
      return nodeToSearch;
    } else if (compare < 0) {
      numberOfKeyComparisonsWhenSearchingForItem += 2;

      // Continue searching to the left of the node
      return findNodeWithData(nodeToSearch.left, dataToSearchFor);
    } else {
      numberOfKeyComparisonsWhenSearchingForItem += 2;

      // Continue searching to the right of the node
      return findNodeWithData(nodeToSearch.right, dataToSearchFor);
    }
  }

}
