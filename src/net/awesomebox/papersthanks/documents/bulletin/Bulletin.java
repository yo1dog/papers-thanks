package net.awesomebox.papersthanks.documents.bulletin;

import net.awesomebox.papersthanks.documents.Document;
import net.awesomebox.papersthanks.ui.Desk;

public class Bulletin extends Document
{
	public static final int WIDTH  = 150;
	public static final int HEIGHT = 200;
	
	public final ClickPoint nextPageLink;
	
	public final BulletinWantedCriminalsPage wantedCriminalsPage;
	
	public Bulletin(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		nextPageLink = new ClickPoint(this, 145, 195, "Bulletin next-page link.");
		
		wantedCriminalsPage = new BulletinWantedCriminalsPage(this);
	}
	
	public void readPages()
	{
		wantedCriminalsPage.readFaces();
	}
}
