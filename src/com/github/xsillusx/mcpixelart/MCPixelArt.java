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
				
				if(!allPixels.contains(colorToNameUtil.getColorNameFromColor(blockColor)))
					allPixels = allPixels + colorToNameUtil.getColorNameFromColor(blockColor) + ",";
				nextBlock.getBlock().setType(nextMat);
			}
		}
		
		try (PrintWriter out1 = new PrintWriter("pictures/pixels.txt")) {
		    out1.println(allPixels);
		}
	}
	
	private Material GetMaterialByColor(Color c) throws FileNotFoundException {
		Material mat = Material.STONE;
		
		String colorname = colorToNameUtil.getColorNameFromColor(c);
		
		switch(colorname) {
		case "Black":
			mat = Material.BLACK_WOOL;
			break;
		case "SaddleBrown":
			mat = Material.BROWN_WOOL;
			break;
		case "DarkOliveGreen":
			mat = Material.GREEN_WOOL;
			break;
		case "IndianRed":
			mat = Material.RED_WOOL;
			break;
		case "PeachPuff":
			mat = Material.PINK_WOOL;
			break;
		case "DimGray":
			mat = Material.GRAY_WOOL;
			break;
		case "Maroon":
			mat = Material.RED_WOOL;
			break;
		case "DarkSlateGray":
			mat = Material.GRAY_WOOL;
			break;
		case "Tan":
			mat = Material.PINK_WOOL;
			break;
		case "Wheat":
			mat = Material.PINK_WOOL;
			break;
		case "BurlyWood":
			mat = Material.OAK_WOOD;
			break;
		case "LightGray":
			mat = Material.GRAY_WOOL;
			break;
		case "White":
			mat = Material.SNOW_BLOCK;
			break;
		case "DarkGray":
			mat = Material.GRAY_WOOL;
			break;
		case "WhiteSmoke":
			mat = Material.WHITE_WOOL;
			break;
		case "Lavender":
			mat = Material.MAGENTA_WOOL;
			break;
		case "LightSlateGray":
			mat = Material.GRAY_WOOL;
			break;
		case "Gainsboro":
			mat = Material.LIGHT_BLUE_WOOL;
			break;
		case "RosyBrown":
			mat = Material.OAK_WOOD;
			break;
		case "NavajoWhite":
			mat = Material.WHITE_WOOL;
			break;
		case "LightPink":
			mat = Material.PINK_WOOL;
			break;
		case "Khaki":
			mat = Material.GREEN_WOOL;
			break;
		case "Peru":
			mat = Material.ORANGE_WOOL;
			break;
		case "OliveDrab":
			mat = Material.GREEN_WOOL;
			break;
		case "DarkKhaki":
			mat = Material.GREEN_WOOL;
			break;
		case "YellowGreen":
			mat = Material.YELLOW_WOOL;
			break;
		case "Sienna":
			mat = Material.BROWN_WOOL;
			break;
		case "SandyBrown":
			mat = Material.BROWN_WOOL;
			break;
		case "DarkGoldenRod":
			mat = Material.GOLD_BLOCK;
			break;
		case "Silver":
			mat = Material.GRAY_WOOL;
			break;
		case "Bisque":
			mat = Material.PINK_WOOL;
			break;
		case "Thistle":
			mat = Material.PINK_WOOL;
			break;
		case "MistyRose":
			mat = Material.PINK_WOOL;
			break;
		case "LightYellow":
			mat = Material.YELLOW_WOOL;
			break;
		case "DarkSalmon":
			mat = Material.ORANGE_WOOL;
			break;
		}
		
		
		if(colorname.toLowerCase().contains("aqua") || colorname.toLowerCase().contains("blue")) {
			mat = Material.BLUE_WOOL;
		}
		if(colorname.toLowerCase().contains("brown")) {
			mat = Material.BROWN_WOOL;
		}
		
		
		
		
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
