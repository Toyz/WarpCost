package com.toyz.warpcost.BaseCommand.Commands;

import com.toyz.warpcost.BaseCommand.BaseCommand;
import com.toyz.warpcost.BaseCommand.ICommand;
import com.toyz.warpcost.Main;
import org.bukkit.entity.Player;

/**
 * Created by Travis on 10/24/2014.
 */
public class FreeWarp extends BaseCommand implements ICommand {
    @Override
    public boolean Trigger() {
        if(getCommandInfo().isPlayer()){
            Task();
        }else{
            sendMessage("&4You can only run this command in-game");
        }
        return true;
    }

    @Override
    public void Task() {
        Player player = getCommandInfo().getPlayer();
        if(!player.getPlayer().hasPermission("warp.make.free"))
        {
            if(!player.isOp()){
                sendMessage("&4You do not have permission to use this command");
            }
        }


        if(getCommandInfo().getArgs().length == 1){
            String name = getCommandInfo().getArg(0);

            if(!Main.getWarpConfig().getConfig().contains(name)){
                String loc =  player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ() + "," + player.getLocation().getWorld().getName();
                Main.getWarpConfig().getConfig().set(name + ".loc", loc);
                Main.getWarpConfig().getConfig().set(name + ".free", true);
                Main.getWarpConfig().saveConfig();

                sendMessage("Created warp '" + name+ "' at " + loc);
            }else{
                sendMessage("&4Warp name already exist in the warp file");
                return;
            }
            return;
        }

        sendMessage("&4Invalid Usage: /setwarp name");
    }
}
