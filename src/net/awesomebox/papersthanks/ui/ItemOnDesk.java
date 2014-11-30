package net.awesomebox.papersthanks.ui;

import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

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
	
	// clicks to ensure the item is showing then clicks on the given point inside the item
	@Override
	public ClickSequence clickTo() 
	{
		return new ClickSequence(desk.clickToBringItemOnDeskToTop(this)); // click to make sure the item is on top
	}
	
	@Override
	public ClickEvent click(int xRelToDocument, int yRelToDocument, String desc) 
	{
		return desk.clickItemOnDesk(this, xRelToDocument, yRelToDocument, desc);
	}
	
	public BufferedImage takeSnapshot()
	{
		return desk.takeSnapshotOfItemOnDesk(this);
	}
}