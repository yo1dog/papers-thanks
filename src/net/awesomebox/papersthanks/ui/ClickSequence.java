package net.awesomebox.papersthanks.ui;

import java.util.ArrayList;

public class ClickSequence
{
	public static class ClickEvent
	{
		public final int    x, y; // absolute
		public final int    dragToX, dragToY; // absolute
		public final int    delayMS;
		public final String desc;
		
		public ClickEvent(int x, int y, String desc)
		{
			this(x, y, -1, -1, 0, desc);
		}
		public ClickEvent(int x, int y, int delayMS, String desc)
		{
			this(x, y, -1, -1, delayMS, desc);
		}
		public ClickEvent(int x, int y, int dragToX, int dragToY, String desc)
		{
			this(x, y, dragToX, dragToY, 0, desc);
		}
		public ClickEvent(int x, int y, int dragToX, int dragToY, int delayMS, String desc)
		{
			this.x       = x;
			this.y       = y;
			this.dragToX = dragToX;
			this.dragToY = dragToY;
			this.delayMS = delayMS;
			this.desc    = desc;
		}
		
		@Override
		public String toString()
		{
			String str = desc + " - Click at (" + x + ", " + y + ")";
			
			if (dragToX > -1)
				str += " and drag to (" + dragToX + ", " + dragToY + ")";
			
			if (delayMS > 0)
				str += " after a delay of " + delayMS + "MS";
			
			return str;
		}
	}
	
	
	private final ArrayList<ClickEvent> clickEvents;
	
	public ClickSequence()
	{
		clickEvents = new ArrayList<ClickEvent>();
	}
	public ClickSequence(ClickEvent clickEvent)
	{
		this();
		clickEvents.add(clickEvent);
	}
	private ClickSequence(ArrayList<ClickEvent> clickEvents)
	{
		this.clickEvents = clickEvents;
	}
	
	
	public ClickSequence add(ClickEvent event)
	{
		clickEvents.add(event);
		return this;
	}
	
	public ClickEvent[] getClickEvents()
	{
		return clickEvents.toArray(new ClickEvent[clickEvents.size()]);
	}
	
	public void execute()
	{
		VirtualUser.executeClickSequence(this);
	}
	
	
	public static ClickSequence combine(ClickSequence sequence1, ClickSequence sequence2)
	{
		ArrayList<ClickEvent> clickEvents = new ArrayList<ClickEvent>();
		clickEvents.addAll(sequence1.clickEvents);
		clickEvents.addAll(sequence2.clickEvents);
		
		return new ClickSequence(clickEvents);
	}
	
	
	@Override
	public String toString()
	{
		String str = "";
		
		for (int i = 0; i < clickEvents.size(); ++i)
		{
			if (i > 0)
				str += "\n";
			
			str += (i + 1) + ". " + clickEvents.get(i);
		}
		
		return str;
	}
}
