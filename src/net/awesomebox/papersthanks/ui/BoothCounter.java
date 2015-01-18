package net.awesomebox.papersthanks.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickAndDragEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;

public class BoothCounter extends Interface
{
	public static final Point itemInitialPoint     = new Point(90, 246);
	public static final Point junkPilePoint        = new Point(18, 246);
	public static final Point failedGivenItemPoint = new Point(BoothWindow.giveToPersonPoint.x, itemInitialPoint.y);
	
	private static final Color counterColor = new Color(132, 138, 107);
	
	
	public final InterrogateItem interrogateItem;
	
	BoothCounter()
	{
		interrogateItem = new InterrogateItem(this, null, WorkView.counterArea.width / 2, WorkView.counterArea.height / 2, "Booth counter.");
	}
	
	
	public boolean checkForItem(BufferedImage screenshot)
	{
		// check if there are items on the counter
		int argb = screenshot.getRGB(itemInitialPoint.x * WorkView.SCALE, itemInitialPoint.y * WorkView.SCALE);
		
		return argb != counterColor.getRGB();
	}
	
	public boolean checkForFailedGivenItem(BufferedImage screenshot)
	{
		// check if there are items on the counter that failed to be given to the person
		int argb = screenshot.getRGB(failedGivenItemPoint.x * WorkView.SCALE, failedGivenItemPoint.y * WorkView.SCALE);
		
		return argb != counterColor.getRGB();
	}
	
	// moves an item at x, y into the junk pile
	public void moveItemIntoJunkPile(int x, int y, String desc)
	{
		new MouseSequence(new MouseClickAndDragEvent(
			x, y,
			junkPilePoint.x, junkPilePoint.y,
			desc
		)).execute();
	}
	
	
	@Override
	public MouseSequence clickTo()
	{
		return new MouseSequence();
	}
	
	@Override
	public MouseClickEvent click(int xRelToBoothCounter, int yRelToBoothCounter, String desc, MouseEventListener mouseEventListener)
	{
		int x = WorkView.counterArea.x + xRelToBoothCounter;
		int y = WorkView.counterArea.y + yRelToBoothCounter;
		return new MouseClickEvent(x, y, desc, mouseEventListener);
	}
}