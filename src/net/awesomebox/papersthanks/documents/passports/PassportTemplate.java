package net.awesomebox.papersthanks.documents.passports;

import java.awt.Point;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.ui.TextReader;

abstract class PassportTemplate
{
	public final Nation nation;
	
	final Point idTextPoint;
	final Point nameTextPoint;
	final Point dobTextPoint;
	final Point sexTextPoint;
	final Point issTextPoint;
	final Point expDateTextPoint;
	
	final TextReader idTextReader;
	final TextReader nameTextReader;
	final TextReader dobTextReader;
	final TextReader sexTextReader;
	final TextReader issTextReader;
	final TextReader expDateTextReader;
	
	final Point idInterrogatePoint;
	final Point nameInterrogatePoint;
	final Point dobInterrogatePoint;
	final Point sexInterrogatePoint;
	final Point issInterrogatePoint;
	final Point expDateInterrogatePoint;
	final Point faceInterrogatePoint;
	
	PassportTemplate(
		Nation nation,
		
		Point idTextPoint,
		Point nameTextPoint,
		Point dobTextPoint,
		Point sexTextPoint,
		Point issTextPoint,
		Point expDateTextPoint,
		
		TextReader idTextReader,
		TextReader nameTextReader,
		TextReader dobTextReader,
		TextReader sexTextReader,
		TextReader issTextReader,
		TextReader expDateTextReader,
		
		Point idInterrogatePoint,
		Point nameInterrogatePoint,
		Point dobInterrogatePoint,
		Point sexInterrogatePoint,
		Point issInterrogatePoint,
		Point expDateInterrogatePoint,
		Point faceInterrogatePoint
	)
	{
		this.nation = nation;
		
		this.idTextPoint      = idTextPoint;
		this.nameTextPoint    = nameTextPoint;
		this.dobTextPoint     = dobTextPoint;
		this.sexTextPoint     = sexTextPoint;
		this.issTextPoint     = issTextPoint;
		this.expDateTextPoint = expDateTextPoint;
		
		this.idTextReader      = idTextReader;
		this.nameTextReader    = nameTextReader;
		this.dobTextReader     = dobTextReader;
		this.sexTextReader     = sexTextReader;
		this.issTextReader     = issTextReader;
		this.expDateTextReader = expDateTextReader;
		
		this.idInterrogatePoint      = idInterrogatePoint      != null? idInterrogatePoint      : new Point(idTextPoint     .x + 5, idTextPoint     .y + 5);
		this.nameInterrogatePoint    = nameInterrogatePoint    != null? nameInterrogatePoint    : new Point(nameTextPoint   .x + 5, nameTextPoint   .y + 5);
		this.dobInterrogatePoint     = dobInterrogatePoint     != null? dobInterrogatePoint     : new Point(dobTextPoint    .x + 5, dobTextPoint    .y + 5);
		this.sexInterrogatePoint     = sexInterrogatePoint     != null? sexInterrogatePoint     : new Point(sexTextPoint    .x + 5, sexTextPoint    .y + 5);
		this.issInterrogatePoint     = issInterrogatePoint     != null? issInterrogatePoint     : new Point(issTextPoint    .x + 5, issTextPoint    .y + 5);
		this.expDateInterrogatePoint = expDateInterrogatePoint != null? expDateInterrogatePoint : new Point(expDateTextPoint.x + 5, expDateTextPoint.y + 5);
		this.faceInterrogatePoint    = faceInterrogatePoint;
	}
}
