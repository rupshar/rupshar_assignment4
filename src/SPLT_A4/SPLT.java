package SPLT_A4;

public class SPLT implements SPLT_Interface{
	private BST_Node root;
	private int size;

	public SPLT() {
		this.size = 0;
	} 

	public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
		return root;
	}

	@Override
	public void insert(String s) {
		if(size == 0) {
			root = new BST_Node(s);
			size++;
		} else {
			if(this.contains(s)) {
				root.insertNode(s);
				root = root.findRoot();
			} else {
				root.insertNode(s);
				size++;
				root = root.findRoot();
			}
		}
	}

	@Override
	public void remove(String s) {
		if(size == 1) {
			root = null;
			size--;
		} else {
			BST_Node contains = root.containsNode(s);
			root = contains;
			if(s.equals(contains.data)) {
				BST_Node root_node = root.getCurrent(s);
				BST_Node left_subtree = root_node.left;
				BST_Node right_subtree = root_node.right;
				if(left_subtree == null && right_subtree != null) {
					root = right_subtree;
					size--;
				} else if(left_subtree != null && right_subtree == null) {
					BST_Node left_max = left_subtree.findMax();
					root = left_max;
					size--;
					root.right = null;
				} else {
					left_subtree.parent = null;
					right_subtree.parent = null;
					BST_Node left_max = left_subtree.findMax();
					BST_Node left_root = left_max;
					root = left_root;
					size--;
					left_root.right = right_subtree;
					right_subtree.parent = left_root;
				}
			}
		}
	}

	@Override
	public String findMin() {
		if(size == 0) {
			return null;
		} else {
			BST_Node min = root.findMin();
			root = root.findRoot();
			return min.getData();
		}
	}

	@Override
	public String findMax() {
		if(size == 0) {
			return null;
		} else {
			BST_Node max = root.findMax();
			root = root.findRoot();
			return max.getData();
		}
	}

	@Override
	public boolean empty() {
		if(size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean contains(String s) {
		if(root == null) {
			return false;
		} else {
			BST_Node contains = root.containsNode(s);
			root = contains;
			if(root.data.equals(s)) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int height() {
		if(root == null) {
			return -1;
		} else {
			return root.getHeight();
		}
	}  

}