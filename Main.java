package FamilyTree;
import java.util.*;
import java.util.ArrayList;
 public class Main {
     public static void main(String[] args) {
         NodeImp tree = new NodeImp();
         tree.display();
     }
 }
     class NodeImp {
         class Node {

             int data;
             ArrayList<Node> children;

             Node(int data) {
                 this.data = data;
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
                 System.out.println("There is no parent of this. ");
                 System.out.println(" Enter the parent..");
             } else {
                 System.out.println(" enter the data for " + i + " the child of " + parent.data);

             }
             int data = s.nextInt();
             Node node = new Node(data);

             System.out.println("number of children for" + node.data);
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
             String str= node.data + "---->";

             for (Node child: node.children){
                 str += child.data + " ,  ";
             }
             System.out.println(str);
             for (Node child: node.children){
                 display(child);
             }
         }
     }
