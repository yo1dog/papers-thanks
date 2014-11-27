package net.awesomebox.papersthanks;

public enum Sex {
	MALE,
	FEMALE;
	
	
	public static Sex fromString(String sexStr)
	{
		sexStr = sexStr.toLowerCase();
		
		if (sexStr.equals("m") || sexStr.equals("male"))
			return Sex.MALE;
		if (sexStr.equals("f") || sexStr.equals("female"))
			return Sex.FEMALE;
		
		return null;
	}
}