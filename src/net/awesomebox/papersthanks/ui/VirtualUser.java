package net.awesomebox.papersthanks.ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.sun.glass.events.KeyEvent;

import net.awesomebox.papersthanks.ClickPreviewView;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class VirtualUser
{
	private static boolean DEBUG = true;
	
	
	private static int MOVE_DELAY_MS    = DEBUG? 100 : 20; // delay after moving
	private static int CLICK_DELAY_MS   = DEBUG? 100 : 0;  // delay after clicking
	private static int DRAG_DELAY_MS    = 20;              // delay after dragging
	private static int RELEASE_DELAY_MS = DEBUG? 100 : 0;  // delay after releasing
	
	private static int SMOOTH_MOVEMENT_INTERVAL_DIST     = 5; // amount mouse should move per interval
	private static int SMOOTH_MOVEMENT_INTERVAL_DELAY_MS = 2; // delay between intervals
	
	private static boolean SMOOTH_MOVEMENT = DEBUG;
	
	
	private static final int rx = -1920 + 8;
	private static final int ry = 0 + 30;
	private static final int s = 2;
	
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
	
	
	private static int lastMouseX = -1, lastMouseY = -1; // used only for smooth movements
	private static void moveMouseTo(int x, int y)
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
					(int)(rx + (lastMouseX + ((double)x - lastMouseX) * (i + 1) / intervals)*s),
					(int)(ry + (lastMouseY + ((double)y - lastMouseY) * (i + 1) / intervals)*s)
				);
				
				robot.delay(SMOOTH_MOVEMENT_INTERVAL_DELAY_MS);
			}
		}
		
		robot.mouseMove(rx + x*s, ry + y*s);
		
		lastMouseX = x;
		lastMouseY = y;
	}
	
	
	private static void executeClickEvent(ClickEvent clickEvent)
	{
		ClickPreviewView.addClickEvent(clickEvent);
		
		moveMouseTo(clickEvent.x, clickEvent.y);
		robot.delay(MOVE_DELAY_MS);
		
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(CLICK_DELAY_MS);
		
		if (clickEvent.dragToX > -1)
		{
			moveMouseTo(clickEvent.dragToX, clickEvent.dragToY);
			
			robot.delay(DRAG_DELAY_MS);
		}
		
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(RELEASE_DELAY_MS);
	}
	
	
	public static void executeClickSequence(ClickSequence clickSequence)
	{
		ClickEvent[] clickEvents = clickSequence.getClickEvents();
		
		//System.out.println("------------------------");
		//System.out.println("Executing Click Sequence");
		
		for (int i = 0; i < clickEvents.length; ++i)
		{
			ClickEvent clickEvent = clickEvents[i];
			
			//System.out.println((i + 1) + ". " + clickEvent);
			executeClickEvent(clickEvent);
			
			if (clickEvent.delayMS > 0)
				robot.delay(clickEvent.delayMS);
		}
	}
	
	
	public static void sendScreenShotCommand()
	{
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_PRINTSCREEN);
		robot.delay(50);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
		robot.delay(50);
	}
}
