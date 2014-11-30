package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.DateUtil;

public class EntryTicket extends Document
{
	public static final int WIDTH  = 140;
	public static final int HEIGHT = 51;
	
	public final Date validOnDate;
	
	EntryTicket(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk,
		
		String validOnDateStr
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		this.validOnDate = DateUtil.parseDate(validOnDateStr);
	}
	
	
	public boolean verify()
	{
		if (validOnDate == null)
			return false;
		
		// make sure the valid on date is today
		if (!WorkView.deskClock.isToday(validOnDate)) return false;
		
		
		return true;
	}
}
