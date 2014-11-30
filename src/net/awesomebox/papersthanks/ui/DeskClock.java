package net.awesomebox.papersthanks.ui;

import java.util.Date;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class DeskClock extends Interface
{
	public final InterrogateItem interrogateItem;
	
	private Date currentDate;
	
	DeskClock()
	{
		interrogateItem = new InterrogateItem(this, WorkView.clockArea.width / 2, WorkView.clockArea.height / 2, "Desk clock.");
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
	public ClickSequence clickTo()
	{
		return new ClickSequence();
	}
	
	@Override
	public ClickEvent click(int xRelToClock, int yRelToClock, String desc)
	{
		int x = WorkView.clockArea.x + xRelToClock;
		int y = WorkView.clockArea.y + yRelToClock;
		return new ClickEvent(x, y, desc);
	}
}
