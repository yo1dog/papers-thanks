package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Rule;
import net.awesomebox.papersthanks.documents.Document;

public class RuleBook extends Document
{
	public final Rule[] rules;
	
	public final ClickPoint tocLink;
	
	// from assets/Data/Papers.xml:<!-- Rules -->
	public final RuleBookTOCPage            tocPage;
	public final RuleBookRulesPage          rulesPage;
	public final RuleBookMapPage            mapPage;
	public final RuleBookNationPage         antegriaPage;
	public final RuleBookNationPage         arstotzkaPage;
	public final RuleBookNationPage         imporPage;
	public final RuleBookNationPage         kolechiaPage;
	public final RuleBookNationPage         obristanPage;
	public final RuleBookNationPage         republiaPage;
	public final RuleBookNationPage         unitedFederationPage;
	//public final RuleBookBoothPage        boothPage;
	public final RuleBookDocumentsTOCPage   documentsTOCPage;
	/*public final RuleBookIDCardPage         idCardPage;
	public final RuleBookEntryPermitPage    entryPermitPage;
	public final RuleBookIDSupplementPage   idSupplementPage;
	public final RuleBookAccessPermitPage   accessPermitPage;
	public final RuleBookWorkPassPage       workPassPage;
	public final RuleBookDiplomaticAuthPage diplomaticAuthPage;
	public final RuleBookAsylumPage         asylumPage;
	public final RuleBookVaccineCertPage    vaccineCertPage;
	public final RuleBookConfiscationPage   confiscationPage;
	//public final RuleBookBackFlapPage     backFlapPage;*/
	
	public RuleBook(Rule[] rules, RuleBookDocumetLinkType[] documentLinkTypes)
	{
		super(246, 160);
		
		this.rules = rules;
		
		tocLink = new ClickPoint(this, 16, 8, "TOC Link");
		
		tocPage              = new RuleBookTOCPage           (this);
		rulesPage            = new RuleBookRulesPage         (this, rules);
		mapPage              = new RuleBookMapPage           (this);
		antegriaPage         = new RuleBookNationPage        (this, Nation.Antegria);
		arstotzkaPage        = new RuleBookNationPage        (this, Nation.Arstotzka);
		imporPage            = new RuleBookNationPage        (this, Nation.Impor);
		kolechiaPage         = new RuleBookNationPage        (this, Nation.Kolechia);
		obristanPage         = new RuleBookNationPage        (this, Nation.Obristan);
		republiaPage         = new RuleBookNationPage        (this, Nation.Republia);
		unitedFederationPage = new RuleBookNationPage        (this, Nation.UnitedFederation);
		//boothPage          = new RuleBookBoothPage         (this);
		documentsTOCPage     = new RuleBookDocumentsTOCPage  (this, documentLinkTypes);
		/*idCardPage           = new RuleBookIDCardPage        (this);
		entryPermitPage      = new RuleBookEntryPermitPage   (this);
		idSupplementPage     = new RuleBookIDSupplementPage  (this);
		accessPermitPage     = new RuleBookAccessPermitPage  (this);
		workPassPage         = new RuleBookWorkPassPage      (this);
		diplomaticAuthPage   = new RuleBookDiplomaticAuthPage(this);
		asylumPage           = new RuleBookAsylumPage        (this);
		vaccineCertPage      = new RuleBookVaccineCertPage   (this);
		confiscationPage     = new RuleBookConfiscationPage  (this);
		//backFlapPage       = new RuleBookBackFlapPage      (this);*/
	}
	
	public RuleBookNationPage getPageForNation(Nation nation)
	{
		if (nation == Nation.Antegria)         return antegriaPage;
		if (nation == Nation.Arstotzka)        return arstotzkaPage;
		if (nation == Nation.Impor)            return imporPage;
		if (nation == Nation.Kolechia)         return kolechiaPage;
		if (nation == Nation.Obristan)         return obristanPage;
		if (nation == Nation.Republia)         return republiaPage;
		if (nation == Nation.UnitedFederation) return unitedFederationPage;
		
		return null;
	}
}
