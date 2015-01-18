package net.awesomebox.papersthanks.ui;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEventListener;


// anything the user can interact with
public abstract class Interface
{
	// returns a sequence of clicks that results in the interface being shown
	public abstract MouseSequence clickTo();
	
	// returns a single click at the given point in the interface
	public final MouseClickEvent click(int xRelToInterface, int yRelToInterface, String desc)
	{
		return click(xRelToInterface, yRelToInterface, desc, null);
	}
	public abstract MouseClickEvent click(int xRelToInterface, int yRelToInterface, String desc, MouseEventListener mouseEventListener);
	
	// returns a sequence of clicks that results in the interface being shown and the given point in the interface being clicked on
	public final MouseSequence clickThrough(int xRelToInterface, int yRelToInterface, String desc)
	{
		return clickThrough(xRelToInterface, yRelToInterface, desc, null);
	}
	public final MouseSequence clickThrough(int xRelToInterface, int yRelToInterface, String desc, MouseEventListener mouseEventListener)
	{
		return clickTo().add(click(xRelToInterface, yRelToInterface, desc));
	}
	
	
	// a point in an interface that can be clicked
	public static class ClickPoint implements MouseEventListener
	{
		public final Interface          _interface;
		public final int                xRelToInterface, yRelToInterface;
		public final String             name;
		public final ClickPointListener clickPointListener;
		
		public ClickPoint(Interface _interface, int xRelToInterface, int yRelToInterface, String name)
		{
			this(_interface, xRelToInterface, yRelToInterface, name, null);
		}
		
		public ClickPoint(Interface _interface, int xRelToInterface, int yRelToInterface, String name, ClickPointListener clickPointListener)
		{
			this._interface      = _interface;
			this.xRelToInterface = xRelToInterface;
			this.yRelToInterface = yRelToInterface;
			this.name            = name;
			
			this.clickPointListener = clickPointListener;
		}
		
		
		public MouseSequence clickThrough()
		{
			return _interface.clickThrough(xRelToInterface, yRelToInterface, name, this);
		}
		public MouseClickEvent click()
		{
			return _interface.click(xRelToInterface, yRelToInterface, name, this);
		}
		
		
		@Override
		public void mouseEventWasExecuted(MouseEvent mouseEvent)
		{
			if (clickPointListener != null && mouseEvent instanceof MouseClickEvent)
				clickPointListener.clickPointWasClicked(this);
		}
	}
	
	public static interface ClickPointListener
	{
		void clickPointWasClicked(ClickPoint clickPoint);
	}
}
