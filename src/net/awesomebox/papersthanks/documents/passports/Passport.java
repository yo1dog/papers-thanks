package net.awesomebox.papersthanks.documents.passports;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.Date;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.Sex;
import net.awesomebox.papersthanks.documents.Document;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.InterrogateItem;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.utils.DateUtil;

public class Passport extends Document
{
	public static final int WIDTH  = 130;
	public static final int HEIGHT = 162;
	
	private static final PassportTemplate[] passportTemplates = {
		new AntegriaPassportTemplate(),
		new ArstotzkaPassportTemplate(),
		new ImporPassportTemplate(),
		new KolechiaPassportTemplate(),
		new ObristanPassportTemplate(),
		new RepubliaPassportTemplate(),
		new UnitedFederationTemplate()
	};
	
	
	public static class PassportData
	{
		public final Nation nation;
		public final String id;
		public final Name   name;
		public final Date   dob;
		public final Sex    sex;
		public final String iss;
		public final Date   expDate;
		public final Face   face;
		
		public final InterrogateItem idInterrogateItem;
		public final InterrogateItem nameInterrogateItem;
		public final InterrogateItem dobInterrogateItem;
		public final InterrogateItem sexInterrogateItem;
		public final InterrogateItem issInterrogateItem;
		public final InterrogateItem expDateInterrogateItem;
		public final InterrogateItem faceInterrogateItem;
		
		PassportData(
			Nation nation,
			String id,
			Name   name,
			Date   dob,
			Sex    sex,
			String iss,
			Date   expDate,
			Face   face,
			
			InterrogateItem idInterrogateItem,
			InterrogateItem nameInterrogateItem,
			InterrogateItem dobInterrogateItem,
			InterrogateItem sexInterrogateItem,
			InterrogateItem issInterrogateItem,
			InterrogateItem expDateInterrogateItem,
			InterrogateItem faceInterrogateItem
		)
		{
			this.nation  = nation;
			this.id      = id;
			this.name    = name;
			this.dob     = dob;
			this.sex     = sex;
			this.iss     = iss;
			this.expDate = expDate;
			this.face    = face;
			
			this.idInterrogateItem      = idInterrogateItem;
			this.nameInterrogateItem    = nameInterrogateItem;
			this.dobInterrogateItem     = dobInterrogateItem;
			this.sexInterrogateItem     = sexInterrogateItem;
			this.issInterrogateItem     = issInterrogateItem;
			this.expDateInterrogateItem = expDateInterrogateItem;
			this.faceInterrogateItem    = faceInterrogateItem;
		}
		
		@Override
		public String toString()
		{
			return 
				  "nation :\n---\n" + nation + "\n---" +
				"\nid     : " + id +
				"\nname   : " + name +
				"\ndob    : " + DateUtil.toString(dob) +
				"\nsex    : " + sex +
				"\niss    : " + iss +
				"\nexpDate: " + DateUtil.toString(expDate) +
				"\nface   :\n---\n" + face + "\n---";
		}
	}
	
	
	public final InterrogateItem passportInterrogateItem;
	private PassportData data;
	
	public Passport(Desk desk, int xRelToDesk, int yRelToDesk)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		this.passportInterrogateItem = new InterrogateItem(this, this, 65, 20, "Passport.");
	}
	
	public PassportData getData()
	{
		if (data == null)
			throw new AssertionError("Attempted to get passport data before it was read.");
		
		return data;
	}
	
	
	
	public void read()
	{
		// take a screenshot of the passport
		BufferedImage screenshot = takeScreenshot();
		
		int argb = screenshot.getRGB(2 * WorkView.SCALE, 2 * WorkView.SCALE);
		Nation nation = Nation.fromPassportColor(argb);
		
		if (nation == null)
			throw new AssertionError("Invalid color for passport: " + argb + ".");
		
		// find the passport template for the nation
		PassportTemplate template = null;
		for (int i = 0; i < passportTemplates.length; ++i)
		{
			if (passportTemplates[i].nation == nation)
			{
				template = passportTemplates[i];
				break;
			}
		}
		
		if (template == null)
			throw new AssertionError("No passport template found for " + nation.name + ".");
		
		
		// read the passport
		// id
		String id = template.idTextReader.readTextNear(screenshot, template.idTextPoint, WorkView.SCALE);
		if (id.length() == 0)
			throw new AssertionError("Unbale to read passport ID.");
		
		
		// name
		String nameStr = template.nameTextReader.readTextNear(screenshot, template.nameTextPoint, WorkView.SCALE);
		if (nameStr.length() == 0)
			throw new AssertionError("Unable to read passport name.");
		
		Name name = Name.fromFullName(nameStr);
		if (name == null)
			throw new AssertionError("Invalid name read \"" + nameStr + "\".");
		
		
		// dob
		String dobStr = template.dobTextReader.readTextNear(screenshot, template.dobTextPoint, WorkView.SCALE);
		if (dobStr.length() == 0)
			throw new AssertionError("Unable to read passport DOB.");
		
		Date dob;
		try
		{
			dob = DateUtil.parseDate(dobStr);
		}
		catch (IllegalArgumentException | ParseException e)
		{
			throw new AssertionError("Invalid passport DOB \"" + dobStr + "\".", e);
		}
		
		
		// sex
		String sexStr = template.sexTextReader.readTextNear(screenshot, template.sexTextPoint, WorkView.SCALE, 1);
		if (sexStr.length() == 0)
			throw new AssertionError("Unable to read passport sex.");
		
		Sex sex = Sex.fromString(sexStr);
		if (sex == null)
			throw new AssertionError("Invalid passport sex \"" + sexStr + "\".");
		
		
		// iss
		String iss = template.issTextReader.readTextNear(screenshot, template.issTextPoint, WorkView.SCALE);
		if (iss.length() == 0)
			throw new AssertionError("Unable to read passport ISS.");
		
		
		// expiration date
		String expDateStr = template.expDateTextReader.readTextNear(screenshot, template.expDateTextPoint, WorkView.SCALE);
		if (expDateStr.length() == 0)
			throw new AssertionError("Unable to read passport expiration date.");
		
		Date expDate;
		try
		{
			expDate = DateUtil.parseDate(expDateStr);
		}
		catch (IllegalArgumentException | ParseException e)
		{
			throw new AssertionError("Invalid passport expiration date \"" + expDateStr + "\".", e);
		}
		
		
		this.data = new PassportData(
			nation,
			
			id,
			name,
			dob,
			sex,
			iss,
			expDate,
			null,
			
			new InterrogateItem(this, this, template.idInterrogatePoint     .x, template.idInterrogatePoint     .y, nation.name + " passport ID."             ),
			new InterrogateItem(this, this, template.nameInterrogatePoint   .x, template.nameInterrogatePoint   .y, nation.name + " passport name."           ),
			new InterrogateItem(this, this, template.dobInterrogatePoint    .x, template.dobInterrogatePoint    .y, nation.name + " passport DOB."            ),
			new InterrogateItem(this, this, template.sexInterrogatePoint    .x, template.sexInterrogatePoint    .y, nation.name + " passport sex."            ),
			new InterrogateItem(this, this, template.issInterrogatePoint    .x, template.issInterrogatePoint    .y, nation.name + " passport ISS."            ),
			new InterrogateItem(this, this, template.expDateInterrogatePoint.x, template.expDateInterrogatePoint.y, nation.name + " passport expiration date."),
			new InterrogateItem(this, this, template.faceInterrogatePoint   .x, template.faceInterrogatePoint   .y, nation.name + " passport face."           )
		);
		
		System.out.println("Read passport:\n" + this);
	}
	
	
	// problems
	// from assets/data/Errors.text:# Passport
	public static abstract class PassportError extends DocumentError
	{
		private static final long serialVersionUID = 1L;

		public PassportError(Passport passport, String message, InterrogateItemSet interrogateItems)
		{
			super("Error with passport. " + message + "\n" + passport, interrogateItems);
		}
	}
	
	// Passport-WrongFaceClear/Passport-WrongFaceError
	public static class PassportWrongFaceError extends PassportError
	{
		private static final long serialVersionUID = 1L;

		public PassportWrongFaceError(Passport passport)
		{
			super(passport, "Wrong face.", new InterrogateItemSet(passport.getData().faceInterrogateItem, WorkView.boothWindow.interrogateItem));
		}
	}
	
	// Passport-WrongIssuingCity
	public static class PassportInvalidISSError extends PassportError
	{
		private static final long serialVersionUID = 1L;

		public PassportInvalidISSError(Passport passport)
		{
			super(passport, "Invalid ISS.", new InterrogateItemSet(passport.getData().issInterrogateItem, WorkView.desk.getRuleBook().getPageForNation(passport.getData().nation).issuingCitiesIterrogateItem));
		}
	}
	
	// Passport-WrongExpiration
	public static class PassportExpiredError extends PassportError
	{
		private static final long serialVersionUID = 1L;

		public PassportExpiredError(Passport passport)
		{
			super(passport, "Expired.", new InterrogateItemSet(passport.getData().expDateInterrogateItem, WorkView.deskClock.interrogateItem));
		}
	}
	
	// Passport-WrongGender
	public static class PassportWrongGenderError extends PassportError
	{
		private static final long serialVersionUID = 1L;

		public PassportWrongGenderError(Passport passport)
		{
			super(passport, "Wrong Gender.", new InterrogateItemSet(passport.getData().sexInterrogateItem, WorkView.boothWindow.interrogateItem));
		}
	}
	
	
	public void verify() throws PassportError
	{
		if (data == null)
			throw new AssertionError("Attempted to verify passport before it was read.");
		
		// make sure iss is valid for nation
		boolean validISS = false;
		for (int i = 0; i < data.nation.passportIssuingCities.length; ++i)
		{
			if (data.iss.equalsIgnoreCase(data.nation.passportIssuingCities[i]))
			{
				validISS = true;
				break;
			}
		}
		
		if (!validISS)
			throw new PassportInvalidISSError(this);
		
		// make sure it is not expired
		if (WorkView.deskClock.isExpired(data.expDate))
			throw new PassportExpiredError(this);
	}
	
	@Override
	public String toString()
	{
		return data.toString();
	}
}
