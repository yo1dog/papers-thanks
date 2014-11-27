package net.awesomebox.papersthanks;

// form assets/data/Facts.xml:<purposes>
public enum Purpose {
	NATIVE, // never appears?
	VISIT,
	TRANSIT,
	WORK,
	IMMIGRATE,
	DIPLOMAT, // never appears?
	ASYLUM;
	
	
	public static Purpose fromString(String purposeStr)
	{
		purposeStr = purposeStr.toLowerCase();
		
		// from observation
		switch (purposeStr)
		{
			case "native"   : return NATIVE;
			case "visit"    : return VISIT;
			case "transit"  : return TRANSIT;
			case "work"     : return WORK;
			case "immigrate": return IMMIGRATE;
			case "diplomat" : return DIPLOMAT;
			case "asylum"   : return ASYLUM;
			
			default: return null;
		}
	}
	
	public static Purpose fromSpeach(String speachStr)
	{
		speachStr = speachStr.toLowerCase();
		
		switch (speachStr)
		{
			// from assets/data/Speeches.txt:state-purpose	Speech/Purpose
			// also from assets/data/Travelers.txt: search for "[Speech/Purpose]"
			case "visiting."                                             : return VISIT;
			case "just visiting."                                        : return VISIT;
			case "i am visiting relatives."                              : return VISIT;
			case "visiting friends."                                     : return VISIT;
			case "i come for visit."                                     : return VISIT;
			case "only to visit."                                        : return VISIT;
			case "i will visit friends."                                 : return VISIT;
			case "i will visit my son. i have not seen him in six years.": return VISIT;
			case "transit."                                              : return TRANSIT;
			case "i pass through."                                       : return TRANSIT;
			case "transit through the country."                          : return TRANSIT;
			case "i am transiting to elsewhere."                         : return TRANSIT;
			case "transit through arstotzka."                            : return TRANSIT;
			case "passing through."                                      : return TRANSIT;
			case "i am in transit."                                      : return TRANSIT;
			case "i am just passing through."                            : return TRANSIT;
			case "work."                                                 : return WORK;
			case "i come to work."                                       : return WORK;
			case "i have job here."                                      : return WORK;
			case "i plan to work."                                       : return WORK;
			case "immigrating."                                          : return IMMIGRATE;
			case "i am immigrating to arstotzka."                        : return IMMIGRATE;
			case "i will move here."                                     : return IMMIGRATE;
			case "i am coming to live with my husband."                  : return IMMIGRATE;
			case "i am coming to live with my wife."                     : return IMMIGRATE;
			case "i am diplomatic envoy."                                : return DIPLOMAT;
			case "i was called to diplomatic discussions."               : return DIPLOMAT;
			case "my presence was requested."                            : return DIPLOMAT;
			case "i am seeking asylum."                                  : return ASYLUM;
			case "i come for political asylum."                          : return ASYLUM;
			case "asylum."                                               : return ASYLUM;
			
			default: return null;
		}
	}
}