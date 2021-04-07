# binary search program makefile
# Hussein Suleman
# 22 March 2017

JAVAC=/usr/bin/javac

.SUFFIXES: .java .class

.java.class:
	$(JAVAC) $<

classes: BinaryTreeNode.class BinaryTree.class BinaryTreeTest.class BTQueueNode.class BTQueue.class

default: $(CLASSES)

clean:
	rm *.class
