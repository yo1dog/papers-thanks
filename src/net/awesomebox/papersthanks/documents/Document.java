package net.awesomebox.papersthanks.documents;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.ui.Interface;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;

public abstract class Document implements Interface
{
	public static abstract class DocumentError
	{
		public final String message;
		public final InterrogateItemSet interrogateItems;
		
		public DocumentError(String message, InterrogateItemSet interrogateItems)
		{
			this.message = message;
			this.interrogateItems = interrogateItems;
		}
		
		@Override
		public String toString()
		{
			return this.getClass().getName() + ": " + message;
		}
	}
	
	
	protected int xRelToDesk = -1, yRelToDesk = -1;
	public final int width, height;
	
	public Document(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	
	// clicks to ensure the document is showing then clicks on the given point inside the document
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc) 
	{
		ClickSequence clickSequence = new ClickSequence();
		
		clickSequence.addClickEvent(DocumentOrganizer.clickToBringDocumentToTop(this)); // click to make sure the document is on top
		clickSequence.addClickEvent(click(xRelToDocument, yRelToDocument, desc));       // click given point in the document
		
		return clickSequence;
	}
	
	@Override
	public ClickEvent click(int xRelToDocument, int yRelToDocument, String desc) 
	{
		return DocumentOrganizer.clickDocument(this, xRelToDocument, yRelToDocument, desc);
	}
}
