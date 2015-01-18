package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;
import net.awesomebox.papersthanks.ui.Interface;

public abstract class RuleBookPage extends Interface
{
	protected final RuleBook ruleBook;
	
	protected RuleBookPage(RuleBook ruleBook)
	{
		this.ruleBook = ruleBook;
	}
	
	
	@Override
	public abstract MouseSequence clickTo();
	
	@Override
	public MouseClickEvent click(int xRelToDocument, int yRelToDocument, String desc, MouseEventListener mouseEventListener)
	{
		return ruleBook.click(xRelToDocument, yRelToDocument, desc, mouseEventListener);
	}
}