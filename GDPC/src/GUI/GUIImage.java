package GUI;

import Platform.CrossBitmap;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DoubleArea;
import Supp.DoublePoint;
import Supp.DrawingInterface;
import java.awt.Color;

/**
 * Klasa realizujaca wyswietlanie obrazkow.
*/
public class GUIImage extends GUIComponent
{
	private CrossBitmap img = null;
	private int stretch = Comm.STRETCH_HV;
	private boolean selectable = false;
	private boolean selectStarted = false;
	
	private double startX, startY, endX, endY;
	
	public GUIImage(String id, double x, double y, double width, double height)
	{
		super(id, x, y, width, height);
	}
	
	public void setStretchFlag(int stretchFlag)
	{
		stretch = stretchFlag;
		if (img == null)
			return;
			
		double nw = img.getWidth();
    double nh = img.getHeight();
    
		if (stretchFlag == Comm.STRETCH_H_PROP)
		{
			nh = Dim.dblY((double)nh*(double)Dim.X(w)/nw);
			nw = w;
		}
		else
		if (stretchFlag == Comm.STRETCH_V_PROP)
		{
			nw = Dim.dblX((double)nw*(double)Dim.Y(h)/nh);
			nh = h;
		}
		else
		{			
			if (stretchFlag == Comm.STRETCH_H || stretchFlag == Comm.STRETCH_HV)
				nw = w;

			if (stretchFlag == Comm.STRETCH_V || stretchFlag == Comm.STRETCH_HV)
				nh = h;
		}		
		
		w = nw;
		h = nh;
		
		Main.main.canvas.paint();
	}	
	
	public void setImage(CrossBitmap image)
	{
		img = image;
		setStretchFlag(stretch);
	}
		
	public void setImage(CrossBitmap image, int stretchFlag)
	{
		img = image;
		setStretchFlag(stretchFlag);
	}
	
	public void setSelectable()
	{
		selectable = true;
		selectStarted = false;
		endX = 0;
		endY = 0;
	}
	
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		setStretchFlag(stretch);
		
		g.gStretchBitmap(getX(), getY(), Dim.X(w, minX), Dim.Y(h, minY), img, Comm.STRETCH_HV);
	
		if (selectable && selectStarted)
		{
			g.gSetColor(Colors.BLACK);
			g.gDrawRectangle((int)Math.min(startX, endX) , (int)Math.min(startY, endY), (int)Math.abs(endX-startX), (int)(Math.abs(endY-startY)));
			g.gSetColor(Colors.WHITE);
			g.gDrawRectangle((int)Math.min(startX, endX)+1, (int)Math.min(startY, endY)+1, (int)Math.abs(endX-startX)-2, (int)(Math.abs(endY-startY))-2);
			
			g.gSetColor(Colors.GREEN, 128);
			g.gFillRectangle((int)Math.min(startX, endX)+2, (int)Math.min(startY, endY)+2, (int)Math.abs(endX-startX)-3, (int)(Math.abs(endY-startY))-3);
		
		}
	}
		
	@Override
	public void onMouseClick(double x, double y)
	{
		Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction(id, GUIAction.IMG_CLICK, new DoublePoint(x,y)));	
	}
	
	@Override
	public void onMouseDown(double x, double y)
	{
		if (selectable)
		{		
			startX = x;
			startY = y;
						
			selectStarted = true;
		}
	}
		
	@Override
	public void onMouseDragged(double x, double y)
	{
		if (selectable && selectStarted)
		{
			endX = x;
			endY = y;
			Main.main.canvas.paint();
		}
	}
	
	@Override
	public void onMouseUp(double x, double y)
	{
		if (selectable && selectStarted && endX!=0 && endY!=0)
		{
			Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction(id, GUIAction.IMG_SELECTED, new DoubleArea(startX, startY, endX, endY)));	
		  selectStarted = false;
			endX = 0;
			endY = 0;
			Main.main.canvas.paint();
		}
	}
	
	
	
}
