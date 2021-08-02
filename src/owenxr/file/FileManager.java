package owenxr.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class FileManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static File f = new File(".//Maze_Game//data.dat");
	
	public static Object load() {

		if(!new File(".//Maze_Game//").exists())
			try {
				new File(".//Maze_Game//").mkdir();
				f.createNewFile();
				return null;
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		
		try {
			FileInputStream inputStream = new FileInputStream(f);
			ObjectInputStream objStream = new ObjectInputStream(inputStream);
			Object obj = objStream.readObject();
			objStream.close();
			
			return obj;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void save(Object obj) {
		
		if(!new File(".//Maze_Game//").exists())
			try {
				new File(".//Maze_Game//").mkdir();
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		try {
			FileOutputStream outStream = new FileOutputStream(f);
			ObjectOutputStream objStream = new ObjectOutputStream(outStream);
			objStream.writeObject(obj);
			objStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, HashMap<String, Integer>> serializeLoc(Location loc) {
		HashMap<String, Integer> location = new HashMap<>();
		location.put("x", loc.getBlockX());
		location.put("y", loc.getBlockY());
		location.put("z", loc.getBlockZ());
		HashMap<String, HashMap<String, Integer>> serialized = new HashMap<>();
		serialized.put(loc.getWorld().getName(), location);
		
		return serialized;
		
	}
	
	public static Location deserializeLoc(HashMap<String, HashMap<String, Integer>> serialized) {
		String worldName = serialized.keySet().iterator().next();
		HashMap<String, Integer> loc = serialized.get(worldName);
		return new Location(Bukkit.getWorld(worldName), loc.get("x"), loc.get("y"), loc.get("z"));
	}
	
}
