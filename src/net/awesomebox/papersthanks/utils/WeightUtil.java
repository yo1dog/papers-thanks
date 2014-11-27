package net.awesomebox.papersthanks.utils;

public class WeightUtil
{
	private static final String WEIGHT_PREFIX = "kg";
	
	public static int parseHeight(String weightStr)
	{
		if (weightStr.length() < 1 + WEIGHT_PREFIX.length())
			return -1;
		
		if (!weightStr.endsWith(WEIGHT_PREFIX))
			return -1;
		
		try
		{
			return Integer.parseInt(weightStr.substring(0, weightStr.length() - WEIGHT_PREFIX.length()));
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
