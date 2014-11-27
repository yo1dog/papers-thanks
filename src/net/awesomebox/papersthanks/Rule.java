package net.awesomebox.papersthanks;

public enum Rule
{
	// from assets/Data/Days.csv:RULES row
	NEED_PASSPORT,
	NEED_CURRENT_DOCS,
	ARSTOTZKA_ONLY,
	NEED_ENTRY_TICKET,
	NEED_ID_CARD,
	NEED_ENTRY_PERMIT,
	NEED_WORK_PERMIT,
	NO_CONTRABAND,
	SEARCH_KOLECHIA,
	NEED_DIPLOMATIC_AUTH,
	NEED_ID_SUPPLEMENT,
	NO_IMPOR,
	NEED_ASYLUM_GRANT,
	NO_UNITED_FED,
	NEED_VACCINE_CERT,
	NEED_ACCESS_PERMIT,
	
	CONFISCATE_ARSTOTZKAN_ATLAN_PASSPORTS,
	CONFISCATE_ARSTOTZKAN_PASSPORTS;
	
	
	
	public static Rule fromDescription(String descriptionStr)
	{
		descriptionStr = descriptionStr.toLowerCase();
		
		switch (descriptionStr)
		{
			// from http://papersplease.wikia.com/wiki/Rulebook
			case "entrant must have a passport"                                : return NEED_PASSPORT;
			case "all documents must be current"                               : return NEED_CURRENT_DOCS;
			case "arstotzkan citizens only"                                    : return ARSTOTZKA_ONLY;
			case "foreigners require an entry ticket"                          : return NEED_ENTRY_TICKET;
			case "citizens must have an id card"                               : return NEED_ID_CARD;
			case "foreigners require an entry permit"                          : return NEED_ENTRY_PERMIT;
			case "workers must have a work pass"                               : return NEED_WORK_PERMIT;
			case "no weapons or contraband"                                    : return NO_CONTRABAND;
			case "all kolechians must be searched"                             : return SEARCH_KOLECHIA;
			case "diplomats require authorization"                             : return NEED_DIPLOMATIC_AUTH;
			case "foreigners require an id supplement"                         : return NEED_ID_SUPPLEMENT;
			case "no entry from impor"                                         : return NO_IMPOR;
			case "asylum seekers must have a grant"                            : return NEED_ASYLUM_GRANT;
			case "no entry from united federation"                             : return NO_UNITED_FED;
			case "entrant must have polio vaccine certificate"                 : return NEED_VACCINE_CERT;
			case "foreigners require an access permit"                         : return NEED_ACCESS_PERMIT;
			case "confiscate arstotzkan passports belonging to altan residents": return CONFISCATE_ARSTOTZKAN_ATLAN_PASSPORTS;
			case "confiscate all arstotzkan passports"                         : return CONFISCATE_ARSTOTZKAN_PASSPORTS;
			
			default: return null;
		}
	}
}