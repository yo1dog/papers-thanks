package net.awesomebox.papersthanks.documents.bulletin;

import net.awesomebox.papersthanks.ui.ClickSequence;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.ui.Interface;

public abstract class BulletinPage extends Interface
{
	protected final Bulletin bulletin;
	
	protected BulletinPage(Bulletin bulletin)
	{
		this.bulletin = bulletin;
	}
	
	
	@Override
	public ClickSequence clickTo()
	{
		return bulletin.clickTo();
	}
	
	@Override
	public ClickEvent click(int xRelToDocument, int yRelToDocument, String desc)
	{
		return bulletin.click(xRelToDocument, yRelToDocument, desc);
	}
}