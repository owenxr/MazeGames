package owenxr.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import owenxr.engine.PlayerHandler;
import owenxr.maze.creation.MazeGenerator;

public class StartGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(sender instanceof Player && sender.isOp()) {
			if (str.equalsIgnoreCase("start")) startGame();
		}
		return false;
	}
	
	public static void startGame() {
		Object[] pList = Bukkit.getServer().getOnlinePlayers().toArray();
		
		MazeGenerator.resetMaze();
		MazeGenerator.generateMaze(pList.length);
		
		for(int i = 0; i < pList.length; i++) {
			Player p = (Player) pList[i];
			p.teleport(PlayerHandler.spawnPoints.get(i));
		}
		
	}
	
	
}
