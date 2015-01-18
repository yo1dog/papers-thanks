package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class RuleBookNationPage extends RuleBookPage
{
	public final Nation nation;
	
	public final InterrogateItem issuingCitiesIterrogateItem;
	public final InterrogateItem diplomaticSealsIterrogateItem;
	public final InterrogateItem districtsIterrogateItem;
	
	RuleBookNationPage(RuleBook ruleBook, Nation nation)
	{
		super(ruleBook);
		
		this.nation = nation;
		
		issuingCitiesIterrogateItem = new InterrogateItem(this, ruleBook, 150, 100, "Rule book " + nation.name + " page issuing cities.");
		
		if (nation == Nation.Arstotzka)
		{
			diplomaticSealsIterrogateItem = null;
			districtsIterrogateItem       = new InterrogateItem(this, ruleBook, 63, 100, "Rule book " + nation.name + " page districts.");
		}
		else
		{
			diplomaticSealsIterrogateItem = new InterrogateItem(this, ruleBook, 63, 100, "Rule book " + nation.name + " page diplomatic seals.");
			districtsIterrogateItem       = null;
		}
	}
	
	@Override
	public MouseSequence clickTo()
	{
		// click link for this nation on the map page
		return ruleBook.mapPage.getLinkForNation(nation).clickThrough();
	}
}
