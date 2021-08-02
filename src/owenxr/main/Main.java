package owenxr.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import owenxr.commands.CreateMazeCmd;
import owenxr.commands.GiveWand;
import owenxr.commands.StartGame;
import owenxr.engine.CreateMaze;
import owenxr.events.SetMazeCreationPoint;
import owenxr.file.FileManager;

public class Main extends JavaPlugin{
	
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		this.getCommand("wand").setExecutor(new GiveWand());
		this.getCommand("start").setExecutor(new StartGame());
		this.getCommand("create").setExecutor(new CreateMazeCmd(this));
		
		getServer().getPluginManager().registerEvents(new SetMazeCreationPoint(), this);
		
		CreateMaze.populateWallMats();
		if(FileManager.load() != null) CreateMaze.startLoc = FileManager.deserializeLoc((HashMap<String, HashMap<String, Integer>>) FileManager.load());
		
		getLogger().info("Minigame enabled!");
	}
	
	@Override 
	public void onDisable() {
		Bukkit.getServer().getScheduler().cancelTasks(this);
		FileManager.save(FileManager.serializeLoc(CreateMaze.startLoc));
		
		getLogger().info("Minigame disabled!");
	}
	
}
