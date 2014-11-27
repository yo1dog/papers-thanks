package net.awesomebox.papersthanks.ui;

import java.util.Date;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class DeskClock implements Interface
{
	public final InterrogateItem interrogateItem;
	
	private Date currentDate;
	
	DeskClock()
	{
		interrogateItem = new InterrogateItem(this, UI.clockArea.width / 2, UI.clockArea.height / 2, "Desk clock.");
	}
	
	
	public Date getDate()
	{
		return currentDate;
	}
	void readDate()
	{
		// TODO: logic for reading the date from the screen
	}
	
	public boolean isExpired(Date date)
	{
		return currentDate.before(date);
	}
	
	public boolean isToday(Date date)
	{
		return currentDate.equals(date);
	}
	
	
	@Override
	public ClickSequence clickTo(int xRelToClock, int yRelToClock, String desc)
	{
		return new ClickSequence(click(xRelToClock, yRelToClock, desc));
	}
	
	@Override
	public ClickEvent click(int xRelToClock, int yRelToClock, String desc)
	{
		int x = UI.clockArea.x + xRelToClock;
		int y = UI.clockArea.y + yRelToClock;
		return new ClickEvent(x, y, desc);
	}
}
