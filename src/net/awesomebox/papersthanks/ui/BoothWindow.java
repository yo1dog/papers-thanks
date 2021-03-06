package net.awesomebox.papersthanks.ui;

import java.awt.Point;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;

public class BoothWindow extends Interface
{
	private static final int MAX_HEIGHT_DIFFERENCE_CM = 4;
	
	public static final Point giveToPersonPoint     = new Point(135, WorkView.windowArea.y + WorkView.windowArea.height / 2);
	public static final Point giveJunkToPersonPoint = new Point(40,  WorkView.windowArea.y + WorkView.windowArea.height / 2);
	
	public final InterrogateItem interrogateItem;
	
	private Face personFace;
	private int  personHeightCM;
	
	
	BoothWindow()
	{
		interrogateItem = new InterrogateItem(this, null, WorkView.windowArea.width / 2, WorkView.windowArea.height / 2, "Booth window.");
	}
	
	
	public Face getPeronFace()
	{
		return personFace;
	}
	public int getPersonHeightCM()
	{
		return personHeightCM;
	}
	
	public static void examinePerson()
	{
		// TODO: logic for detecting the person's face from the screen
		// face.imageDetector.checkImage(image, UI.windowArea.x, UI.windowArea.y, scale, positiveColors, negativeColors)
		// personFace = ...
		
		// TODO: logic for determining the person's height
		// personHeightCM = ...
	}
	
	// checks if the given height is close enough to the current person's height
	public boolean comparePersonsHeight(int heightCM)
	{
		return Math.abs(personHeightCM - heightCM) <= MAX_HEIGHT_DIFFERENCE_CM;
	}
	
	
	@Override
	public MouseSequence clickTo()
	{
		return new MouseSequence();
	}
	
	@Override
	public MouseClickEvent click(int xRelToBoothWindow, int yRelToBoothWindow, String desc, MouseEventListener mouseEventListener)
	{
		int x = WorkView.windowArea.x + xRelToBoothWindow;
		int y = WorkView.windowArea.y + yRelToBoothWindow;
		return new MouseClickEvent(x, y, desc, mouseEventListener);
	}
}
