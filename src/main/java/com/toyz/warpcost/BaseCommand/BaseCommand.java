package com.toyz.warpcost.BaseCommand;

import com.toyz.warpcost.BaseCommand.Handler.IssueCommands;
import com.toyz.warpcost.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BaseCommand implements CommandExecutor {
	private static IssueCommands Info;
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Info = new IssueCommands(sender, cmd.getName(), args, cmd);

        if(Main.getCommands().containsKey(cmd.getName().toLowerCase()))
            Main.getCommands().get(cmd.getName().toLowerCase()).Trigger();

		return false;
	}
	
	protected static void sendMessage(String Message){
		if(Info.isPlayer()){
			Info.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("prefix") + " " + Message ));
		}
		if(Info.isConsole()){
			Info.getConsole().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("prefix") + " " + Message));
		}
	}

    protected static IssueCommands getCommandInfo(){
        return Info;
    }
}
