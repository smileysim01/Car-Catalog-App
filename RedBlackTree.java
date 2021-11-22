import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
 	public double price;
        public String model;
        public String title;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
	public boolean isBlack=false;
        public Node<T> rightChild; 
         public Node(T data,double price,String model, String title)
        {
            this.data =data;
            this.model=model;
            this.title=title;
     
       this.price=price;
        }
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node. The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * Note that the Node's implementation of toString generates a level
         * order traversal. The toString of the RedBlackTree class below
         * produces an inorder traversal of the nodes / values of the tree.
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    
    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
       public boolean insert(T data,double price,String model, String title) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
                "This RedBlackTree cannot store null references.");

        Node<T> newNode = new Node<>(data, price,model, title);
        if(root == null)
        {
            root = newNode;
            size++;
            root.isBlack=true;
            return true;
            //set to black
        } // add first node to an empty tree
        else{
            boolean returnValue = insertHelper(newNode,root); // recursively insert into subtree
            if (returnValue) size++;
            else throw new IllegalArgumentException(
                    "This RedBlackTree already contains that value.");
            //set to black
            root.isBlack=true;
            return returnValue;
        }
    }

    /**
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {

        // do not allow duplicate values to be stored within this tree
        if(subtree.price== newNode.price) return false;

            // store newNode within left subtree of subtree
        else if(newNode.price < subtree.price) {
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                this.enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else {
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                this.enforceRBTreePropertiesAfterInsert(newNode);
                return true;
                // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }
    private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {

        if (newNode.parent == null) {
            return;
        }
        if (newNode.parent.isBlack == true) {
            return;
        }

        if (newNode.parent.isLeftChild()) {
            Node<T> grandparent = newNode.parent.parent;
            if (grandparent.rightChild == null){
                newNode.parent.isBlack = true;
                grandparent.isBlack = false;
                rotate(newNode, newNode.parent);
                return;
            }
            if (grandparent.rightChild.isBlack == false) {
                // case 1
                grandparent.rightChild.isBlack = true;
                grandparent.leftChild.isBlack = true;
                grandparent.isBlack = false;
                this.enforceRBTreePropertiesAfterInsert(grandparent);
            } else {
                if (newNode.isLeftChild() == false) {
                    this.enforceRBTreePropertiesAfterInsert(newNode.parent);
                    rotate(newNode, newNode.parent);
                }
                newNode.parent.isBlack = true;
                grandparent.isBlack = false;
                rotate(newNode.parent, grandparent);
            }
        } else {
            Node<T> grandparent = newNode.parent.parent;
            if (grandparent.leftChild == null){
                newNode.parent.isBlack = true;
                grandparent.isBlack = false;
                rotate(newNode, newNode.parent);
                return;
            }
            if (grandparent.leftChild.isBlack == false) {
                // case 1
                grandparent.rightChild.isBlack = true;
                grandparent.leftChild.isBlack = true;
                grandparent.isBlack = false;
                this.enforceRBTreePropertiesAfterInsert(grandparent);
            } else {
                if (newNode.isLeftChild() == false) {
                    this.enforceRBTreePropertiesAfterInsert(newNode.parent);
                    rotate(newNode, newNode.parent);
                }
                newNode.parent.isBlack = true;
                grandparent.isBlack = false;
                rotate(newNode.parent, grandparent);
            }
        }
        // or the parent is red, violate red property
    }
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
if(parent.leftChild==child||parent.rightChild==child)
        {
            if (child.isLeftChild())
            {
                //parent.parent null   child become root
                //

                parent.leftChild = child.rightChild;
                child.rightChild = parent;
                if(parent.parent==null)
                {
                    root=child;
                }
                else if(parent.parent!=null&&!parent.isLeftChild())
                {
                    parent.parent.rightChild=child;
                }
                else
                {
                    parent.parent.leftChild=child;
                }
            }
            else
            {
                parent. rightChild= child.leftChild;
                child.leftChild = parent;
                if(parent.parent==null)
                {
                    root=child;
                }
                else if(parent.parent!=null&&!parent.isLeftChild())
                {
                    parent.parent.rightChild=child;
                }
                else
                {
                    parent.parent.leftChild=child;
                }


            }


        }
        else
        {
            throw new IllegalArgumentException();
        }

    }

    // Add at least 3 JUnit5 test methods with the test annotation similar to
    // the one from the comment below. You can use your notes from lecture for
    // ideas on concrete examples of rotation to test for. Make sure to include
    // rotations within and at the root of a tree in your test cases.
	/*
    @Test
	public void test1() {
		fail("This test has not been implemented.");		
	}
    */

    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }
public String containsPrice(double price ) {
        // null references will not be stored within this tree
        if(price == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private String containsHelper(double price , Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return "There are no car present in the tree with the specific price";
        } else {
            int compare = data.compareTo(subtree.data);
            if (price < subtree.price) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (price > subtree.price) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
String a=subtree.model.concat("with the title"+subtree.title);
                // we found it :)
                return a;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in until we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }

            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()) );
            }
            
        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    @Override
    public String containCar(String title) {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        
      
        while (treeNodeIterator.hasNext())
 {
		if(title.compareTo(treeNodeIterator.next().title)==0)
{
	String a= (String)treeNodeIterator.next().price.concat(" "+treeNodeIterator.next().model);
	return a;
	break;
}
        return "there are no cars in the catalog with the name of the current title";
    }

}
 public String containModel(String model) {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
                     
         
        while (treeNodeIterator.hasNext())  
 {
                if(model.compareTo(treeNodeIterator.next().model)==0)
{ 
        String a= (String)treeNodeIterator.next().price.concat(" "+treeNodeIterator.next().title);
        return a;
        break;
}
        return "there are no cars in the catalog with the name of the current model";
    }

}
