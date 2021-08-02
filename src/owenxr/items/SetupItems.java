package owenxr.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class SetupItems {

	public static ItemStack wand(){
		ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        
        String displayName = ChatColor.DARK_PURPLE + "Maze Creation Wand";
        meta.setDisplayName(displayName);
        
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "RIGHT-CLICK for Start Point");
        meta.setLore(lore);
        wand.setItemMeta(meta);
       
        return wand;
    }
	
}
