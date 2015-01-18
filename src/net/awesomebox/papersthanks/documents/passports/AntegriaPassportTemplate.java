package net.awesomebox.papersthanks.documents.passports;

import java.awt.Color;
import java.awt.Point;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.TextReader;

public class AntegriaPassportTemplate extends PassportTemplate
{
	private static final int tollerance = 75;
	
	private static final Font font = Font.bm_mini_a8_6;
	private static final Color[] foregroundColors = {new Color(87, 72, 72)};
	private static final Color[] backgroundColors = {new Color(237, 224, 216), new Color(162, 148, 144)};
	
	private static final Point idTextPoint = new Point(121, 147);
	
	AntegriaPassportTemplate()
	{
		super(
			Nation.Antegria,
			
			// text
			idTextPoint, // id
			new Point(8,   137), // name
			new Point(23,  99 ), // dob
			new Point(23,  107), // sex
			new Point(23,  117), // iss
			new Point(23,  126), // exp date
			
			// readers
			// id
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET + TextReader.NUMERIC_CHAR_SET)
				.setTollerance(tollerance)
				.setRightAligned(true),
			// name
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET)
				.setTollerance(tollerance),
			// dob
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.NUMERIC_CHAR_SET)
				.setTollerance(tollerance),
			// sex
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet("MF")
				.setTollerance(tollerance),
			// iss
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET)
				.setTollerance(tollerance),
			// exp date
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.NUMERIC_CHAR_SET)
				.setTollerance(tollerance),
			
			// interrogate
			new Point(idTextPoint.x - 5, idTextPoint.y + 5), // id
			null, // name
			null, // dob
			null, // sex
			null, // iss
			null, // exp date
			new Point(102, 112) // face
		);
	}
}
