package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookWorkPassPage extends RuleBookPage
{
	public final InterrogateItem sealsInterrogateItem;
	
	RuleBookWorkPassPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		sealsInterrogateItem = new InterrogateItem(this, 180, 45,  "Rule book work pass page seals.");
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click the work pass document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.WORK_PASS).clickThrough();
	}
}
