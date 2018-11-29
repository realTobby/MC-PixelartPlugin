package com.github.xsillusx.mcpixelart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

    	Player p = (Player)sender;
    	
    	// ==> https://pm1.narvii.com/6842/660e3758e9712b95e4a7e57fde3dee187d5abf92v2_hq.jpg <== TEST URL
    	if(cmd.getName().equalsIgnoreCase("pixelart")){ 

    		sender.sendMessage("Generating pixelart...");
    		
    		
    		try {
    			GeneratePixelart(args[0], p);
    		} catch (IOException e) {
    			this.getLogger().info(e.getMessage());
    		}
    		
    		
    		
    		return true;

    	}

    	return false; 

    }
	private void GeneratePixelart(String urlBase, Player playerRef) throws IOException {
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
		
		BufferedImage originalImage = ImageIO.read(new File("pictures/test.png"));
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		//BufferedImage resizeImagePng = resizeImage(originalImage, type);
		ImageIO.write(originalImage, "png", new File("pictures/test.png")); 
		
		World w = Bukkit.getWorld("world");
		//String allPixels = "";
		playerRef.playSound(playerRef.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100f, 50f);
		for(int y = 0; y < originalImage.getHeight(); y++) {
			for(int x = 0; x < originalImage.getWidth(); x++) {
				int px = originalImage.getRGB(x, y);
				Color blockColor = new Color(px);
				
				// DEBUG ==> allPixels = allPixels + mycolor.getRed() + "," + mycolor.getGreen() + "," + mycolor.getBlue() + ";";
				Location nextBlock = new Location(w, playerRef.getLocation().getX()+x, playerRef.getLocation().getY()-y, playerRef.getLocation().getZ());
				
				Material nextMat = GetMaterialByColor(blockColor);
				
				nextBlock.getBlock().setType(nextMat);
			
				
			}
		}
		
		//try (PrintWriter out1 = new PrintWriter("pictures/pixels.txt")) {
		//    out1.println(allPixels);
		//}
		
		
	}
	
	private Material GetMaterialByColor(Color c) {
		Material mat = Material.STONE;
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		if(r > g && r > b) {
			mat = Material.REDSTONE_BLOCK;
		}
		if(g > r && g > b) {
			mat = Material.GREEN_WOOL;
		}
		if(b > g && b > r) {
			mat = Material.LAPIS_BLOCK;
		}
		if(r == 255 && g == 255 && b == 255) {
			mat = Material.SNOW_BLOCK;
		}
		if(r < 60 && g < 60 && b < 60) {
			mat = Material.BLACK_WOOL;
		}
		return mat;
	}
	
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(50, 50, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 50, 50, null);
		g.dispose();
			
		return resizedImage;
	    }
    
}
