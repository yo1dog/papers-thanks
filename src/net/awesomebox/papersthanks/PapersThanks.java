package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.documents.passports.AntegriaPassportTemplate;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.MenuView;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class PapersThanks
{
	public static void main(String[] args)
	{
		MousePreviewView.show();
		
		// click resume
		MenuView.clickResume();
		
		WorkView.play();
		
		/*BufferedImage image = ImageUtil.readImage("testscreen.png");
		
		long start = System.currentTimeMillis();
		
		Font font = Font.bm_mini_a8_6;
		Color[] foregroundColors = {new Color(87, 72, 72)};
		Color[] backgroundColors = {new Color(237, 224, 216)};
		TextReader textReader = new TextReader(font, foregroundColors, backgroundColors)
			.setFirstCharSet(TextReader.ALPHA_UPPER_CHAR_SET + TextReader.NUMERIC_CHAR_SET)
			.setRightAligned(true);
		
		Point origin = new Point(119, 144);
		
		String str = textReader.readTextNear(image, origin, WorkView.SCALE, 1);
		
		System.out.println("str: \"" + str + "\"");
		System.out.println(origin);
		System.out.println(System.currentTimeMillis() - start);*/
	}
}