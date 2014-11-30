package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookVaccineCertPage extends RuleBookPage
{
	public final InterrogateItem requiredRuleInterrogateItem;
	
	RuleBookVaccineCertPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		requiredRuleInterrogateItem = new InterrogateItem(this, 180, 45,  "Rule book vaccine cert page required rule.");
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click the vaccine cert document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.VACCINE_CERT).clickThrough();
	}
}
