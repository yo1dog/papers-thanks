package net.awesomebox.papersthanks.ui;

import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;

public abstract class ItemOnDesk extends Interface
{
	private final Desk desk;
	final int width, height;
	
	int xRelToDesk, yRelToDesk;
	
	protected ItemOnDesk(Desk desk, int xRelToDesk, int yRelToDesk, int width, int height)
	{
		this.desk   = desk;
		
		this.width  = width;
		this.height = height;
		
		this.xRelToDesk = xRelToDesk;
		this.yRelToDesk = yRelToDesk;
		
		// add this item to list of items on desk
		this.desk.addItemOnDesk(this);
	}
	
	public final int getAbsoluteX(int xRelToItem)
	{
		return desk.getAbsoluteX(xRelToDesk + xRelToItem);
	}
	public final int getAbsoluteY(int yRelToItem)
	{
		return desk.getAbsoluteY(yRelToDesk + yRelToItem);
	}
	
	// clicks to ensure the item is showing then clicks on the given point inside the item
	@Override
	public MouseSequence clickTo() 
	{
		return new MouseSequence(desk.clickToBringItemOnDeskToTop(this)); // click to make sure the item is on top
	}
	
	@Override
	public MouseClickEvent click(int xRelToItem, int yRelToItem, String desc, MouseEventListener mouseEventListener) 
	{
		return desk.clickItemOnDesk(this, xRelToItem, yRelToItem, desc, mouseEventListener);
	}
	
	public BufferedImage takeScreenshot()
	{
		return desk.takeScreenshotOfItemOnDesk(this);
	}
}