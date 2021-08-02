package owenxr.engine;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import owenxr.maze.creation.MazeGenerator;

public class CreateMaze {

	private Plugin plugin;
	public static Location startLoc;

	public CreateMaze(Plugin plugin) {
		this.plugin = plugin;
	}

	private static ArrayList<Material> wallMats = new ArrayList<>();

	public static void populateWallMats() {
		wallMats.add(Material.STONE_BRICKS);
		wallMats.add(Material.MOSSY_STONE_BRICKS);
		wallMats.add(Material.CRACKED_STONE_BRICKS);
		wallMats.add(Material.CRACKED_STONE_BRICKS);
	}

	public void create() {
		if(startLoc == null) return;
		
		Bukkit.getServer().getScheduler().runTask(plugin, (Runnable) new BukkitRunnable() {
			@Override
			public void run() {
				generateMaze();
			}

		});
	}
	
	private int generateMaze() {
		
		int chests = 0;
		boolean midChest = false;
		
		for (int i = 0; i < MazeGenerator.maze.length; i++) {
			for (int j = 0; j < MazeGenerator.maze[0].length; j++) {

				Location floor = new Location(startLoc.getWorld(), startLoc.getX() + i, startLoc.getY(),
						startLoc.getZ() + j);
				floor.getBlock().setType(Material.SANDSTONE);

				for (int z = 1; z < 10; z++) {
					Location wall = new Location(startLoc.getWorld(), startLoc.getX() + i, startLoc.getY() + z,
							startLoc.getZ() + j);

					if (MazeGenerator.maze[i][j] == 4 && z == 1) {
						chests++;
						wall.getBlock().setType(Material.CHEST);
					} else if (MazeGenerator.maze[i][j] == 5) {
						wall.getBlock().setType(wallMats.get((int) (Math.random() * wallMats.size())));
						for(int k = 10; k < 20; k++) {
							Location border = new Location(startLoc.getWorld(), startLoc.getX() + i, startLoc.getY() + k,
									startLoc.getZ() + j);
							border.getBlock().setType(Material.AIR);
						}
					} else if (MazeGenerator.maze[i][j] == 1) {
						wall.getBlock().setType(wallMats.get((int) (Math.random() * wallMats.size())));
					} else if (MazeGenerator.maze[i][j] == 3 && z == 1) {
						if(i > MazeGenerator.maze.length / 2 && j > MazeGenerator.maze.length / 2 && !midChest && z == 1) {
							wall.getBlock().setType(Material.CHEST);
							midChest = true;
						}
						floor.getBlock().setType(Material.EMERALD_BLOCK);
					} else
						wall.getBlock().setType(Material.AIR);
				}
			}
		}
		return chests;
	}
}
