package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.ui.Interface;

public abstract class RuleBookPage extends Interface
{
	protected final RuleBook ruleBook;
	
	protected RuleBookPage(RuleBook ruleBook)
	{
		this.ruleBook = ruleBook;
	}
	
	
	@Override
	public ClickSequence clickTo()
	{
		return ruleBook.clickTo();
	}
	
	@Override
	public ClickEvent click(int xRelToDocument, int yRelToDocument, String desc)
	{
		return ruleBook.click(xRelToDocument, yRelToDocument, desc);
	}
}