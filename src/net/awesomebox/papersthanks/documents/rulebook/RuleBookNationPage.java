package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.ClickSequence;
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
		
		issuingCitiesIterrogateItem = new InterrogateItem(this, 150,  100, "Rule book " + nation.name + " page issuing cities.");
		
		if (nation == Nation.Arstotzka)
		{
			diplomaticSealsIterrogateItem = null;
			districtsIterrogateItem       = new InterrogateItem(this, 63,  100, "Rule book " + nation.name + " page districts.");
		}
		else
		{
			diplomaticSealsIterrogateItem = new InterrogateItem(this, 63,  100, "Rule book " + nation.name + " page diplomatic seals.");
			districtsIterrogateItem       = null;
		}
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click link for this nation on the map page
		return ruleBook.mapPage.getLinkForNation(nation).clickThrough();
	}
}
