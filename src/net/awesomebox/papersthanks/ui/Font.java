package net.awesomebox.papersthanks.ui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.awesomebox.papersthanks.ImageDetector;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class Font
{
	private static final int ARRAY_VALUE_OFFSET = -32;
	private static final int CHAR_POSITIVE_ARGB = new Color(255, 255, 255).getRGB();
	private static final int CHAR_NEGATIVE_ARGB = new Color(0, 0, 0).getRGB();
	
	private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	
	
	public final int lineHeight;
	public final int ascenderLine;
	public final int descenderLine;
	public final int spaceXAdvance;
	public final FontChar[] chars;
	
	private Font(int lineHeight, int ascenderLine, int descenderLine, int spaceXAdvance, FontChar[] chars)
	{
		this.lineHeight    = lineHeight;
		this.ascenderLine  = ascenderLine;
		this.descenderLine = descenderLine;
		this.spaceXAdvance = spaceXAdvance;
		this.chars         = chars;
	}
	
	public int getCharIndex(char c)
	{
		return c + ARRAY_VALUE_OFFSET;
	}
	
	
	
	public static final Font bm_mini_a8_6 = loadFont("bm_mini_a8_6");
	
	
	
	private static Font loadFont(String name)
	{
		org.w3c.dom.Document xmlDoc;
		try
		{
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(new File(ImageUtil.FONTS_DIR + name + ".fnt"));
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			throw new AssertionError("Error reading \"" + name + "\" font XML.", e);
		}
		
		BufferedImage charImageMap = ImageUtil.readImage(ImageUtil.FONTS_DIR + name + ".bmp");
		
		int lineHeight = Integer.parseInt(xmlDoc.getElementsByTagName("common").item(0).getAttributes().getNamedItem("lineHeight").getNodeValue());
		int spaceXAdvance = -1;
		int ascenderLine  = lineHeight;
		int descenderLine = 0;
		
		
		NodeList charNodes = xmlDoc.getElementsByTagName("char");
		
		FNTChar[] fntChars = new FNTChar[charNodes.getLength()];
		for (int i = 0; i < fntChars.length; ++i)
		{
			FNTChar fntChar = new FNTChar(charNodes.item(i).getAttributes());
			
			int ascender = fntChar.yOffset;
			if (ascender < ascenderLine)
				ascenderLine = ascender;
			
			int descender = fntChar.yOffset + fntChar.height;
			if (descender > descenderLine)
				descenderLine = descender;
			
			fntChars[i] = fntChar;
		}
		
		
		FontChar[] chars = new FontChar[95];
		for (int i = 0; i < fntChars.length; ++i)
		{
			FNTChar fntChar = fntChars[i];
			
			char value = (char)fntChar.id;
			
			if (value == ' ')
			{
				spaceXAdvance = fntChar.xAdvance;
				continue;
			}
			
			ImageDetector imageDetector = ImageDetector.fromDetectorImage(
				charImageMap,
				fntChar.x, fntChar.y,
				fntChar.width, fntChar.height,
				CHAR_POSITIVE_ARGB, CHAR_NEGATIVE_ARGB,
				fntChar.xOffset, fntChar.yOffset - ascenderLine,
				fntChar.xAdvance - (fntChar.xOffset + fntChar.width), descenderLine - (fntChar.yOffset + fntChar.height)
			);
			
			chars[fntChar.id + ARRAY_VALUE_OFFSET] = new FontChar(
				value,
				fntChar.width, fntChar.height,
				fntChar.xOffset, fntChar.yOffset,
				fntChar.xAdvance,
				imageDetector
			);
		}
		
		return new Font(lineHeight, ascenderLine, descenderLine, spaceXAdvance, chars);
	}
	
	
	static class FNTChar
	{
		public final int id;
		public final int x;
		public final int y;
		public final int width;
		public final int height;
		public final int xOffset;
		public final int yOffset;
		public final int xAdvance;
		
		FNTChar(NamedNodeMap attributes)
		{
			id       = Integer.parseInt(attributes.getNamedItem("id"      ).getNodeValue());
			x        = Integer.parseInt(attributes.getNamedItem("x"       ).getNodeValue());
			y        = Integer.parseInt(attributes.getNamedItem("y"       ).getNodeValue());
			width    = Integer.parseInt(attributes.getNamedItem("width"   ).getNodeValue());
			height   = Integer.parseInt(attributes.getNamedItem("height"  ).getNodeValue());
			xOffset  = Integer.parseInt(attributes.getNamedItem("xoffset" ).getNodeValue());
			yOffset  = Integer.parseInt(attributes.getNamedItem("yoffset" ).getNodeValue());
			xAdvance = Integer.parseInt(attributes.getNamedItem("xadvance").getNodeValue());
		}
	}
	
	
	public static class FontChar
	{
		public final char value;
		public final int width, height;
		public final int xOffset, yOffset;
		public final int xAdvance;
		
		public final ImageDetector imageDetector;
		
		FontChar(
			char value,
			int width, int height,
			int xOffset, int yOffset,
			int xAdvance,
			ImageDetector imageDetector
		)
		{
			this.value = value;
			
			this.width  = width;
			this.height = height;
			
			this.xOffset  = xOffset;
			this.yOffset  = yOffset;
			
			this.xAdvance = xAdvance;
			
			this.imageDetector = imageDetector;
		}
	}
}
