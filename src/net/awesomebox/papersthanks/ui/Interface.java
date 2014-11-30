package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;


// anything the user can interact with
public abstract class Interface
{
	// returns a sequence of clicks that results in the interface being shown
	public abstract ClickSequence clickTo();
	
	// returns a sequence of clicks that results in the interface being shown and the given point in the interface being clicked on
	public final ClickSequence clickThrough(int xRelToInterface, int yRelToInterface, String desc)
	{
		return clickTo().add(click(xRelToInterface, yRelToInterface, desc));
	}
	
	// returns a single click at the given point in the interface
	public abstract ClickEvent click(int xRelToInterface, int yRelToInterface, String desc);
	
	
	// a point in an interface that can be clicked
	public static class ClickPoint
	{
		public final Interface _interface;
		public final int       xRelToInterface, yRelToInterface;
		public final String    name;
		
		public ClickPoint(Interface _interface, int xRelToInterface, int yRelToInterface, String name)
		{
			this._interface      = _interface;
			this.xRelToInterface = xRelToInterface;
			this.yRelToInterface = yRelToInterface;
			this.name            = name;
		}
		
		
		public ClickSequence clickThrough()
		{
			return _interface.clickThrough(xRelToInterface, yRelToInterface, name);
		}
		public ClickEvent click()
		{
			return _interface.click(xRelToInterface, yRelToInterface, name);
		}
	}
}
