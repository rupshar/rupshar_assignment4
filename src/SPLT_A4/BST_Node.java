package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node parent; //parent...not necessarily required, but can be useful in splay tree
	boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
	//I personally use it to indicate to my SPLT insert whether or not we increment size.

	BST_Node(String data){ 
		this.data=data;
		this.justMade=true;
	}

	BST_Node(String data, BST_Node left,BST_Node right,BST_Node parent){ //feel free to modify this constructor to suit your needs
		this.data=data;
		this.left=left;
		this.right=right;
		this.parent=parent;
		this.justMade=true;
	}

	// --- used for testing  ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

	public String getData(){ return data; }
	public BST_Node getLeft(){ return left; }
	public BST_Node getRight(){ return right; }
	public BST_Node getParent(){ return parent; }

	// --- end used for testing -------------------------------------------


	// --- Some example methods that could be helpful ------------------------------------------
	//
	// add the meat of correct implementation logic to them if you wish

	// you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
	// make them take more or different parameters
	// have them return different types
	//
	// you may use recursive or iterative implementations

	/*
  public BST_Node containsNode(String s){ return false; } //note: I personally find it easiest to make this return a Node,(that being the node splayed to root), you are however free to do what you wish.
  public BST_Node insertNode(String s){ return false; } //Really same logic as above note
  public boolean removeNode(String s){ return false; } //I personal do not use the removeNode internal method in my impl since it is rather easily done in SPLT, feel free to try to delegate this out, however we do not "remove" like we do in BST
  public BST_Node findMin(){ return left; } 
  public BST_Node findMax(){ return right; }
  public int getHeight(){ return 0; }

  private void splay(BST_Node toSplay) { return false; } //you could have this return or take in whatever you want..so long as it will do the job internally. As a caller of SPLT functions, I should really have no idea if you are "splaying or not"
                        //I of course, will be checking with tests and by eye to make sure you are indeed splaying
                        //Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!
	 */

	// --- end example methods --------------------------------------




	// --------------------------------------------------------------------
	// you may add any other methods you want to get the job done
	// --------------------------------------------------------------------


	public BST_Node containsNode(String s) {
		int comp = s.compareTo(this.data);
		BST_Node currentNode = this;
		if(comp == 0) {
			splay(currentNode);
			return currentNode;
		} else if(comp < 0) {
			if(this.left == null) {
				splay(currentNode);
				return currentNode;
			}
			return this.left.containsNode(s);
		} else {
			if(this.right == null) {
				splay(currentNode);
				return currentNode;
			}
			return this.right.containsNode(s);
		}
	}

	public BST_Node insertNode(String s) {
		int comp = s.compareTo(this.data);
		BST_Node new_node = new BST_Node(s);
		if(s.equals(this.data)) {
			splay(this);
			return this;
		} else if(comp > 0) {
			if(this.right == null) {
				this.right = new_node;
				this.right.parent = this;
				this.splay(this.right);
				return this.right;
			}
			return this.right.insertNode(s);
		} else {
			if(this.left == null) {
				this.left = new_node;
				this.left.parent = this;
				this.splay(this.left);
				return this.left;
			}
			return this.left.insertNode(s);
		}
	}
	
	public BST_Node getParent(String s) {
		BST_Node parent = this;
		BST_Node left_child = parent.left;
		BST_Node right_child = parent.right;
		if(left_child == null || right_child == null) {
			return this.getCurrent(s).getParent();
		} else if(left_child.data.equals(s) || right_child.data.equals(s)) {
			return parent;
		} else {
			int comp = s.compareTo(parent.data);
			if(comp > 0) {
				return parent.right.getParent(s);
			} else {
				return parent.left.getParent(s);
			}
		}
	}

	public BST_Node getCurrent(String s) {
		int comp = s.compareTo(this.data);
		if(comp == 0) {
			return this;
		} else if(comp > 0) {
			return this.right.getCurrent(s);
		} else {
			return this.left.getCurrent(s);
		}
	}

	public BST_Node findMin() {
		BST_Node minimum = this;
		if(minimum.left == null) {
			this.splay(minimum);
			return minimum;
		} else {
			return minimum.left.findMin();
		}
	}

	public BST_Node findMax() {
		BST_Node maximum = this;
		if(maximum.right == null) {
			this.splay(maximum);
			return maximum;
		} else {
			return maximum.right.findMax();
		}
	}

	public int getHeight() {
		int l=0;
		int r=0;
		if(left!=null) {
			l+=left.getHeight()+1;
		}
		if(right!=null) {
			r+=right.getHeight()+1;
		}
		return Integer.max(l, r);
	}

	public String toString(){
		return "Data: "+this.data+", Left: "+((this.left!=null)?left.data:"null")
				+",Right: "+((this.right!=null)?right.data:"null");
	}

	private void splay(BST_Node toSplay) {
		boolean splayed = false;

		while(splayed == false) {
			if(toSplay.parent == null) {
				splayed = true;
				break;
			}
			BST_Node parent = toSplay.parent;
			if(parent.parent == null) {
				if(parent.left == toSplay) {
					if(toSplay.right == null) {
						parent.left = null;
						toSplay.right = parent;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						parent.left = toSplay.right;
						toSplay.right.parent = parent;
						toSplay.right = parent;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				} else if(parent.right == toSplay) {
					if(toSplay.left == null) {
						parent.right = null;
						toSplay.left = parent;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						parent.right = toSplay.left;
						toSplay.left.parent = parent;
						toSplay.left = parent;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				}
			}
			BST_Node grandparent = parent.parent;
			if(grandparent.parent == null) {
				if(grandparent.left == parent && parent.left == toSplay) {
					if(toSplay.right == null && parent.right == null) {
						grandparent.left = null;
						parent.left = null;
						parent.right = grandparent;
						toSplay.right = parent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.right == null && parent.right != null) {
						grandparent.left = parent.right;
						parent.right.parent = grandparent;
						parent.left = null;
						parent.right = grandparent;
						toSplay.right = parent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.right != null && parent.right == null) {
						grandparent.left = null;
						parent.left = toSplay.right;
						toSplay.right.parent = parent;
						toSplay.right = parent;
						parent.right = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						grandparent.left = parent.right;
						parent.right.parent = grandparent;
						parent.left = toSplay.right;
						toSplay.right.parent = parent;
						toSplay.right = parent;
						parent.right = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				} else if(grandparent.right == parent && parent.right == toSplay) {
					if(toSplay.left == null && parent.left == null) {
						grandparent.right = null;
						parent.right = null;
						toSplay.left = parent;
						parent.left = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left == null && parent.left != null) {
						grandparent.right = parent.left;
						parent.left.parent = grandparent;
						parent.right = null;
						toSplay.left = parent;
						parent.left = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left != null && parent.left == null) {
						grandparent.right = null;
						parent.right = toSplay.left;
						toSplay.left.parent = parent;
						toSplay.left = parent;
						parent.left = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						grandparent.right = parent.left;
						parent.left.parent = grandparent;
						parent.right = toSplay.left;
						toSplay.left.parent = parent;
						toSplay.left = parent;
						parent.left = grandparent;
						parent.parent = toSplay;
						grandparent.parent = parent;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				} else if(grandparent.left == parent && parent.right == toSplay) {
					if(toSplay.left == null && toSplay.right == null) {
						grandparent.left = null;
						parent.right = null;
						toSplay.left = parent;
						toSplay.right = grandparent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left == null && toSplay.right != null) {
						grandparent.left = toSplay.right;
						toSplay.right.parent = grandparent;
						parent.right = null;
						toSplay.left = parent;
						toSplay.right = grandparent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left != null && toSplay.right == null) {
						grandparent.left = null;
						parent.right = toSplay.left;
						toSplay.left.parent = parent;
						toSplay.left = parent;
						toSplay.right = grandparent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						grandparent.left = toSplay.right;
						toSplay.right.parent = grandparent;
						parent.right = toSplay.left;
						toSplay.left.parent = parent;
						toSplay.left = parent;
						toSplay.right = grandparent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				} else if(grandparent.right == parent && parent.left == toSplay) {
					if(toSplay.left == null && toSplay.right == null) {
						grandparent.right = null;
						parent.left = null;
						toSplay.left = grandparent;
						toSplay.right = parent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left == null && toSplay.right != null) {
						grandparent.right = null;
						parent.left = toSplay.right;
						toSplay.right.parent = parent;
						toSplay.left = grandparent;
						toSplay.right = parent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else if(toSplay.left != null && toSplay.right == null) {
						grandparent.right = toSplay.left;
						toSplay.left.parent = grandparent;
						parent.left = null;
						toSplay.left = grandparent;
						toSplay.right = parent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					} else {
						grandparent.right = toSplay.left;
						toSplay.left.parent = grandparent;
						parent.left = toSplay.right;
						toSplay.right.parent = parent;
						toSplay.left = grandparent;
						toSplay.right = parent;
						grandparent.parent = toSplay;
						parent.parent = toSplay;
						toSplay.parent = null;
						splayed = true;
						break;
					}
				}
			}
			BST_Node great_grandparent = grandparent.parent;
			if(great_grandparent.left == grandparent && grandparent.left == parent && parent.left == toSplay) {
				if(parent.right == null && toSplay.right == null) {
					grandparent.left = null;
					parent.left = null;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(parent.right == null && toSplay.right != null) {
					grandparent.left = null;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(parent.right != null && toSplay.right == null) {
					grandparent.left = parent.right;
					parent.right.parent = grandparent;
					parent.left = null;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else {
					grandparent.left = parent.right;
					parent.right.parent = grandparent;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				}
			} else if(great_grandparent.right == grandparent && grandparent.left == parent && parent.left ==toSplay) {
				if(parent.right == null && toSplay.right == null) {
					grandparent.left = null;
					parent.left = null;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					great_grandparent.right = toSplay;
					toSplay.parent = great_grandparent;
				} else if(parent.right == null && toSplay.right != null) {
					grandparent.left = null;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					great_grandparent.right = toSplay;
					toSplay.parent = great_grandparent;
				} else if(parent.right != null && toSplay.right == null) {
					grandparent.left = parent.right;
					parent.right.parent = grandparent;
					parent.left = null;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					great_grandparent.right = toSplay;
					toSplay.parent = great_grandparent;
				} else {
					grandparent.left = parent.right;
					parent.right.parent = grandparent;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.right = parent;
					parent.right = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					great_grandparent.right = toSplay;
					toSplay.parent = great_grandparent;
				}
			} else if(great_grandparent.left == grandparent && grandparent.right == parent && parent.right == toSplay) {
				if(parent.left == null && toSplay.left == null) {
					grandparent.right = null;
					parent.left = null;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(parent.left == null && toSplay.left != null) {
					grandparent.right = null;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(parent.left != null && toSplay.left == null) {
					grandparent.right = parent.left;
					parent.left.parent = grandparent;
					parent.right = null;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else {
					grandparent.right = parent.left;
					parent.left.parent = grandparent;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				}
			} else if(great_grandparent.right == grandparent && grandparent.right == parent && parent.right == toSplay) {
				if(parent.left == null && toSplay.left == null) {
					grandparent.right = null;
					parent.right = null;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(parent.left == null && toSplay.left != null) {
					grandparent.right = null;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(parent.left != null && toSplay.left == null) {
					grandparent.right = parent.left;
					parent.left.parent = grandparent;
					parent.right = null;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else {
					grandparent.right = parent.left;
					parent.left.parent = grandparent;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					parent.left = grandparent;
					parent.parent = toSplay;
					grandparent.parent = parent;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				}
			} else if(great_grandparent.left == grandparent && grandparent.right == parent && parent.left == toSplay) {
				if(toSplay.left == null && toSplay.right == null) {
					grandparent.right = null;
					parent.left = null;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(toSplay.left == null && toSplay.right != null) {
					grandparent.right = null;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(toSplay.left != null && toSplay.right == null) {
					grandparent.right = toSplay.left;
					toSplay.left.parent = grandparent;
					parent.left = null;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else {
					grandparent.right = toSplay.left;
					toSplay.left.parent = grandparent;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				}
			} else if(great_grandparent.right == grandparent && grandparent.right == parent && parent.left == toSplay) {
				if(toSplay.left == null && toSplay.right == null) {
					grandparent.right = null;
					parent.left = null;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(toSplay.left == null && toSplay.right != null) {
					grandparent.right = null;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(toSplay.left != null && toSplay.right == null) {
					grandparent.right = toSplay.left;
					toSplay.left.parent = grandparent;
					parent.left = null;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else {
					grandparent.right = toSplay.left;
					toSplay.left.parent = grandparent;
					parent.left = toSplay.right;
					toSplay.right.parent = parent;
					toSplay.left = grandparent;
					toSplay.right = parent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				}
			} else if(great_grandparent.left == grandparent && grandparent.left == parent && parent.right == toSplay) {
				if(toSplay.right == null && toSplay.left == null) {
					grandparent.left = null;
					parent.right = null;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(toSplay.right == null && toSplay.left != null) {
					grandparent.left = null;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else if(toSplay.right != null && toSplay.left == null) {
					grandparent.left = toSplay.right;
					toSplay.right.parent = grandparent;
					parent.right = null;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				} else {
					grandparent.left = toSplay.right;
					toSplay.right.parent = grandparent;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.left = toSplay;
				}
			} else if(great_grandparent.right == grandparent && grandparent.left == parent && parent.right == toSplay) {
				if(toSplay.right == null && toSplay.left == null) {
					grandparent.left = null;
					parent.right = null;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(toSplay.right == null && toSplay.left != null) {
					grandparent.left = null;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else if(toSplay.right != null && toSplay.left == null) {
					grandparent.left = toSplay.right;
					toSplay.right.parent = grandparent;
					parent.right = null;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				} else {
					grandparent.left = toSplay.right;
					toSplay.right.parent = grandparent;
					parent.right = toSplay.left;
					toSplay.left.parent = parent;
					toSplay.left = parent;
					toSplay.right = grandparent;
					grandparent.parent = toSplay;
					parent.parent = toSplay;
					toSplay.parent = great_grandparent;
					great_grandparent.right = toSplay;
				}
			}
		}

	}
	
	public BST_Node findRoot() {
		if(this.parent == null) {
			return this;
		}
		return this.parent.findRoot();
	}

}