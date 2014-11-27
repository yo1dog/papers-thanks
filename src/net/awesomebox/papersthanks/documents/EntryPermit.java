package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.Duration;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.Purpose;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.DateUtil;

public class EntryPermit extends Document
{
	public final Name     name;
	public final String   passportID;
	public final Purpose  purpose;
	public final Duration duration;
	public final Date     enterByDate;
	
	EntryPermit(
		String nameStr,
		String passportID,
		String purposeStr,
		String durationStr,
		String enterByDateStr
	)
	{
		super(150, 201);
		
		this.name        = Name.fromFullName(nameStr);
		this.passportID  = passportID;
		this.purpose     = Purpose.fromString(purposeStr);
		this.duration    = Duration.fromString(durationStr);
		this.enterByDate = DateUtil.parseDate(enterByDateStr);
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
