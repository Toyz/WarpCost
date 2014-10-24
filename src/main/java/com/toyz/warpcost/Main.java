package com.toyz.warpcost;

import com.toyz.warpcost.BaseCommand.BaseCommand;
import com.toyz.warpcost.BaseCommand.Commands.DeleteWarp;
import com.toyz.warpcost.BaseCommand.Commands.FreeWarp;
import com.toyz.warpcost.BaseCommand.Commands.Warp;
import com.toyz.warpcost.BaseCommand.Commands.WarpPrice;
import com.toyz.warpcost.BaseCommand.ICommand;
import com.toyz.warpcost.Utils.ConfigAccessor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Travis on 10/23/2014.
 */
public class Main extends JavaPlugin {
    private static Main plugin;
    private static ConfigAccessor WarpConfig;
    private static HashMap<String, ICommand> commands = new HashMap<String, ICommand>();

    public void onEnable(){
        plugin = this;

        //configs and files
        saveDefaultConfig();
        File warpFile = new File(getDataFolder(), "warps.yml");
        if(!warpFile.exists()) {
            try {
                warpFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WarpConfig = new ConfigAccessor(this, "warps.yml");

        //Set up commands for easier access
        commands.put("warp", new Warp());
        commands.put("setwarpprice", new WarpPrice());
        commands.put("setwarp", new FreeWarp());
        commands.put("delwarp", new DeleteWarp());

        //Commands stuff
        BaseCommand bsc = new BaseCommand();

        /*getCommand("warp").setExecutor(bsc);
        getCommand("setwarpprice").setExecutor(bsc);*/

        Iterator it = commands.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            getCommand((String) pairs.getKey()).setExecutor(bsc);
        }
    }

    public void onDisable(){
         getWarpConfig().saveConfig();
    }

    //getters
    public static ConfigAccessor getWarpConfig() {
        return WarpConfig;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static HashMap<String, ICommand> getCommands() {
        return commands;
    }

    public void setCommands(HashMap<String, ICommand> commands) {
        this.commands = commands;
    }
}
