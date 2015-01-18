package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.awesomebox.papersthanks.utils.ImageUtil;

public class Nation
{
	private static final int PASSPORT_COLOR_TOLLERANCE = 10;
	
	public final String          name;
	public final Color           passportColor;
	public final String[]        passportIssuingCities;
	public final String[]        districts;
	public final ImageDetector[] diplomaticSealDetectors;
	
	private Nation(
		String   name,
		String[] passportIssuingCities,
		String[] districts,
		Color    passportColor
	)
	{
		this.name                  = name;
		this.passportIssuingCities = passportIssuingCities;
		this.districts             = districts;
		this.passportColor         = passportColor;
		
				
		// TODO: move to Passport
		String pathName = name.substring(0, 1).toLowerCase() + name.substring(1).replace(" ", "");
		
		// get passport diplomatic seal detectors
		String dir = ImageUtil.IMAGES_DIR + "nations/" + pathName + "/diplomaticSeals/";
		ArrayList<BufferedImage> diplomaticSealDetectorImages = ImageUtil.getDirectoryImages(dir);
		
		diplomaticSealDetectors = new ImageDetector[diplomaticSealDetectorImages.size()];
		for (int i = 0; i < diplomaticSealDetectorImages.size(); ++i)
			diplomaticSealDetectors[i] = ImageDetector.fromDetectorImage(diplomaticSealDetectorImages.get(i));
	}
	
	
	@Override
	public String toString()
	{
		String str = "name                 : " + name;
			
		str += "\npassportIssuingCities: ";
		for (int i = 0; i < passportIssuingCities.length; ++i)
		{
			if (i > 0)
				str += ", ";
			
			str += passportIssuingCities[i];
		}
		
		str += "\ndistricts            : ";
		for (int i = 0; i < passportIssuingCities.length; ++i)
		{
			if (i > 0)
				str += ", ";
			
			str += passportIssuingCities[i];
		}
		
		return str;
	}
	
	
	public static Nation fromNationName(String nationName)
	{
		for (int i = 0; i < nations.length; ++i)
		{
			if (nations[i].name.equalsIgnoreCase(nationName))
				return nations[i];
		}
		
		return null;
	}
	
	public static Nation fromPassportColor(int argb)
	{
		for (int i = 0; i < nations.length; ++i)
		{
			double diff = ImageUtil.calculateColorDifference(argb, nations[i].passportColor.getRGB());
			if (diff <= PASSPORT_COLOR_TOLLERANCE)
				return nations[i];
		}
		
		return null;
	}
	
	
	
	// from assets/data/Facts.xml:<nations>
	public static final Nation Antegria = new Nation(
		"Antegria",
		new String[] {"St. Marmero", "Glorian", "Outer Grouse"},
		new String[] {},
		new Color(49, 77, 33)
	);
	
	public static final Nation Arstotzka = new Nation(
		"Arstotzka",
		new String[] {"Orvech Vonor", "East Grestin", "Paradizna"},
		new String[] {"Altan", "Vescillo", "Burnton", "Octovalis", "Gennistora", "Lendiforma", "Wozenfield", "Fardesto"},
		new Color(59, 72, 59)
	);
	
	public static final Nation Impor = new Nation(
		"Impor",
		new String[] {"Enkyo", "Haihan", "Tsunkeido"},
		new String[] {},
		new Color(102, 31, 9)
	);
	
	public static final Nation Kolechia = new Nation(
		"Kolechia",
		new String[] {"Yurko City", "Vedor", "West Grestin"},
		new String[] {},
		new Color(85, 37, 63)
	);
	
	public static final Nation Obristan = new Nation(
		"Obristan",
		new String[] {"Skal", "Lorndaz", "Mergerous"},
		new String[] {},
		new Color(133, 8, 20)
	);
	
	public static final Nation Republia = new Nation(
		"Republia",
		new String[] {"True Glorian", "Lesrenadi", "Bostan"},
		new String[] {},
		new Color(73, 42, 28)
	);
	
	public static final Nation UnitedFederation = new Nation(
		"United Federation",
		new String[] {"Great Rapid", "Shingleton", "Korista City"},
		new String[] {},
		new Color(35, 30, 85)
	);
	
	
	
	public static final Nation[] nations = new Nation[] {
		Antegria,
		Arstotzka,
		Impor,
		Kolechia,
		Obristan,
		Republia,
		UnitedFederation
	};
}
