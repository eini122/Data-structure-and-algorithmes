/*
 * It is a single linked list which includes four method that user can use.
 * add method allow used add name and automaticlly sort it. 
 * remove method allow user remove a name from the list
 * remove letter method allow user to remove all name under one letter
 * find method will return true or false to check if the name in the list or not
 * @author Kaitian Li 
 * 2/14/2019
 */
package namelist;
import java.util.*;
public class NameList{
    Scanner in = new Scanner(System.in);
    private Node head;//variable of head
    private int size;//variable count size
    //constractor that ceate new list
    public NameList(){
        head = new Node(null, null);
        size = 0;
    }
    //add node base on the list and input string
    public void add(String str){
        //check string is not null, if it is null, return NullPointerException
        if(str == null || str.trim() == null){
            throw new NullPointerException();
        }
        //using format method to change the first letter uppercase 
        //and others lowercase
        str = format(str);
        //get the first letter of the string
        String temp = str.substring(0,1);
        //check the list size, if it is zero, add temp to the first node
        //and str to the node after temp
        if (size == 0)
        {
            head.next = new Node(temp, new Node(str, null));
            size += 2;
        }
        //if the first letter is not in the list, add the first letter and string
        else{
            if(!checkExit(temp)){
                Node p = getLocation(str);//pointer to get the location should be in frond of str
                Node n = new Node(temp, new Node(str, p.next));
                p.next = n;
                size+=2;
            }
            //else just add the node into currect position
            else{
                Node p = getLocation(str);
                Node n = new Node(str, p.next);
                p.next = n;
                size++;
            }
            
        }
    }
    //remove method that remove the require node from the list
    public void remove(String str){
        //string cannot be null
        if(str == null){
            throw new NullPointerException();
        }
        //format the input
        str = format(str);
        //set the pointer locate before the input
        Node p = getLocation(str);
        //if do not have the node, throw exception
        if(!p.next.data.equals(str)){
            throw new NoSuchElementException();
        }
        else{
            //if the node is the only one of the letter
            //remove the letter and the node
            //if the pointer's length equal 1 and the lenth of the node after the remove node is 1
            //it means the remove node is the only one of the letter
            if(p.data.length() == 1){
                if(p.next.next != null){
                    if(p.next.next.data.length() == 1){
                        p.data = p.next.next.data;
                        p.next = p.next.next.next;
                        size -= 2;
                    }
                    //remove the input only
                    else{
                        p.next = p.next.next;
                        size --;
                    }
                }
                //if the node after the remove node is null
                //it means it is the end of the list
                else{
                    Node p1 = getLocation(p.data);//set the pointer before the pointer p 
                    p1.next = null;//set the node after p1 is null
                    size -= 2;
                }
            }
            //not special situation, remove the input only
            else
            {
                p.next = p.next.next;
                size --;
            }
        }
    }
    //method that remove all nodes contains the input letter
    public void removeLetter(char input){
        //change the data type from char to string
        String str = Character.toString(input);
        //set it to uppercase letter
        str = str.toUpperCase();
        //set the pointer
        Node p = getLocation(str);
        //check the input is in the list
        if(!p.next.data.equals(str)){
            throw new NoSuchElementException();
        }
        //sent another pointer that two location after p
        Node t = p.next.next;
        int count = 1;
        //find the next single letter node or null 
        while(t.data.length() != 1 && t.next!=null){
            t = t.next;
            count++;
        }
        //if the pointer.next is null, connect pointer p and null
        if(t.next == null){
            p.next = null;
            size -= count+1;
        }
        //else, just connect the pointer p and the single letter node
        else{
            p.next = t;
            size -= count;
        }
    }
    //return true if find the str, else return false
    public Boolean find(String str){
        str = format(str);
        Node p = getLocation(str);
        return p.next.data.equals(str);
    }
    //return the size of the list
    public int size(){
        return size;
    }
    //format the string into first letter uppercase and other lowercase
    private String format(String str){
        String temp = str.substring(0, 1).toUpperCase()+str.substring(1).toLowerCase();
        return temp;
    }
    //check is the first character exit in the list
    //if exit return true, else return flase
    private boolean checkExit(String str){
        Node t;
        t=head;
        //through the whole list to check the list exit
        while(t.next != null){
            if(str.equals(t.data)){
                return true;
            }
            else t = t.next;
        }
        return false;
    }
    //method that get the location before the input string
    private Node getLocation(String str){
        Node previous = head;//pointer at head
        Node t = head.next;//pointer at the node atfer head
        //while loop to check the pointed achieve the end of list
        //or find the node is less than the input string
        while(t != null && t.data.compareTo(str) <0){
            previous = t;//p to pointer t position
            t=t.next;//t go to the next node
        }
        //return the node brefore t
        return previous; 
    }
    @Override
    //using string builder to set the output format as letter then the name string
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node p = head.next;
        while(p!= null){
            if(p.data.length() == 1){
                sb.append(p.data).append("\n");
            }
            else{
                sb.append("   ").append(p.data).append("\n");
            }
            p=p.next;
        }
        String str = new String(sb);
        return str;
    }
    //node class include set data and the next node
    private class Node{
        String data;
        Node next;
        Node(String data, Node next){
            this.data = data;
            this.next = next;
        }
    }
    public static void main(String[] args){
        //test the adding function
        NameList list = new NameList();
        System.out.println("Print the list after adding some name: ");
        list.add("peter");
        list.add("austin");
        list.add("poal");
        list.add("place");
        list.add("ford");
        list.add("lol");
        list.add("ZZ");
        list.add("adeal");
        //print the list after adding node
        System.out.println(list);
        //test the find method, should display true
        System.out.println("Print the resule of find name peter in the list, it should be true.");
        System.out.println(list.find("peter"));
        System.out.println();
        
        //test the remove method
        System.out.println("Print the list by remove Peter, austin, and poal: ");
        list.remove("peter");
        list.remove("austin");
        list.remove("poal");
        //print the list after remove some node
        System.out.println(list);
        
        //test the removeLetter method
        System.out.println("Print the list after remove all the name contains 'A': ");
        list.removeLetter('a');
        //print the list after remove the letter and the contains names
        System.out.println(list);
        
        //test the find method, should display false
        System.out.println("Print the result of find name peter in the list, the result should be false.");
        System.out.println(list.find("peter"));
        

    }
}
