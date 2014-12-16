//*******************************************************************
//  BTShootNode.java       Java Foundations
//
//  Represents a node in a binary tree with a left and right child.
//  Therefore this class also represents the root of a subtree.
//*******************************************************************

import javafoundations.*;
import java.util.*;
import java.lang.Integer;

public class BTShootNode<Integer> {
  
  protected Integer direction;
  protected BTShootNode<Integer> left, right;
  private Integer RANDOM;
  private int NORTH = 1;
  private int SOUTH = 2;
  private int EAST = 3;
  private int WEST = 4;

  
  //-----------------------------------------------------------------
  //  Creates a new tree node with the specified data.
  //-----------------------------------------------------------------
  public BTShootNode (Integer dir)
  {
    RANDOM = new Interger
    this.direction = dir;
    left = new BTShootNode(RANDOM);
    
  }
  
  //-----------------------------------------------------------------
  //  Returns the direction stored in this node.
  //-----------------------------------------------------------------
  public Integer getDirection()
  {
    return direction;
  }
  
  //-----------------------------------------------------------------
  //  Sets the direction stored in this node.
  //-----------------------------------------------------------------
  public void setDirection (Integer direction)
  {
    this.direction = direction;
  }
  
  //-----------------------------------------------------------------
  //  Returns the left subtree of this node.
  //-----------------------------------------------------------------
  public BTShootNode<Integer> getLeft()
  {
    return left;
  }
  
  //-----------------------------------------------------------------
  //  Sets the left child of this node.
  //-----------------------------------------------------------------
  public void setLeft (BTShootNode<Integer> left)
  {
    this.left = left;
  }
  
  //-----------------------------------------------------------------
  //  Returns the right subtree of this node.
  //-----------------------------------------------------------------
  public BTShootNode<Integer> getRight()
  {
    return right;
  }
  
  //-----------------------------------------------------------------
  //  Sets the right child of this node.
  //-----------------------------------------------------------------
  public void setRight (BTShootNode<Integer> right)
  {
    this.right = right;
  }
  
  //-----------------------------------------------------------------
  //  Returns the direction in this subtree that matches the
  //  specified target. Returns null if the target is not found.
  //-----------------------------------------------------------------
  public BTShootNode<Integer> find (Integer target)
  {
    BTShootNode<Integer> result = null;
    
    if (direction.equals(target))
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
  public void inorder (ArrayIterator<Integer> iter)
  {
    if (left != null)
      left.inorder (iter);
    
    iter.add (direction);
    
    if (right != null)
      right.inorder (iter);
  }
  
  //-----------------------------------------------------------------
  //  The following methods are left as programming projects.
  //-----------------------------------------------------------------
  public void preorder (ArrayIterator<Integer> iter) {
    iter.add (direction);
    
    if (left != null)
      left.inorder (iter);
    
    if (right != null)
      right.inorder (iter);
    
  }
  
  public void postorder (ArrayIterator<Integer> iter) {
    
    if (left != null)
      left.inorder (iter);
    
    if (right != null)
      right.inorder (iter);
    
     iter.add (direction);
    
  }
  
  
  public int height() {
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

    BTShootNode temp; //declare variable
    temp = left; // initialize
    left = right;
    right = temp;
    if (left != null) {
      left.spin();
    }
    if (right != null) {
      right.spin();
    }
    
  }
  
  public static void main(String[] args) {
    BTShootNode<String> one = new BTShootNode<String>("one");
    BTShootNode<String> two = new BTShootNode<String>("two");
   
    BTShootNode<String> three = new BTShootNode<String>("three");
    BTShootNode<String> four = new BTShootNode<String>("four");
    
    ArrayIterator<String> it = new ArrayIterator<String>();
    
    one.setLeft(two);
    one.setRight(three);
    
    one.inorder(it);
    
    one.setLeft(two);
    one.setRight(three);
   // (one.getRight()).setLeft(four);
    
    one.inorder(it);
    while (it.hasNext()) {
      System.out.println(it.next());
    
    
  }
  }
  
}
