package net.awesomebox.papersthanks.documents.bulletin;

import java.awt.image.BufferedImage;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class BulletinWantedCriminalsPage extends BulletinPage
{
	public final InterrogateItem[] faceInterrogateItems;
	
	BufferedImage screenshot;
	private Face[] faces;
	
	BulletinWantedCriminalsPage(Bulletin bulletin)
	{
		super(bulletin);
		
		faceInterrogateItems = new InterrogateItem[] {
			new InterrogateItem(this, bulletin, 35, 65,  "Bulletin wanted criminals page face 1."),
			new InterrogateItem(this, bulletin, 35, 114, "Bulletin wanted criminals page face 2."),
			new InterrogateItem(this, bulletin, 35, 163, "Bulletin wanted criminals page face 3.")
		};
	}
	
	public Face getFace(int i)
	{
		if (faces == null)
			throw new AssertionError("Attempting to access faces before they have been read.");
		
		return faces[i];
	}
	public InterrogateItem getInterrogateItemForFace(Face face)
	{
		if (faces == null)
			throw new AssertionError("Attempting to access faces before they have been read.");
		
		for (int i = 0; i < faces.length; ++i)
		{
			if (faces[i] == face)
				return faceInterrogateItems[i];
		}
		
		return null;
	}
	
	
	
	public void readFaces()
	{
		if (screenshot == null)
			throw new AssertionError("Attempting to read faces before screenshot exists.");
		
		// TODO: logic for reading faces from screenshot
		faces = new Face[0];
	}
}
