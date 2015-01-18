package net.awesomebox.papersthanks.documents.bulletin;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;
import net.awesomebox.papersthanks.ui.Interface;

public abstract class BulletinPage extends Interface
{
	protected final Bulletin bulletin;
	protected int pageNumber = -1;
	
	protected BulletinPage(Bulletin bulletin)
	{
		this.bulletin = bulletin;
	}
	
	
	@Override
	public MouseSequence clickTo()
	{
		if (pageNumber == -1)
			throw new AssertionError("Attempted to click to bulletin page " + this.getClass().getSimpleName() + " which does not have a page number.");
		
		// click to show the bulletin
		MouseSequence mouseSequence = bulletin.clickTo();
		
		int currentPageNumber = bulletin.getCurrentPageNumber();
		
		if (pageNumber > currentPageNumber)
		{
			// click to the next page until we are on this page
			for (int i = currentPageNumber; i < pageNumber; ++i)
				mouseSequence.add(bulletin.nextPageLink.click());
		}
		else if (pageNumber < currentPageNumber)
		{
			// click to the previous page until we are on this page
			for (int i = currentPageNumber; i > pageNumber; --i)
				mouseSequence.add(bulletin.previousPageLink.click());
		}
		
		return mouseSequence;
	}
	
	@Override
	public MouseClickEvent click(int xRelToDocument, int yRelToDocument, String desc, MouseEventListener mouseEventListener)
	{
		return bulletin.click(xRelToDocument, yRelToDocument, desc, mouseEventListener);
	}
}