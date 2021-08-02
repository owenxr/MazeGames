package owenxr.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import owenxr.items.SetupItems;

public class GiveWand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(sender instanceof Player) {
			if (str.equalsIgnoreCase("wand")) {
				Player p = (Player) sender;
				if(p.isOp()) p.getInventory().addItem(SetupItems.wand());
			}
		}
		return false;
	}

}
