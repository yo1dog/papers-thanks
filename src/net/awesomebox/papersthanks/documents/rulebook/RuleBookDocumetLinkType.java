package net.awesomebox.papersthanks.documents.rulebook;

public enum RuleBookDocumetLinkType
{
	// from assets/data/Papers.xml:<page id="docs"
	ARSTOTZKAN_ID_CARD,
	ENTRY_PERMIT,
	ID_SUPPLIMENT,
	ACCESS_PERMIT,
	WORK_PASS,
	DIPLOMATIC_AUTH,
	ASYLUM_GRANT,
	VACCINE_CERT;
	
	
	public static RuleBookDocumetLinkType fromDescription(String descriptionStr)
	{
		descriptionStr = descriptionStr.toLowerCase();
		
		switch (descriptionStr)
		{
			// from assets/data/Papers.xml:<page id="docs"
			case "id card (native)": return ARSTOTZKAN_ID_CARD;
			case "entry permit"    : return ENTRY_PERMIT;
			case "id supplement"   : return ID_SUPPLIMENT;
			case "access permit"   : return ACCESS_PERMIT;			
			case "work pass"       : return WORK_PASS;
			case "diplomatic auth.": return DIPLOMATIC_AUTH;
			case "grant of asylum" : return ASYLUM_GRANT;
			case "vaccine cert."   : return VACCINE_CERT;
			
			default: return null;
		}
	}
}