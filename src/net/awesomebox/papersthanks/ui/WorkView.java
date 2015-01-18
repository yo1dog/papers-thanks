package net.awesomebox.papersthanks.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.awesomebox.papersthanks.DebugOptions;
import net.awesomebox.papersthanks.EntryError;
import net.awesomebox.papersthanks.ItemType;
import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Rule;
import net.awesomebox.papersthanks.Rule.RuleError;
import net.awesomebox.papersthanks.documents.EntryTicket;
import net.awesomebox.papersthanks.documents.bulletin.Bulletin;
import net.awesomebox.papersthanks.documents.passports.Passport;
import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickAndDragEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseMoveEvent;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.utils.ImageUtil;


public class WorkView
{
	public static final int SCALE  = 2;
	public static final int WIDTH  = 570;
	public static final int HEIGHT = 320;
	
	public static final Rectangle deskArea    = new Rectangle(180, 104, 380, 215);
	public static final Rectangle clockArea   = new Rectangle(2,   289, 32,  30 );
	public static final Rectangle windowArea  = new Rectangle(22,  103, 126, 105);
	public static final Rectangle counterArea = new Rectangle(2,   232, 173, 36 );
	
	public static final Point deskItemDropPointRelToDesk   = new Point(deskArea.width  / 2, deskArea.height / 2);
	public static final Point bulletinInitalPointRelToDesk = new Point(301 - deskArea.x, 106 - deskArea.y);
	
	public static final Point ruleBookSlotPoint                      = new Point(126, 295);
	public static final Point bulletinSlotPoint                      = new Point(52 , 295);
	public static final Point audioLogSlotPoint                      = new Point(89 , 282);
	public static final Point stampSliderButtonPoint                 = new Point(564, 156);
	public static final Point stampApprovedButtonPoint               = new Point(488, 144);
	public static final Point stampDeniedButtonPoint                 = new Point(368, 144);
	public static final Point passportStampingApprovedPointRelToDesk = new Point(422 - deskArea.x, 154 - deskArea.y);
	public static final Point passportStampingDeniedPointRelToDesk   = new Point(303 - deskArea.x, 154 - deskArea.y);
	
	public static final TextReader clockDateTextReader = new TextReader(Font.digits, new Color(66, 42, 28), new Color(106, 82, 47), -1, false, TextReader.NUMERIC_CHAR_SET, 40);
	public static final Point clockDateTextPoint = new Point(4, 311);
	
	public static final Point iterrogateButtonPoint = new Point(548, 310);
	public static final Color iterrogateButtonColor = new Color(180, 43, 43);
	
	public static final Point loudSpeakerPoint       = new Point(173, 70);
	public static final Point loudSpeakerActivePoint = new Point(162, 79);
	public static final Color loudSpeakerActiveColor = new Color(181, 181, 181);
	
	public static DeskClock    deskClock;
	public static Desk         desk;
	public static BoothWindow  boothWindow;
	public static BoothCounter boothCounter;
	
	private static boolean canInterrogate;
	
	public static void play()
	{
		VirtualUser.delay(1000);
		
		// ---------------------------
		// environment
		// ---------------------------
		deskClock = new DeskClock();
		deskClock.readDate();
		
		desk = new Desk();
		boothWindow = new BoothWindow();
		boothCounter = new BoothCounter();
		
		
		// ---------------------------
		// bulletin
		// ---------------------------
		Bulletin bulletin = null;
		if (!DebugOptions.SKIP_BULLETIN)
		{
			Point bulletinPointRelToDesk;
			if (DebugOptions.BULLETIN_IN_SLOT)
			{
				// move the rule book from its slot onto the desk
				bulletinPointRelToDesk = moveItemOntoDesk(bulletinSlotPoint.x, bulletinSlotPoint.y, Bulletin.WIDTH, Bulletin.HEIGHT, "Move bulletin from slot onto desk.");
			}
			else
			{
				// the bulletin is always on the desk initially
				bulletinPointRelToDesk = bulletinInitalPointRelToDesk;
			}
			
			// create a new bulletin using the initial starting position
			bulletin = new Bulletin(desk, bulletinPointRelToDesk.x, bulletinPointRelToDesk.y);
			bulletin.read();
			desk.organizeItemOnDesk(bulletin);
		}
		
		
		// ---------------------------
		// rule book
		// ---------------------------
		// move the rule book from its slot onto the desk
		Point droppedPointRelToDesk = moveItemOntoDesk(ruleBookSlotPoint.x, ruleBookSlotPoint.y, RuleBook.WIDTH, RuleBook.HEIGHT, "Move rule book from slot onto desk.");
		
		// create a new rule book using the dropped position
		RuleBook ruleBook = new RuleBook(desk, droppedPointRelToDesk.x, droppedPointRelToDesk.y);
		
		// read rule book
		if (!DebugOptions.SKIP_READING_RULE_BOOK)
			ruleBook.read();
		
		// organize rule book on desk
		desk.organizeItemOnDesk(ruleBook);
		
		
		// ---------------------------
		// abilities
		// ---------------------------
		// check if we can interrogate
		BufferedImage screenshot = ImageUtil.takeScreenshot();
		canInterrogate = screenshot.getRGB(iterrogateButtonPoint.x * WorkView.SCALE, iterrogateButtonPoint.y * WorkView.SCALE) == iterrogateButtonColor.getRGB();
		
		
		// ---------------------------
		// process people
		// ---------------------------
		boolean isFirstPerson = true;
		while (true)
		{
			processPerson(isFirstPerson);
			isFirstPerson = false;
		}
	}
	
	
	private static void processPerson(boolean isFirstPerson)
	{
		RuleBook ruleBook = desk.getRuleBook();
		Rule[] rules = ruleBook.getRules();
		
		// wait for loudspeaker to become active
		if (!isFirstPerson || !DebugOptions.PERSON_IS_PRESENT)
		{
			boolean isActive = waitForLoudspeaker();
			if (!isActive)
				throw new AssertionError("Loudspeaker did not become active.");
			
			// click the loudspeaker
			new MouseSequence(new MouseClickEvent(loudSpeakerPoint.x, loudSpeakerPoint.y, "Loudspeaker.")).execute();
			
			// wait for items
			VirtualUser.delay(5000);
		}
		
		// get items
		ArrayList<ItemOnDesk> items = new ArrayList<ItemOnDesk>();
		
		boolean approvePassport = true;
		
		Passport    passport    = null;
		EntryTicket entryTicket = null;
		
		// wait for items
		System.out.println("Wait for items on counter or loudspeaker...");
		
		boolean foundItem = false;
		for (int attempts = 0; attempts < 200; ++attempts)
		{
			// check if there are items on the counter
			BufferedImage screenshot = ImageUtil.takeScreenshot();
			
			if (boothCounter.checkForItem(screenshot))
			{
				foundItem = true;
				break;
			}
			
			// check if the person left and the loudspeaker is active
			if (checkLoudspeakerIsActive())
				return; // if the loudspeaker is active, there is nothing left to do
			
			VirtualUser.delay(20);
		}
		
		
		// get all the items from the counter
		if (foundItem)
		{
			do
			{
				ItemOnDesk item = getItemFromCounter();
				if (item == null)
					continue;
				
				items.add(item);
				
				if (item instanceof Passport)
				{
					passport = (Passport)item;
					
					new MouseSequence(new MouseMoveEvent(0, 0, "Move mouse out of the way for reading passport.")).execute();
					passport.read();
				}
				if (item instanceof EntryTicket)
				{
					entryTicket = (EntryTicket)item;
					
					new MouseSequence(new MouseMoveEvent(0, 0, "Move mouse out of the way for reading entry ticket.")).execute();
					entryTicket.read();
				}
				
				desk.organizeItemOnDesk(item);
			}
			while(boothCounter.checkForItem(ImageUtil.takeScreenshot()));
		}
		
		
		try
		{
			// verify documents
			if (passport != null)
				passport.verify();
			if (entryTicket != null)
				entryTicket.verify();
			
			
			// check rules
			boolean isCitizen = passport != null && passport.getData().nation == Nation.Arstotzka;
			
			for (int i = 0; i < rules.length; ++i)
			{
				switch (rules[i])
				{
					case NEED_PASSPORT:
						if (passport == null)
							throw new RuleError(Rule.NEED_PASSPORT, new InterrogateItemSet(boothCounter.interrogateItem, ruleBook.rulesPage.getIterrogateItemForRule(Rule.NEED_PASSPORT)));
						break;
					
					case ARSTOTZKA_ONLY:
						if (!isCitizen)
							throw new RuleError(Rule.ARSTOTZKA_ONLY, new InterrogateItemSet(passport.passportInterrogateItem, ruleBook.rulesPage.getIterrogateItemForRule(Rule.ARSTOTZKA_ONLY)));
						break;
					
					case NEED_ENTRY_TICKET:
						if (!isCitizen && entryTicket == null)
							throw new RuleError(Rule.NEED_ENTRY_TICKET, new InterrogateItemSet(boothCounter.interrogateItem, ruleBook.rulesPage.getIterrogateItemForRule(Rule.NEED_ENTRY_TICKET)));
						break;
					
					default:
						break;
				}
			}
			
		}
		catch (EntryError e)
		{
			System.out.println("!!!!! Error Found: (" + e.getClass().getSimpleName() + ") " + e.getMessage());
			
			if (canInterrogate)
			{
				desk.prepareInterrogationItems(e.interrogateItems);
				
				new MouseSequence()
					.add(new MouseClickEvent(iterrogateButtonPoint.x, iterrogateButtonPoint.y, "Interrogate button."))
					.add(e.interrogateItems.item1.click())
					.add(e.interrogateItems.item2.click())
					.add(new MouseClickEvent(audioLogSlotPoint.x, audioLogSlotPoint.y, "Audio log slot.", 2000))
					.execute();
				
				// unable to move documents for a time
				VirtualUser.delay(100);
				
				desk.reorganzieInterrogationItems(e.interrogateItems);
			}
			
			// deny passport
			approvePassport = false;
		}
		
		
		// stamp passport
		if (passport != null)
		{
			// move the passport to stamping position
			if (approvePassport)
				desk.moveItem(passport, passportStampingApprovedPointRelToDesk.x, passportStampingApprovedPointRelToDesk.y, "Move passport to approved stamping position.");
			else
				desk.moveItem(passport, passportStampingDeniedPointRelToDesk.x, passportStampingDeniedPointRelToDesk.y, "Move passport to dennied stamping position.");
			
			// open stamp slider
			new MouseSequence(new MouseClickEvent(stampSliderButtonPoint.x, stampSliderButtonPoint.y, "Open stamp slider.")).execute();
			
			// wait a second for slider to open
			VirtualUser.delay(750);
			
			// click approve or deny stamp
			MouseSequence mouseSequence = new MouseSequence();
			
			if (approvePassport)
				mouseSequence.add(new MouseClickEvent(stampApprovedButtonPoint.x, stampApprovedButtonPoint.y, "Approved stamp."));
			else
				mouseSequence.add(new MouseClickEvent(stampDeniedButtonPoint.x, stampDeniedButtonPoint.y, "Dennied stamp."));
			
			// close the stamp slider
			mouseSequence.add(new MouseClickEvent(stampSliderButtonPoint.x, stampSliderButtonPoint.y, "Close stamp slider."));
			mouseSequence.execute();
			
			// wait a second for slider to close
			VirtualUser.delay(750);
			
			// reorganize passport
			desk.organizeItemOnDesk(passport);
		}
		
		if (DebugOptions.DONT_FINISH_PERSON)
			throw new AssertionError("DEBUG: Not finishing.");
		
		// give items back to person
		returnItemsToPerson(items);
	}
	
	private static ItemOnDesk getItemFromCounter()
	{
		// move the item onto the desk
		Point droppedCenterPointRelToDesk = moveItemOntoDesk(BoothCounter.itemInitialPoint.x, BoothCounter.itemInitialPoint.y, "Move item from counter to desk.");
		
		// check what type the item is
		Point itemPosRelToDesk = new Point();
		ItemType type = getItemOnDeskType(droppedCenterPointRelToDesk.x, droppedCenterPointRelToDesk.y, itemPosRelToDesk);
		System.out.println("Found " + type);
		
		switch(type)
		{
			case Passport:
				return new Passport(desk, itemPosRelToDesk.x, itemPosRelToDesk.y);
			
			case EntryTicket:
				return new EntryTicket(desk, itemPosRelToDesk.x, itemPosRelToDesk.y);
			
			default:
				// give back to person on the junk side
				giveItemToPerson(
					desk.getAbsoluteX(itemPosRelToDesk.x), desk.getAbsoluteY(itemPosRelToDesk.y),
					true,
					"Give " + type + " item to person on junk side."
				);
				return null;
		}
	}
	
	private static final void returnItemsToPerson(ArrayList<ItemOnDesk> items)
	{
		// give items on desk back to person
		for (ItemOnDesk item : items)
			giveItemOnDeskToPerson(item);
		
		
		// continue to give items to person if they failed to be given for some reason and wait for the loudspeaker
		System.out.println("Wait for failed given items on counter or loudspeaker...");
		
		for (int attempts = 0; attempts < 200; ++attempts)
		{
			// check if there are items on the counter
			BufferedImage screenshot = ImageUtil.takeScreenshot();
			
			// check if there are items that failed to be given back to person
			if (boothCounter.checkForFailedGivenItem(screenshot))
			{
				// give the item to the person
				giveItemToPerson(
					BoothCounter.failedGivenItemPoint.x, BoothCounter.failedGivenItemPoint.y,
					false,
					"Give failed given item to person."
				);
			}
			
			// check if there are new items
			if (boothCounter.checkForItem(screenshot))
			{
				// move the item onto the desk
				Point droppedCenterPointRelToDesk = moveItemOntoDesk(BoothCounter.itemInitialPoint.x, BoothCounter.itemInitialPoint.y, "Move item from counter to desk.");
				
				// check what type the item is
				Point itemPosRelToDesk = new Point();
				ItemType type = getItemOnDeskType(droppedCenterPointRelToDesk.x, droppedCenterPointRelToDesk.y, itemPosRelToDesk);
				
				// give the item to the person
				giveItemToPerson(
					desk.getAbsoluteX(itemPosRelToDesk.x), desk.getAbsoluteY(itemPosRelToDesk.y),
					type == ItemType.Junk,
					"Give leftover " + type + " item to person."
				);
			}
			
			// check if the person left and the loudspeaker is active
			if (checkLoudspeakerIsActive())
				return;
			
			VirtualUser.delay(20);
		}
	}
	
	private static boolean checkLoudspeakerIsActive()
	{
		BufferedImage screenshot = ImageUtil.takeScreenshot();
		
		int argb = screenshot.getRGB(loudSpeakerActivePoint.x * WorkView.SCALE, loudSpeakerActivePoint.y * WorkView.SCALE);
		return argb == loudSpeakerActiveColor.getRGB();
	}
	private static boolean waitForLoudspeaker()
	{
		System.out.println("Wait for loadspeaker to become active...");
		
		for (int attempts = 0; attempts < 400; ++attempts)
		{
			if (checkLoudspeakerIsActive())
				return true;
			
			VirtualUser.delay(20);
		}
		
		return false;
	}
	
	
	// moves item at x, y with a size of width x height onto the desk and returns its center position relative to the desk
	private static Point moveItemOntoDesk(int x, int y, String desc)
	{
		new MouseSequence(new MouseClickAndDragEvent(
			x, y,
			desk.getAbsoluteX(deskItemDropPointRelToDesk.x), desk.getAbsoluteY(deskItemDropPointRelToDesk.y),
			desc
		)).execute();
		
		return new Point(
			deskItemDropPointRelToDesk.x,
			deskItemDropPointRelToDesk.y
		);
	}
	// moves item at x, y with a size of width x height onto the desk and returns its position relative to the desk
	private static Point moveItemOntoDesk(int x, int y, int width, int height, String desc)
	{
		Point droppedPointRelToDesk = moveItemOntoDesk(x, y, desc);
		droppedPointRelToDesk.x -= width / 2;
		droppedPointRelToDesk.y -= height / 2;
		
		return droppedPointRelToDesk;
	}
	
	// moves the given item on the desk to the person
	private static void giveItemOnDeskToPerson(ItemOnDesk item)
	{
		Point itemVisiblePointRelToDesk = desk.getItemOnDeskVisiblePointRelToDesk(item);
		
		giveItemToPerson(
			desk.getAbsoluteX(itemVisiblePointRelToDesk.x), desk.getAbsoluteY(itemVisiblePointRelToDesk.y),
			false,
			"Give " + item.getClass().getSimpleName() + " to person."
		);
		
		desk.removeItemOnDesk(item);
	}
	
	// moves the item at x, y to the person
	private static void giveItemToPerson(int x, int y, boolean isJunk, String desc)
	{
		Point moveToPoint = isJunk? BoothWindow.giveJunkToPersonPoint : BoothWindow.giveToPersonPoint;
		
		new MouseSequence(new MouseClickAndDragEvent(
			x, y,
			moveToPoint.x, moveToPoint.y,
			desc
		)).execute();
	}
	
	
	private static ItemType getItemOnDeskType(int xCenterRelToDesk, int yCenterRelToDesk, Point itemPosRelToDesk)
	{
		// take screenshot of desk
		BufferedImage screenshot = ImageUtil.takeScreenshot(deskArea.x, deskArea.y, deskArea.width, deskArea.height);
		
		
		// check if passport
		int xRelToDesk = xCenterRelToDesk - Passport.WIDTH  / 2;
		int yRelToDesk = yCenterRelToDesk - Passport.HEIGHT / 2;
		
		int argb = screenshot.getRGB((xRelToDesk + 2) * WorkView.SCALE, (yRelToDesk + 2) * WorkView.SCALE);
		if (Nation.fromPassportColor(argb) != null)
		{
			itemPosRelToDesk.x = xRelToDesk;
			itemPosRelToDesk.y = yRelToDesk;
			return ItemType.Passport;
		}
		
		
		// check if entry ticket
		xRelToDesk = xCenterRelToDesk - EntryTicket.WIDTH  / 2;
		yRelToDesk = yCenterRelToDesk - EntryTicket.HEIGHT / 2;
		
		boolean matched = EntryTicket.imageDetector.checkImageNear(
			screenshot,
			EntryTicket.imageDetectorPositiveColors, EntryTicket.imageDetectorNegativeColors,
			new Point(xRelToDesk, yRelToDesk),
			WorkView.SCALE
		);
		
		if (matched)
		{
			itemPosRelToDesk.x = xRelToDesk;
			itemPosRelToDesk.y = yRelToDesk;
			return ItemType.EntryTicket;
		}
		
		
		itemPosRelToDesk.x = xCenterRelToDesk;
		itemPosRelToDesk.y = yCenterRelToDesk;
		return ItemType.Junk;
	}
}
