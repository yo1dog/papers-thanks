package net.awesomebox.papersthanks.documents.rulebook;

import java.util.HashMap;

import net.awesomebox.papersthanks.ui.ClickSequence;

public class RuleBookDocumentsTOCPage extends RuleBookPage
{
	private final HashMap<RuleBookDocumetLinkType, ClickPoint> documentLinks = new HashMap<RuleBookDocumetLinkType, ClickPoint>();
	
	RuleBookDocumentsTOCPage(RuleBook ruleBook, RuleBookDocumetLinkType[] documentLinkTypes)
	{
		super(ruleBook);
		
		int[][] documentLinkPoints = new int[][] {
			{145, 37},
			{145, 52},
			{145, 67},
			{145, 82},
			{145, 97},
			{145, 113},
			{145, 128},
			{145, 143} // TODO: does this exist? - what is the max number of links shown at one time?
		};
		
		for (int i = 0; i < documentLinkTypes.length; ++i)
			documentLinks.put(documentLinkTypes[i], new ClickPoint(this, documentLinkPoints[i][0], documentLinkPoints[i][1], documentLinkTypes[i] + " document link."));
	}
	
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
		ClickSequence clickSequence = ruleBook.tocPage.documentsLink.clickTo();   // click the documents link on the TOC page
		clickSequence.addClickEvent(click(xRelToDocument, yRelToDocument, desc)); // click the given point in the TOC page
		
		return clickSequence;
	}
	
	public ClickPoint getLinkForDocumentLinkType(RuleBookDocumetLinkType documentLinkType)
	{
		return documentLinks.get(documentLinkType);
	}
}
