package net.awesomebox.papersthanks.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ui.Font.FontChar;

public class TextReader
{
	public static String readTextNear(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin)
	{
		return readTextNear(font, image, foregroundColor, backgroundColor, origin, 20, true);
	}
	public static String readTextNear(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin, boolean firstAlphaCapitalsOnly)
	{
		return readTextNear(font, image, foregroundColor, backgroundColor, origin, 20, firstAlphaCapitalsOnly);
	}
	public static String readTextNear(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin, int sqrDist)
	{
		return readTextNear(font, image, foregroundColor, backgroundColor, origin, sqrDist, true);
	}
	public static String readTextNear(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin, int sqrDist, boolean firstAlphaCapitalsOnly)
	{
		// try the origin first
		String str = readMultiLineText(font, image, foregroundColor, backgroundColor, origin, firstAlphaCapitalsOnly);
		if (str.length() > 1)
			return str;
		
		for (int yOffset = 0; yOffset < sqrDist; ++yOffset)
		{
			for (int xOffset = 0; xOffset < sqrDist; ++xOffset)
			{
				Point newOrigin = new Point(origin.x + xOffset - (sqrDist / 2), origin.y + yOffset - (sqrDist / 2));
				
				str = readMultiLineText(font, image, foregroundColor, backgroundColor, newOrigin, firstAlphaCapitalsOnly);
				if (str.length() > 1)
				{
					origin.x = newOrigin.x;
					origin.y = newOrigin.y;
					
					return str;
				}
			}
		}
		
		return "";
	}
	
	public static String readMultiLineText(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin)
	{
		return readSingleLineText(font, image, foregroundColor, backgroundColor, origin, true);
	}
	public static String readMultiLineText(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin, boolean firstAlphaCapitalsOnly)
	{
		String str = readSingleLineText(font, image, foregroundColor, backgroundColor, origin, firstAlphaCapitalsOnly);
		
		if (str.length() == 0)
			return str;
		
		// find the y offset between lines
		int yOffsetBetweenLines = -1;
		
		String nextLine = "";
		int yOffset;
		
		for (yOffset = font.lineHeight; yOffset < font.lineHeight * 2; ++yOffset)
		{
			nextLine = readSingleLineText(font, image, foregroundColor, backgroundColor, new Point(origin.x, origin.y + yOffset), false);
			
			if (nextLine.length() > 0)
				break;
		}
		
		while(nextLine.length() > 0)
		{
			str += ' ' + nextLine;
			origin = new Point(origin.x, origin.y + yOffset);
			
			nextLine = readSingleLineText(font, image, foregroundColor, backgroundColor, new Point(origin.x, origin.y + yOffsetBetweenLines), false);
		}
		
		return str;
	}
	
	public static String readSingleLineText(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin)
	{
		return readSingleLineText(font, image, foregroundColor, backgroundColor, origin, true);
	}
	public static String readSingleLineText(Font font, BufferedImage image, Color foregroundColor, Color backgroundColor, Point origin, boolean firstAlphaCapitalsOnly)
	{
		String str = "";
		
		int x = origin.x;
		int y = origin.y;
		
		if (x < 0 || y < 0)
			return str;
		
		boolean triedSpace = false;
		FontChar lastChar = null;
		
		while (true)
		{
			FontChar foundFontChar = null;
			
			int firstIndex = 0;
			int lastIndex = font.chars.length - 1;
			
			if (lastChar == null && firstAlphaCapitalsOnly)
			{
				firstIndex = font.getCharIndex('A');
				lastIndex  = font.getCharIndex('Z');
			}
			
			for (int i = firstIndex; i <= lastIndex; ++i)
			{
				FontChar fontChar = font.chars[i];
				if (fontChar == null)
					continue;
				
				if (i == font.getCharIndex('t'))
				{
					int z = 2;
				}
				
				if (fontChar.imageDetector.checkImage(
					image,
					x, y + font.ascenderLine,
					2.0f,
					new Color[] {foregroundColor}, new Color[] {backgroundColor})
				)
				{
					foundFontChar = fontChar;
					break;
				}
			}
			
			if (foundFontChar != null)
			{
				if (triedSpace)
				{
					str += ' ';
					triedSpace = false;
				}
				
				str += foundFontChar.value;
				x += foundFontChar.xAdvance;
				
				lastChar = foundFontChar;
				continue;
			}
			
			if (!triedSpace && lastChar != null)
			{
				triedSpace = true;
				
				int lastCharTrailingSpace = lastChar.xAdvance - (lastChar.xOffset + lastChar.width);
				x += font.spaceXAdvance - lastCharTrailingSpace;
				
				continue;
			}
			
			break;
		}
		
		return str;
	}
}