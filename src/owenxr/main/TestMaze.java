package owenxr.main;

import owenxr.engine.PlayerHandler;
import owenxr.maze.creation.MazeGenerator;

public class TestMaze {

	public static void main(String[] args) {
		MazeGenerator.generateMaze(8);
		System.out.println(MazeGenerator.printMaze());
		System.out.println(PlayerHandler.spots.size());
	}
	
}
