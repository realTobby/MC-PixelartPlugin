package com.github.xsillusx.mcpixelart;

import org.bukkit.plugin.java.JavaPlugin;

public class PixelArt extends JavaPlugin{

	public void onEnable(){ 
		this.getLogger().info("Your plugin has been enabled!");
	}

	public void onDisable(){ 
		this.getLogger().info("Your plugin has been disabled.");
	}
	
}
