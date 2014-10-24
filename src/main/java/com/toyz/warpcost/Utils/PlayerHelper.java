package com.toyz.warpcost.Utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Travis on 10/23/2014.
 */
public class PlayerHelper {
    public static ItemStack hasEnchantedItem(Player p, Material i) {
        ItemStack[] inv = p.getInventory().getContents();
        for (ItemStack item : inv) {
            if (item.getType().equals(i)) {
                return item;
            }
        }

        return null;
    }

    public static String MessageBuilder(String message, String name, ConfigurationSection cs){
        message = message.replace("%cost%", cs.getInt("cost") + "").replace("%name%", name).replace("%item%", StringUtils.capitalize(cs.getString("item").replace("_", " ")));
        return message;
    }
}
