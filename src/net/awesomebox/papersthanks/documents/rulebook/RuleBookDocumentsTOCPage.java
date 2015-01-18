package net.awesomebox.papersthanks.documents.rulebook;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import net.awesomebox.papersthanks.ui.Font;
import net.awesomebox.papersthanks.ui.MouseSequence;
import net.awesomebox.papersthanks.ui.TextReader;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseMoveEvent;

public class RuleBookDocumentsTOCPage extends RuleBookPage
{
	private static final TextReader textReader = new TextReader(
		Font.bm_mini_a8_6,
		new Color(228, 230, 189), new Color(87, 72, 72),
		-1, false, TextReader.ALPHA_UPPER_CHAR_SET
	);
	
	private static final Point[] documentLinkTextPoints = new Point[] {
		new Point(136, 32 ),
		new Point(136, 47 ),
		new Point(136, 62 ),
		new Point(136, 77),
		new Point(136, 92),
		new Point(136, 107 ),
		new Point(136, 122 ),
		new Point(136, 137 )  // TODO: does this exist? - what is the max number of links shown at one time?
	};
	
	private static final int[][] documentLinkPoints = new int[][] {
		{145, 37},
		{145, 52},
		{145, 67},
		{145, 82},
		{145, 97},
		{145, 113},
		{145, 128},
		{145, 143} // TODO: does this exist? - what is the max number of links shown at one time?
	};
	
	private HashMap<RuleBookDocumetLinkType, ClickPoint> documentLinks = null;
	
	RuleBookDocumentsTOCPage(RuleBook ruleBook)
	{
		super(ruleBook);
	}
	
	
	@Override
	public MouseSequence clickTo()
	{
		// click the documents link on the TOC page
		return ruleBook.tocPage.documentsLink.clickThrough();
	}
	
	public ClickPoint getLinkForDocumentLinkType(RuleBookDocumetLinkType documentLinkType)
	{
		if (documentLinks == null)
			throw new AssertionError("Attempting to access document links before they have been read.");
		
		return documentLinks.get(documentLinkType);
	}
	
	
	public void readDocumentLinks()
	{
		// go to this page
		clickTo()
			// move mouse out of the way for reading
			.add(new MouseMoveEvent(0, 0, "Move mouse to top-left corner for reading document links."))
			.execute();
		
		// read the document links
		// take a screenshot of the page
		BufferedImage screenshot = ruleBook.takeScreenshot();
		
		ArrayList<RuleBookDocumetLinkType> documentLinkTypeList = new ArrayList<RuleBookDocumetLinkType>();
		
		for (int i = 0; i < documentLinkTextPoints.length; ++i)
		{
			String documentLinkDescriptionStr = textReader.readTextNear(screenshot, documentLinkTextPoints[i], WorkView.SCALE);
			System.out.println("Read document link : " + documentLinkDescriptionStr);
			
			if (documentLinkDescriptionStr.length() == 0)
				continue;
			
			RuleBookDocumetLinkType documentLinkType = RuleBookDocumetLinkType.fromDescription(documentLinkDescriptionStr);
			if (documentLinkType == null)
				throw new AssertionError("Unkown document link description \"" + documentLinkDescriptionStr + "\".");
			
			documentLinkTypeList.add(documentLinkType);
		}
		
		RuleBookDocumetLinkType[] documentLinkTypes = documentLinkTypeList.toArray(new RuleBookDocumetLinkType[documentLinkTypeList.size()]);
		documentLinks = new HashMap<RuleBookDocumetLinkType, ClickPoint>(documentLinkTypes.length);
		
		for (int i = 0; i < documentLinkTypes.length; ++i)
			documentLinks.put(documentLinkTypes[i], new ClickPoint(this, documentLinkPoints[i][0], documentLinkPoints[i][1], "Rule book documents TOC page " + documentLinkTypes[i] + " document link."));
	}
}
