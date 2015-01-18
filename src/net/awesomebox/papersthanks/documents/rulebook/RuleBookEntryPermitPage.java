package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookEntryPermitPage extends RuleBookPage
{
	public final InterrogateItem sealsInterrogateItem;
	
	RuleBookEntryPermitPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		sealsInterrogateItem = new InterrogateItem(this, ruleBook, 180, 45, "Rule book entry permit page seals.");
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click the entry permit document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.ENTRY_PERMIT).clickThrough();
	}
}
