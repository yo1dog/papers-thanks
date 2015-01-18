package net.awesomebox.papersthanks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickAndDragEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseClickEvent;
import net.awesomebox.papersthanks.ui.MouseSequence.MouseMoveEvent;
import net.awesomebox.papersthanks.utils.ImageUtil;

public class MousePreviewView
{
	static final Color moveColor   = new Color(0, 0, 255);
	static final Color clickColor  = new Color(0, 255, 0);
	static final Color dragColor   = new Color(255, 0, 0);
	static final int   moveRadius  = 5;
	static final int   clickRadius = 5;
	static final int   dragRadius  = 2;
	
	static final BufferedImage backgroundImage = ImageUtil.readImage("assets/images/background.png");
	static final ArrayList<MouseEvent> mouseEvents = new ArrayList<MouseEvent>();
	
	private static JFrame frame = null;
	
    public static void show()
    {
        frame = new JFrame("Click Preview");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ClickPreviewPanel());
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void addMouseEvent(MouseEvent mouseEvent)
    {
    	mouseEvents.add(mouseEvent);
    	
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
            g.drawImage(MousePreviewView.backgroundImage, 0, 0, 570, 320, 0, 0, 570, 320, null);
            
            for (int i = 0; i < mouseEvents.size(); ++i)
            {
            	MouseEvent mouseEvent = mouseEvents.get(i);
            	
            	if (mouseEvent instanceof MouseMoveEvent)
            	{
            		MouseMoveEvent mouseMoveEvent = (MouseMoveEvent)mouseEvent;
            		drawMove(g, mouseMoveEvent.x, mouseMoveEvent.y);
            	}
            	else if (mouseEvent instanceof MouseClickEvent)
            	{
            		MouseClickEvent mouseClickEvent = (MouseClickEvent)mouseEvent;
            		drawClick(g, mouseClickEvent.x, mouseClickEvent.y);
            	}
            	else if (mouseEvent instanceof MouseClickAndDragEvent)
            	{
            		MouseClickAndDragEvent mouseClickAndDragEvent = (MouseClickAndDragEvent)mouseEvent;
            		drawDrag(g, mouseClickAndDragEvent.x1, mouseClickAndDragEvent.y1, mouseClickAndDragEvent.x2, mouseClickAndDragEvent.y2);
            	}
            }
        }
        
        private static void drawMove(Graphics g, int x, int y)
        {
        	g.setColor(moveColor);
        	g.fillOval(x - moveRadius, y - moveRadius, moveRadius*2, moveRadius*2);
        }
        
        private static void drawClick(Graphics g, int x, int y)
        {
        	g.setColor(clickColor);
    		g.fillOval(x - clickRadius, y - clickRadius, clickRadius*2, clickRadius*2);
        }
        
        private static void drawDrag(Graphics g, int x1, int y1, int x2, int y2)
        {
        	g.setColor(dragColor);
        	g.drawLine(x1, y1, x2, y2);
        	g.fillOval(x2 - dragRadius, y2 - dragRadius, dragRadius*2, dragRadius*2);
        }
    }
}
