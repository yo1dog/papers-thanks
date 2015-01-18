package net.awesomebox.papersthanks.documents.passports;

import java.awt.Color;
import java.awt.Point;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.TextReader;

public class ObristanPassportTemplate extends PassportTemplate
{
	private static final Font font = Font.bm_mini_a8_6;
	private static final Color[] foregroundColors     = {new Color(237, 224, 216)};
	private static final Color[] backgroundColors     = {new Color(161, 148, 144)};
	private static final Color[] nameForegroundColors = {new Color(87 , 72 , 72 )};
	private static final Color[] nameBackgroundColors = {new Color(237, 224, 216)};
	
	ObristanPassportTemplate()
	{
		super(
			Nation.Obristan,
			
			// text
			new Point(10,  146), // id
			new Point(8,   96 ), // name
			new Point(28,  109), // dob
			new Point(28,  117), // sex
			new Point(28,  125), // iss
			new Point(28,  133), // exp date
			
			// readers
			// id
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET + TextReader.NUMERIC_CHAR_SET),
			// name
			new TextReader(font, nameForegroundColors, nameBackgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET),
			// dob
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.NUMERIC_CHAR_SET),
			// sex
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet("MF"),
			// iss
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET),
			// exp date
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.NUMERIC_CHAR_SET),
			
			// interrogate
			null, // id
			null, // name
			null, // dob
			null, // sex
			null, // iss
			null, // exp date
			new Point(102, 128) // face
		);
	}
}
