package net.awesomebox.papersthanks;

import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;

public class EntryError extends Throwable
{
	private static final long serialVersionUID = 1L;
	
	public final InterrogateItemSet interrogateItems;
	
	public EntryError(String message, InterrogateItemSet interrogateItems)
	{
		super(message);
		this.interrogateItems = interrogateItems;
	}
}
