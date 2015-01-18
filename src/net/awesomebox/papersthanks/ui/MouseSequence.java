package net.awesomebox.papersthanks.ui;

import java.util.ArrayList;

import net.awesomebox.papersthanks.MousePreviewView;

public class MouseSequence
{
	public static abstract class MouseEvent
	{
		public final String desc;
		public final int    waitMS;
		
		private final MouseEventListener listener;
		
		MouseEvent(String desc, int waitMS, MouseEventListener listener)
		{
			this.desc    = desc;
			this.waitMS  = waitMS;
			
			this.listener = listener;
		}
		
		
		protected abstract void __execute();
		
		final void execute()
		{
			MousePreviewView.addMouseEvent(this);
			
			__execute();
			
			if (listener != null)
				listener.mouseEventWasExecuted(this);
		}
		
		protected abstract String getActionString();
		
		@Override
		public final String toString()
		{
			String str = desc + " -";
			
			if (waitMS > 0)
				str += " Wait " + waitMS + "ms then";
			
			str += " " + getActionString() + ".";
			
			return str;
		}
	}
	
	public static interface MouseEventListener
	{
		void mouseEventWasExecuted(MouseEvent mouseEvent);
	}
	
	
	public static class MouseMoveEvent extends MouseEvent
	{
		public final int x, y; // absolute
		
		public MouseMoveEvent(int x, int y, String desc)
		{
			this(x, y, desc, 0, null);
		}
		public MouseMoveEvent(int x, int y, String desc, MouseEventListener listener)
		{
			this(x, y, desc, 0, listener);
		}
		public MouseMoveEvent(int x, int y, String desc, int waitMS)
		{
			this(x, y, desc, waitMS, null);
		}
		public MouseMoveEvent(int x, int y, String desc, int waitMS, MouseEventListener listener)
		{
			super(desc, waitMS, listener);
			
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void __execute()
		{
			VirtualUser.mouseMove(x, y);
		}
		
		@Override
		protected String getActionString()
		{
			return "Move to (" + x + ", " + y + ")";
		}
	}
	
	public static class MouseClickEvent extends MouseEvent
	{
		public final int x, y; // absolute
		
		public MouseClickEvent(int x, int y, String desc)
		{
			this(x, y, desc, 0, null);
		}
		public MouseClickEvent(int x, int y, String desc, MouseEventListener listener)
		{
			this(x, y, desc, 0, listener);
		}
		public MouseClickEvent(int x, int y, String desc, int waitMS)
		{
			this(x, y, desc, waitMS, null);
		}
		public MouseClickEvent(int x, int y, String desc, int waitMS, MouseEventListener listener)
		{
			super(desc, waitMS, listener);
			
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void __execute()
		{
			VirtualUser.mouseClick(x, y);
		}
		
		@Override
		protected String getActionString()
		{
			return "Click at (" + x + ", " + y + ")";
		}
	}
	
	public static class MouseClickAndDragEvent extends MouseEvent
	{
		public final int x1, y1; // absolute
		public final int x2, y2; // absolute
		
		public MouseClickAndDragEvent(int x1, int y1, int x2, int y2, String desc)
		{
			this(x1, y1, x2, y2, desc, 0, null);
		}
		public MouseClickAndDragEvent(int x1, int y1, int x2, int y2, String desc, MouseEventListener listener)
		{
			this(x1, y1, x2, y2, desc, 0, listener);
		}
		public MouseClickAndDragEvent(int x1, int y1, int x2, int y2, String desc, int waitMS)
		{
			this(x1, y1, x2, y2, desc, waitMS, null);
		}
		public MouseClickAndDragEvent(int x1, int y1, int x2, int y2, String desc, int waitMS, MouseEventListener listener)
		{
			super(desc, waitMS, listener);
			
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		
		@Override
		public void __execute()
		{
			VirtualUser.mouseClickAndDrag(x1, y1, x2, y2);
		}
		
		@Override
		protected String getActionString()
		{
			return "Click at (" + x1 + ", " + y1 + ") and Drag to (" + x2 + ", " + y2 + ")";
		}
	}
	
	
	
	private final ArrayList<MouseEvent> mouseEvents;
	
	public MouseSequence()
	{
		mouseEvents = new ArrayList<MouseEvent>();
	}
	public MouseSequence(MouseEvent mouseEvent)
	{
		this();
		mouseEvents.add(mouseEvent);
	}
	
	
	public MouseSequence add(MouseEvent mouseEvent)
	{
		mouseEvents.add(mouseEvent);
		return this;
	}
	
	public void execute()
	{
		if (mouseEvents.size() == 0)
			return;
		
		System.out.println("------------------------");
		System.out.println("Executing Mouse Sequence");
		
		for (int i = 0; i < mouseEvents.size(); ++i)
		{
			MouseEvent mouseEvent = mouseEvents.get(i);
			
			System.out.println((i + 1) + ". " + mouseEvents.get(i));
			
			if (mouseEvent.waitMS > 0)
				VirtualUser.delay(mouseEvent.waitMS);
			
			mouseEvent.execute();
		}
	}
	
	
	
	@Override
	public String toString()
	{
		String str = "";
		
		for (int i = 0; i < mouseEvents.size(); ++i)
		{
			if (i > 0)
				str += "\n";
			
			str += (i + 1) + ". " + mouseEvents.get(i);
		}
		
		return str;
	}
}
