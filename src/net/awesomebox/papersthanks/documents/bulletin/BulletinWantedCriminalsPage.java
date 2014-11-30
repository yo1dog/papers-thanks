package net.awesomebox.papersthanks.documents.bulletin;

import net.awesomebox.papersthanks.Face;
import net.awesomebox.papersthanks.ui.InterrogateItem;

public class BulletinWantedCriminalsPage extends BulletinPage
{
	public final InterrogateItem[] faceInterrogateItems;
	
	private Face[] faces;
	
	BulletinWantedCriminalsPage(Bulletin bulletin)
	{
		super(bulletin);
		
		faceInterrogateItems = new InterrogateItem[] {
			new InterrogateItem(this, 35, 65,  "Bulletin wanted criminals page face 1."),
			new InterrogateItem(this, 35, 114, "Bulletin wanted criminals page face 2."),
			new InterrogateItem(this, 35, 163, "Bulletin wanted criminals page face 3.")
		};
	}
	
	// since this is the only useful page in the bulletin, the bulletin should always be turned to his page
	// thus, there are no clicks needed to get to this page
	/*@Override
	public ClickSequence clickTo(int xRelToDocument, int yRelToDocument, String desc)
	{
	}*/
	
	
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
		// TODO: logic for going to this page and reading the faces
		bulletin.nextPageLink.clickThrough()    // click to the next page
			.add(bulletin.nextPageLink.click()) // click to the next page again
			.execute();
		
		faces = new Face[0];
	}
}
