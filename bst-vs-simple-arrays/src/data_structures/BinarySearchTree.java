package data_structures;

import java.util.NoSuchElementException;

import data_structures.forks.BinaryTreeNode;

/**
 * A simple binary search tree collection.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SimpleCollection<T> {
  BinaryTreeNode<T> rootNode = null;

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
      addNode(rootNode, rootNode, 1, items);
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
        addNode(rootNode, rootNode, itemIndexToAdd + 1, items);
      } else {
        // Follow the left child
        addNode(rootNode, nodeToAddTo.left, itemIndexToAdd, items);
      }
    } else {
      // Add the node to the right or follow the right

      if (nodeToAddTo.right == null) {
        nodeToAddTo.right = nextNode;
        addNode(rootNode, rootNode, itemIndexToAdd + 1, items);
      } else {
        // Follow the right child
        addNode(rootNode, nodeToAddTo.right, itemIndexToAdd, items);
      }
    }
  }

  private BinaryTreeNode<T> findNodeWithData(BinaryTreeNode<T> nodeToSearch, T dataToSearchFor) {
    if (nodeToSearch == null)
      return null;

    int compare = dataToSearchFor.compareTo(nodeToSearch.data);
    if (compare < 0) {
      // Continue searching to the left of the node
      return findNodeWithData(nodeToSearch.left, dataToSearchFor);
    } else if (compare == 0) {
      // We found the node
      return nodeToSearch;
    } else {
      // Continue searching to the right of the node
      return findNodeWithData(nodeToSearch.right, dataToSearchFor);
    }
  }

}
