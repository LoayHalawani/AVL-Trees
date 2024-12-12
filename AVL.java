public class AVL {
	
	private class Node {
		public int key, height;

		public Node leftChild, rightChild;

		public Node(int key) {
			this.key = key;
			height = 1;
			leftChild = rightChild = null;
		}

		@Override
		public String toString() {
			return key + " ";
		}
	}

	private Node root;

	// Insertion
	private Node insertNode(Node node, int key) {
		if(node == null) {
			return new Node(key);
		}
		if(key < node.key) {
			node.leftChild = insertNode(node.leftChild, key);
		}
		else if(key > node.key) {
			node.rightChild = insertNode(node.rightChild, key);
		}

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;

		return node;
	}

	private int getHeight(Node node) {
		if(node == null) {
			return 0;
		}
		return node.height;
	}

	private int computeBalanceFactor(Node node) {
		if(node == null) {
			return 0;
		}
		return getHeight(node.leftChild) - getHeight(node.rightChild);
	}

	// Rotations
	private Node rotateLeft(Node node) {
		if(node == null) {
			return null;
		}
		
		Node newNode = node.rightChild;
		node.rightChild = newNode.leftChild;
		newNode.leftChild = node;

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		newNode.height = Math.max(getHeight(newNode.leftChild), getHeight(newNode.rightChild)) + 1;

		return newNode;
	}

	private Node rotateRight(Node node) {
		if(node == null) {
			return null;
		}
		
		Node newNode = node.leftChild;
		node.leftChild = newNode.rightChild;
		newNode.rightChild = node;

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		newNode.height = Math.max(getHeight(newNode.leftChild), getHeight(newNode.rightChild)) + 1;

		return newNode;
	}

	// Balancing
	private Node balanceTree(Node node) {
		if(node == null) {
			return null;
		}
		
		int balanceFactor = computeBalanceFactor(node);		
		
		if(balanceFactor > 1) {
			// LL
			if(computeBalanceFactor(node.leftChild) >= 0) {
				return rotateRight(node);
			}
			// LR
			else {
				node.leftChild = rotateLeft(node.leftChild);
				return rotateRight(node);
			}
		}

		if(balanceFactor < -1) {
			// RR
			if(computeBalanceFactor(node.rightChild) <= 0) {
				return rotateLeft(node);
			}
			// RL
			else {
				node.rightChild = rotateRight(node.rightChild);
				return rotateLeft(node);
			}
		}
		return node;
	}

	private void insert(int key) {
		root = insertNode(root, key);
		root = balanceTree(root);
	}

	// Deletion
	private Node deleteNode(Node node, int key) {
		if(node == null) {
			return null;
		}
		if(key < node.key) {
			node.leftChild = deleteNode(node.leftChild, key);
		}
		else if(key > node.key) {
			node.rightChild = deleteNode(node.rightChild, key);
		}
		else {
			if(node.rightChild == null) {
				return node.leftChild;
			}
			else if(node.leftChild == null) {
				return node.rightChild;
			}
			else {
				Node minNode = findMinNode(node);
				node.key = minNode.key;
				node.rightChild = deleteNode(node.rightChild, minNode.key);
			}
		}

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;

		return node;
	}

	private Node findMinNode(Node node) {
		if(node == null) {
			return null;
		}
		if(node.leftChild != null) {
			return findMinNode(node.leftChild);
		}
		return node;
	}

	private void delete(int key) {
		root = deleteNode(root, key);
		root = balanceTree(root);
	}

	// Searching
	private Node searchNode(Node node, int key) {
		if(node == null) {
			return null;
		}
		if(key < node.key) {
			return searchNode(node.leftChild, key);
		}
		else if(key > node.key) {
			return searchNode(node.rightChild, key);
		}
		return node;
	}

	private void search(int key) {
		Node node = searchNode(root, key);
		if(node == null) {
			System.out.println("Node not found :(");
		}
		else {
			System.out.println("Node found :)");
		}
	} 

	// Traversals
	private void preOrderTraverse(Node node) {
		if(node == null) {
			return;
		}
		System.out.print(node);
		preOrderTraverse(node.leftChild);
		preOrderTraverse(node.rightChild);
	}

	private void inOrderTraverse(Node node) {
		if(node == null) {
			return;
		}
		inOrderTraverse(node.leftChild);
		System.out.print(node);
		inOrderTraverse(node.rightChild);
	}

	private void postOrderTraverse(Node node) {
		if(node == null) {
			return;
		}
		postOrderTraverse(node.leftChild);
		postOrderTraverse(node.rightChild);
		System.out.print(node);
	}

	// Driver
	public static void main(String[] args) {
		AVL tree = new AVL();

		System.out.println("Inserting nodes...\n");

		tree.insert(61);
		tree.insert(53);
		tree.insert(35);
		tree.insert(27);
		tree.insert(49);

		System.out.println("Inorder traversal:");
		tree.inOrderTraverse(tree.root);

		System.out.println("\n\nDeleting node with key = 61...");

		tree.delete(61);

		System.out.println("\nInorder traversal:");
		tree.inOrderTraverse(tree.root);

		System.out.println("\n\nSearching for node with key = 35...");

		tree.search(35);

		System.out.println("\nSearching for node with key = 61...");

		tree.search(61);
	}
}