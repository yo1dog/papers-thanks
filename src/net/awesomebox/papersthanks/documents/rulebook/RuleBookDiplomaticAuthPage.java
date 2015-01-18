package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookDiplomaticAuthPage extends RuleBookPage
{
	public final InterrogateItem mustBeForArstotzkanRuleInterrogateItem;
	
	RuleBookDiplomaticAuthPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		mustBeForArstotzkanRuleInterrogateItem = new InterrogateItem(this, ruleBook, 180, 80,  "Rule book diplomatic auth page must-be-for-Arstotzkan rule.");
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click the diplomatic auth document link on the documents TOC page in the rule book
		return ruleBook.documentsTOCPage.getLinkForDocumentLinkType(RuleBookDocumetLinkType.DIPLOMATIC_AUTH).clickThrough();
	}
}
