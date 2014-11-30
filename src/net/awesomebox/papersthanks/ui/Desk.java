package net.awesomebox.papersthanks.ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.awesomebox.papersthanks.documents.bulletin.Bulletin;
import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class Desk extends Interface
{
	private static final int ITEM_SPACING = 10;
	
	
	private RuleBook ruleBook;
	private Bulletin bulletin;
	
	private ArrayList<ItemOnDesk> itemsOnDesk = new ArrayList<ItemOnDesk>();
	
	
	Desk() {}
	
	
	public RuleBook getRuleBook()
	{
		return ruleBook;
	}
	public Bulletin getBulletin()
	{
		return bulletin;
	}
	
	void addItemOnDesk(ItemOnDesk itemOnDesk)
	{
		itemsOnDesk.add(itemOnDesk);
		
		if (itemOnDesk instanceof RuleBook)
			ruleBook = (RuleBook)itemOnDesk;
		else if (itemOnDesk instanceof Bulletin)
			bulletin = (Bulletin)itemOnDesk;
	}
	
	
	public int getAbsoluteX(int xRelToDesk)
	{
		return WorkView.deskArea.x + xRelToDesk;
	}
	public int getAbsoluteY(int yRelToDesk)
	{
		return WorkView.deskArea.y + yRelToDesk;
	}
	
	@Override
	public ClickSequence clickTo()
	{
		return new ClickSequence();
	}
	
	@Override
	public ClickEvent click(int xRelToDesk, int yRelToDesk, String desc)
	{
		int x = getAbsoluteX(xRelToDesk);
		int y = getAbsoluteY(yRelToDesk);
		return new ClickEvent(x, y, desc);
	}
	
	
	// clicks on an item in a place that is guaranteed to be showing, which ensures the item will move to the top of the pile
	public ClickEvent clickToBringItemOnDeskToTop(ItemOnDesk itemOnDesk)
	{
		int xRelToDesk = itemOnDesk.xRelToDesk + itemOnDesk.width - (ITEM_SPACING / 2);
		int yRelToDesk = itemOnDesk.yRelToDesk + (ITEM_SPACING / 2);
		return click(xRelToDesk, yRelToDesk, "Top-right corner of " + itemOnDesk.getClass().getSimpleName() + " on desk.");
	}
	
	// clicks on a point inside an item
	public ClickEvent clickItemOnDesk(ItemOnDesk itemOnDesk, int xRelToItem, int yRelToItem, String desc)
	{
		int xRelToDesk = itemOnDesk.xRelToDesk + xRelToItem;
		int yRelToDesk = itemOnDesk.yRelToDesk + yRelToItem;
		return click(xRelToDesk, yRelToDesk, desc);
	}
	
	
	private void moveItem(ItemOnDesk itemOnDesk, int xRelToDesk, int yRelToDesk, String desc)
	{
		int xRelToItem = itemOnDesk.width - (ITEM_SPACING / 2);
		int yRelToItem = ITEM_SPACING / 2;
		
		new ClickSequence(new ClickEvent(
				getAbsoluteX(itemOnDesk.xRelToDesk + xRelToItem),
				getAbsoluteY(itemOnDesk.yRelToDesk + yRelToItem),
				getAbsoluteX(xRelToDesk + xRelToItem),
				getAbsoluteY(yRelToDesk + yRelToItem),
				desc
			)).execute();
		
		itemOnDesk.xRelToDesk = xRelToDesk;
		itemOnDesk.yRelToDesk = yRelToDesk;
	}
	
	public void organizeItemOnDesk(ItemOnDesk itemOnDesk)
	{
		int i = itemsOnDesk.indexOf(itemOnDesk);
		
		if (i == -1)
			throw new AssertionError("Attempted to orginize an ItemOnDesk that is not on this desk.");
		
		int xRelToDesk = 0;
		int yRelToDesk = 0;
		
		if (i > 0)
		{
			ItemOnDesk previousItem = itemsOnDesk.get(i - 1);
			xRelToDesk = Math.max(0, (previousItem.xRelToDesk + previousItem.width + ITEM_SPACING) - itemOnDesk.width);
			yRelToDesk = previousItem.yRelToDesk  + ITEM_SPACING;
		}
		
		moveItem(itemOnDesk, xRelToDesk, yRelToDesk, "Move " + itemOnDesk.getClass().getSimpleName() + " to orginized position " + i + " on desk.");
	}
	
	
	public BufferedImage takeSnapshotOfItemOnDesk(ItemOnDesk itemOnDesk)
	{
		return ImageUtil.takeSnapshot(
			getAbsoluteX(itemOnDesk.xRelToDesk),
			getAbsoluteY(itemOnDesk.yRelToDesk),
			itemOnDesk.width,
			itemOnDesk.height
		);
	}
}
