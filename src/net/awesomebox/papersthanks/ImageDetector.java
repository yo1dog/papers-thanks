package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.awesomebox.papersthanks.utils.ImageUtil;

public class ImageDetector {
	public static final Color DETECTOR_IMAGE_POSITIVE_COLOR = new Color(34, 177, 76); // green
	public static final Color DETECTOR_IMAGE_NEGATIVE_COLOR = new Color(237, 38, 36); // red
	
	public static final int DEFAULT_SQR_DIST  = 6;
	public static final int DEFAULT_TOLERANCE = 100;
	
	public final Point[] positivePoints; // pixels that should be occupied
	public final Point[] negativePoints; // pixels that should not be occupied
	
	public ImageDetector(Point[] positivePoints, Point[] negativePoints)
	{
		this.positivePoints = positivePoints;
		this.negativePoints = negativePoints;
	}
	
	
	public boolean checkImageNear(BufferedImage image, Color[] positiveColors, Color[] negativeColors, Point origin, int scale)
	{
		return checkImageNear(image, positiveColors, negativeColors, origin, scale, DEFAULT_TOLERANCE, DEFAULT_SQR_DIST);
	}
	public boolean checkImageNear(BufferedImage image, Color[] positiveColors, Color[] negativeColors, Point origin, int scale, int tolerance, int sqrDist)
	{
		// try the origin first
		boolean matches = checkImage(image, positiveColors, negativeColors, origin, scale, tolerance);
		
		if (matches)
			return true;
		
		for (int yOffset = 0; yOffset < sqrDist; ++yOffset)
		{
			for (int xOffset = 0; xOffset < sqrDist; ++xOffset)
			{
				Point newOrigin = new Point(origin.x + xOffset - (sqrDist / 2), origin.y + yOffset - (sqrDist / 2));
				
				matches = checkImage(image, positiveColors, negativeColors, newOrigin, scale, tolerance);
				if (matches)
				{
					origin.x = newOrigin.x;
					origin.y = newOrigin.y;
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean checkImage(BufferedImage image, Color[] positiveColors, Color[] negativeColors, Point point, int scale)
	{
		return checkImage(image, positiveColors, negativeColors, point, scale, DEFAULT_TOLERANCE);
	}
	public boolean checkImage(BufferedImage image, Color[] positiveColors, Color[] negativeColors, Point point, int scale, int tolerance)
	{
		// return false if we have no points to check
		if (positivePoints.length == 0 && negativePoints.length == 0)
			return false;
		
		// check all the positive points
		for (int i = 0; i < positivePoints.length; ++i)
		{
			int x = (point.x + positivePoints[i].x) * scale;
			int y = (point.y + positivePoints[i].y) * scale;
			
			if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight())
				return false;
			
			int pixel = image.getRGB(x, y);
			
			// check if the pixel is close to any of the valid positive colors
			boolean colorFound = false;
			for (int j = 0; j < positiveColors.length; ++j)
			{
				int positivePixel = positiveColors[j].getRGB();
				
				double diff = ImageUtil.calculateColorDifference(pixel, positivePixel);
				if (diff <= tolerance)
				{
					colorFound = true;
					break;
				}
			}
			
			if (!colorFound)
				return false;
		}
		
		
		// check all the negative points
		for (int i = 0; i < negativePoints.length; ++i)
		{
			int x = (point.x + negativePoints[i].x) * scale;
			int y = (point.y + negativePoints[i].y) * scale;
			
			if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
				return false;
			}
			
			int pixel = image.getRGB(x, y);
			
			// check if the pixel is close to any of the valid negative colors
			boolean colorFound = false;
			for (int j = 0; j < negativeColors.length; ++j)
			{
				int negativePixel = negativeColors[j].getRGB();
				
				double diff = ImageUtil.calculateColorDifference(pixel, negativePixel);
				if (diff <= tolerance)
				{
					colorFound = true;
					break;
				}
			}
			
			if (!colorFound)
				return false;
		}
		
		
		return true;
	}
	
	
	
	public static ImageDetector fromDetectorImage(BufferedImage image)
	{
		return fromDetectorImage(image, 0, 0, image.getWidth(), image.getHeight(), DETECTOR_IMAGE_POSITIVE_COLOR.getRGB(), DETECTOR_IMAGE_NEGATIVE_COLOR.getRGB());
	}
	public static ImageDetector fromDetectorImage(BufferedImage image, int sx, int sy, int swidth, int sheight, int positiveARGB, int negativeARGB)
	{
		return fromDetectorImage(image, sx, sy, swidth, sheight, positiveARGB, negativeARGB, 0, 0, 0, 0);
	}
	public static ImageDetector fromDetectorImage(BufferedImage image, int sx, int sy, int swidth, int sheight, int positiveARGB, int negativeARGB, int pleft, int ptop, int pright, int pbottom)
	{
		ArrayList<Point> positivePointsList = new ArrayList<Point>();
		ArrayList<Point> negativePointsList = new ArrayList<Point>();
		
		for (int y = 0; y < sheight; ++y)
		{
			for (int x = 0; x < swidth; ++x)
			{
				int pixel = image.getRGB(sx + x, sy + y);
				
				if (pixel == positiveARGB)
					positivePointsList.add(new Point(pleft + x, ptop + y));
				else if (pixel == negativeARGB)
					negativePointsList.add(new Point(pleft + x, ptop + y));
			}
		}
		
		// top padding
		for (int y = 0; y < ptop; ++y)
		{
			for (int x = 0; x < swidth; ++x)
				negativePointsList.add(new Point(pleft + x, y));
		}
		
		// bottom padding
		for (int y = 0; y < pbottom; ++y)
		{
			for (int x = 0; x < swidth; ++x)
				negativePointsList.add(new Point(pleft + x, ptop + sheight + y));
		}
		
		// left padding
		for (int x = 0; x < pleft; ++x)
		{
			for (int y = 0; y < ptop + sheight + pbottom; ++y)
				negativePointsList.add(new Point(x, y));
		}
		
		// right padding
		for (int x = 0; x < pright; ++x)
		{
			for (int y = 0; y < ptop + sheight + pbottom; ++y)
				negativePointsList.add(new Point(pleft + swidth + x, y));
		}
		
		return new ImageDetector(
			positivePointsList.toArray(new Point[positivePointsList.size()]),
			negativePointsList.toArray(new Point[negativePointsList.size()])
		);
	}
}
