package net.awesomebox.papersthanks.ui;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.awesomebox.papersthanks.documents.bulletin.Bulletin;
import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickAndDragEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class Desk extends Interface
{
	private static final int ITEM_SPACING = 5;
	
	
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
	
	void removeItemOnDesk(ItemOnDesk itemOnDesk)
	{
		if (!itemsOnDesk.contains(itemOnDesk))
			throw new AssertionError("Attempted to remove item fomr desk that was not on desk.");
		
		itemsOnDesk.remove(itemOnDesk);
		
		if (itemOnDesk instanceof RuleBook)
			ruleBook = null;
		else if (itemOnDesk instanceof Bulletin)
			bulletin = null;
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
	public MouseSequence clickTo()
	{
		return new MouseSequence();
	}
	
	@Override
	public MouseClickEvent click(int xRelToDesk, int yRelToDesk, String desc, MouseEventListener mouseEventListener)
	{
		int x = getAbsoluteX(xRelToDesk);
		int y = getAbsoluteY(yRelToDesk);
		return new MouseClickEvent(x, y, desc, mouseEventListener);
	}
	
	
	// returns a point relative to the desk on which the given item is guaranteed to be showing
	public Point getItemOnDeskVisiblePointRelToDesk(ItemOnDesk itemOnDesk)
	{
		// return the top-right corner of the document
		return new Point(
			itemOnDesk.xRelToDesk + itemOnDesk.width - (ITEM_SPACING / 2),
			itemOnDesk.yRelToDesk + (ITEM_SPACING / 2)
		);
	}
	
	// clicks on an item in a place that is guaranteed to be showing, which ensures the item will move to the top of the pile
	public MouseClickEvent clickToBringItemOnDeskToTop(ItemOnDesk itemOnDesk)
	{
		Point itemVisiblePointRelToDesk = getItemOnDeskVisiblePointRelToDesk(itemOnDesk);
		return click(itemVisiblePointRelToDesk.x, itemVisiblePointRelToDesk.y, "Bring " + itemOnDesk.getClass().getSimpleName() + " on desk to top.");
	}
	
	// clicks on a point inside an item
	public MouseClickEvent clickItemOnDesk(ItemOnDesk itemOnDesk, int xRelToItem, int yRelToItem, String desc, MouseEventListener mouseEventListener)
	{
		int xRelToDesk = itemOnDesk.xRelToDesk + xRelToItem;
		int yRelToDesk = itemOnDesk.yRelToDesk + yRelToItem;
		return click(xRelToDesk, yRelToDesk, desc, mouseEventListener);
	}
	
	
	public void prepareInterrogationItems(InterrogateItemSet iterrogateItems)
	{
		// move item1 to the top-left corner
		if (iterrogateItems.item1.parentItemOnDesk != null)
			moveItem(iterrogateItems.item1.parentItemOnDesk, 0, 0, "Move iterrogate item 1 to the top-left corner.");
		
		// move item2 to the top-right corner
		if (iterrogateItems.item2.parentItemOnDesk != null)
			moveItem(iterrogateItems.item2.parentItemOnDesk, WorkView.deskArea.width - iterrogateItems.item2.parentItemOnDesk.width, 0, "Move iterrogate item 2 to the top-right corner.");
		
		// click to show the items
		iterrogateItems.item1._interface.clickTo().execute();
		iterrogateItems.item2._interface.clickTo().execute();
	}
	
	public void reorganzieInterrogationItems(InterrogateItemSet iterrogateItems)
	{
		if (iterrogateItems.item1.parentItemOnDesk != null)
			organizeItemOnDesk(iterrogateItems.item1.parentItemOnDesk);
		
		if (iterrogateItems.item2.parentItemOnDesk != null)
			organizeItemOnDesk(iterrogateItems.item2.parentItemOnDesk);
	}
	
	
	public void moveItem(ItemOnDesk itemOnDesk, int xRelToDesk, int yRelToDesk, String desc)
	{
		Point itemVisiblePointRelToDesk = getItemOnDeskVisiblePointRelToDesk(itemOnDesk);
		
		int xRelToItem = itemVisiblePointRelToDesk.x - itemOnDesk.xRelToDesk;
		int yRelToItem = itemVisiblePointRelToDesk.y - itemOnDesk.yRelToDesk;
		
		new MouseSequence(new MouseClickAndDragEvent(
			getAbsoluteX(itemVisiblePointRelToDesk.x),
			getAbsoluteY(itemVisiblePointRelToDesk.y),
			getAbsoluteX(xRelToDesk + xRelToItem),
			getAbsoluteY(yRelToDesk + yRelToItem),
			desc
		)).execute();
		
		itemOnDesk.xRelToDesk = xRelToDesk;
		itemOnDesk.yRelToDesk = yRelToDesk;
	}
	
	public void organizeItemOnDesk(ItemOnDesk itemOnDesk)
	{
		int stackIndex = itemsOnDesk.indexOf(itemOnDesk);
		
		if (stackIndex == -1)
			throw new AssertionError("Attempted to orginize an ItemOnDesk that is not on this desk.");
		
		// calculate the position for this item
		int xRelToDesk = 0;
		for (int i = 0; i < stackIndex; ++i)
		{
			ItemOnDesk previousItem = itemsOnDesk.get(i);
			ItemOnDesk item = itemsOnDesk.get(i + 1);
			
			int rightSide = xRelToDesk + previousItem.width + ITEM_SPACING;
			xRelToDesk = Math.max(0, rightSide - item.width);
		}
		
		int yRelToDesk = ITEM_SPACING * stackIndex;
		
		moveItem(itemOnDesk, xRelToDesk, yRelToDesk, "Move " + itemOnDesk.getClass().getSimpleName() + " to orginized position " + stackIndex + " on desk.");
	}
	
	
	public BufferedImage takeScreenshotOfItemOnDesk(ItemOnDesk itemOnDesk)
	{
		return ImageUtil.takeScreenshot(
			getAbsoluteX(itemOnDesk.xRelToDesk),
			getAbsoluteY(itemOnDesk.yRelToDesk),
			itemOnDesk.width,
			itemOnDesk.height
		);
	}
}
