package com.github.xsillusx.mcpixelart;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MCPixelArt extends JavaPlugin {

	// Fired when plugin is first enabled
    @Override
    public void onEnable() {
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
	
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

    	if(cmd.getName().equalsIgnoreCase("basic")){ 

    		sender.sendMessage("Command Works!");
    		return true;

    	}

    	return false; 

    }
    
}
