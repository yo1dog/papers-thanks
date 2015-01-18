package net.awesomebox.papersthanks.ui;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.Date;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;
import net.awesomebox.papersthanks.utils.DateUtil;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class DeskClock extends Interface
{
	public final InterrogateItem interrogateItem;
	
	private Date currentDate;
	
	DeskClock()
	{
		interrogateItem = new InterrogateItem(this, null, WorkView.clockArea.width / 2, WorkView.clockArea.height / 2, "Desk clock.");
	}
	
	
	public Date getDate()
	{
		if (currentDate == null)
			throw new AssertionError("Attempted to use date before it was read.");
		
		return currentDate;
	}
	void readDate()
	{
		BufferedImage screenshot = ImageUtil.takeScreenshot();
		String dateStr = WorkView.clockDateTextReader.readTextNear(screenshot, WorkView.clockDateTextPoint, WorkView.SCALE);
		
		if (dateStr.length() == 0)
			throw new AssertionError("Unable to read clock date.");
		
		try
		{
			currentDate = DateUtil.parseDate(dateStr);
		}
		catch (ParseException | IllegalArgumentException e)
		{
			throw new AssertionError("Invalid clock date \"" + dateStr + "\".", e);
		}
		
		System.out.println("Read clock date: " + DateUtil.toString(currentDate));
	}
	
	public boolean isExpired(Date date)
	{
		if (currentDate == null)
			throw new AssertionError("Attempted to use date before it was read.");
		
		return date.before(currentDate);
	}
	
	public boolean isToday(Date date)
	{
		if (currentDate == null)
			throw new AssertionError("Attempted to use date before it was read.");
		
		return currentDate.equals(date);
	}
	
	
	@Override
	public MouseSequence clickTo()
	{
		return new MouseSequence();
	}
	
	@Override
	public MouseClickEvent click(int xRelToClock, int yRelToClock, String desc, MouseEventListener mouseEventListener)
	{
		int x = WorkView.clockArea.x + xRelToClock;
		int y = WorkView.clockArea.y + yRelToClock;
		return new MouseClickEvent(x, y, desc, mouseEventListener);
	}
}
