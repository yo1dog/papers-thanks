package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.MenuView;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class PapersThanks
{
	public static void main(String[] args)
	{
		ClickPreviewView.show();
		
		// click resume
		MenuView.clickResume();
		
		WorkView.init();
		
		/*BufferedImage image = ImageUtil.readImage("testscreen.png");
		
		long start = System.currentTimeMillis();
		
		Point origin = new Point(22, 103);
		String str = TextReader.readTextNear(Font.bm_mini_a8_6, image, new Color(87, 72, 72), new Color(228, 230, 189), origin);
		
		System.out.println("str: \"" + str + "\"");
		System.out.println(origin);
		System.out.println(System.currentTimeMillis() - start);*/
	}
}