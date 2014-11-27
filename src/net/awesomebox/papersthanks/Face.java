package net.awesomebox.papersthanks;

import java.awt.image.BufferedImage;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class Face
{
	public final String        id;
	public final Sex           sex;
	public final int           ageYears;
	public final int           hairHeightCM;
	public final String[]      descriptions;
	public final ImageDetector imageDetector;
	
	private Face(
		String   id,
		Sex      sex,
		int      ageYears,
		int      hairHeightCM,
		String[] descriptions)
	{
		this.id           = id;
		this.sex          = sex;
		this.ageYears     = ageYears;
		this.hairHeightCM = hairHeightCM;
		this.descriptions = descriptions;
		
		
		// image detector
		String filepath = ImageUtil.IMAGES_DIR + ImageUtil.FACE_IMAGES_SUBDIR + this.id + "/" + this.id + " Detector.png";
		imageDetector = ImageDetector.fromDetectorImage(ImageUtil.readImage(filepath));
	}
	
	
	@Override
	public String toString()
	{
		String str =
			  "id          : " + id +
			"\nsex         : " + sex +
			"\nageYears    : " + ageYears +
			"\nhairHeightCM: " + hairHeightCM;
		
		str += "\ndescriptions: ";
		for (int i = 0; i < descriptions.length; ++i)
		{
			if (i > 0)
				str += ", ";
			
			str += descriptions[i];
		}
		
		return str;
	}
	
	public static Face fromImage(BufferedImage faceImage)
	{
		// TODO: detecting faces from images
		return null;
	}
	
	
	// from assets/data/Faces.xml
	public static final Face[] faces = new Face[]
	{
		new Face("M0-0", Sex.MALE,   45, 0,  new String[] {"Short Hair"         , "No Facial Hair"}),
		new Face("M0-1", Sex.MALE,   70, 5,  new String[] {"Bald"                                 }),
		new Face("M0-2", Sex.MALE,   38, 4,  new String[] {"Cropped Hair"       , "Glasses"       }),
		new Face("M0-3", Sex.MALE,   36, 6,  new String[] {"Dark Hair"          , "Mustache"      }),
		new Face("M1-0", Sex.MALE,   40, 4,  new String[] {"Beard"              , "Mustache"      }),
		new Face("M1-1", Sex.MALE,   29, 4,  new String[] {"Goatee"             , "Perfect Vision"}),
		new Face("M1-2", Sex.MALE,   35, 5,  new String[] {"Short Cropped Hair"                   }),
		new Face("M1-3", Sex.MALE,   43, 2,  new String[] {"Balding"                              }),
		new Face("M2-0", Sex.MALE,   40, 4,  new String[] {"Balding"                              }),
		new Face("M2-1", Sex.MALE,   29, 2,  new String[] {"Short Straight Hair"                  }),
		new Face("M2-2", Sex.MALE,   35, 7,  new String[] {"Short Light Hair"                     }),
		new Face("M2-3", Sex.MALE,   43, 10, new String[] {"Widow's Peak"       , "Mustache"      }),
		new Face("M3-0", Sex.MALE,   35, 8,  new String[] {"Tall Forehead"                        }),
		new Face("M3-1", Sex.MALE,   40, 12, new String[] {"Killer Sideburns"                     }),
		new Face("M3-2", Sex.MALE,   46, 3,  new String[] {"Bobbed Hair"                          }),
		new Face("M3-3", Sex.MALE,   30, 6,  new String[] {"Short Curly Hair"   , "Mustache"      }),
		new Face("M4-0", Sex.MALE,   37, 0,  new String[] {"Bald"                                 }),
		new Face("M4-1", Sex.MALE,   28, 18, new String[] {"Mohawk"                               }),
		new Face("M4-2", Sex.MALE,   52, 4,  new String[] {"Short Curly Hair"                     }),
		new Face("M4-3", Sex.MALE,   44, 4,  new String[] {"Unkempt Curly Hair" , "Mustache"      }),
		
		new Face("F0-0", Sex.FEMALE, 25, 10, new String[] {"Wavy Hair"                            }),
		new Face("F0-1", Sex.FEMALE, 68, 3,  new String[] {"Small Head"         , "Glasses"       }),
		new Face("F0-2", Sex.FEMALE, 39, 7,  new String[] {"Overweight"                           }),
		new Face("F0-3", Sex.FEMALE, 27, 6,  new String[] {"Bobbed Hair"                          }),
		new Face("F1-0", Sex.FEMALE, 20, 3,  new String[] {"Straight Hair"                        }),
		new Face("F1-1", Sex.FEMALE, 30, 5,  new String[] {"Bobbed Hair"                          }),
		new Face("F1-2", Sex.FEMALE, 22, 2,  new String[] {"Short Hair"                           }),
		new Face("F1-3", Sex.FEMALE, 65, 0,  new String[] {"Very Short Hair"                      }),
		new Face("F2-0", Sex.FEMALE, 19, 4,  new String[] {"Short Hair"                           }),
		new Face("F2-1", Sex.FEMALE, 36, 2,  new String[] {"Overweight"                           }),
		new Face("F2-2", Sex.FEMALE, 31, 5,  new String[] {"Bobbed Hair"                          }),
		new Face("F2-3", Sex.FEMALE, 40, 13, new String[] {"Short Curly Hair"                     }),
		new Face("F3-0", Sex.FEMALE, 25, 9,  new String[] {"Curly Bobbed Hair"                    }),
		new Face("F3-1", Sex.FEMALE, 42, 5,  new String[] {"Short Wavy Hair"    , "Glasses"       }),
		new Face("F3-2", Sex.FEMALE, 20, 8,  new String[] {"Bobbed Hair"                          }),
		new Face("F3-3", Sex.FEMALE, 67, 14, new String[] {"Long Hair"                            }),
		new Face("F4-0", Sex.FEMALE, 19, 5,  new String[] {"Straight Hair"                        }),
		new Face("F4-1", Sex.FEMALE, 40, 3,  new String[] {"Bobbed Hair"                          }),
		new Face("F4-2", Sex.FEMALE, 43, 7,  new String[] {"Dark Hair"                            }),
		new Face("F4-3", Sex.FEMALE, 50, 23, new String[] {"Long Hair"                            })
	};
}