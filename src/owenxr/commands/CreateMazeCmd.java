package owenxr.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import owenxr.engine.CreateMaze;
import owenxr.maze.creation.MazeGenerator;

public class CreateMazeCmd implements CommandExecutor {

	public Plugin plugin;

	public CreateMazeCmd(Plugin p) {
		plugin = p;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (sender instanceof Player && sender.isOp()) {
			if (str.equalsIgnoreCase("create")) {
				MazeGenerator.resetMaze();
				MazeGenerator.generateMaze(Bukkit.getServer().getOnlinePlayers().size());
				CreateMaze maze = new CreateMaze(plugin);
				maze.create();
			}
		}
		return false;
	}

}
