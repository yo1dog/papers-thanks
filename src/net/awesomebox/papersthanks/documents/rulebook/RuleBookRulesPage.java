package net.awesomebox.papersthanks.documents.rulebook;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import net.awesomebox.papersthanks.DebugOptions;
import net.awesomebox.papersthanks.Rule;
import net.awesomebox.papersthanks.ui.InterrogateItem;
import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseMoveEvent;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;

public class RuleBookRulesPage extends RuleBookPage
{
	private static final TextReader textReader = new TextReader(
		Font.bm_mini_a8_6,
		new Color(87, 72, 72), new Color(228, 230, 189),
		1, false, TextReader.ALPHA_UPPER_CHAR_SET
	);
	
	private static final Point[] ruleTextPoints = new Point[] {
		new Point(22,  24 ),
		new Point(22,  48 ),
		new Point(22,  72 ),
		new Point(22,  96 ),
		new Point(22,  120),
		new Point(143, 24 ),
		new Point(143, 48 ),
		new Point(143, 72 ),
		new Point(143, 96 ),
		new Point(143, 120)
	};
	
	private static final int[][] ruleInterrogateItemPoints = new int[][] {
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
	
	
	private Rule[] rules = DebugOptions.RULES_OVERRIDE;
	private HashMap<Rule, InterrogateItem> ruleInterrogateItems = null;
	
	RuleBookRulesPage(RuleBook ruleBook)
	{
		super(ruleBook);
	}
	
	@Override
	public MouseSequence clickTo()
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
	public InterrogateItem getIterrogateItemForRule(Rule rule)
	{
		if (ruleInterrogateItems == null)
			throw new AssertionError("Attempting to access rules before they have been read.");
		
		return ruleInterrogateItems.get(rule);
	}
	
	
	public void readRules()
	{
		// go to this page
		clickTo()
			// move mouse out of the way for reading
			.add(new MouseMoveEvent(0, 0, "Move mouse to top-left corner for reading rules."))
			.execute();
		
		// read the rules
		// take a screenshot of the page
		BufferedImage screenshot = ruleBook.takeScreenshot();
		
		ArrayList<Rule> rulesList = new ArrayList<Rule>();
		
		for (int i = 0; i < ruleTextPoints.length; ++i)
		{
			String ruleDescriptionStr = textReader.readTextNear(screenshot, ruleTextPoints[i], WorkView.SCALE);
			System.out.println("Read rule: " + ruleDescriptionStr);
			
			if (ruleDescriptionStr.length() == 0)
				continue;
			
			Rule rule = Rule.fromDescription(ruleDescriptionStr);
			if (rule == null)
				throw new AssertionError("Unkown rule description \"" + ruleDescriptionStr + "\".");
			
			rulesList.add(rule);
		}
		
		rules = rulesList.toArray(new Rule[rulesList.size()]);
		ruleInterrogateItems = new HashMap<Rule, InterrogateItem>(rules.length);
		
		for (int i = 0; i < rules.length; ++i)
			ruleInterrogateItems.put(rules[i], new InterrogateItem(this, ruleBook, ruleInterrogateItemPoints[i][0], ruleInterrogateItemPoints[i][1], "Rule book rules page " + rules[i] + " rule page link."));
	}
}
