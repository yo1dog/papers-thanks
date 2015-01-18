package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;

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
		
		rulesLink        = new ClickPoint(this, 160, 47,  "Rule book TOC page rules page link.");
		mapLink          = new ClickPoint(this, 160, 64,  "Rule book TOC page map page link.");
		boothLink        = new ClickPoint(this, 160, 81,  "Rule book TOC page booth page link.");
		documentsLink    = new ClickPoint(this, 160, 98,  "Rule book TOC page documents page link.");
		confiscationLink = new ClickPoint(this, 160, 115, "Rule book TOC page confiscation page link.");
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click TOC link
		return ruleBook.tocLink.clickThrough();                 
	}
}
