//*******************************************************************
//  BTNode.java       Java Foundations
//
//  Represents a node in a binary tree with a left and right child.
//  Therefore this class also represents the root of a subtree.
//*******************************************************************

package javafoundations;
<<<<<<< HEAD
=======
import java.util.*;
>>>>>>> master

public class BTNode<T>
{
  protected T element;
  protected BTNode<T> left, right;
  
  //-----------------------------------------------------------------
  //  Creates a new tree node with the specified data.
  //-----------------------------------------------------------------
  public BTNode (T element)
  {
    this.element = element;
    left = right = null;
  }
  
  //-----------------------------------------------------------------
  //  Returns the element stored in this node.
  //-----------------------------------------------------------------
  public T getElement()
  {
    return element;
  }
  
  //-----------------------------------------------------------------
  //  Sets the element stored in this node.
  //-----------------------------------------------------------------
  public void setElement (T element)
  {
    this.element = element;
  }
  
  //-----------------------------------------------------------------
  //  Returns the left subtree of this node.
  //-----------------------------------------------------------------
  public BTNode<T> getLeft()
  {
    return left;
  }
  
  //-----------------------------------------------------------------
  //  Sets the left child of this node.
  //-----------------------------------------------------------------
  public void setLeft (BTNode<T> left)
  {
    this.left = left;
  }
  
  //-----------------------------------------------------------------
  //  Returns the right subtree of this node.
  //-----------------------------------------------------------------
  public BTNode<T> getRight()
  {
    return right;
  }
  
  //-----------------------------------------------------------------
  //  Sets the right child of this node.
  //-----------------------------------------------------------------
  public void setRight (BTNode<T> right)
  {
    this.right = right;
  }
  
  //-----------------------------------------------------------------
  //  Returns the element in this subtree that matches the
  //  specified target. Returns null if the target is not found.
  //-----------------------------------------------------------------
  public BTNode<T> find (T target)
  {
    BTNode<T> result = null;
    
    if (element.equals(target))
      result = this;
    else
    {
      if (left != null)
        result = left.find(target);
      if (result == null && right != null)
        result = right.find(target);
    }
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Returns the number of nodes in this subtree.
  //-----------------------------------------------------------------
  public int count()
  {
    int result = 1;
    
    if (left != null)
      result += left.count();
    
    if (right != null)
      result += right.count();
    
    return result;
  }
  
  //-----------------------------------------------------------------
  //  Performs an inorder traversal on this subtree, updating the
  //  specified iterator.
  //-----------------------------------------------------------------
  public void inorder (ArrayIterator<T> iter)
  {
    if (left != null)
      left.inorder (iter);
    
    iter.add (element);
    
    if (right != null)
      right.inorder (iter);
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as programming projects.
  //-----------------------------------------------------------------
  public void preorder (ArrayIterator<T> iter) {
<<<<<<< HEAD
    iter.add (element);
    
    if (left != null)
      left.inorder (iter);
    
    if (right != null)
      right.inorder (iter);
=======
    
    iter.add (element);
    
    if (left != null)
      left.preorder (iter);
    
    
    if (right != null)
      right.preorder (iter);
>>>>>>> master
    
  }
  
  public void postorder (ArrayIterator<T> iter) {
    
    if (left != null)
<<<<<<< HEAD
      left.inorder (iter);
    
    if (right != null)
      right.inorder (iter);
    
     iter.add (element);
=======
      left.postorder (iter);
    
    
    if (right != null)
      right.postorder (iter);
    
    iter.add (element);
    
>>>>>>> master
    
  }
  
  
  public int height() {
<<<<<<< HEAD
    int h1 = 0;
    int h2 = 0;
    if (left==null & right==null) {
      return 0;
    }
    if (right!= null) {
      h2 = right.height();
    }
    if (left != null){
      h1 = left.height();
    }
    return Math.max(h1,h2);
  }
  
  public void spin(){

    BTNode temp; //declare variable
    temp = left; // initialize
    left = right;
    right = temp;
    if (left != null) {
      left.spin();
    }
    if (right != null) {
      right.spin();
    }
=======
    
    int h1 = 0;
    int h2 = 0;
    
    // base case
    if (left == null && right == null)
      return 0;
    
    if (left != null)
      h1 = left.height(); // call left recursively
    
    if (right != null)
      h2 = right.height(); 
    
    return (1 + Math.max(h1,h2)); // add one for the zero case
      
  }
  
  public void spin(){
   
    BTNode temp = left;
    left = right;
    right = temp;
    
    if (left != null) 
      left.spin();
    
    if (right != null) 
      right.spin();
>>>>>>> master
    
  }
  
  public static void main(String[] args) {
    BTNode<String> one = new BTNode<String>("one");
    BTNode<String> two = new BTNode<String>("two");
<<<<<<< HEAD
   
=======
    
>>>>>>> master
    BTNode<String> three = new BTNode<String>("three");
    BTNode<String> four = new BTNode<String>("four");
    
    ArrayIterator<String> it = new ArrayIterator<String>();
    
    one.setLeft(two);
    one.setRight(three);
    
    one.inorder(it);
    
    one.setLeft(two);
    one.setRight(three);
<<<<<<< HEAD
   // (one.getRight()).setLeft(four);
=======
    // (one.getRight()).setLeft(four);
>>>>>>> master
    
    one.inorder(it);
    while (it.hasNext()) {
      System.out.println(it.next());
<<<<<<< HEAD
    
    
  }
  }
  
=======
      
      
    }
  }
>>>>>>> master
}
