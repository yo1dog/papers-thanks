package net.awesomebox.papersthanks.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ImageDetector;
import net.awesomebox.papersthanks.ui.Font.FontChar;

public class TextReader
{
	public static final int DEFAULT_SQR_DIST   = 6;
	public static final int DEFAULT_MIN_LENGTH = 2;
	public static final int DEFAULT_TOLLERANCE = ImageDetector.DEFAULT_TOLERANCE;
	
	public static final String ALPHA_UPPER_CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA_LOWER_CHAR_SET = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMERIC_CHAR_SET     = "0123456789";
	
	public static final String ALPHA_CHAR_SET         = ALPHA_UPPER_CHAR_SET + ALPHA_LOWER_CHAR_SET;
	public static final String ALPHA_NUMERIC_CHAR_SET = ALPHA_CHAR_SET + NUMERIC_CHAR_SET;
	
	
	private final Font    font;
	private final Color[] foregroundColors;
	private final Color[] backgroundColors;
	
	private int     lineSpacing;
	private boolean isRightAligned;
	private String  firstCharSet;
	private int     tollerance;
	
	public TextReader(Font font, Color foregroundColor, Color backgroundColor)
	{
		this(font, new Color[] {foregroundColor}, new Color[] {backgroundColor});
	}
	public TextReader(Font font, Color[] foregroundColors, Color[] backgroundColors)
	{
		this(font, foregroundColors, backgroundColors, -1, false, null);
	}
	public TextReader(Font font, Color foregroundColor, Color backgroundColor, int lineSpacing, boolean isRightAligned, String firstCharSet)
	{
		this(font, new Color[] {foregroundColor}, new Color[] {backgroundColor}, lineSpacing, isRightAligned, firstCharSet);
	}
	public TextReader(Font font, Color[] foregroundColors, Color[] backgroundColors, int lineSpacing, boolean isRightAligned, String firstCharSet)
	{
		this(font, foregroundColors, backgroundColors, lineSpacing, isRightAligned, firstCharSet, DEFAULT_TOLLERANCE);
	}
	public TextReader(Font font, Color foregroundColor, Color backgroundColor, int lineSpacing, boolean isRightAligned, String firstCharSet, int tollerance)
	{
		this(font, new Color[] {foregroundColor}, new Color[] {backgroundColor}, lineSpacing, isRightAligned, firstCharSet, tollerance);
	}
	public TextReader(Font font, Color[] foregroundColors, Color[] backgroundColors, int lineSpacing, boolean isRightAligned, String firstCharSet, int tollerance)
	{
		this.font             = font;
		this.foregroundColors = foregroundColors;
		this.backgroundColors = backgroundColors;
		this.lineSpacing      = lineSpacing;
		this.isRightAligned   = isRightAligned;
		this.firstCharSet     = firstCharSet;
		this.tollerance       = tollerance;
	}
	
	public TextReader setLineSpacing (int     lineSpacing   ) {this.lineSpacing    = lineSpacing;    return this;}
	public TextReader setRightAligned(boolean isRightAligned) {this.isRightAligned = isRightAligned; return this;}
	public TextReader setFirstCharSet(String  firstCharSet  ) {this.firstCharSet   = firstCharSet;   return this;}
	public TextReader setTollerance  (int  tollerance       ) {this.tollerance     = tollerance;     return this;}
	
	
	public String readText(BufferedImage image, Point origin, int scale)
	{
		if (lineSpacing > -1)
			return readMultiLineText(image, origin, scale);
		else
			return readSingleLineText(image, origin, scale);
	}
	
	
	public String readTextNear(BufferedImage image, Point origin, int scale)
	{
		return readTextNear(image, origin, scale, DEFAULT_MIN_LENGTH, DEFAULT_SQR_DIST);
	}
	public String readTextNear(BufferedImage image, Point origin, int scale, int minLength)
	{
		return readTextNear(image, origin, scale, minLength, DEFAULT_SQR_DIST);
	}
	public String readTextNear(BufferedImage image, Point origin, int scale, int minLength, int sqrDist)
	{
		return readTextNear(image, origin, scale, minLength, sqrDist, sqrDist);
	}
	public String readTextNear(BufferedImage image, Point origin, int scale, int minLength, int width, int height)
	{
		// try the origin first
		String str = readText(image, origin, scale);
		if (str.length() >= minLength)
			return str;
		
		for (int yOffset = 0; yOffset < height; ++yOffset)
		{
			int startXOffset;
			int endingXOffset;
			int dXOffset;
			
			// travel right to left if we are right aligned
			if (isRightAligned)
			{
				startXOffset  = width - 1;
				endingXOffset = -1;
				dXOffset      = -1;
			}
			else
			{
				startXOffset  = 0;
				endingXOffset = width;
				dXOffset      = 1;
			}
			
			for (int xOffset = startXOffset; xOffset != endingXOffset; xOffset += dXOffset)
			{
				Point newOrigin = new Point(origin.x + xOffset - (width / 2), origin.y + yOffset - (height / 2));
				
				str = readText(image, newOrigin, scale);
				if (str.length() >= minLength)
				{
					origin.x = newOrigin.x;
					origin.y = newOrigin.y;
					
					return str;
				}
			}
		}
		
		return "";
	}
	
	
	private String readSingleLineText(BufferedImage image, Point origin, int scale)
	{
		return readSingleLineText(image, origin, scale, false);
	}
	private String readSingleLineText(BufferedImage image, Point origin, int scale, boolean ignoreFirstCharSet)
	{
		String str = "";
		
		int x = origin.x;
		int y = origin.y;
		
		if (x < 0 || y < 0)
			return str;
		
		boolean tryingSpace = false;
		FontChar lastChar = null;
		
		while (true)
		{
			FontChar foundFontChar = null;
			
			for (int i = 0; i <= font.chars.length - 1; ++i)
			{
				FontChar fontChar = font.chars[i];
				if (fontChar == null)
					continue;
				
				if (lastChar == null && firstCharSet != null && !ignoreFirstCharSet)
				{
					if (firstCharSet.indexOf(fontChar.value) == -1)
						continue;
				}
				
				int charX;
				int charY = y;
				
				if (isRightAligned)
				{
					charX = x - fontChar.xAdvance;
					
					if (tryingSpace)
						charX -= fontChar.xSpaceAdvance;
				}
				else
				{
					charX = x;
					
					if (tryingSpace)
						charX += lastChar.xSpaceAdvance;
				}
				
				boolean matched = fontChar.imageDetector.checkImage(
					image,
					foregroundColors, backgroundColors,
					new Point(charX - 1, charY + font.ascenderLine), // always subtract 1 because we always add 1 padding to the left for left-aligned reading
					scale,
					tollerance
				);
				
				if (matched)
				{
					foundFontChar = fontChar;
					break;
				}
			}
			
			if (foundFontChar != null)
			{
				if (tryingSpace)
				{
					tryingSpace = false;
					
					if (isRightAligned)
					{
						x -= foundFontChar.xSpaceAdvance;
						str = ' ' + str;
					}
					else
					{
						x += lastChar.xSpaceAdvance;
						str += ' ';
					}
				}
				
				if (isRightAligned)
				{
					x -= foundFontChar.xAdvance;
					str = foundFontChar.value + str;
				}
				else
				{
					x += foundFontChar.xAdvance;
					str += foundFontChar.value;
				}
				
				lastChar = foundFontChar;
				continue;
			}
			
			if (!tryingSpace && lastChar != null && font.hasSpace)
			{
				tryingSpace = true;
				continue;
			}
			
			break;
		}
		
		return str;
	}
	
	private String readMultiLineText(BufferedImage image, Point origin, int scale)
	{
		String str = readSingleLineText(image, origin, scale);
		
		if (str.length() == 0)
			return str;
		
		Point nextLineOrigin = new Point(origin.x, origin.y);
		while(true)
		{
			nextLineOrigin.y += font.lineHeight + lineSpacing;
			String nextLine = readSingleLineText(image, nextLineOrigin, scale, true);
			
			if (nextLine.length() == 0)
				break;
			
			str += ' ' + nextLine;
		}
		
		return str;
	}
}