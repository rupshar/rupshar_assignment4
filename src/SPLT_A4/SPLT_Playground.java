package SPLT_A4;

public class SPLT_Playground {
  public static void main(String[] args){
    genTest();
  }
  
  public static void genTest(){
	//Case 2: insert(a), insert(b), remove(a), size() == 1
    SPLT tree= new SPLT();
    tree.insert("a");
    tree.insert("a");
    
    //tree.insert("e");
    //System.out.println("Min: " + tree.findMin());
    System.out.println("Root: " + tree.getRoot());
    System.out.println("Empty: " + tree.empty());
    //System.out.println("Right: " + tree.getRoot().left.right);
    System.out.println("size is "+tree.size());
    //System.out.println("height is " + tree.height());
    
    //printLevelOrder(tree);
    printInOrder(tree.getRoot());
  }

    static void printLevelOrder(SPLT tree){ 
    //will print your current tree in Level-Order...Requires a correct height method
    //https://en.wikipedia.org/wiki/Tree_traversal
      int h=tree.getRoot().getHeight();
      for(int i=0;i<=h;i++){
        System.out.print("Level "+i+":");
        printGivenLevel(tree.getRoot(), i);
        System.out.println();
      }
      
    }
    static void printGivenLevel(BST_Node root,int level){
      if(root==null)return;
      if(level==0)System.out.print(root.data+" ");
      else if(level>0){
        printGivenLevel(root.left,level-1);
        printGivenLevel(root.right,level-1);
      }
    }
   static void printInOrder(BST_Node root){
      if(root!=null){
      printInOrder(root.getLeft());
      System.out.print(root.getData()+" ");
      printInOrder(root.getRight());
      }
  }
  
}