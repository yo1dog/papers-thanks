package net.awesomebox.papersthanks.documents.rulebook;

import java.util.HashMap;

import net.awesomebox.papersthanks.ui.ClickSequence;

public class RuleBookDocumentsTOCPage extends RuleBookPage
{
	private final int[][] documentLinkPoints = new int[][] {
		{145, 37},
		{145, 52},
		{145, 67},
		{145, 82},
		{145, 97},
		{145, 113},
		{145, 128},
		{145, 143} // TODO: does this exist? - what is the max number of links shown at one time?
	};
	
	private HashMap<RuleBookDocumetLinkType, ClickPoint> documentLinks = null;
	
	RuleBookDocumentsTOCPage(RuleBook ruleBook)
	{
		super(ruleBook);
	}
	
	
	@Override
	public ClickSequence clickTo()
	{
		// click the documents link on the TOC page
		return ruleBook.tocPage.documentsLink.clickThrough();
	}
	
	public ClickPoint getLinkForDocumentLinkType(RuleBookDocumetLinkType documentLinkType)
	{
		if (documentLinks == null)
			throw new AssertionError("Attempting to access document links before they have been read.");
		
		return documentLinks.get(documentLinkType);
	}
	
	
	public void readDocumentLinks()
	{
		// go to this page
		clickTo().execute();
		
		// TODO: logic for reading documents
		RuleBookDocumetLinkType[] documentLinkTypes = new RuleBookDocumetLinkType[0];
		
		documentLinks = new HashMap<RuleBookDocumetLinkType, ClickPoint>(documentLinkTypes.length);
		
		for (int i = 0; i < documentLinkTypes.length; ++i)
			documentLinks.put(documentLinkTypes[i], new ClickPoint(this, documentLinkPoints[i][0], documentLinkPoints[i][1], "Rule book documents TOC page " + documentLinkTypes[i] + " document link."));
	}
}
