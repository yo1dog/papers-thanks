package net.awesomebox.papersthanks.ui;

import java.awt.Point;
import java.awt.Rectangle;

import net.awesomebox.papersthanks.documents.bulletin.Bulletin;
import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;

public class WorkView
{
	public static final Rectangle deskArea   = new Rectangle(181, 104, 354, 215); // TODO: get positions
	public static final Rectangle clockArea  = new Rectangle(2,   289, 32,  30 ); // TODO: get positions
	public static final Rectangle windowArea = new Rectangle(22,  103, 126, 105); // TODO: get positions
	
	public static final Point ruleBookInSlotPos          = new Point(126, 295); // TODO: get positions
	public static final Point deskItemDropPosRelToDesk   = new Point(deskArea.width  / 2, deskArea.height / 2); // TODO: get positions
	public static final Point bulletinInitalPosRelToDesk = new Point(120, 2); // TODO: get positions
	
	
	public static DeskClock   deskClock;
	public static Desk        desk;
	public static BoothWindow boothWindow;
	
	public static void init()
	{
		// ---------------------------
		// clock
		// ---------------------------
		deskClock = new DeskClock();
		deskClock.readDate();
		
		// ---------------------------
		// desk
		// ---------------------------
		desk = new Desk();
		
		// ---------------------------
		// bulletin
		// ---------------------------
		// the bulletin is always on the desk initially
		// DEBUG: move bulletin back to starting position
		new ClickSequence(new ClickEvent(deskArea.x, deskArea.y, deskArea.x + bulletinInitalPosRelToDesk.x, deskArea.y + bulletinInitalPosRelToDesk.y, "Move bulletin back to starting position.")).execute();
		
		// create a new bulletin using the initial starting position
		Bulletin bulletin = new Bulletin(desk, bulletinInitalPosRelToDesk.x, bulletinInitalPosRelToDesk.y);
		bulletin.readPages();
		desk.organizeItemOnDesk(bulletin);
		
		// rule book
		// move the rule book from its slot onto the desk
		Point droppedPosRelToDesk = moveOntoDesk(ruleBookInSlotPos.x, ruleBookInSlotPos.y, RuleBook.WIDTH, RuleBook.HEIGHT, "Move rule book from slot onto desk.");
		
		// create a new rule book using the dropped position
		RuleBook ruleBook = new RuleBook(desk, droppedPosRelToDesk.x, droppedPosRelToDesk.y);
		ruleBook.readPages();
		desk.organizeItemOnDesk(ruleBook);
		
		
		boothWindow = new BoothWindow();
	}
	
	
	// moves something at x, y with a size of width x height onto the desk and returns its position relative to the desk
	private static Point moveOntoDesk(int x, int y, int width, int height, String desc)
	{
		VirtualUser.executeClickSequence(new ClickSequence(new ClickEvent(x, y, deskArea.x + deskItemDropPosRelToDesk.x, deskArea.y + deskItemDropPosRelToDesk.y, desc)));
		return new Point(
			deskItemDropPosRelToDesk.x - width / 2,
			deskItemDropPosRelToDesk.y - height / 2
		);
	}
}
