package net.awesomebox.papersthanks.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.DebugOptions;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class VirtualUser
{
	private static int MOVE_DELAY_MS          = DebugOptions.SLOW_VIRTUAL_USER? 1000 : 50; // delay after moving
	private static int CLICK_DELAY_MS         = DebugOptions.SLOW_VIRTUAL_USER? 200  : 0;  // delay after clicking
	private static int MOVE_DRAGGING_DELAY_MS = Math.max(50, MOVE_DELAY_MS);               // delay after dragging
	private static int RELEASE_DELAY_MS       = DebugOptions.SLOW_VIRTUAL_USER? 200  : 0;  // delay after releasing
	
	private static int SMOOTH_MOVEMENT_INTERVAL_DIST     = 5; // amount mouse should move per interval
	private static int SMOOTH_MOVEMENT_INTERVAL_DELAY_MS = 2; // delay between intervals
	
	private static boolean SMOOTH_MOVEMENT = DebugOptions.SLOW_VIRTUAL_USER;
	
	
	private static final int rx;
	private static final int ry;
	
	
	private static final Robot robot = getRobot();
	private static final Robot getRobot()
	{
		try
		{
			return new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	static
	{
		BufferedImage screenshot = ImageUtil.takeFullscreenScreenshot(0, 0, 100, 100);
		
		// find the top left part of the screen
		int width  = screenshot.getWidth();
		int height = screenshot.getHeight();
		
		int checkSqrSize = 10;
		int checkRGBA = Color.BLACK.getRGB();
		
		int foundX = -1;
		int foundY = -1;
		
		for (int y = 0; y < height - checkSqrSize; ++y)
		{
			for (int x = 0; x < width - checkSqrSize; ++x)
			{
				// make sure there is a 5x5 block of black
				boolean valid = true;
				
				for (int ry = 0; ry < checkSqrSize; ++ry)
				{
					for (int rx = 0; rx < checkSqrSize; ++rx)
					{
						if (screenshot.getRGB(x + rx, y + ry) != checkRGBA)
						{
							valid = false;
							break;
						}
					}
					
					if (!valid)
						break;
				}
				
				if (valid)
				{
					foundX = x;
					foundY = y;
					break;
				}
			}
			
			if (foundX > -1)
				break;
		}
		
		if (foundX == -1)
			throw new AssertionError("Unable to find game window.");
		
		System.out.println("Found game window at (" + foundX + ", " + foundY + ").");
		
		rx = -1920 + foundX;
		ry = foundY;
	}
	
	
	private static int lastMouseX = -1, lastMouseY = -1; // used only for smooth movements
	static void mouseMove(int x, int y)
	{
		mouseMove(x, y, MOVE_DELAY_MS);
	}
	static void mouseMove(int x, int y, int delayMS)
	{
		if (lastMouseX > -1 && SMOOTH_MOVEMENT)
		{
			int distX = x - lastMouseX;
			int distY = y - lastMouseY;
			double dist = Math.sqrt((distX * distX) + (distY * distY));
			
			int intervals = (int)Math.round(dist / SMOOTH_MOVEMENT_INTERVAL_DIST);
			
			// last interval will be covered by the final movement
			for (int i = 0; i < intervals - 1; ++i)
			{
				robot.mouseMove(
					(int)(rx + (lastMouseX + ((double)x - lastMouseX) * (i + 1) / intervals) * WorkView.SCALE),
					(int)(ry + (lastMouseY + ((double)y - lastMouseY) * (i + 1) / intervals) * WorkView.SCALE)
				);
				
				robot.delay(SMOOTH_MOVEMENT_INTERVAL_DELAY_MS);
			}
		}
		
		robot.mouseMove(rx + x * WorkView.SCALE, ry + y * WorkView.SCALE);
		robot.delay(MOVE_DELAY_MS);
		
		lastMouseX = x;
		lastMouseY = y;
	}
	
	static void mouseClick(int x, int y)
	{
		mouseMove(x, y);
		
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(CLICK_DELAY_MS);
		
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(RELEASE_DELAY_MS);
	}
	
	static void mouseClickAndDrag(int x1, int y1, int x2, int y2)
	{
		mouseMove(x1, y1);
		
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(CLICK_DELAY_MS);
		
		mouseMove(x2, y2, MOVE_DRAGGING_DELAY_MS);
		
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(RELEASE_DELAY_MS);
	}
	
	static void delay(int ms)
	{
		robot.delay(ms);
	}
	
	
	
	public static void sendScreenShotCommand()
	{
		sendScreenShotCommand(false);
	}
	public static void sendScreenShotCommand(boolean fullscreen)
	{
		if (!fullscreen)
			robot.keyPress(KeyEvent.VK_ALT);
		
		robot.keyPress(KeyEvent.VK_PRINTSCREEN);
		robot.delay(50);
		
		if (!fullscreen)
			robot.keyRelease(KeyEvent.VK_ALT);
		
		robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
		robot.delay(50);
	}
}
