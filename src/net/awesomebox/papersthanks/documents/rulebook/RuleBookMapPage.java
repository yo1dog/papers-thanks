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
		
		antegriaLink         = new ClickPoint(this, 45,  57,  "Antegria link.");
		arstotzkaLink        = new ClickPoint(this, 192, 90,  "Arstotzka link.");
		imporLink            = new ClickPoint(this, 88,  136, "Impor link.");
		kolechiaLink         = new ClickPoint(this, 158, 25,  "Kolechia link.");
		obristanLink         = new ClickPoint(this, 64,  13,  "Obristan link.");
		republiaLink         = new ClickPoint(this, 65,  99,  "Republia link.");
		unitedFederationLink = new ClickPoint(this, 23,  128, "United Federation link.");
	}
	
	@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
		ClickSequence clickSequence = ruleBook.tocPage.mapLink.clickTo();         // click map link on the TOC page
		clickSequence.addClickEvent(click(xRelToDocument, yRelToDocument, desc)); // click given point in the TOC page
		
		return clickSequence;
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
