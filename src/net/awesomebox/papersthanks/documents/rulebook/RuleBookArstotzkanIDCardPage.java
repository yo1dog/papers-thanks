package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookArstotzkanIDCardPage extends RuleBookPage
{
	public final InterrogateItem districtsInterrogateItem;
	
	RuleBookArstotzkanIDCardPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		districtsInterrogateItem = new InterrogateItem(this, ruleBook, 180, 80,  "Rule book Arstotzkan ID card page districts.");
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click the Arstotzkan ID card document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.ARSTOTZKAN_ID_CARD).clickThrough();
	}
}
