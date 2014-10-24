package com.toyz.warpcost.Events;

import com.toyz.warpcost.Main;
import com.toyz.warpcost.Utils.PlayerHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Travis on 10/24/2014.
 */
public class InventoryClick implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void on(InventoryClickEvent e) {
        if ((e.getInventory().getTitle() != null) && (e.getInventory().getTitle().equalsIgnoreCase("List of warps"))) {
            e.setCancelled(true);

            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType() == Material.AIR)) {
                return;
            }

            ItemStack is = e.getInventory().getItem(e.getRawSlot());

            System.out.println(is.getItemMeta().getDisplayName());

            String warp = is.getItemMeta().getDisplayName().replace("Warp to ", "");

            ConfigurationSection cs = Main.getWarpConfig().getConfig().getConfigurationSection(warp);

            Player player = (Player) e.getWhoClicked();
            if(cs.getBoolean("free")){
                String[] s = cs.getString("loc").split(",");
                String msg = Main.getPlugin().getConfig().getString("messages.gofree");
                player.sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                player.teleport(new Location(Main.getPlugin().getServer().getWorld(s[3]), Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
                //player.closeInventory();
            }else{
                Material m = Material.getMaterial(cs.getString("item"));
                if (!player.getInventory().contains(m)) {
                    String msg = Main.getPlugin().getConfig().getString("messages.cantafford");
                    player.sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                    return;
                }

                for (ItemStack i : player.getInventory()) {
                    if (i != null) {
                        if (i.getType().equals(m)) {
                            if (i.getAmount() < cs.getInt("cost")) {
                                String msg = Main.getPlugin().getConfig().getString("messages.cantafford");
                                player.sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                                break;
                            }

                            int a = i.getAmount() - cs.getInt("cost");
                            if (a == 0)
                                player.getInventory().remove(i);
                            else
                                i.setAmount(a);

                            player.updateInventory();
                            String[] s = cs.getString("loc").split(",");
                            String msg = Main.getPlugin().getConfig().getString("messages.gocost");
                            player.sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                            player.teleport(new Location(Main.getPlugin().getServer().getWorld(s[3]), Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));

                            //player.closeInventory();
                            break;
                        }
                    }
                }
            }
        }
    }
}
