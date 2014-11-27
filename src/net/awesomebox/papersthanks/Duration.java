package net.awesomebox.papersthanks;

public enum Duration {
	FOREVER,
	TWO_DAYS,
	TWO_WEEKS,
	ONE_MONTH,
	TWO_MONTHS,
	THREE_MONTHS,
	SIX_MONTHS,
	ONE_YEAR;
	
	
	public static Duration fromString(String durationStr)
	{
		durationStr = durationStr.toLowerCase();
		
		// from observation
		switch (durationStr)
		{
			case "forever" : return FOREVER;
			case "2 days"  : return TWO_DAYS;
			case "2 weeks" : return TWO_WEEKS;
			case "1 month" : return ONE_MONTH;
			case "2 months": return TWO_MONTHS;
			case "3 months": return THREE_MONTHS;
			case "6 months": return SIX_MONTHS;
			case "1 year"  : return ONE_YEAR;
			
			default: return null;
		}
	}
	
	public static Duration fromSpeach(String speachStr)
	{
		speachStr = speachStr.toLowerCase();
		
		// from assets/data/Speeches.txtstate-duration	Speech/Duration
		// also from assets/data/Travelers.txt: search for "[Speech/Duration]"
		if (speachStr.endsWith("forever."              )) return FOREVER;
		if (speachStr.endsWith("until i die."          )) return FOREVER;
		if (speachStr.endsWith("i don't plan to leave.")) return FOREVER;
		if (speachStr.endsWith("2 days."               )) return TWO_DAYS;
		if (speachStr.endsWith("couple days."          )) return TWO_DAYS;
		if (speachStr.endsWith("just 14 days."         )) return TWO_WEEKS;
		if (speachStr.endsWith("couple weeks."         )) return TWO_WEEKS;
		if (speachStr.endsWith("only two weeks."       )) return TWO_WEEKS;
		if (speachStr.endsWith("30 days."              )) return ONE_MONTH;
		if (speachStr.endsWith("a few weeks."          )) return ONE_MONTH;
		if (speachStr.endsWith("one month."            )) return ONE_MONTH;
		if (speachStr.endsWith("60 days."              )) return TWO_MONTHS;
		if (speachStr.endsWith("8 weeks."              )) return TWO_MONTHS;
		if (speachStr.endsWith("couple months."        )) return TWO_MONTHS;
		if (speachStr.endsWith("two months."           )) return TWO_MONTHS;
		if (speachStr.endsWith("90 days."              )) return THREE_MONTHS;
		if (speachStr.endsWith("12 weeks."             )) return THREE_MONTHS;
		if (speachStr.endsWith("three months."         )) return THREE_MONTHS;
		if (speachStr.endsWith("a few months."         )) return THREE_MONTHS;
		if (speachStr.endsWith("six months."           )) return SIX_MONTHS;
		if (speachStr.endsWith("half a year."          )) return SIX_MONTHS;
		if (speachStr.endsWith("6 months."             )) return SIX_MONTHS;
		if (speachStr.endsWith("one year."             )) return ONE_YEAR;
		if (speachStr.endsWith("full year."            )) return ONE_YEAR;
		if (speachStr.endsWith("a year."               )) return ONE_YEAR;
		
		return null;
	}
}