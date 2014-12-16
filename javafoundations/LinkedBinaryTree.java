//*******************************************************************
//  LinkedBinaryTree.java       Java Foundations
//
//  Implements a binary tree using a linked representation.
//*******************************************************************

package javafoundations;

import java.util.Iterator;
import javafoundations.*;
import javafoundations.exceptions.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>
{
<<<<<<< HEAD
  protected BTNode<T> root;
=======
  protected LinkedBinaryTree<T> root;
>>>>>>> master
  
  //-----------------------------------------------------------------
  //  Creates an empty binary tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree()
  {
    root = null;
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the specified element as its root.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element)
  {
<<<<<<< HEAD
    root = new BTNode<T>(element);
=======
    root = new LinkedBinaryTree<T>(element);
>>>>>>> master
  }
  
  //-----------------------------------------------------------------
  //  Creates a binary tree with the two specified subtrees.
  //-----------------------------------------------------------------
  public LinkedBinaryTree (T element, LinkedBinaryTree<T> left,
                           LinkedBinaryTree<T> right)
  {
<<<<<<< HEAD
    root = new BTNode<T>(element);
=======
    root = new LinkedBinaryTree<T>(element);
>>>>>>> master
    root.setLeft(left.root);
    root.setRight(right.root);
  }
  
  //-----------------------------------------------------------------
  //  Returns the element stored in the root of the tree. Throws an
  //  EmptyCollectionException if the tree is empty.
  //-----------------------------------------------------------------
  public T getRootElement() {
    if (root == null)
      throw new EmptyCollectionException ("Get root operation "
                                            + "failed. The tree is empty.");
    
    return root.getElement();
  }
  
<<<<<<< HEAD
=======
   public void setLeft(LinkedBinaryTree<T> bt)
  {
     
    if (root == null)
      throw new ElementNotFoundException("setLeft() failed. LBT is empty");

       root.setLeft(bt.root); 

  
  }
  
>>>>>>> master
  //-----------------------------------------------------------------
  //  Returns the left subtree of the root of this tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree<T> getLeft() {
    if (root == null)
      throw new EmptyCollectionException ("Get left operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getLeft();
    
    return result;
  }
  
  //-----------------------------------------------------------------
<<<<<<< HEAD
=======
  //  Returns the right subtree of the root of this tree.
  //-----------------------------------------------------------------
  public LinkedBinaryTree<T> getRight() {
    if (root == null)
      throw new EmptyCollectionException ("Get right operation "
                                            + "failed. The tree is empty.");
    
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getRight();
    
    return result;
  }
  
  //-----------------------------------------------------------------
>>>>>>> master
  //  Returns the element in this binary tree that matches the
  //  specified target. Throws a ElementNotFoundException if the
  //  target is not found.
  //-----------------------------------------------------------------
  public T find (T target) {
<<<<<<< HEAD
    BTNode<T> node = null;
=======
    LinkedBinaryTree<T> node = null;
>>>>>>> master
    
    if (root != null)
      node = root.find(target);
    
    if (node == null)
      throw new ElementNotFoundException("Find operation failed. "
                                           + "No such element in tree.");
    
    return node.getElement();
  }
  
  //-----------------------------------------------------------------
  //  Returns the number of elements in this binary tree.
  //-----------------------------------------------------------------
  public int size() {
    int result = 0;
    
    if (root != null)
      result = root.count();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> inorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
<<<<<<< HEAD
    //cannot call methods on null
=======
>>>>>>> master
    if (root != null)
      root.inorder (iter);
    
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
<<<<<<< HEAD
  //  this binary tree using a levelorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> levelorder() {
    LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
=======
  //  this binary tree using an preorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> preorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.preorder (iter);
    
    return iter;
  }
  
  
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using an postorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> postorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.postorder (iter);
    
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Populates and returns an iterator containing the elements in
  //  this binary tree using a levelorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> levelorder() {
    LinkedQueue<LinkedBinaryTree<T>> queue = new LinkedQueue<LinkedBinaryTree<T>>();
>>>>>>> master
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null) {
      queue.enqueue(root);
      while (!queue.isEmpty()) {
<<<<<<< HEAD
        BTNode<T> current = queue.dequeue();
=======
        LinkedBinaryTree<T> current = queue.dequeue();
>>>>>>> master
        
        iter.add (current.getElement());
        
        if (current.getLeft() != null)
          queue.enqueue(current.getLeft());
        if (current.getRight() != null)
          queue.enqueue(current.getRight());
      }
    }
    return iter;
  }
  
  //-----------------------------------------------------------------
  //  Satisfies the Iterable interface using an inorder traversal.
  //-----------------------------------------------------------------
  public Iterator<T> iterator() {
<<<<<<< HEAD
    return inorder();
=======
    return levelorder();
>>>>>>> master
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as programming projects.
  //-----------------------------------------------------------------
  
  public String toString() {
    String s = "";
    
<<<<<<< HEAD
    return s;
  }

  public LinkedBinaryTree<T> getRight() {
    return this.getRight();
  }
  
  public boolean contains (T target) {
    if (root == null) {
      throw new EmptyCollectionException("contains(): failed.");
    } else {
    return (root.find(target) != null);
    }
  }
  
  public boolean isEmpty() {
    return false;
  }
  
  public Iterator<T> preorder() {
    //Iterator<T> temp = (new Iterator<T>());
    return null;
  }
  
  public Iterator<T> postorder() {
    //Iterator<T> temp = new Iterator<T>();
    return null;
  }
  
  public int height() {
    if (root != null) 
      return root.height(); // root is a BTNode
    else
      throw new EmptyCollectionException("height(): failed");
  }
  
  public void spin() {
    if (root==null) 
      throw new EmptyCollectionException("spin(): failed");
    else
      root.spin();

=======
    if (root != null ){  
      Iterator i = iterator(); // can also call this.levelorder() or root.preorder(); or root.postorder();
      while (i.hasNext())
        s += i.next();
    }
    
    return s;
  }
  
  public boolean contains (T target) {
    
    if (root == null) 
      throw new EmptyCollectionException("contains() failed, tree is empty"); 
    
    return (root.find(target) != null);
  }
  
  public boolean isEmpty() {
    
    return (root == null);
    
  }
  
  
  public int height() {
    
    if (root == null) 
      throw new EmptyCollectionException("height() failed, tree is empty"); 
    
    return root.height() ; 
  }
  
  public void spin() {
    
    if (root == null) 
      throw new EmptyCollectionException("spin() failed, tree is empty"); 
    
    root.spin();
    
  }
  
  public static void main(String[] args){
    //LinkedBinaryTree<String> t1 = new  LinkedBinaryTree<String>("does it meow?", new  LinkedBinaryTree<String>("dog"), new  LinkedBinaryTree<String>("cat")); 
    LinkedBinaryTree<String> big = new LinkedBinaryTree<String>("is it big?");
    LinkedBinaryTree<String> meow = new  LinkedBinaryTree<String>("does it meow?");
    LinkedBinaryTree<String> cage = new  LinkedBinaryTree<String>("does it live in a cage?");
    LinkedBinaryTree<String> chirp = new  LinkedBinaryTree<String>("does it chirp?");
    LinkedBinaryTree<String> dog = new  LinkedBinaryTree<String>("dog");
    LinkedBinaryTree<String> cat = new  LinkedBinaryTree<String>("cat");
    LinkedBinaryTree<String> hamster = new  LinkedBinaryTree<String>("hamster");
    LinkedBinaryTree<String> bird = new  LinkedBinaryTree<String>("bird");
    LinkedBinaryTree<String> grass = new  LinkedBinaryTree<String>("does it eat grass");
    LinkedBinaryTree<String> neck = new  LinkedBinaryTree<String>("does it have a long neck"); 
    LinkedBinaryTree<String> bear = new  LinkedBinaryTree<String>("bear");
    LinkedBinaryTree<String> giraffe = new LinkedBinaryTree<String>("giraffe");
    LinkedBinaryTree<String> moo = new  LinkedBinaryTree<String>("does it moo?");
    LinkedBinaryTree<String> horse = new LinkedBinaryTree<String>("horse");
    LinkedBinaryTree<String> cow = new  LinkedBinaryTree<String>("cow"); 
    big.setLeft(cage);
    big.setRight(grass);
    cage.setLeft(meow);
    cage.setRight(chirp);
    meow.setLeft(dog);
    meow.setRight(cat);
    chirp.setLeft(hamster);
    chirp.setRight(bird);
    neck.setLeft(bear);
    neck.setRight(giraffe);
    grass.setLeft(neck);
    grass.setRight(moo);
    moo.setLeft(horse);
    moo.setRight(cow);
    
>>>>>>> master
  }
}

