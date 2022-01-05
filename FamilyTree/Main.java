package FamilyTree;
import java.util.*;
import java.util.ArrayList;
 public class Main {

     public static void main(String[] args) {
         NodeImp tree = new NodeImp();
         tree.display();
         Scanner s = new Scanner(System.in);
     }
 }
     class NodeImp {
         class Node {

             int currentParent;
             ArrayList<Node> children;

             Node(int data) {
                 this.currentParent = currentParent;
                 children = new ArrayList<>();
             }
         }

         private Node root;

         NodeImp() {
             Scanner s = new Scanner(System.in);
             this.root = constructNI(s, null, 0);
         }

         private Node constructNI(Scanner s, Node parent, int i) {
             if (parent == null) {
                 System.out.println("There is no parent of this tree. ");
                 System.out.println(" Enter the parent..");
                 String name = s.nextLine();
                 System.out.println("Name is: " + name);
                 System.out.println("How many children "+ name + " have?");
             } else {
                 System.out.println(" Enter the data for " + i + " the child of " + parent.currentParent);

             }

             int currentParent = s.nextInt();
             Node node = new Node(currentParent);

             System.out.println("Number of children for " + node.currentParent);
             int n = s.nextInt();

             for (int c = 0; c < n; c++) {
                 Node child = constructNI(s, node, c);
                 node.children.add(child);
             }
             return node;
         }
         public void display(){
             display(this.root);
         }
         private void display(Node node){
             String st= node.currentParent + "---->";

             for (Node child: node.children){
                 st += child.currentParent + " ,  ";
             }
             System.out.println(st);
             for (Node child: node.children){
                 display(child);
             }
         }

     }
//jhdfjvhvbdfh