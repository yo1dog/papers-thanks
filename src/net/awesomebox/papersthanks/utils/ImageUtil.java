package net.awesomebox.papersthanks.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.awesomebox.papersthanks.ui.VirtualUser;

public class ImageUtil
{
	public static final String IMAGE_EXT = ".png";
	
	public static final String IMAGES_DIR                    = "assets/images/";
	public static final String NATION_IMAGES_SUBDIR          = "nations/";
	public static final String FACE_IMAGES_SUBDIR            = "faces/";
	public static final String DIPLOMATIC_SEAL_IMAGES_SUBDIR = "diplomaticSeals/";
	public static final String DETECTOR_IMAGES_SUBDIR        = "detectors/";
	
	public static final String FONTS_DIR = "assets/fonts/";
	
	
	private final static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	
	// gets all valid, invalid, and detector images from a directory
	public static ArrayList<BufferedImage> getDirectoryImages(String dirStr)
	{
		return getDirectoryImages(new File(dirStr));
	}
	public static ArrayList<BufferedImage> getDirectoryImages(File dir)
	{
		if (!dir.isDirectory())
			throw new AssertionError("\"" + dir + "\" is not a directory.");
		
		File[] files = dir.listFiles();
		
		
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		for (int i = 0; i < files.length; ++i)
		{
			File file = files[i];
			
			// make sure the file is a file
			if (!file.isFile())
				continue;
			
			// make sure it has the correct ext
			if (!file.getName().endsWith(IMAGE_EXT))
				continue;
			
			// read the file as an image
			images.add(readImage(file));
		}
		
		return images;
	}
	
	public static BufferedImage readImage(String fileStr)
	{
		return readImage(new File(fileStr));
	}
	public static BufferedImage readImage(File file)
	{
		try
		{
			return ImageIO.read(file);
		}
		catch (IOException e)
		{
			throw new AssertionError("Error reading file \"" + file.getPath() + "\" as an image.", e);
		}
	}
	
	
	
	public static BufferedImage takeSnapshot(int x, int y, int width, int height)
	{
		int s = 2;
		
		VirtualUser.sendScreenShotCommand();
		
		// get image from clipboard
		if (!clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor))
			throw new AssertionError("Attempting to get image from clipboard when clipboard does not contain an image.");
		
		BufferedImage image;
		try
		{
			image = (BufferedImage)clipboard.getData(DataFlavor.imageFlavor);
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			throw new AssertionError("Error getting image from clipboard after screenshot.", e);
		}
		
		
		image = image.getSubimage(8 + x*s, 30 + y*s, width*s, height*s);
		
		File outputfile = new File("testscreen.png");
	    try
		{
			ImageIO.write(image, "png", outputfile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	    
	    return image;
	}
}
