package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.awesomebox.papersthanks.ui.ClickSequence.ClickEvent;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class ClickPreviewView
{
	static final Color clickColor  = new Color(0, 255, 0);
	static final Color dragColor   = new Color(255, 0, 0);
	static final int   clickRadius = 5;
	static final int   dragRadius  = 2;
	
	static final BufferedImage backgroundImage = ImageUtil.readImage("assets/images/background.png");
	static final ArrayList<ClickEvent> clickEvents = new ArrayList<ClickEvent>();
	
	private static JFrame frame = null;
	
    public static void show()
    {
        frame = new JFrame("Click Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ClickPreviewPanel());
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void addClickEvent(ClickEvent clickEvent)
    {
    	clickEvents.add(clickEvent);
    	
    	if (frame != null)
    		frame.repaint();
    }
    
    
    private static class ClickPreviewPanel extends JPanel
    {
    	private static final long serialVersionUID = 1L;
    	
    	ClickPreviewPanel() {}

        @Override
    	public Dimension getPreferredSize()
        {
            return new Dimension(570, 320);
        }

        @Override
    	public void paintComponent(Graphics g)
        {
            super.paintComponent(g);       

            // draw background image
            g.drawImage(ClickPreviewView.backgroundImage, 0, 0, 570, 320, 0, 0, 570, 320, null);
            
            for (int i = 0; i < clickEvents.size(); ++i)
            {
            	ClickEvent clickEvent = clickEvents.get(i);
            	
            	g.setColor(clickColor);
            	g.fillOval(clickEvent.x - clickRadius, clickEvent.y - clickRadius, clickRadius*2, clickRadius*2);
            	
            	if (clickEvent.dragToX > -1)
            	{
                	g.fillOval(clickEvent.dragToX - clickRadius, clickEvent.dragToY - clickRadius, clickRadius*2, clickRadius*2);
                	
                	g.setColor(dragColor);
                	g.drawLine(clickEvent.x, clickEvent.y, clickEvent.dragToX, clickEvent.dragToY);
                	g.fillOval(clickEvent.dragToX - dragRadius, clickEvent.dragToY - dragRadius, dragRadius*2, dragRadius*2);
            	}
            }
        }
    }
}
