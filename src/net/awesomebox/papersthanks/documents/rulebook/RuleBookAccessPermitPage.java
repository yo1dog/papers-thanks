package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookAccessPermitPage extends RuleBookPage
{
	public final InterrogateItem sealsInterrogateItem;
	
	RuleBookAccessPermitPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		sealsInterrogateItem = new InterrogateItem(this, ruleBook, 180, 45,  "Rule book access permit page seals.");
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click the access permit document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.ACCESS_PERMIT).clickThrough();
	}
}
