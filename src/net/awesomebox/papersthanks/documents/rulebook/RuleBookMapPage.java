package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.ClickSequence;

public class RuleBookMapPage extends RuleBookPage
{
	public final ClickPoint antegriaLink;
	public final ClickPoint arstotzkaLink;
	public final ClickPoint imporLink;
	public final ClickPoint kolechiaLink;
	public final ClickPoint obristanLink;
	public final ClickPoint republiaLink;
	public final ClickPoint unitedFederationLink;
	
	RuleBookMapPage(RuleBook ruleBook)
	{
		super(ruleBook);
		
		antegriaLink         = new ClickPoint(this, 45,  57,  "Rule book map page Antegria page link.");
		arstotzkaLink        = new ClickPoint(this, 192, 90,  "Rule book map page Arstotzka page link.");
		imporLink            = new ClickPoint(this, 88,  136, "Rule book map page Impor page link.");
		kolechiaLink         = new ClickPoint(this, 158, 25,  "Rule book map page Kolechia page link.");
		obristanLink         = new ClickPoint(this, 64,  13,  "Rule book map page Obristan page link.");
		republiaLink         = new ClickPoint(this, 65,  99,  "Rule book map page Republia page link.");
		unitedFederationLink = new ClickPoint(this, 23,  128, "Rule book map page United Federation page link.");
	}
	
	@Override
	public ClickSequence clickTo()
	{
		// click map link on the TOC page
		return ruleBook.tocPage.mapLink.clickThrough();
	}
	
	public ClickPoint getLinkForNation(Nation nation)
	{
		if (nation == Nation.Antegria)         return antegriaLink;
		if (nation == Nation.Arstotzka)        return arstotzkaLink;
		if (nation == Nation.Impor)            return imporLink;
		if (nation == Nation.Kolechia)         return kolechiaLink;
		if (nation == Nation.Obristan)         return obristanLink;
		if (nation == Nation.Republia)         return republiaLink;
		if (nation == Nation.UnitedFederation) return unitedFederationLink;
		
		return null;
	}
}
