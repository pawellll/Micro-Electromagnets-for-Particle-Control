
package GUI;

import Platform.Main;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;

/**
 * Klasa bazowa dla komponentow GUI.
*/
public class GUIComponent
{
	public int minX = 0;
	public int minY = 0;

	public int align = Comm.ALIGN_NONE;
	
	public String id;
	public double x, y;
	public double w, h;
	public double contWidth, contHeight; 
	public boolean focusable = false;
	
	public GUIComponent(String id, double x, double y, double width, double height)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	public void setMin(int minWidth, int minHeight)
	{
		minX = minWidth;
		minY = minHeight;
	}
	
	public void setContainer(double contWidth, double contHeight)
	{
		this.contWidth = contWidth;
		this.contHeight = contHeight;
	}
	
	public int getX()
	{
    int nx = Dim.X(x);

    if ((align & Comm.ALIGN_CENTER) == Comm.ALIGN_CENTER)
      nx = Dim.X(x) + (int)(Dim.X(contWidth)/2.0) - (int)(Dim.X(w, minX)/2.0);
    else
    if ((align & Comm.ALIGN_RIGHT) == Comm.ALIGN_RIGHT)
      nx = Dim.X(x) + Dim.X(contWidth) - Dim.X(w, minX); 
    
		return nx;
	}
	
	public int getY()
	{
    int ny = Dim.Y(y);
    
    if ((align & Comm.ALIGN_VCENTER) == Comm.ALIGN_VCENTER)
      ny = Dim.Y(y) + (int)(Dim.Y(contHeight)/2.0) - (int)(Dim.Y(h, minY)/2.0);
    else
    if ((align & Comm.ALIGN_BOTTOM) == Comm.ALIGN_BOTTOM)
      ny = Dim.Y(y) + Dim.Y(contHeight) - Dim.Y(h, minY);
		
		return ny; 		
	}
	
	
	public void changeCursor(int cursorID)
	{
		Main.main.canvas.changeCursor(cursorID);
	}
	
	public void paintGUI()
	{
		Main.main.canvas.paint();
	}
	
	public void onPaint(DrawingInterface g, int width, int height)
	{}
	
	public void onMouseEnter()
	{}
	
	public void onMouseExit()
	{}
		
	public void onMouseDown(double x, double y)
	{}
		
	public void onMouseMove(double x, double y)
	{}
		
	public void onMouseDragged(double x, double y)
	{}
	
	public void onMouseUp(double x, double y)
	{}
	
	public void onMouseClick(double x, double y)
	{}
	
	public void onKeyDown(int key)
	{}
		
	public void onKeyTyped(int key)
	{}
	
	public void onKeyUp(int key)
	{}
	
	
	
}
