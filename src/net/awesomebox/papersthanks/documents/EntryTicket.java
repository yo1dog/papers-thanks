package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.DateUtil;

public class EntryTicket extends Document
{
	public final Date validOnDate;
	
	EntryTicket(String validOnDateStr)
	{
		super(140, 51);
		
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
