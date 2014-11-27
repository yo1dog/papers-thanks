package net.awesomebox.papersthanks;

public class Name
{
	public final String first;
	public final String last;
	
	public Name(String first, String last)
	{
		this.first = first;
		this.last  = last;
	}
	
	
	public boolean equals(Name otherName)
	{
		return first.equalsIgnoreCase(otherName.first) && last.equalsIgnoreCase(otherName.last);
	}
	
	@Override
	public String toString()
	{
		return first + " " + last;
	}
	
	
	public static Name fromFullName(String fullName)
	{
		// check if "lastname, firstname"
		int index = fullName.indexOf(", ");
		if (index > -1)
		{
			// make sure we are not at the begging or end of the string
			if (index == 0 || index > fullName.length() - 3)
				return null;
			
			return new Name(fullName.substring(index + 2), fullName.substring(0, index));
		}
		
		// check if "firstname lastname"
		index = fullName.indexOf(' ');
		if (index > -1)
		{
			// make sure we are not at the begging or end of the string
			if (index == 0 || index > fullName.length() - 2)
				return null;
			
			return new Name(fullName.substring(0, index), fullName.substring(index + 1));
		}
		
		return null;
	}
}
