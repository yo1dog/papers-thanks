package net.awesomebox.papersthanks.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageUtil
{
	public static final String IMAGE_EXT = ".png";
	
	public static final String IMAGES_DIR                    = "assets/images/";
	public static final String NATION_IMAGES_SUBDIR          = "nations/";
	public static final String FACE_IMAGES_SUBDIR            = "faces/";
	public static final String DIPLOMATIC_SEAL_IMAGES_SUBDIR = "diplomaticSeals/";
	public static final String DETECTOR_IMAGES_SUBDIR        = "detectors/";
	
	
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
	
	
	public static Color convertPixelToColor(int pixel)
	{
		int r = (pixel >> 16) & 0b00000000000000000000000011111111;
		int g = (pixel >> 8)  & 0b00000000000000000000000011111111;
		int b = (pixel)       & 0b00000000000000000000000011111111;
		
		return new Color(r, g, b);
	}
}
