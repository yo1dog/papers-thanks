package net.awesomebox.papersthanks;

import java.awt.image.BufferedImage;

public class FingerPrint
{
	public static enum Digit
	{
		PINKY,
		RING,
		MIDDLE,
		POINTER,
		THUMB
	}
	
	
	public final Digit digit;
	
	public FingerPrint(Digit digit)
	{
		this.digit = digit;
	}
	
	
	public static FingerPrint fromImage(Digit digit, BufferedImage image)
	{
		return new FingerPrint(digit);
	}
}
