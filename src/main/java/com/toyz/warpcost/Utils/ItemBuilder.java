package com.toyz.warpcost.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Travis on 10/24/2014.
 */
public class ItemBuilder {
    public static ItemStack CreateItem(String id, String name, List<String> lore, int amount){
        ItemStack is = new ItemStack(Material.getMaterial(id));
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if(amount > 0){
            is.setAmount(amount);
        }

        if(lore != null){
            List<String> Lores = new ArrayList<String>();
            for(String l : lore){
                Lores.add(ChatColor.translateAlternateColorCodes('&', l));
            }
            im.setLore(Lores);
        }
        is.setItemMeta(im);

        return is;
    }
}
