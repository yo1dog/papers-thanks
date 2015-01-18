package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.ui.Interface.ClickPoint;


// a type of click point that is specific to an item for an interrogation
public class InterrogateItem extends ClickPoint
{
	public final ItemOnDesk parentItemOnDesk;
	
	public InterrogateItem(Interface _interface, ItemOnDesk parentItemOnDesk, int xRelToInterface, int yRelToInterface, String name)
	{
		super(_interface, xRelToInterface, yRelToInterface, name);
		this.parentItemOnDesk = parentItemOnDesk;
	}

	public static class InterrogateItemSet
	{
		public final InterrogateItem item1;
		public final InterrogateItem item2;
		
		public InterrogateItemSet(InterrogateItem item1, InterrogateItem item2)
		{
			this.item1 = item1;
			this.item2 = item2;
		}
	}
}
