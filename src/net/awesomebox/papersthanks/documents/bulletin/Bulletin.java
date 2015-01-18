package net.awesomebox.papersthanks.documents.bulletin;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.ImageDetector;
import net.awesomebox.papersthanks.documents.Document;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.Interface.ClickPointListener;
import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class Bulletin extends Document implements ClickPointListener
{
	public static final int WIDTH  = 150;
	public static final int HEIGHT = 200;
	
	private static final Color textForegroundColor = new Color(86,  86,  86 );
	private static final Color textBackgroundColor = new Color(240, 240, 240);
	
	private static final TextReader pagesTextReader = new TextReader(
		Font._04b03_regular_6,
		textForegroundColor, textBackgroundColor,
		-1, true, TextReader.NUMERIC_CHAR_SET
	);
	
	private static final Point wantedCriminalsDetectorCheckPoint = new Point(0, 0);
	private static final Point pageNumbersPoint = new Point(128, 189);
	
	private static final ImageDetector wantedCriminalsPageDetector = ImageDetector.fromDetectorImage(
		ImageUtil.readImage(ImageUtil.IMAGES_DIR + "documents/bulletin/bulletinWantedCriminalsPageDetector.png")
	);
	
	
	public final ClickPoint nextPageLink;
	public final ClickPoint previousPageLink;
	
	private final BulletinWantedCriminalsPage wantedCriminalsPage;
	
	private int currentPage = 0;
	private int totalNumPages = 0;
	
	public Bulletin(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		nextPageLink     = new ClickPoint(this, 145, 195, "Bulletin next-page link.",     this);
		previousPageLink = new ClickPoint(this, 5,   195, "Bulletin previous-page link.", this);
		
		wantedCriminalsPage = new BulletinWantedCriminalsPage(this);
	}
	
	
	public int getCurrentPageNumber()
	{
		return currentPage;
	}
	
	public BulletinWantedCriminalsPage getWantedCriminalsPage()
	{
		if (wantedCriminalsPage.pageNumber > -1)
			return wantedCriminalsPage;
		
		return null;
	}
	
	
	public void read()
	{
		// read the number of pages
		BufferedImage screenshot = takeScreenshot();
		
		String pagesStr = pagesTextReader.readTextNear(screenshot, pageNumbersPoint, WorkView.SCALE);
		System.out.println("Read bulletin pages: " + pagesStr);
		
		int index = pagesStr.indexOf('/');
		if (index < 1 || index == pagesStr.length() - 1)
			throw new AssertionError("Read invalid bulletin page numbers \"" + pagesStr + "\".");
		
		String numPagesStr = pagesStr.substring(index + 1);
		try
		{
			totalNumPages = Integer.parseInt(numPagesStr);
		}
		catch (NumberFormatException e)
		{
			throw new AssertionError("Read invalid bulletin page numbers \"" + pagesStr + "\".", e);
		}
		
		
		// the wanted criminals page is never the first or last page, so make sure we have at least 3 pages
		if (totalNumPages > 2)
		{
			// flip to the second-to-last page
			int possibleWantedCriminalsPageNumber = totalNumPages - 2;
			
			MouseSequence mouseSequence = new MouseSequence();
			
			for (int i = currentPage; i < possibleWantedCriminalsPageNumber; ++i)
				mouseSequence.add(nextPageLink.click());
			
			mouseSequence.execute();
			
			
			// check if this is the wanted criminals page
			screenshot = takeScreenshot();
			boolean isWantedCriminalsPage = wantedCriminalsPageDetector.checkImageNear(
				screenshot,
				new Color[] {textForegroundColor}, new Color[] {textBackgroundColor},
				wantedCriminalsDetectorCheckPoint,
				WorkView.SCALE
			);
			
			System.out.println("wantedCriminalsDetectorCheckPoint: " + wantedCriminalsDetectorCheckPoint.x + ", " + wantedCriminalsDetectorCheckPoint.y);
			
			if (isWantedCriminalsPage)
			{
				System.out.println("Found wanted criminals page on page " + possibleWantedCriminalsPageNumber);
				
				wantedCriminalsPage.pageNumber = possibleWantedCriminalsPageNumber;
				wantedCriminalsPage.screenshot = screenshot;
				
				wantedCriminalsPage.readFaces();
			}
		}
	}


	@Override
	public void clickPointWasClicked(ClickPoint clickPoint)
	{
		if (clickPoint == nextPageLink)
		{
			if (currentPage < totalNumPages - 1)
				++currentPage;
		}
		else if (clickPoint == previousPageLink)
		{
			if (currentPage > 0)
				--currentPage;
		}
	}
}
