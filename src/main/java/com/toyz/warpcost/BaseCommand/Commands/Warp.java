package com.toyz.warpcost.BaseCommand.Commands;

import com.toyz.warpcost.BaseCommand.BaseCommand;
import com.toyz.warpcost.BaseCommand.ICommand;
import com.toyz.warpcost.Main;
import com.toyz.warpcost.Utils.ConfigAccessor;
import com.toyz.warpcost.Utils.PlayerHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Travis on 10/23/2014.
 */
public class Warp extends BaseCommand implements ICommand {
    public boolean Trigger(){
        if(getCommandInfo().isPlayer()){
            Task();
        }else{
            sendMessage("&4You can only do this from in-game");
        }

        return true;
    }

    public void Task(){
        Player player = getCommandInfo().getPlayer();
        if(!player.getPlayer().hasPermission("warp.go"))
        {
            if(!player.isOp()){
                sendMessage("&4You do not have permission to use this command");
            }
        }

        if(Main.getPlugin().getConfig().getBoolean("usegui")){
            PlayerHelper.CreateAndOpenInventory(player);
            return;
        }

        if(getCommandInfo().getArgs().length == 1) {
            String warp = getCommandInfo().getArg(0);
            ConfigurationSection cs = Main.getWarpConfig().getConfig().getConfigurationSection(warp);
            if (cs == null) {
                sendMessage("&4Warp does not exist in the server");
                return;
            }

            if(!cs.getBoolean("free")) {
                Material m = Material.getMaterial(cs.getString("item"));
                if (!player.getInventory().contains(m)) {
                    String msg = Main.getPlugin().getConfig().getString("messages.cantafford");
                    sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                    return;
                }

                for (ItemStack i : player.getInventory()) {
                    if (i != null) {
                        if (i.getType().equals(m)) {
                            if (i.getAmount() < cs.getInt("cost")) {
                                String msg = Main.getPlugin().getConfig().getString("messages.cantafford");
                                sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                                break;
                            }

                            int a = i.getAmount() - cs.getInt("cost");
                            if (a == 0)
                                player.getInventory().remove(i);
                            else
                                i.setAmount(a);

                            player.updateInventory();
                            String[] s = cs.getString("loc").split(",");
                            player.teleport(new Location(Main.getPlugin().getServer().getWorld(s[3]), Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
                            String msg = Main.getPlugin().getConfig().getString("messages.gocost");
                            sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
                            break;
                        }
                    }
                }
            }else{
                String[] s = cs.getString("loc").split(",");
                player.teleport(new Location(Main.getPlugin().getServer().getWorld(s[3]), Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
                String msg = Main.getPlugin().getConfig().getString("messages.gofree");
                sendMessage(PlayerHelper.MessageBuilder(msg, warp, cs));
            }
            return;
        }

        sendMessage("&4Invalid Usage: /warp name");
    }
}
