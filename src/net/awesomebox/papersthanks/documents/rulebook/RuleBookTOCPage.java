package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;

public class RuleBookTOCPage extends RuleBookPage
{
	public final ClickPoint rulesLink;
	public final ClickPoint mapLink;
	public final ClickPoint boothLink;
	public final ClickPoint documentsLink;
	public final ClickPoint confiscationLink;
	
	RuleBookTOCPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		rulesLink        = new ClickPoint(this, 160, 47,  "Rules page link.");
		mapLink          = new ClickPoint(this, 160, 64,  "Map page link.");
		boothLink        = new ClickPoint(this, 160, 81,  "Booth page link.");
		documentsLink    = new ClickPoint(this, 160, 98,  "Documents page link.");
		confiscationLink = new ClickPoint(this, 160, 115, "Confiscation page link.");
	}
	
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
		ClickSequence clickSequence = ruleBook.tocLink.clickTo();                 // click TOC link
		clickSequence.addClickEvent(click(xRelToDocument, yRelToDocument, desc)); // click given point in the TOC page
		
		return clickSequence;
	}
}
