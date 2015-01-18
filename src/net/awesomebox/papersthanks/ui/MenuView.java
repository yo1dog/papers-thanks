package net.awesomebox.papersthanks.ui;

import java.awt.Point;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;

public class MenuView
{
	public static final Point resumeOptionPoint = new Point(285, 230);
	
	public static void clickResume()
	{
		new MouseSequence(new MouseClickEvent(resumeOptionPoint.x, resumeOptionPoint.y, "Resume.")).execute();
	}
}
