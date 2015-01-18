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
import net.awesomebox.papersthanks.ui.WorkView;

public class ImageUtil
{
	public static final String IMAGE_EXT = ".png";
	
	public static final String IMAGES_DIR = "assets/images/";
	public static final String FONTS_DIR  = "assets/fonts/";
	
	
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
	
	
	
	public static BufferedImage takeScreenshot()
	{
		return takeScreenshot(0, 0, WorkView.WIDTH, WorkView.HEIGHT);
	}
	
	public static BufferedImage takeScreenshot(int x, int y, int width, int height)
	{
		VirtualUser.sendScreenShotCommand();
		
		// get image from clipboard
		if (!clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor))
			throw new AssertionError("Attempting to get image from clipboard when clipboard does not contain an image.");
		
		BufferedImage image = getImageFromClipboard();
	    
		image = image.getSubimage(8 + x * WorkView.SCALE, 30 + y * WorkView.SCALE, width * WorkView.SCALE, height * WorkView.SCALE);
		
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
	
	public static BufferedImage takeFullscreenScreenshot()
	{
		VirtualUser.sendScreenShotCommand(true);
	    return getImageFromClipboard();
	}
	
	public static BufferedImage takeFullscreenScreenshot(int x, int y, int width, int height)
	{
	    return takeFullscreenScreenshot().getSubimage(x, y, width, height);
	}
	
	private static BufferedImage getImageFromClipboard()
	{
		try
		{
			return (BufferedImage)clipboard.getData(DataFlavor.imageFlavor);
		}
		catch (UnsupportedFlavorException | IOException e)
		{
			throw new AssertionError("Error getting image from clipboard after screenshot.", e);
		}
	}
	
	
	public static double calculateColorDifference(int argb1, int argb2)
	{
		int r1 = (argb1 >> 16) & 0b00000000000000000000000011111111;
		int g1 = (argb1 >> 8)  & 0b00000000000000000000000011111111;
		int b1 = (argb1)       & 0b00000000000000000000000011111111;
		
		int r2 = (argb2 >> 16) & 0b00000000000000000000000011111111;
		int g2 = (argb2 >> 8)  & 0b00000000000000000000000011111111;
		int b2 = (argb2)       & 0b00000000000000000000000011111111;
		
		// the difference between two colors is really a 3D problem.
		// each color value (red, green, and blue) is an axis (X, Y, Z). A
		// color is a 3D position on these axis. So the distance between two
		// colors is the distance between their 3D points. So we use the
		// distance formula:
		// sqrt((X2-X1)^2, (Y2-Y1)^2, (Z2-Z1)^2)
		return Math.sqrt(
			(r2 - r1)*(r2 - r1) +
			(g2 - g1)*(g2 - g1) +
			(b2 - b1)*(b2 - b1)
		);
	}
}
