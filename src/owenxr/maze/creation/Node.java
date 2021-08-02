package owenxr.maze.creation;

import java.util.ArrayList;

public class Node {

	public static ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<Node> neighborNodes = new ArrayList<>(); 
	
	public int c;
	public int r;
	
	public Node(int r, int c) {
		this.c = c;
		this.r = r;
	}
	
	public Node(int r, int c, boolean isCenter) {
		this.c = c;
		this.r = r;
	}
	
	@Override
	public String toString() {
		return "Row: " + r + " Col: " + c;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != this.getClass()) return false;
		else {
			Node n = (Node) o;
			return n.r == r && n.c == c;
		}
	}
	
	public static Node findNode(int row, int col) {
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).r == row && nodes.get(i).c == col) return nodes.get(i);
		}
		return null;
	}
	
	public static ArrayList<Node> findClosestNode(int row, int col) {
		ArrayList<Node> foundNodes = new ArrayList<>();
		
		if(findNode(row, col) != null) {
			foundNodes.add(findNode(row, col));
			return foundNodes;
		}
		
		int offset = 1;
		
		while(offset < MazeGenerator.EDGE && foundNodes.size() == 0) {
			for(int i = row - offset; i < row + offset; i++) {
				for(int j = col - offset; j < col + offset; j++) {
					if(findNode(i, j) != null && !foundNodes.contains(findNode(i, j))) {
						foundNodes.add(findNode(i, j));
					}
				}
			}
			offset++;
		}
		
		return foundNodes;
		
	}
	
}
