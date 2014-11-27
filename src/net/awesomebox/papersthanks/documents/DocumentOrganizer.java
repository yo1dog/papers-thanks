package net.awesomebox.papersthanks.documents;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.ui.WorkView;

public class DocumentOrganizer
{
	private static final int DOCUMENT_SPACING = 10;
	
	
	// clicks on a document in a place that is guaranteed to be showing, which ensures the document is at the top of the pile
	public static ClickEvent clickToBringDocumentToTop(Document document)
	{
		if (document.xRelToDesk == -1)
			throw new AssertionError("Can not click on a document that is not on the desk.");
		
		int xRelToDesk = document.xRelToDesk + document.width - (DOCUMENT_SPACING / 2);
		int yRelToDesk = document.yRelToDesk + (DOCUMENT_SPACING / 2);
		return WorkView.desk.click(xRelToDesk, yRelToDesk, "Bottom corner of " + document.getClass().getSimpleName() + ".");
	}
	
	// clicks on a point inside a document
	public static ClickEvent clickDocument(Document document, int xRelToDocument, int yRelToDocument, String desc)
	{
		if (document.xRelToDesk == -1)
			throw new AssertionError("Can not click on a document that is not on the desk.");
		
		int xRelToDesk = document.xRelToDesk + xRelToDocument;
		int yRelToDesk = document.yRelToDesk + yRelToDocument;
		return WorkView.desk.click(xRelToDesk, yRelToDesk, desc);
	}
	
	
	public static void placeDocument(Document document, int xRelToDesk, int yRelToDesk)
	{
		document.xRelToDesk = xRelToDesk;
		document.yRelToDesk = yRelToDesk;
	}
}
