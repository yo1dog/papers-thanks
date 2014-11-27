package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageDetector {
	public final Point[] positivePoints; // pixels that should be occupied
	public final Point[] negativePoints; // pixels that should not be occupied
	
	public ImageDetector(Point[] positivePoints, Point[] negativePoints)
	{
		this.positivePoints = positivePoints;
		this.negativePoints = negativePoints;
	}
	
	public boolean checkImage(BufferedImage image, int xOrigin, int yOrigin, float scale, Color[] positiveColors, Color[] negativeColors)
	{
		return checkImage(image, xOrigin, yOrigin, scale, positiveColors, negativeColors, 20);
	}
	public boolean checkImage(BufferedImage image, int xOrigin, int yOrigin, float scale, Color[] positiveColors, Color[] negativeColors, int tolerance)
	{
		// check all the positive points
		for (int i = 0; i < positivePoints.length; ++i)
		{
			int x = Math.round(xOrigin + positivePoints[i].x * scale);
			int y = Math.round(yOrigin + positivePoints[i].y * scale);
			
			if (x >= image.getWidth() || y >= image.getHeight())
				return false;
			
			int pixel = image.getRGB(x, y);
			
			// check if the pixel is close to any of the valid positive colors
			boolean colorFound = false;
			for (int j = 0; j < positiveColors.length; ++j)
			{
				int positivePixel = positiveColors[i].getRGB();
				
				double diff = calculateColorDifference(pixel, positivePixel);
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
			int x = Math.round(xOrigin + negativePoints[i].x * scale);
			int y = Math.round(yOrigin + negativePoints[i].y * scale);
			
			if (x >= image.getWidth() || y >= image.getHeight()) {
				return false;
			}
			
			int pixel = image.getRGB(x, y);
			
			// check if the pixel is close to any of the valid negative colors
			boolean colorFound = false;
			for (int j = 0; j < negativeColors.length; ++j)
			{
				int negativePixel = negativeColors[i].getRGB();
				
				double diff = calculateColorDifference(pixel, negativePixel);
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
	
	
	private static double calculateColorDifference(int pixel1, int pixel2) {
		int r1 = (pixel1 >> 16) & 0b00000000000000000000000011111111;
		int g1 = (pixel1 >> 8)  & 0b00000000000000000000000011111111;
		int b1 = (pixel1)       & 0b00000000000000000000000011111111;
		
		int r2 = (pixel2 >> 16) & 0b00000000000000000000000011111111;
		int g2 = (pixel2 >> 8)  & 0b00000000000000000000000011111111;
		int b2 = (pixel2)       & 0b00000000000000000000000011111111;
		
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
	
	
	
	
	public static final Color DETECTOR_IMAGE_POSITIVE_COLOR = new Color(6, 255, 9);
	public static final Color DETECTOR_IMAGE_NEGATIVE_COLOR = new Color(6, 9, 255);
	
	public static ImageDetector fromDetectorImage(BufferedImage image) {
		ArrayList<Point> positivePointsList = new ArrayList<Point>();
		ArrayList<Point> negativePointsList = new ArrayList<Point>();
		
		int positiveRGB = DETECTOR_IMAGE_POSITIVE_COLOR.getRGB();
		int negativeRGB = DETECTOR_IMAGE_NEGATIVE_COLOR.getRGB();
		
		for (int y = 0; y < image.getHeight(); ++y)
		{
			for (int x = 0; x < image.getWidth(); ++x)
			{
				int pixel = image.getRGB(x, y);
				
				if (pixel == positiveRGB)
					positivePointsList.add(new Point(x, y));
				else if (pixel == negativeRGB)
					negativePointsList.add(new Point(x, y));
			}
		}
		
		return new ImageDetector(
			positivePointsList.toArray(new Point[positivePointsList.size()]),
			negativePointsList.toArray(new Point[negativePointsList.size()])
		);
	}
}
