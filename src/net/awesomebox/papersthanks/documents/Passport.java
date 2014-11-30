package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.Sex;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.ui.InterrogateItem;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.ui.InterrogateItem.InterrogateItemSet;
import net.awesomebox.papersthanks.utils.DateUtil;

public class Passport extends Document
{
	public static final int WIDTH  = 130;
	public static final int HEIGHT = 162;
	
	public final Nation nation;
	public final String	id;
	public final Name   name;
	public final Date   dob;
	public final Sex    sex;
	public final String	iss;
	public final Date   expDate;
	public final Face   face;
	
	
	public final InterrogateItem idInterrogateItem;
	public final InterrogateItem nameInterrogateItem;
	public final InterrogateItem dobInterrogateItem;
	public final InterrogateItem sexInterrogateItem;
	public final InterrogateItem issInterrogateItem;
	public final InterrogateItem expDateInterrogateItem;
	public final InterrogateItem faceInterrogateItem;
	
	
	Passport(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk,
		
		int    colorARGB,
		String id,
		String nameStr,
		String dobStr,
		String sexStr,
		String iss,
		String expDateStr
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		this.nation  = Nation.fromPassportColor(colorARGB);
		this.id      = id;
		this.name    = Name.fromFullName(nameStr);
		this.dob     = DateUtil.parseDate(dobStr);
		this.sex     = Sex.fromString(sexStr);
		this.iss     = iss;
		this.expDate = DateUtil.parseDate(expDateStr);
		this.face    = null;
		
		
		this.idInterrogateItem      = new InterrogateItem(this, nation.passportIDInterrogateItemX,      nation.passportIDInterrogateItemY,      "Passport ID.");
		this.nameInterrogateItem    = new InterrogateItem(this, nation.passportNameInterrogateItemX,    nation.passportNameInterrogateItemY,    "Passport name.");
		this.dobInterrogateItem     = new InterrogateItem(this, nation.passportDOBInterrogateItemX,     nation.passportDOBInterrogateItemY,     "Passport DOB.");
		this.sexInterrogateItem     = new InterrogateItem(this, nation.passportSexInterrogateItemX,     nation.passportSexInterrogateItemY,     "Passport sex.");
		this.issInterrogateItem     = new InterrogateItem(this, nation.passportISSInterrogateItemX,     nation.passportISSInterrogateItemY,     "Passport ISS.");
		this.expDateInterrogateItem = new InterrogateItem(this, nation.passportExpDateInterrogateItemX, nation.passportExpDateInterrogateItemY, "Passport expiration date.");
		this.faceInterrogateItem    = new InterrogateItem(this, nation.passportFaceInterrogateItemX,    nation.passportFaceInterrogateItemY,    "Passport face.");
	}
	
	
	// problems
	// from assets/data/Errors.text:# Passport
	public static abstract class PassportError extends DocumentError {
		public PassportError(Passport passport, String message, InterrogateItemSet interrogateItems)
		{
			super("Error with passport. " + message + "\n" + passport, interrogateItems);
		}
	}
	
	// Passport-WrongFaceClear/Passport-WrongFaceError
	public static class PassportWrongFaceError extends PassportError { public PassportWrongFaceError(Passport passport) {
		super(passport, "Wrong face.", new InterrogateItemSet(passport.faceInterrogateItem, WorkView.boothWindow.interrogateItem));
	}}
	
	// Passport-WrongIssuingCity
	public static class PassportInvalidISSError extends PassportError { public PassportInvalidISSError(Passport passport) {
		super(passport, "Invalid ISS.", new InterrogateItemSet(passport.issInterrogateItem, WorkView.desk.getRuleBook().getPageForNation(passport.nation).issuingCitiesIterrogateItem));
	}}
	
	// Passport-WrongExpiration
	public static class PassportExpiredError extends PassportError { public PassportExpiredError(Passport passport) {
		super(passport, "Expired.", new InterrogateItemSet(passport.expDateInterrogateItem, WorkView.deskClock.interrogateItem));
	}}
	
	// Passport-WrongGender
	public static class PassportWrongGenderError extends PassportError { public PassportWrongGenderError(Passport passport) {
		super(passport, "Wrong Gender.", new InterrogateItemSet(passport.sexInterrogateItem, WorkView.deskClock.interrogateItem));
	}}
	
	public DocumentError verify()
	{
		/*if (nation == null || id == null || name == null || dob == null || sex == null || iss == null || expDate == null)
			return false;
		
		// check if expired
		if (WorkView.deskClock.isExpired(expDate))
			return new DocumentError()
		*/
		
		// make sure iss is valid for nation
		boolean validISS = false;
		for (int i = 0; i < nation.passportIssuingCities.length; ++i)
		{
			if (iss.equalsIgnoreCase(nation.passportIssuingCities[i]))
			{
				validISS = true;
				break;
			}
		}
		
		if (!validISS)
			return new PassportInvalidISSError(this);
		
		/*
		// crude test to make sure the person is at least 18
		if (WorkView.deskClock.getDate().getTime() - dob.getTime() < 18 * 365 * 24 * 60 * 60 * 1000)
			return false;
		
		
		return true;*/
		return null;
	}
	
	@Override
	public String toString()
	{
		return
			  "nation :\n---" + nation + "\n---" +
			"\nid     : " + id +
			"\nname   : " + name +
			"\ndob    : " + DateUtil.toString(dob) +
			"\nsex    : " + sex +
			"\niss    : " + iss +
			"\nexpDate: " + DateUtil.toString(expDate) +
			"\nface   :\n---" + face + "\n---";
	}
}
