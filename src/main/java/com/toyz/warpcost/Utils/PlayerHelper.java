package com.toyz.warpcost.Utils;

import com.toyz.warpcost.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Travis on 10/23/2014.
 */
public class PlayerHelper {
    public static String MessageBuilder(String message, String name, ConfigurationSection cs){
        if(!cs.getBoolean("free")) {
            message = message.replace("%cost%", cs.getInt("cost") + "").replace("%name%", name).replace("%item%", StringUtils.capitalize(cs.getString("item").replace("_", " ")));
        }else{
            message = message.replace("%name%", name);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void CreateAndOpenInventory(Player player) {
        Hashtable<Integer, ItemStack> items = new Hashtable<Integer, ItemStack>();

        Set<String> keys = Main.getWarpConfig().getConfig().getKeys(false);

        int i = 0;
        for (String s : keys) {
            ConfigurationSection cs = Main.getWarpConfig().getConfig().getConfigurationSection(s);

            List<String> lore = new ArrayList<String>();
            if(!cs.getBoolean("free")) {
                lore.add("Cost: " + (cs.getBoolean("free") ? "Free" : cs.getInt("cost") + " " + StringUtils.capitalize(cs.getString("item").replace("_", " "))));
            }

            ItemStack os = ItemBuilder.CreateItem("REDSTONE_BLOCK", "Warp to " + s, lore, 1);

            items.put(i, os);
            i++;
        }

        Inventory inv = new Inventory(player, items, true, "List of warps");

        inv.Open();
    }
}
