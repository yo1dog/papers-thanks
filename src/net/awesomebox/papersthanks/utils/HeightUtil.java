package net.awesomebox.papersthanks.utils;

public class HeightUtil
{
	private static final String HEIGHT_PREFIX = "cm";
	
	public static int parseHeight(String heightStr)
	{
		if (heightStr.length() < 1 + HEIGHT_PREFIX.length())
			return -1;
		
		if (!heightStr.endsWith(HEIGHT_PREFIX))
			return -1;
		
		try
		{
			return Integer.parseInt(heightStr.substring(0, heightStr.length() - HEIGHT_PREFIX.length()));
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
