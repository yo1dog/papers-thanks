package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;


// anything the user can interact with
public interface Interface
{
	// returns a sequence of clicks that results in the given point in the interface being clicked on
	public ClickSequence clickTo(int xRelToInterface, int yRelToInterface, String desc);
	
	// returns a single click at the given point in the interface
	public ClickEvent click(int xRelToInterface, int yRelToInterface, String desc);
	
	
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
		
		
		public ClickSequence clickTo()
		{
			return _interface.clickTo(xRelToInterface, yRelToInterface, name);
		}
	}
}
