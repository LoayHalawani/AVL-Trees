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
			return key + "";
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
		return node;
	}

	private int getHeight(Node node) {
		if(node == null) {
			return 0;
		}
		return node.height;
	}

	private int getBalanceFactor(Node node) {
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

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		int balanceFactor = getBalanceFactor(node);

		// LL
		if(balanceFactor > 1 && node.key < node.leftChild.key) {
			return rotateRight(node);
		}

		// RR
		if(balanceFactor < -1 && node.key > node.rightChild.key) {
			return rotateLeft(node);
		}

		// LR
		if(balanceFactor > 1 && node.key > node.leftChild.key) {
			node.leftChild = rotateLeft(node.leftChild);
			return rotateRight(node);
		}

		// RL
		if(balanceFactor < -1 && node.key < node.rightChild.key) {
			node.rightChild = rotateRight(node.rightChild);
			return rotateLeft(node);
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
		System.out.print(node + " ");
		preOrderTraverse(node.leftChild);
		preOrderTraverse(node.rightChild);
	}

	private void inOrderTraverse(Node node) {
		if(node == null) {
			return;
		}
		inOrderTraverse(node.leftChild);
		System.out.print(node + " ");
		inOrderTraverse(node.rightChild);
	}

	private void postOrderTraverse(Node node) {
		if(node == null) {
			return;
		}
		postOrderTraverse(node.leftChild);
		postOrderTraverse(node.rightChild);
		System.out.print(node + " ");
	}

	// Driver
	public static void main(String[] args) {
		AVL tree = new AVL();

		System.out.println("Inserting nodes into an AVL Tree...\n");
		
		tree.insert(12);
		tree.insert(8);
		tree.insert(18);
		tree.insert(5);
		tree.insert(11);
		tree.insert(17);
		tree.insert(4);

		System.out.println("Inorder traversal:");
		tree.inOrderTraverse(tree.root);

		System.out.println("\n\nDeleting node with key = 11...");

		tree.delete(11);

		System.out.println("\nInorder traversal:");
		tree.inOrderTraverse(tree.root);

		System.out.println("\n\nDeleting node with key = 8...");

		tree.delete(8);

		System.out.println("\nInorder traversal:");
		tree.inOrderTraverse(tree.root);

		System.out.println("\n\nSearching for node with key = 18...");

		tree.search(18);

		System.out.println("\nSearching for node with key = 11...");

		tree.search(11);

		System.out.println("\nThank you for trying my BST program!");
	}
}