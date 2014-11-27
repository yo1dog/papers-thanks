package net.awesomebox.papersthanks.documents;

import java.util.Date;

import net.awesomebox.papersthanks.Nation;
import net.awesomebox.papersthanks.Name;
import net.awesomebox.papersthanks.utils.DateUtil;
import net.awesomebox.papersthanks.utils.HeightUtil;
import net.awesomebox.papersthanks.utils.WeightUtil;

public class ArstotzkanIDCard extends Document
{
	public final String district;
	public final Name   name;
	public final Date   dob;
	public final int    heightCM;
	public final int    weightKG;
	
	ArstotzkanIDCard(
		String district,
		String nameStr,
		String dobStr,
		String heightStr,
		String weightStr
	)
	{
		super(126, 71);
		
		this.district = district;
		this.name     = Name.fromFullName(nameStr);
		this.dob      = DateUtil.parseDate(dobStr);
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
