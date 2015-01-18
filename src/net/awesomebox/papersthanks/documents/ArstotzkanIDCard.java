package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.ui.Desk;
import net.awesomebox.papersthanks.utils.HeightUtil;
import net.awesomebox.papersthanks.utils.WeightUtil;

public class ArstotzkanIDCard extends Document
{
	public static final int WIDTH  = 126;
	public static final int HEIGHT = 71;
	
	public final String district;
	public final Name   name;
	public final Date   dob;
	public final int    heightCM;
	public final int    weightKG;
	
	ArstotzkanIDCard(
		Desk desk,
		int xRelToDesk,
		int yRelToDesk,
		
		String district,
		String nameStr,
		String dobStr,
		String heightStr,
		String weightStr
	)
	{
		super(desk, xRelToDesk, yRelToDesk, WIDTH, HEIGHT);
		
		this.district = district;
		this.name     = Name.fromFullName(nameStr);
		this.dob      = new Date();//DateUtil.parseDate(dobStr);
		this.heightCM = HeightUtil.parseHeight(heightStr);
		this.weightKG = WeightUtil.parseHeight(weightStr);
	}
	
	
	public boolean verify()
	{
		if (district == null || name == null || dob == null || heightCM < 1 || weightKG < 1)
			return false;
		
		// make sure the district is a valid Arstotzkan district
		boolean validDistrict = false;
		
		for (int i = 0; i < Nation.Arstotzka.districts.length; ++i)
		{
			if (district.equalsIgnoreCase(Nation.Arstotzka.districts[i]))
			{
				validDistrict = true;
				break;
			}
		}
		
		if (!validDistrict)
			return false;
		
		
		return true;
	}
}
