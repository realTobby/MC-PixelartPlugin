package com.github.xsillusx.mcpixelart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

	
	public Player lastUser;
	public ColorUtil colorToNameUtil = new ColorUtil();
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
    	lastUser = p;
    	
    	// ==> https://pm1.narvii.com/6842/660e3758e9712b95e4a7e57fde3dee187d5abf92v2_hq.jpg <== TEST URL
    	if(cmd.getName().equalsIgnoreCase("pixelart")){ 

    		sender.sendMessage("Generating pixelart...");
    		
    		
    		try {
    			GeneratePixelart(args[0], p, Integer.parseInt(args[1]), Integer.parseInt(args[2])	);
    		} catch (IOException e) {
    			this.getLogger().info(e.getMessage());
    		}
    		
    		
    		
    		return true;

    	}

    	return false; 

    }
	private void GeneratePixelart(String urlBase, Player playerRef, int width, int height) throws IOException {
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

		
		BufferedImage resizeImagePng = resizeImage(originalImage, type, width, height);
		ImageIO.write(resizeImagePng, "png", new File("pictures/test.png")); 
		
		World w = Bukkit.getWorld("world");
		String allPixels = "";
		playerRef.playSound(playerRef.getLocation(), Sound.BLOCK_ANVIL_BREAK, 100f, 50f);
		for(int y = 0; y < resizeImagePng.getHeight(); y++) {
			for(int x = 0; x < resizeImagePng.getWidth(); x++) {
				int px = resizeImagePng.getRGB(x, y);
				Color blockColor = new Color(px);
				Location nextBlock = new Location(w, playerRef.getLocation().getX()+x, playerRef.getLocation().getY()+resizeImagePng.getHeight()-y, playerRef.getLocation().getZ());
				Material nextMat = GetMaterialByColor(blockColor);
				nextBlock.getBlock().setType(nextMat);
			}
		}
		
		try (PrintWriter out1 = new PrintWriter("pictures/pixels.txt")) {
		    out1.println(allPixels);
		}
	}
	
	private Material GetMaterialByColor(Color c) throws FileNotFoundException {
		Material mat = Material.STONE;
		
		mat = colorToNameUtil.getColorNameFromColor(c);
		return mat;
	}
	
	
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height){
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
			
		return resizedImage;
	    }
    
}
