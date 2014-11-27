package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import net.awesomebox.papersthanks.utils.ImageUtil;

public class Nation
{
	private static final int PASSPORT_TEMPLATE_ID_INTERROGATE_ARGB      = new Color(237, 28,  36 ).getRGB(); // red
	private static final int PASSPORT_TEMPLATE_NAME_INTERROGATE_ARGB    = new Color(255, 127, 39 ).getRGB(); // orange
	private static final int PASSPORT_TEMPLATE_DOB_INTERROGATE_ARGB     = new Color(255, 242, 0  ).getRGB(); // yellow
	private static final int PASSPORT_TEMPLATE_SEX_INTERROGATE_ARGB     = new Color(34,  177, 76 ).getRGB(); // green
	private static final int PASSPORT_TEMPLATE_ISS_INTERROGATE_ARGB     = new Color(0,   162, 232).getRGB(); // blue
	private static final int PASSPORT_TEMPLATE_EXPDATE_INTERROGATE_ARGB = new Color(63,  72,  204).getRGB(); // blue-purple
	private static final int PASSPORT_TEMPLATE_FACE_INTERROGATE_ARGB    = new Color(163, 73,  164).getRGB(); // purple
	
	
	
	public final String          name;
	public final int             passportColorARGB;
	public final String[]        passportIssuingCities;
	public final String[]        districts;
	public final ImageDetector[] diplomaticSealDetectors;
	
	
	public final int passportIDInterrogateItemX,      passportIDInterrogateItemY;
	public final int passportNameInterrogateItemX,    passportNameInterrogateItemY;
	public final int passportDOBInterrogateItemX,     passportDOBInterrogateItemY;
	public final int passportSexInterrogateItemX,     passportSexInterrogateItemY;
	public final int passportISSInterrogateItemX,     passportISSInterrogateItemY;
	public final int passportExpDateInterrogateItemX, passportExpDateInterrogateItemY;
	public final int passportFaceInterrogateItemX,    passportFaceInterrogateItemY;
	
	
	private Nation(
		String   name,
		String[] passportIssuingCities,
		String[] districts
	)
	{
		this.name                  = name;
		this.passportIssuingCities = passportIssuingCities;
		this.districts             = districts;
		
		
		diplomaticSealDetectors = new ImageDetector[0];
		passportColorARGB = new Color(255, 0, 0).getRed();
		
		// TODO: move to Passport
		// get passport diplomatic seal detectors
		/*String dir = ImageUtil.IMAGES_DIR + ImageUtil.NATION_IMAGES_SUBDIR + this.name + "/" + ImageUtil.DIPLOMATIC_SEAL_IMAGES_SUBDIR + ImageUtil.DETECTOR_IMAGES_SUBDIR;
		ArrayList<BufferedImage> diplomaticSealDetectorImages = ImageUtil.getDirectoryImages(dir);
		
		diplomaticSealDetectors = new ImageDetector[diplomaticSealDetectorImages.size()];
		for (int i = 0; i < diplomaticSealDetectorImages.size(); ++i)
			diplomaticSealDetectors[i] = ImageDetector.fromDetectorImage(diplomaticSealDetectorImages.get(i));
		
		
		// get passport template
		String filepath = ImageUtil.IMAGES_DIR + ImageUtil.NATION_IMAGES_SUBDIR + this.name + "/" + this.name + " Passport.png";
		BufferedImage passportTemplateImage = ImageUtil.readImage(filepath);
		
		int[] pixelData = ((DataBufferInt)passportTemplateImage.getRaster().getDataBuffer()).getData();
		
		
		// get passport color
		passportColorARGB = pixelData[0];
		
		// get passport interrogate items
		*/
		int _passportIDInterrogateItemX      = -1, _passportIDInterrogateItemY      = -1;
		int _passportNameInterrogateItemX    = -1, _passportNameInterrogateItemY    = -1;
		int _passportDOBInterrogateItemX     = -1, _passportDOBInterrogateItemY     = -1;
		int _passportSexInterrogateItemX     = -1, _passportSexInterrogateItemY     = -1;
		int _passportISSInterrogateItemX     = -1, _passportISSInterrogateItemY     = -1;
		int _passportExpDateInterrogateItemX = -1, _passportExpDateInterrogateItemY = -1;
		int _passportFaceInterrogateItemX    = -1, _passportFaceInterrogateItemY    = -1;
		/*
		for (int y = 0; y < passportTemplateImage.getHeight(); ++y)
		{
			for (int x = 0; x < passportTemplateImage.getWidth(); ++x)
			{
				int pixel = pixelData[y * passportTemplateImage.getHeight() + x];
				
				if (pixel == PASSPORT_TEMPLATE_ID_INTERROGATE_ARGB)
				{
					_passportIDInterrogateItemX = x;
					_passportIDInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_NAME_INTERROGATE_ARGB)
				{
					_passportNameInterrogateItemX = x;
					_passportNameInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_DOB_INTERROGATE_ARGB)
				{
					_passportDOBInterrogateItemX = x;
					_passportDOBInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_SEX_INTERROGATE_ARGB)
				{
					_passportSexInterrogateItemX = x;
					_passportSexInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_ISS_INTERROGATE_ARGB)
				{
					_passportISSInterrogateItemX = x;
					_passportISSInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_EXPDATE_INTERROGATE_ARGB)
				{
					_passportExpDateInterrogateItemX = x;
					_passportExpDateInterrogateItemY = y;
				}
				else if (pixel == PASSPORT_TEMPLATE_FACE_INTERROGATE_ARGB)
				{
					_passportFaceInterrogateItemX = x;
					_passportFaceInterrogateItemY = y;
				}
			}
		}
		
		if (_passportIDInterrogateItemX      == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportNameInterrogateItemX    == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportDOBInterrogateItemX     == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportSexInterrogateItemX     == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportISSInterrogateItemX     == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportExpDateInterrogateItemX == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		if (_passportFaceInterrogateItemX    == -1) throw new AssertionError("id interrogate point missing from teamplte for " + this.name);
		*/
		passportIDInterrogateItemX      = _passportIDInterrogateItemX;      passportIDInterrogateItemY      = _passportIDInterrogateItemY;
		passportNameInterrogateItemX    = _passportNameInterrogateItemX;    passportNameInterrogateItemY    = _passportNameInterrogateItemY;
		passportDOBInterrogateItemX     = _passportDOBInterrogateItemX;     passportDOBInterrogateItemY     = _passportDOBInterrogateItemY;
		passportSexInterrogateItemX     = _passportSexInterrogateItemX;     passportSexInterrogateItemY     = _passportSexInterrogateItemY;
		passportISSInterrogateItemX     = _passportISSInterrogateItemX;     passportISSInterrogateItemY     = _passportISSInterrogateItemY;
		passportExpDateInterrogateItemX = _passportExpDateInterrogateItemX; passportExpDateInterrogateItemY = _passportExpDateInterrogateItemY;
		passportFaceInterrogateItemX    = _passportFaceInterrogateItemX;    passportFaceInterrogateItemY    = _passportFaceInterrogateItemY;
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
			if (nations[i].passportColorARGB == argb)
				return nations[i];
		}
		
		return null;
	}
	
	
	
	// from assets/data/Facts.xml:<nations>
	public static final Nation Antegria = new Nation(
		"Antegria",
		new String[] {"St. Marmero", "Glorian", "Outer Grouse"},
		new String[] {}
	);
	
	public static final Nation Arstotzka = new Nation(
		"Arstotzka",
		new String[] {"Orvech Vonor", "East Grestin", "Paradizna"},
		new String[] {"Altan", "Vescillo", "Burnton", "Octovalis", "Gennistora", "Lendiforma", "Wozenfield", "Fardesto"}
	);
	
	public static final Nation Impor = new Nation(
		"Impor",
		new String[] {"Enkyo", "Haihan", "Tsunkeido"},
		new String[] {}
	);
	
	public static final Nation Kolechia = new Nation(
		"Kolechia",
		new String[] {"Yurko City", "Vendor", "West Grestin"},
		new String[] {}
	);
	
	public static final Nation Obristan = new Nation(
		"Obristan",
		new String[] {"Skal", "Lorndaz", "Mergerous"},
		new String[] {}
	);
	
	public static final Nation Republia = new Nation(
		"Republia",
		new String[] {"True Glorian", "Lesrenadi", "Bostan"},
		new String[] {}
	);
	
	public static final Nation UnitedFederation = new Nation(
		"United Federation",
		new String[] {"Great Rapid", "Shingleton", "Korista City"},
		new String[] {}
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
