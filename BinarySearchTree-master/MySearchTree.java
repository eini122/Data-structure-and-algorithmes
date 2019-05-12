/*
 * This is a binary serach tree include method add, find, leafCout, parentCount,
 * height, is perfect, ancestores, isOrderPrint, and preOrderPrint.
 * assume there are no duplicate nood
 * @author Kaitian Li @data 2/27/2019
 */
package mysearchtree;
import java.util.ArrayList;
public class MySearchTree<T extends Comparable<? super T>>{
    Node root;
    //constructor to set an empty binary tree
    public MySearchTree(){
        root = null;
    }
    //method that get a data from used and add it into the list
    private void add(T data){
        root = add(data, root);
    }
    //recursion function to find the place to add node
    private Node add(T data, Node<T> p){
        //if the pointer is null, add the node
        if(p == null){
            return new Node<>(data, null, null);
        }
        //check which is bigger
        int result = data.compareTo(p.data);
        //if the input is less than the pointer, check the pointer.left
        if(result < 0){
           p.left = add(data, p.left);
        }
        //if the input is greater than the pointer data, check the pointer right
        else if(result > 0){
            p.right = add(data, p.right);
        }
        return p;
    }
    //call the method find that to find data
    private boolean find(T data){
        return find(data, root);
    }
    private boolean find(T data, Node<T> t){
        //reach the end of the tree, return false
        if(t == null){
            return false;
        }
        //find the data, return true
        if(t.data.equals(data)){
            return true;
        }
        //check the input and pointer data which is bigger
        int compareResult = data.compareTo(t.data);
        //if input data is less than pointer, go to left
        if(compareResult < 0){
            return find(data, t.left);
        }
        //if input data is greater than pointer, go to right
        else{
            return find(data, t.right);
        }   
    }
    //call the recursive method leafCount start at root
    private int leafCount(){
        return leafCount(root);
    }
    //method that return the number of leaf nodes
    private int leafCount(Node<T> t){
        //any situation that only one side is null and other is not, return null
        if(t == null){
            return 0;
        }
        //get the leaf node, return 1
        if(t.left == null && t.right == null){
            return 1;
        }
        //add all the leaf node together
        return leafCount(t.left)+leafCount(t.right);
    }
    //call the recursive method parentCount
    private int parentCount(){
        return parentCount(root);
    }
    //method that return the number of parent node
    private int parentCount(Node<T> t){
        //the pointer reach the null, return 0
        if(t == null){
            return 0;
        }
        //pointer reach the leaf node, return 0
        else if(t.left == null && t.right == null){
            return 0;
        }
        //add all the non-leaf node together
        else{
            int count = parentCount(t.left) + parentCount(t.right);   
            return count + 1;
        }
    }
    //call the recursive function hight
    private int hight(){
        return hight(root);
    }
    //method to return the maximum hight 
    private int hight(Node<T> t){
        //reach the end of the tree, return 0
        if(t == null){
            return 0;
        }
        //reach the leaf node, return 0
        else if(t.left == null && t.right == null){
            return 0;
        }
        else{
            int max_left = hight(t.left);//get an integer that get the hight of the left maximum
            int max_right = hight(t.right);//get an integer that get the hight of the left maximum
            //if left subtree is greater than right subtree
            //left subtree + 1 and return
            if(max_left > max_right){
                return max_left + 1;
            }
            //if right subtree is greater than left subtree
            //right subtree + 1 and return
            else{
                return max_right + 1;
            }
        }
    }
    //call the recursive method isPerfect
    private boolean isPerfect(){
        return isPerfect(root, 0, 0);
        
    }
    //check every nodes except leaf nodes is has two children nodes
    //and the leaf nodes are same depth
    private boolean isPerfect(Node<T> t, int max_level, int level){
        //empty tree is perfect tree
        if(root == null){
            return true;
        }
        //check the level of the leaf node
        //if any one of the level is not equal to others, return false
        if(t.left == null && t.right == null){
            return level == max_level;
        }
        //if any node that only have one child, return false
        if(t.left == null || t.right == null){
            return false;
        }
        //set the max_level by adding 1 when max_levle less than the current level
        if(max_level < level){
            max_level = level + 1;
        }
        //both left sub tree and right sub tree are equal, if both true, return true,
        //else return false
        return isPerfect(t.left, max_level, level+1) && isPerfect(t.right, max_level, level+1);
    }
    //call the method ancestors, then return the arraylist about the ancestors of the input data
    private ArrayList<T> ancestors(T data){
        //initial an arraylist
        ArrayList<T> arraylist = new ArrayList<>();
        return ancestors(data, root, arraylist);
    }
    //create the string for all the ancestors for the input data
    private ArrayList<T> ancestors(T data, Node<T> t, ArrayList<T> arraylist){
        //if cannot find the data, return an empty list
        if(t == null){
            return new ArrayList<>();
        }
        //find the value retunr arraylist
        if(t.data.equals(data)){
            arraylist.add(t.data);
            return arraylist;
        }
        else{
            //set variable that get the result of compare input data and pointer data
            int compareResult = data.compareTo(t.data);
            //if result less than 1, go to the left sub tree
            if(compareResult < 0){
                //add the current data, and pointer move to the left 
                arraylist.add(t.data);
                return ancestors(data, t.left, arraylist);
            }
            else{
                //add the current data, and pointer move to the right 
                arraylist.add(t.data);
                return ancestors(data, t.right, arraylist);
            }
        }
    }
    //call the method of preOrder by pssing root
    private String inOrder(){
        return inOrder(root);
    }
    //create a string by in order. which follow by left child - parent - right right
    private String inOrder(Node<T> t){
        if(t != null){
            return "" + inOrder(t.left) + t.data + " " + inOrder(t.right);
        }
        else{
            return "";
        }
    }
    //call the method of preOrder by pssing root
    private String preOrder(){
        return preOrder(root);
    }
    //create a string by pre order, which follow by parent - left child - right child
    private String preOrder(Node<T> t){
        if(t != null){
            return  "" + t.data + " "+ preOrder(t.left) + preOrder(t.right);
        }
        else{
            return "";
        }
    }
    //Node class that include the data, left, and right
    class Node<T>{ 
        private T data;
        private Node left;
        private Node right;
        //constructor that set the data and left right child
        private Node(T data, Node left, Node right){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    public static void main(String[] args){
        //chech all the method of MySearchTree
        MySearchTree<Integer> tree = new MySearchTree<>();
        tree.add(6);
        tree.add(3);
        tree.add(9);
        tree.add(2);
        tree.add(4);
        tree.add(8);
        tree.add(7);
        tree.add(12);
        tree.add(10);
        tree.add(19);
        tree.add(22);
        tree.add(17);
        tree.add(23);
        tree.add(233);
        //out put the result
        System.out.println("Inorder: "+tree.inOrder());//printe by inorder
        System.out.println("preorder: "+tree.preOrder());//print by preorder
        System.out.println("find 10 in the tree(should return true): "+tree.find(10));//check the method find, should return true
        System.out.println("find 1 in the tree(should return false): "+tree.find(1));//check the method find, should return false
        System.out.println("The leaf count is "+tree.leafCount());//should be 6
        System.out.println("parent count:" + tree.parentCount());//should be 8
        System.out.println("The hight of the tree is: "+tree.hight());//hight is 6
        System.out.println("check is it perfect tree: " + tree.isPerfect());//false
        System.out.println("The ancestors of 23: "+tree.ancestors(23));//shoule be 6, 9, 12,22
        System.out.println();
        //Create a perfect binary tree
        MySearchTree<Integer> PerfectTree = new MySearchTree<>();
        PerfectTree.add(10);
        PerfectTree.add(5);
        PerfectTree.add(6);
        PerfectTree.add(4);
        PerfectTree.add(15);
        PerfectTree.add(14);
        PerfectTree.add(16);
        System.out.println("Check is it perfect tree(should return true): " + PerfectTree.isPerfect());
        
    }
}
