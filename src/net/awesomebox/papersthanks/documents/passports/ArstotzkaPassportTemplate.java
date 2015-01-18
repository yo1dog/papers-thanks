package net.awesomebox.papersthanks.documents.passports;

import java.awt.Color;
import java.awt.Point;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.TextReader;

public class ArstotzkaPassportTemplate extends PassportTemplate
{
	private static final Font font = Font.bm_mini_a8_6;
	private static final Color[] foregroundColors = {new Color(87, 72, 72)};
	private static final Color[] backgroundColors = {new Color(237, 224, 216)};
	
	ArstotzkaPassportTemplate()
	{
		super(
			Nation.Arstotzka,
			
			// text
			new Point(8,   146), // id
			new Point(8,   85 ), // name
			new Point(68,  96 ), // dob
			new Point(68,  104), // sex
			new Point(68,  112), // iss
			new Point(68,  120), // exp date
			
			// readers
			// id
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET + TextReader.NUMERIC_CHAR_SET),
			// name
			new TextReader(font, foregroundColors, backgroundColors)
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
			new Point(26, 124) // face
		);
	}
}
