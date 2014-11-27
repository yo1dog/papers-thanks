package net.awesomebox.papersthanks.documents.rulebook;

import java.util.HashMap;

import net.awesomebox.papersthanks.Rule;
import net.awesomebox.papersthanks.ui.ClickSequence;

public class RuleBookRulesPage extends RuleBookPage
{
	private final HashMap<Rule, ClickPoint> ruleLinks = new HashMap<Rule, ClickPoint>();
	
	RuleBookRulesPage(RuleBook ruleBook, Rule[] rules)
	{
		super(ruleBook);
		
		int[][] ruleLinkPoints = new int[][] {
			{40,  35},
			{40,  59},
			{40,  83},
			{40,  107},
			{40,  131},
			{159, 35},
			{159, 59},
			{159, 83},
			{159, 107},
			{159, 131}
		};
		
		for (int i = 0; i < rules.length; ++i)
			ruleLinks.put(rules[i], new ClickPoint(this, ruleLinkPoints[i][0], ruleLinkPoints[i][1], rules[i] + " rule link."));
	}
	
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
		ClickSequence clickSequence = ruleBook.tocLink.clickTo();                 // click TOC link
		clickSequence.addClickEvent(click(xRelToDocument, yRelToDocument, desc)); // click given point in the TOC page
		
		return clickSequence;
	}
	
	public ClickPoint getLinkForRule(Rule rule)
	{
		return ruleLinks.get(rule);
	}
}
