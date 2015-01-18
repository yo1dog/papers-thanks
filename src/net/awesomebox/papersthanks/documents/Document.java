package net.awesomebox.papersthanks.documents;

import net.awesomebox.papersthanks.EntryError;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.ui.ItemOnDesk;

public abstract class Document extends ItemOnDesk
{
	public static class DocumentError extends EntryError
	{
		private static final long serialVersionUID = 1L;
		
		public DocumentError(String message, InterrogateItemSet interrogateItems)
		{
			super(message, interrogateItems);
		}
	}
	
	public Document(Desk desk, int xRelToDesk, int yRelToDesk, int width, int height)
	{
		super(desk, xRelToDesk, yRelToDesk, width, height);
	}
}
