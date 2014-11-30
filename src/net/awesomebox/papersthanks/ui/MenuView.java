package net.awesomebox.papersthanks.ui;

import java.awt.Point;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class MenuView
{
	public static final Point resumeOptionPoint = new Point(285, 230);
	
	public static void clickResume()
	{
		VirtualUser.executeClickSequence(new ClickSequence(new ClickEvent(resumeOptionPoint.x, resumeOptionPoint.y, "Resume.")));
	}
}
