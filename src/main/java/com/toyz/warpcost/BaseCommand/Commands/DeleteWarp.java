package com.toyz.warpcost.BaseCommand.Commands;

import com.toyz.warpcost.BaseCommand.BaseCommand;
import com.toyz.warpcost.BaseCommand.ICommand;
import com.toyz.warpcost.Main;
import org.bukkit.entity.Player;

/**
 * Created by Travis on 10/24/2014.
 */
public class DeleteWarp extends BaseCommand implements ICommand {
    @Override
    public boolean Trigger() {
        Task();
        return true;
    }

    @Override
    public void Task() {
        if(getCommandInfo().isPlayer()){
            Player p = getCommandInfo().getPlayer();
            if(!p.hasPermission("warp.remove")){
                 if(!p.isOp()){
                     sendMessage("&4You do not have permission to use this command");
                 }
            }
        }


        if(getCommandInfo().getArgs().length == 1){
            String name = getCommandInfo().getArg(0);

            if(Main.getWarpConfig().getConfig().contains(name)) {
                Main.getWarpConfig().getConfig().set(name, null);
                Main.getWarpConfig().saveConfig();

                sendMessage("Deleted warp &o" + name);
                return;
            }

            sendMessage("&4Warp does not exist");
            return;
        }

        sendMessage("&4Invalid Usage: /delwarp name");
    }
}
