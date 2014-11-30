package net.awesomebox.papersthanks.documents;

import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.ui.ItemOnDesk;

public abstract class Document extends ItemOnDesk
{
	public static abstract class DocumentError
	{
		public final String message;
		public final InterrogateItemSet interrogateItems;
		
		public DocumentError(String message, InterrogateItemSet interrogateItems)
		{
			this.message = message;
			this.interrogateItems = interrogateItems;
		}
		
		@Override
		public String toString()
		{
			return this.getClass().getName() + ": " + message;
		}
	}
	
	public Document(Desk desk, int xRelToDesk, int yRelToDesk, int width, int height)
	{
		super(desk, xRelToDesk, yRelToDesk, width, height);
	}
}
