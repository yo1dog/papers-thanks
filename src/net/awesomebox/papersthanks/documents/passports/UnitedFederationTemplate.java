package net.awesomebox.papersthanks.documents.passports;

import java.awt.Color;
import java.awt.Point;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.TextReader;

public class UnitedFederationTemplate extends PassportTemplate
{
	private static final Font font = Font.bm_mini_a8_6;
	private static final Color[] foregroundColors = {new Color(87, 72, 72)};
	private static final Color[] backgroundColors = {new Color(237, 224, 216)};
	
	private static final Point idTextPoint = new Point(122, 146);
	
	UnitedFederationTemplate()
	{
		super(
			Nation.UnitedFederation,
			
			// text
			idTextPoint, // id
			new Point(8,   96 ), // name
			new Point(70,  104), // dob
			new Point(70,  112), // sex
			new Point(70,  120), // iss
			new Point(70,  128), // exp date
			
			// readers
			// id
			new TextReader(font, foregroundColors, backgroundColors)
				.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET + TextReader.NUMERIC_CHAR_SET)
				.setRightAligned(true),
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
			new Point(idTextPoint.x - 5, idTextPoint.y + 5), // id
			null, // name
			null, // dob
			null, // sex
			null, // iss
			null, // exp date
			new Point(26, 134) // face
		);
	}
}
