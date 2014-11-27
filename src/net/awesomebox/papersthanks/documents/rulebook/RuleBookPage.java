package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.ui.Interface;

public abstract class RuleBookPage implements Interface
{
	protected final RuleBook ruleBook;
	
	protected RuleBookPage(RuleBook ruleBook)
	{
		this.ruleBook = ruleBook;
	}
	
	
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
		return ruleBook.clickTo(xRelToDocument, yRelToDocument, desc);
	}
	
	@Override
	public ClickEvent click(int xRelToDocument, int yRelToDocument, String desc)
	{
		return ruleBook.click(xRelToDocument, yRelToDocument, desc);
	}
}