package owenxr.maze.creation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import owenxr.engine.PlayerHandler;

public class MazeGenerator {

	public static final int EDGE = 79; // Odd not divisible by 3
	public static final int MID_EDGE = 8;
	public static final int mid_offs = (int) MID_EDGE / 2;
	private static Stack<Node> nodesVisited = new Stack<>();


	public static int[][] maze = new int[EDGE][EDGE];

	public static void generateMaze(int numPlayers) {
		generateWalls();

		addAllNodesToSet();

		ArrayList<Node> firstNodes = Node.findClosestNode(EDGE / 2, EDGE / 2);

		Node center = new Node(EDGE / 2, EDGE / 2);
		Node.nodes.add(center);
		
		for(Node n : firstNodes) {
			center.neighborNodes.add(n);
			n.neighborNodes.add(center);
		}
		
		generatePaths(firstNodes.get(0));

		generateChestLoc();

		PlayerHandler.generatePlayerSpawns(numPlayers);

	}

	public static void resetMaze() {
		maze = new int[EDGE][EDGE];
		nodesVisited.clear();
		Node.nodes.clear();
		PlayerHandler.spots.clear();
		PlayerHandler.spawnPoints.clear();
		for (Node n : Node.nodes) {
			n.neighborNodes.clear();
		}
	}

	private static void generateWalls() {
		for (int i = 0; i < EDGE; i += 3) {
			for (int j = 0; j < EDGE; j++) {
				maze[j][i] = 1;
			}
		}

		for (int i = 0; i < EDGE; i++) {
			for (int j = 0; j < EDGE; j += 3) {
				maze[j][i] = 1;
			}
		}

		for (int i = 0; i < EDGE; i++) {

			maze[i][0] = 5;
			maze[i][EDGE - 1] = 5;
			maze[0][i] = 5;
			maze[EDGE - 1][i] = 5;

		}

		for (int i = (int) (EDGE / 2 - mid_offs); i < (int) (EDGE / 2 + mid_offs); i++) {
			for (int j = (int) (EDGE / 2 - mid_offs); j < (int) EDGE / 2 + mid_offs; j++) {
				maze[j][i] = 3;
			}
		}

	}

	private static void generatePaths(Node startNode) {

		nodesVisited.push(startNode);

		while(nodesVisited.size() > 0) {
			Node n = nodesVisited.pop();
			fillArea(n, 2);
			ArrayList<Node> neighbors = getNeighbors(n);
			if(neighbors.size() > 0) {
				nodesVisited.push(n);
				Node next = neighbors.get((int) (Math.random() * neighbors.size()));
				nodesVisited.push(next);
				fillPath(n, next);
				Node.nodes.get(Node.nodes.indexOf(n)).neighborNodes.add(Node.nodes.get(Node.nodes.indexOf(next)));
				Node.nodes.get(Node.nodes.indexOf(next)).neighborNodes.add(Node.nodes.get(Node.nodes.indexOf(n)));
			}
		}

	}

	private static void generateChestLoc() {
		int chestsSec1 = 0;
		int chestsSec2 = 0;
		int chestsSec3 = 0;
		int chestsSec4 = 0;

		while (chestsSec1 < 6) {
			for (int i = 0; i < EDGE / 2; i++) {
				for (int j = 0; j < EDGE / 2; j++) {
					if (maze[i][j] == 2 && Math.random() * 100 < .5 && chestsSec1 < 6) {
						maze[i][j] = 4;
						chestsSec1++;
					}
				}
			}
		}
		while (chestsSec2 < 6) {
			for (int i = 0; i < EDGE / 2; i++) {
				for (int j = EDGE / 2; j < EDGE; j++) {
					if (maze[i][j] == 2 && Math.random() * 100 < .5 && chestsSec2 < 6) {
						maze[i][j] = 4;
						chestsSec2++;
					}
				}
			}
		}
		while (chestsSec3 < 6) {
			for (int i = EDGE / 2; i < EDGE; i++) {
				for (int j = 0; j < EDGE / 2; j++) {
					if (maze[i][j] == 2 && Math.random() * 100 < .5 && chestsSec3 < 6) {
						maze[i][j] = 4;
						chestsSec3++;
					}
				}
			}
		}
		while (chestsSec4 < 6) {
			for (int i = EDGE / 2; i < EDGE; i++) {
				for (int j = EDGE / 2; j < EDGE; j++) {
					if (maze[i][j] == 2 && Math.random() * 100 < .5 && chestsSec4 < 6) {
						maze[i][j] = 4;
						chestsSec4++;
					}
				}
			}
		}
	}

	public static String printMaze() {
		String r = "";
		for (int i = 0; i < EDGE; i++) {
			for (int j = 0; j < EDGE; j++) {
				r += maze[i][j] + ", ";
			}
			r += "\n";
		}

		return r;
	}

	private static void addAllNodesToSet() {
		for(int i = 1; i < EDGE; i += 3) {
			for(int j = 1; j < EDGE; j += 3) {
				if(maze[i][j] == 0) Node.nodes.add(new Node(i, j));
			}
		}
	}

	private static ArrayList<Node> getNeighbors(Node curNode) {

		ArrayList<Node> neighbors = new ArrayList<>();

		if (curNode.r - 3 > 0 && maze[curNode.r - 3][curNode.c] == 0)
			neighbors.add(new Node(curNode.r - 3, curNode.c));
		if (curNode.r + 3 < EDGE && maze[curNode.r + 3][curNode.c] == 0)
			neighbors.add(new Node(curNode.r + 3, curNode.c));
		if (curNode.c - 3 > 0 && maze[curNode.r][curNode.c - 3] == 0)
			neighbors.add(new Node(curNode.r, curNode.c - 3));
		if (curNode.c + 3 < EDGE && maze[curNode.r][curNode.c + 3] == 0)
			neighbors.add(new Node(curNode.r, curNode.c + 3));

		return neighbors;

	}

	private static void fillArea(Node curInd, int num) {

		maze[curInd.r][curInd.c] = num;

		if (curInd.r % 3 == 1 && curInd.c % 3 == 1) {
			maze[curInd.r + 1][curInd.c] = num;
			maze[curInd.r + 1][curInd.c + 1] = num;
			maze[curInd.r][curInd.c + 1] = num;
		}
		if (curInd.r % 3 == 2 && curInd.c % 3 == 1) {
			maze[curInd.r - 1][curInd.c] = num;
			maze[curInd.r - 1][curInd.c + 1] = num;
			maze[curInd.r][curInd.c + 1] = num;
		}
		if (curInd.r % 3 == 1 && curInd.c % 3 == 2) {
			maze[curInd.r + 1][curInd.c] = num;
			maze[curInd.r + 1][curInd.c - 1] = num;
			maze[curInd.r][curInd.c - 1] = num;
		}
		if (curInd.r % 3 == 2 && curInd.c % 3 == 2) {
			maze[curInd.r - 1][curInd.c] = num;
			maze[curInd.r - 1][curInd.c - 1] = num;
			maze[curInd.r][curInd.c - 1] = num;
		}
	}

	private static void fillPath(Node curInd, Node nextVisit) {
		if (maze[nextVisit.r][nextVisit.c] == 0) {
			maze[nextVisit.r - (nextVisit.r - curInd.r) * 1 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 1 / 3] = 2;
			maze[nextVisit.r - (nextVisit.r - curInd.r) * 2 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 2 / 3] = 2;

			if ((nextVisit.c - curInd.c) == 0 && (nextVisit.r - curInd.r) != 0) {
				if (nextVisit.r % 3 == 1) {
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 1 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 1 / 3
					                                                     + 1] = 2;
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 2 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 2 / 3
					                                                     + 1] = 2;
				} else {
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 1 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 1 / 3
					                                                     - 1] = 2;
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 2 / 3][nextVisit.c - (nextVisit.c - curInd.c) * 2 / 3
					                                                     - 1] = 2;
				}
			}
			if ((nextVisit.c - curInd.c) != 0 && (nextVisit.r - curInd.r) == 0) {
				if (nextVisit.r % 3 == 1) {
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 1 / 3 + 1][nextVisit.c
					                                                         - (nextVisit.c - curInd.c) * 1 / 3] = 2;
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 2 / 3 + 1][nextVisit.c
					                                                         - (nextVisit.c - curInd.c) * 2 / 3] = 2;
				} else {
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 1 / 3 - 1][nextVisit.c
					                                                         - (nextVisit.c - curInd.c) * 1 / 3] = 2;
					maze[nextVisit.r - (nextVisit.r - curInd.r) * 2 / 3 - 1][nextVisit.c
					                                                         - (nextVisit.c - curInd.c) * 2 / 3] = 2;
				}
			}
		}
	}

	private static class InfoNode
	{
		Node n;
		Node bkptr;
		int dist;

		public InfoNode(Node pt, Node bkptr, int dist)
		{
			n = pt;
			this.bkptr = bkptr;
			this.dist = dist;
		}


		@Override
		public boolean equals(Object o) {
			if(o == null || o.getClass() != this.getClass()) return false;
			else {
				InfoNode iNode = (InfoNode) o;
				return iNode.n.equals(n) && iNode.bkptr.equals(bkptr) && iNode.dist == dist;
			}
		}

		@Override
		public String toString() {
			String r = "Node: " + n.toString();
			r += ", Backpointer: " + bkptr.toString();
			r += ", Distance from Source: " + dist;
			return r;
		}
	};

	private static class InfoNodeComparator implements Comparator<InfoNode>
	{
		@Override
		public int compare(InfoNode o1, InfoNode o2) {
			if(o1.dist < o2.dist) return -1;
			if(o1.dist > o2.dist) return 1;
			return 0;
		}
	};

	public static List<Node> shortestPath(Node startNode, Node endNode) {

		Comparator<InfoNode> comparator = new InfoNodeComparator();

		PriorityQueue<InfoNode> frontier = new PriorityQueue<>(Node.nodes.size(), comparator);
		HashMap<Node, InfoNode> mapSF = new HashMap<>();

		frontier.add(new InfoNode(startNode, null, 0));
		mapSF.put(startNode, new InfoNode(startNode, null, 0));

		while(frontier.size() > 0) {
			InfoNode f = frontier.poll();
			if(f.n.equals(endNode)) return path(mapSF, f.n);

			int dist = f.dist;

			for(Node w : f.n.neighborNodes) {
				InfoNode wInfo = mapSF.get(w);
				int wDist = dist + 3;

				if(wInfo == null) {
					wInfo = new InfoNode(w, f.n, wDist);
					mapSF.put(w, wInfo);
					frontier.add(wInfo);
				} else if (wDist < wInfo.dist) {
					wInfo.dist = wDist;
					wInfo.bkptr = f.n;
					frontier.add(wInfo);
				}
			}
		}

		return new LinkedList<>();	
	}

	private static List<Node> path(HashMap<Node, InfoNode> mapSF, Node n) {

		List<Node> path = new LinkedList<>();
		Node p = n;

		while(p != null) {
			path.add(0, p);
			p = mapSF.get(p).bkptr;
		}

		return path;

	}

}
