package com.complete_bst.services;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Create_Binary_tree {
	private static Scanner sc=new Scanner(System.in);
	public static BinarySearchTree bst = new BinarySearchTree();
	//These 3 arrays below are required to print the structure of the binary search tree
	public static int arr[]; //This array stores the node values in exact same order as the user enters from the console
	static int verpos[];//This array stores the vertical positions of the corresponding node values from the root node i.e. the heights of each node value,with the height of root node being 0.
	static int horpos[];//This array stores the horizontal positions of corresponding node values from the extreme left with the node value being at the extreme left at horizontal position 0.
   //Below method fiils up the horpos array, by storing the horizontal positions of corresponding node values
	static void horposFunc() {
		int len = arr.length;
		int newarr[] = new int[len];
		int i = 0;
		for (int k : arr) {
			newarr[i] = k;
			i++;
		}
		horpos = new int[len];
		merge_Sort(newarr, 0, len - 1);

		for (int j = 0; j < len; j++) {
			for (int k = 0; k < len; k++) {
				if (arr[j] == newarr[k]) {
					horpos[j] = k;
					break;
				}
			}
		}

	}
    //Below method returns the height of a node value. It takes 3 arguments viz node value as n, root node as start and height of root node as h.
	static int nodeHeight(int n, BinaryNode start, int h) {
		if (start == null) {
			return -1;
		}
		BinaryNode temp = start;
		if (temp.data == n) {
			return h;
		} else {
			if (n < temp.data) {
				return nodeHeight(n, start.left, h + 1);
			} else {
				return nodeHeight(n, start.right, h + 1);
			}
		}
	}
    //Below method fills up the verpos array by storing the heights of the corresponding node values
	static void verposFunc() {
		verpos = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			verpos[i] = nodeHeight(arr[i], bst.root, 0);

		}

	}
    //Below method prints the structure of the entire binary search tree
	static void display() {

		int height = 0;
		horposFunc();
		verposFunc();
		int d;// iterator variable to check if a certain node belongs to a particular height
		int num = 0;
		int k;
		int i;
		while (num < arr.length) {
			int array[] = new int[arr.length];
			int a[] = new int[arr.length];
			for (int j = 0; j < array.length; j++) {
				array[j] = -1;
				a[j] = Integer.MAX_VALUE;
			}

			k=0;
			i = 0;
			d = 0;
			while (d < arr.length) {

				if (verpos[d] == height) {
					array[i] = arr[d];
					a[i] = horpos[d];
					i++;
				}
				d++;
			}
			merge_Sort(a, 0, i - 1);
			merge_Sort(array, 0, i - 1);
			int it = 0;
			int next = 0;
			while (it < arr.length) {
				if (array[it] == -1) {
					break;
				}
				try {
					for (int id = k; id < a[next]; id++) {
						System.out.print(" ");
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("dhor dhor");
					return;
				}

				System.out.print(array[it]);
				num++;
				k = a[next]+1;
				next++;
				it++;

			}

			height++;
			System.out.println();
		}
	}
    //Below method prints the structure of the entire right-skewed-tree
	static void rightSkewedDisplay() {
		BinaryNode bn = bst.root;
		int n = 1;
		while (bn != null) {
			System.out.println(bn.data);
			if (bn.right != null) {
				for (int i = 0; i < n; i++) {
					System.out.print(" ");
				}
			}
			n++;
			bn = bn.right;
		}

	}

	public static void merge(int arr[], int start, int mid, int end) {
		int n1 = mid - start + 1;
		int n2 = end - mid;
		int i, j;
		int larr[] = new int[n1];
		int rarr[] = new int[n2];

		for (i = 0; i < n1; i++)
			larr[i] = arr[start + i];
		for (j = 0; j < n2; j++)
			rarr[j] = arr[mid + j + 1];
		i = j = 0;
		for (int k = start; k < start + n1 + n2; k++) {
			if (i == n1) {
				arr[k] = rarr[j];
				j += 1;
			} else if (j == n2) {
				arr[k] = larr[i];
				i += 1;
			} else if (larr[i] < rarr[j]) {
				arr[k] = larr[i];
				i += 1;

			} else {
				arr[k] = rarr[j];
				j += 1;

			}
		}
	}

	public static void merge_Sort(int arr[], int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			merge_Sort(arr, left, mid);
			merge_Sort(arr, mid + 1, right);
			merge(arr, left, mid, right);

		}
	}
    //Below method starts solving the problem after getting the inputs from the user i.e. starts converting the binary search tree into a right skewed tree by calling another method called the right-skewed-converter
	public static void solve() {
		boolean b = false;
		String st;
		if (arr == null) {
			return;
		}
		if (bst.root == null) {
			return;
		}
		System.out.println("Do you want to see the structure of the original Binary search tree ? Type 'y' for yes or 'n' for no ");
		try {
		st = sc.next();
		}catch(InputMismatchException e) {
			System.out.println("You did not enter the correct data format. Character type data should be provided.");
			return;
		}
		if (st.equals("y") || st.equals("Y")) {
			b = true;
		} else if (st.equals("n") || st.equals("N")) {
			b = false;
		} else {
			System.out.println("Invalid character entered");
			return;
		}

		if (b) {
			System.out.println("Original Binary Search Tree:");
			display();

		}
		rightSkewedConverter(bst.root);// The right skewed converter converts the original binary search tree into a right skewed tree
		System.out.println("The original binary search tree is converted into a right-skewed tree in place.It actually modified the tree without creating a completely new tree.");
		System.out.println();
		System.out.println("Do you want to see the structure of the Binary search tree after converting it into a right skewed tree ? Type 'y' for yes or 'n' for no ");
		try {
		st = sc.next();
		}catch(InputMismatchException e) {
			System.out.println("You did not enter the correct data format. Character type data should be provided.");
			return;
		}
		if (st.equals("y") || st.equals("Y")) {
			b = true;
		} else if (st.equals("n") || st.equals("N")) {
			b = false;
		} else {
			System.out.println("Invalid character entered");
			return;
		}
		if (b) {

			System.out.println("After converting the original tree to right skewed tree :");
			rightSkewedDisplay();

		}
		BinaryNode bnode = bst.root;
		System.out.print("Desired output : ");
		while (bnode != null) {
			System.out.print(bnode.data + " ");
			bnode = bnode.right;
		}
		sc.close();

	}
    //Below method takes a binary node called source as input which is the topmost node and then disappears the left subtree from that height of source and finally makes the leftmost node from source as the new topmost node 
	// This method attaches the topmost node source to the right of the extreme right most node of its left sub tree and then the leftmost descendant of the node source becomes the new topmost node.
	public static BinaryNode vanish_LeftSubtree(BinaryNode source) {
		if (source == null) {
			return source;
		}
		BinaryNode t = source;
		while (t.left != null) {

			BinaryNode temp = source;
			t = t.left;
			BinaryNode temp2 = t;
			while (temp2.right != null) {
				temp2 = temp2.right;
			}
			temp2.right = temp;

			temp2.right.left = null;
			t = vanish_LeftSubtree(t);
		}
		return t;
	}
    //Below method converts the binary search tree into a right skewed tree. It takes input of the binary node Bnode as the topmost node of tree
	public static void rightSkewedConverter(BinaryNode Bnode) {
		if (Bnode == null) {
			return;
		}
		BinaryNode newnode = Bnode;
		BinaryNode tnode = Bnode;
		while (tnode.left != null) {
			tnode = tnode.left;
		}
		newnode = vanish_LeftSubtree(Bnode);
		BinaryNode node = newnode;
		while (newnode.right != null) {
			node = newnode.right;
			if (node.left != null) {
				node = vanish_LeftSubtree(node);
				newnode.right = node;
			}
			newnode = newnode.right;
		}
		bst.root = tnode;

	}
    //Below method takes the node values as input from the user and does not allow duplicate node values
	public static void takeInputs() {
		int size;
		boolean inserted;
		System.out.println("Enter the size of Binary Search Tree i.e the number of nodes in the tree: ");
		try {
		size = sc.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("You did not enter the correct data format. Integer data should be provided.");
			return;
		}
		Create_Binary_tree.arr = new int[size];
		for (int i = 0; i < size; i++) {
			int num;
			if ((i + 1) % 10 == 1 && (i + 1) % 100 != 11) {
				if (i == 0) {
					System.out.println("Enter the " + (i + 1) + "st element that will be the value of the root node:");
				} else {
					System.out.println("Enter the " + (i + 1) + "st element:");
				}
			} else if ((i + 1) % 10 == 2 && (i + 1) % 100 != 12)
				System.out.println("Enter the " + (i + 1) + "nd element:");
			else if ((i + 1) % 10 == 3 && (i + 1) % 100 != 13)
				System.out.println("Enter the " + (i + 1) + "rd element:");
			else
				System.out.println("Enter the " + (i + 1) + "th element:");
			try {
			num = sc.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("You did not enter the correct data format. Integer data should be provided.");
				return;
			}
			inserted = bst.insert(num);
			if (inserted) {
				arr[i] = num;
			} else {
				i--;
				continue;
			}
		}
		
	}

}
