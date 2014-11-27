package net.awesomebox.papersthanks.documents;

import java.awt.image.BufferedImage;
import java.util.Date;

import net.awesomebox.papersthanks.FingerPrint;
import net.awesomebox.papersthanks.ui.WorkView;
import net.awesomebox.papersthanks.utils.DateUtil;
import net.awesomebox.papersthanks.utils.HeightUtil;
import net.awesomebox.papersthanks.utils.WeightUtil;

public class IDSupplement extends Document
{
	public final int         heightCM;
	public final int         weightKG;
	public final String      description;
	public final FingerPrint thumbPrint;
	public final Date        expDate;
	
	IDSupplement(
		String        heightStr,
		String        weightStr,
		String        description,
		BufferedImage thumbPrintImage,
		String        expDateStr
	)
	{
		super(90, 150);
		
		this.heightCM    = HeightUtil.parseHeight(heightStr);
		this.weightKG    = WeightUtil.parseHeight(weightStr);
		this.description = description;
		this.thumbPrint  = FingerPrint.fromImage(FingerPrint.Digit.THUMB, thumbPrintImage);
		this.expDate     = DateUtil.parseDate(expDateStr);
	}
	
	public boolean verify()
	{
		if (heightCM < 0 || weightKG < 0 || description == null || thumbPrint == null || expDate == null)
			return false;
		
		// check if expired
		if (WorkView.deskClock.isExpired(expDate)) return false;
		
		return true;
	}
}
