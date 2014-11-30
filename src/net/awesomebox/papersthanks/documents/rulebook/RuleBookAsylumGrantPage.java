package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookAsylumGrantPage extends RuleBookPage
{
	public final InterrogateItem sealsInterrogateItem;
	
	RuleBookAsylumGrantPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		sealsInterrogateItem = new InterrogateItem(this, 180, 45,  "Rule book asylum grant page seals.");
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click the asylum grant document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.ASYLUM_GRANT).clickThrough();
	}
}
