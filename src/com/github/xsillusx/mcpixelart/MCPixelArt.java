package com.github.xsillusx.mcpixelart;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    	
    	// ==> https://pm1.narvii.com/6842/660e3758e9712b95e4a7e57fde3dee187d5abf92v2_hq.jpg <== TEST URL
    	if(cmd.getName().equalsIgnoreCase("pixelart")){ 

    		sender.sendMessage("Generating pixelart...");
    		
    		
    		try {
    			DownloadImage(args[0]);
    		} catch (IOException e) {
    			this.getLogger().info(e.getMessage());
    		}
    		
    		
    		
    		return true;

    	}

    	return false; 

    }
	private void DownloadImage(String urlBase) throws IOException {
		URL url = new URL(urlBase);
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		FileOutputStream fos = new FileOutputStream("pictures/test.png");
		fos.write(response);
		fos.close();
	}
    
}
