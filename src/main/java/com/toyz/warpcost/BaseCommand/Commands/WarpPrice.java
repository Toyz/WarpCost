package com.toyz.warpcost.BaseCommand.Commands;

import com.toyz.warpcost.BaseCommand.BaseCommand;
import com.toyz.warpcost.BaseCommand.ICommand;
import com.toyz.warpcost.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Travis on 10/23/2014.
 */
public class WarpPrice extends BaseCommand implements ICommand{
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
        if(!player.getPlayer().hasPermission("warp.make"))
        {
            if(!player.isOp()){
               sendMessage("&4You do not have permission to use this command");
            }
        }


        if(getCommandInfo().getArgs().length == 3){
            String name = getCommandInfo().getArg(0);
            int cost = Integer.parseInt(getCommandInfo().getArg(1));
            String item = getCommandInfo().getArg(2).toUpperCase();

            if(!Main.getWarpConfig().getConfig().contains(name)){
                Material m = Material.getMaterial(item);
                if(m == null){
                    sendMessage("&4'" + item + "' does not exist in Minecraft");
                    return;
                }

                String loc =  player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ() + "," + player.getLocation().getWorld().getName();
                Main.getWarpConfig().getConfig().set(name + ".cost", cost);
                Main.getWarpConfig().getConfig().set(name + ".item", item);
                Main.getWarpConfig().getConfig().set(name + ".loc", loc);
                Main.getWarpConfig().saveConfig();
                sendMessage("Created warp '" + name+ "' at " + loc);
            }else{
                sendMessage("&4Warp name already exist in the warp file");
                return;
            }
            return;
        }

        sendMessage("&4Invalid Usage: /setwarpprice name cost material");
    }
}
