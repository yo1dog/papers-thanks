package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class Desk implements Interface
{
	private RuleBook ruleBook;
	
	Desk() {}
	
	public RuleBook getRuleBook()
	{
		return ruleBook;
	}
	public void setRuleBook(RuleBook ruleBook)
	{
		this.ruleBook = ruleBook;
	}
	
	
	@Override
	public ClickSequence clickTo(int xRelToDesk, int yRelToDesk, String desc)
	{
		return new ClickSequence(click(xRelToDesk, yRelToDesk, desc));
	}
	
	@Override
	public ClickEvent click(int xRelToDesk, int yRelToDesk, String desc)
	{
		int x = UI.deskArea.x + xRelToDesk;
		int y = UI.deskArea.y + yRelToDesk;
		return new ClickEvent(x, y, desc);
	}
}
