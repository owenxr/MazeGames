package owenxr.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import owenxr.engine.CreateMaze;
import owenxr.file.FileManager;
import owenxr.items.SetupItems;

public class SetMazeCreationPoint implements Listener{

	@EventHandler
	public void onWandClick(PlayerInteractEvent e) {
		if(e.getPlayer().getInventory().getItemInMainHand().equals(SetupItems.wand())) {
			if(e.getPlayer().isOp()) {
				if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					CreateMaze.startLoc = e.getClickedBlock().getLocation();
					e.getPlayer().sendMessage("Location set");
					FileManager.save(FileManager.serializeLoc(CreateMaze.startLoc));
				}
			}
			e.setCancelled(true);
		}
	}
	
}
