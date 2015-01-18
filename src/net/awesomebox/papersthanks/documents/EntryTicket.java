package net.awesomebox.papersthanks.documents;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.Date;

import net.awesomebox.papersthanks.ImageDetector;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.InterrogateItem;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.utils.DateUtil;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class EntryTicket extends Document
{
	public static final int WIDTH  = 140;
	public static final int HEIGHT = 51;
	
	private static final Point validOnDateTextPoint        = new Point(91, 30);
	private static final Point validOnDateInterrogatePoint = new Point(validOnDateTextPoint.x + 5, validOnDateTextPoint.y + 5);
	
	private static final TextReader validOnDateTextReader = new TextReader(
		Font.bm_mini_a8_6,
		new Color[] {new Color(119, 103, 137)}, new Color[] {new Color(224, 233, 199), new Color(135, 106, 103)},
		-1, false, TextReader.NUMERIC_CHAR_SET,
		34
	);
	
	public static final ImageDetector imageDetector = ImageDetector.fromDetectorImage(
		ImageUtil.readImage(ImageUtil.IMAGES_DIR + "documents/entryTicket/entryTicketDetector.png")
	);
	public static final Color[] imageDetectorPositiveColors = new Color[] {new Color(137, 106, 103)};
	public static final Color[] imageDetectorNegativeColors = new Color[] {new Color(224, 233, 199)};
	
	
	public final InterrogateItem entryTicketInterrogateItem;
	public final InterrogateItem validOnDateInterrogateItem;
	
	private Date validOnDate;
	
	public EntryTicket(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		entryTicketInterrogateItem = new InterrogateItem(this, this, 25, 25, "Entry ticket.");
		validOnDateInterrogateItem = new InterrogateItem(
			this, this,
			validOnDateInterrogatePoint.x, validOnDateInterrogatePoint.y,
			"Entry ticket valid on date."
		);
	}
	
	public Date getValidOnDate()
	{
		if (validOnDate == null)
			throw new AssertionError("Attempted to get entry ticket valid on date before it was read.");
		
		return validOnDate;
	}
	
	
	// problems
	public static abstract class EntryTicketError extends DocumentError
	{
		private static final long serialVersionUID = 1L;

		public EntryTicketError(EntryTicket entryTicket, String message, InterrogateItemSet interrogateItems)
		{
			super("Error with entry ticket. " + message + "\n" + entryTicket, interrogateItems);
		}
	}
	
	public static class EntryTicketWrongDayError extends EntryTicketError
	{
		private static final long serialVersionUID = 1L;
		
		public EntryTicketWrongDayError(EntryTicket entryTicket)
		{
			super(entryTicket, "Wrong day.", new InterrogateItemSet(entryTicket.validOnDateInterrogateItem, WorkView.deskClock.interrogateItem));
		}
	}
	
	public void read()
	{
		// take a screenshot of the passport
		BufferedImage screenshot = takeScreenshot();
		
		// read valid on date
		// date can move vertically
		String validOnDateStr = validOnDateTextReader.readTextNear(
			screenshot, validOnDateTextPoint,
			WorkView.SCALE, TextReader.DEFAULT_MIN_LENGTH,
			TextReader.DEFAULT_SQR_DIST, 30
		);
		
		if (validOnDateStr.length() == 0)
			throw new AssertionError("Unable to read entry ticket valid on date.");
		
		Date validOnDate;
		try
		{
			validOnDate = DateUtil.parseDate(validOnDateStr);
		}
		catch (IllegalArgumentException | ParseException e)
		{
			throw new AssertionError("Invalid entry ticket valid on date \"" + validOnDateStr + "\".", e);
		}
		
		this.validOnDate = validOnDate;
		
		System.out.println("Read entry ticket:\n" + this);
	}
	
	public void verify() throws EntryTicketError
	{
		if (validOnDate == null)
			throw new AssertionError("Attempted to verify entry ticket before it was read.");
		
		// make sure the valid on date is today
		if (!WorkView.deskClock.isToday(validOnDate))
			throw new EntryTicketWrongDayError(this);
	}
	
	@Override
	public String toString()
	{
		return "validOnDate: " + DateUtil.toString(validOnDate);
	}
}
