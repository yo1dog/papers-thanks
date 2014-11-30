package net.awesomebox.papersthanks.documents.rulebook;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.documents.Document;
import net.awesomebox.papersthanks.ui.Desk;

public class RuleBook extends Document
{
	public static final int WIDTH  = 245;
	public static final int HEIGHT = 160;
	
	public final ClickPoint tocLink;
	
	// from assets/Data/Papers.xml:<!-- Rules -->
	public final RuleBookTOCPage              tocPage;
	public final RuleBookRulesPage            rulesPage;
	public final RuleBookMapPage              mapPage;
	public final RuleBookNationPage           antegriaPage;
	public final RuleBookNationPage           arstotzkaPage;
	public final RuleBookNationPage           imporPage;
	public final RuleBookNationPage           kolechiaPage;
	public final RuleBookNationPage           obristanPage;
	public final RuleBookNationPage           republiaPage;
	public final RuleBookNationPage           unitedFederationPage;
	//public final RuleBookBoothPage          boothPage;
	public final RuleBookDocumentsTOCPage     documentsTOCPage;
	public final RuleBookArstotzkanIDCardPage arstotzkanIDCardPage;
	public final RuleBookEntryPermitPage      entryPermitPage;
	//public final RuleBookIDSupplementPage   idSupplementPage; // not needed
	public final RuleBookAccessPermitPage     accessPermitPage;
	public final RuleBookWorkPassPage         workPassPage;
	public final RuleBookDiplomaticAuthPage   diplomaticAuthPage;
	public final RuleBookAsylumGrantPage      asylumGrantPage;
	public final RuleBookVaccineCertPage      vaccineCertPage;
	//public final RuleBookConfiscationPage   confiscationPage; // TODO: do confiscation page
	//public final RuleBookBackFlapPage       backFlapPage;
	
	public RuleBook(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		tocLink = new ClickPoint(this, 16, 8, "Rule book TOC link.");
		
		tocPage              = new RuleBookTOCPage             (this);
		rulesPage            = new RuleBookRulesPage           (this);
		mapPage              = new RuleBookMapPage             (this);
		antegriaPage         = new RuleBookNationPage          (this, Nation.Antegria);
		arstotzkaPage        = new RuleBookNationPage          (this, Nation.Arstotzka);
		imporPage            = new RuleBookNationPage          (this, Nation.Impor);
		kolechiaPage         = new RuleBookNationPage          (this, Nation.Kolechia);
		obristanPage         = new RuleBookNationPage          (this, Nation.Obristan);
		republiaPage         = new RuleBookNationPage          (this, Nation.Republia);
		unitedFederationPage = new RuleBookNationPage          (this, Nation.UnitedFederation);
		//boothPage          = new RuleBookBoothPage           (this);
		documentsTOCPage     = new RuleBookDocumentsTOCPage    (this);
		arstotzkanIDCardPage = new RuleBookArstotzkanIDCardPage(this);
		entryPermitPage      = new RuleBookEntryPermitPage     (this);
		//idSupplementPage   = new RuleBookIDSupplementPage    (this);
		accessPermitPage     = new RuleBookAccessPermitPage    (this);
		workPassPage         = new RuleBookWorkPassPage        (this);
		diplomaticAuthPage   = new RuleBookDiplomaticAuthPage  (this);
		asylumGrantPage      = new RuleBookAsylumGrantPage     (this);
		vaccineCertPage      = new RuleBookVaccineCertPage     (this);
		//confiscationPage   = new RuleBookConfiscationPage    (this);
		//backFlapPage       = new RuleBookBackFlapPage        (this);
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
	
	
	public void readPages()
	{
		rulesPage.readRules();
		documentsTOCPage.readDocumentLinks();
	}
}
