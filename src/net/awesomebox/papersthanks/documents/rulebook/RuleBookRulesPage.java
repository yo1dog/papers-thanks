package net.awesomebox.papersthanks.documents.rulebook;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import net.awesomebox.papersthanks.Rule;
import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.TextReader;

public class RuleBookRulesPage extends RuleBookPage
{
	private final Font textFont = Font.bm_mini_a8_6;
	private final Color textForegroundColor = new Color(87,  72,  72 );
	private final Color textBackgroundColor = new Color(228, 230, 189);
	
	private final Point[] ruleTextPoints = new Point[] {
		new Point(22,  31 ),
		new Point(22,  55 ),
		new Point(22,  79 ),
		new Point(22,  103),
		new Point(22,  127),
		new Point(143, 31 ),
		new Point(143, 55 ),
		new Point(143, 79 ),
		new Point(143, 103),
		new Point(143, 127)
	};
	
	private final int[][] ruleLinkPoints = new int[][] {
		{40,  35 },
		{40,  59 },
		{40,  83 },
		{40,  107},
		{40,  131},
		{159, 35 },
		{159, 59 },
		{159, 83 },
		{159, 107},
		{159, 131}
	};
	
	private Rule[] rules = null;
	private HashMap<Rule, ClickPoint> ruleLinks = null;
	
	RuleBookRulesPage(RuleBook ruleBook)
	{
		super(ruleBook);
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click the rules link on the TOC page
		return ruleBook.tocPage.rulesLink.clickThrough();
	}
	
	
	public Rule[] getRules()
	{
		if (rules == null)
			throw new AssertionError("Attempting to access rules before they have been read.");
		
		return rules;
	}
	public ClickPoint getLinkForRule(Rule rule)
	{
		if (ruleLinks == null)
			throw new AssertionError("Attempting to access rules before they have been read.");
		
		return ruleLinks.get(rule);
	}
	
	
	public void readRules()
	{
		// go to this page
		clickTo().execute();
		
		// read the rules
		// take a screenshot of the page
		BufferedImage snapshot = ruleBook.takeSnapshot();
		
		ArrayList<Rule> rulesList = new ArrayList<Rule>();
		
		for (int i = 0; i < ruleTextPoints.length; ++i)
		{
			String ruleDescriptionStr = TextReader.readTextNear(textFont, snapshot, textForegroundColor, textBackgroundColor, ruleTextPoints[i]);
			
			System.out.println("READ: " + ruleDescriptionStr);
			
			if (ruleDescriptionStr.length() == 0)
				continue;
			
			Rule rule = Rule.fromDescription(ruleDescriptionStr);
			if (rule == null)
				throw new AssertionError("Unkown rule description \"" + ruleDescriptionStr + "\".");
			
			rulesList.add(rule);
		}
		
		rules = rulesList.toArray(new Rule[rulesList.size()]);
		ruleLinks = new HashMap<Rule, ClickPoint>(rules.length);
		
		for (int i = 0; i < rules.length; ++i)
			ruleLinks.put(rules[i], new ClickPoint(this, ruleLinkPoints[i][0], ruleLinkPoints[i][1], "Rule book rules page " + rules[i] + " rule page link."));
	}
}
