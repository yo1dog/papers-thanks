package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.Duration;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.Purpose;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.WorkView;

public class EntryPermit extends Document
{
	public static final int WIDTH  = 150;
	public static final int HEIGHT = 201;
	
	public final Name     name;
	public final String   passportID;
	public final Purpose  purpose;
	public final Duration duration;
	public final Date     enterByDate;
	
	EntryPermit(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk,
		
		String nameStr,
		String passportID,
		String purposeStr,
		String durationStr,
		String enterByDateStr
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		this.name        = Name.fromFullName(nameStr);
		this.passportID  = passportID;
		this.purpose     = Purpose.fromString(purposeStr);
		this.duration    = Duration.fromString(durationStr);
		this.enterByDate = new Date();//DateUtil.parseDate(enterByDateStr);
	}
	
	
	public boolean verify()
	{
		if (name == null || passportID == null || purpose == null || duration == null || enterByDate == null)
			return false;
		
		// make sure the enter by date has not passed
		if (WorkView.deskClock.isExpired(enterByDate)) return false;
		
		
		return true;
	}
}
