package owenxr.engine;

import java.util.ArrayList;

import org.bukkit.Location;

import owenxr.maze.creation.MazeGenerator;
import owenxr.maze.creation.Node;

public class PlayerHandler {

	public static ArrayList<Node> spots = new ArrayList<>();
	public static ArrayList<Location> spawnPoints = new ArrayList<>();
	public static int playerSpawnDist = 90;

	public static void generatePlayerSpawns(int numPlayers) {
		int offset = 0;
		Node center = Node.findNode(MazeGenerator.EDGE / 2, MazeGenerator.EDGE / 2);
		
		while(spots.size() < numPlayers && playerSpawnDist - offset > 65) {
			spots.clear();
			
			generatePlayerSpawns(center, center, playerSpawnDist - offset);
			offset++;
		}
		
		if(spots.size() == 0) {
			MazeGenerator.resetMaze();
			MazeGenerator.generateMaze(numPlayers);
			return;
		} 
		
		generateSpawnPoints();

	}
	
	private static void generatePlayerSpawns(Node startNode, Node curNode, int stepsLeft) {
		
		if (stepsLeft <= 0) {
			if((3 * MazeGenerator.shortestPath(curNode, startNode).size()) >= playerSpawnDist) {
				spots.add(curNode);
			}
			
			return;
		}

		for(Node n : curNode.neighborNodes) {
			if(MazeGenerator.shortestPath(startNode, n).size() > MazeGenerator.shortestPath(startNode, curNode).size()) {
				generatePlayerSpawns(startNode, n, stepsLeft - 3);
			}
		}
		
	}
	
	private static void generateSpawnPoints() {

		for(Node n : spots) {
			Location startLoc = CreateMaze.startLoc;
			Location loc = new Location(startLoc.getWorld(), startLoc.getX() + n.r + 1, startLoc.getY() + 2, startLoc.getZ() + n.c + 1);
			spawnPoints.add(loc);
		}
	}

}
