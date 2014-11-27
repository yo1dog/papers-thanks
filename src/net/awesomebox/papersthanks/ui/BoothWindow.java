package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class BoothWindow implements Interface
{
	private static final int MAX_HEIGHT_DIFFERENCE_CM = 4;
	
	public final InterrogateItem interrogateItem;
	
	private Face personFace;
	private int  personHeightCM;
	
	
	BoothWindow()
	{
		interrogateItem = new InterrogateItem(this, UI.windowArea.width / 2, UI.windowArea.height / 2, "Booth window.");
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
	public ClickSequence clickTo(int xRelToBoothWindow, int yRelToBoothWindow, String desc)
	{
		return new ClickSequence(click(xRelToBoothWindow, yRelToBoothWindow, desc));
	}
	
	@Override
	public ClickEvent click(int xRelToBoothWindow, int yRelToBoothWindow, String desc)
	{
		int x = UI.windowArea.x + xRelToBoothWindow;
		int y = UI.windowArea.y + yRelToBoothWindow;
		return new ClickEvent(x, y, desc);
	}
}
