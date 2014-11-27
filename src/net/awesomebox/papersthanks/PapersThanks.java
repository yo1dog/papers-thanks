package net.awesomebox.papersthanks;

import java.awt.AWTException;

import net.awesomebox.papersthanks.documents.Document.DocumentError;
import net.awesomebox.papersthanks.documents.DocumentOrganizer;
import net.awesomebox.papersthanks.documents.Passport;
import net.awesomebox.papersthanks.documents.rulebook.RuleBook;
import net.awesomebox.papersthanks.documents.rulebook.RuleBookDocumetLinkType;
import net.awesomebox.papersthanks.ui.WorkView;

public class PapersThanks
{
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		RuleBook ruleBook = new RuleBook(new Rule[] {}, new RuleBookDocumetLinkType[] {});
		WorkView.desk.setRuleBook(ruleBook);
		
		Passport passport = new Passport(
			Nation.Arstotzka.passportColorARGB,
			"some-id",
			"some name",
			"11.21.98",
			"m",
			"invalid iss",
			"11.22.98");
		
		DocumentOrganizer.placeDocument(passport, 0, 0);
		DocumentOrganizer.placeDocument(ruleBook, 0, 0);
		
		DocumentError error = passport.verify();
		
		System.out.println("Item 1 Click Sequence");
		System.out.println(error.interrogateItems.item1.clickTo());
		
		System.out.println();
		System.out.println("---------------------");
		System.out.println("Item 2 Click Sequence");
		System.out.println(error.interrogateItems.item2.clickTo());
	}
}