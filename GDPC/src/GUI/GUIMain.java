package GUI;

import Platform.Main;
import Supp.Colors;
import Supp.Dim;
import Supp.DrawingInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa bedaca glownym kontrolerem GUI.
 */
public class GUIMain
{
	//Definicje kolorów
	public final static int C_GRAY    = Colors.RGB(124, 124, 124); //7c7c7c;
	public final static int C_GRAY_L  = Colors.RGB(242, 242, 242); //f2f2f2;

	public final static int C_GREEN_D = Colors.RGB(135, 167, 66);  //87a742;
	public final static int C_GREEN   = Colors.RGB(144, 191, 73);  //90bf49;
	public final static int C_GREEN_L = Colors.RGB(202, 217, 169); //cad9a9;                            
 	
	public final static int C_BORDER  = Colors.RGB(115, 115, 115); //cad9a9;                            
  
	//Zmienne
	public int width = 100;
	public int height = 100;
	
	//Komponenty
	private final List<GUIComponent> components = new ArrayList<>();
	private GUIComponent focused = null;
	private GUIComponent last = null;
	
	public void clearComponents()
	{
		focused = null;
		components.clear();
	}
	
	public void addComponent(GUIComponent newComponent)
	{
		components.add(newComponent);
		Main.main.canvas.paint();
	}
  
  public void onDraw(DrawingInterface g, int w, int h)
  {
		//Aktualizacja
		width = w;
		height = h;
		Dim.update(width, height);
		
		//Tlo
    g.gSetColor(C_GRAY_L);
    g.gFillRectangle(0, 0, w, h);
		
		//Rysuj controller
		if (Main.main.mainCrtl.currentCtrl!=null)
			Main.main.mainCrtl.currentCtrl.onPaint(g, width, height);
		
		//Rysuj komponenty
		for (GUIComponent componet: components)
		{
			componet.onPaint(g, width, height);
		}		
		
  }
		
	public GUIComponent getComponentAtXY(int x, int y)
	{
		for (GUIComponent componet: components)
		{
			if (x>=componet.getX() && x<=componet.getX()+Dim.X(componet.w) &&
				  y>=componet.getY() && y<=componet.getY()+Dim.Y(componet.h))
				return componet;
		}
		return null;
	}
	
	public void onMouseDown(int x, int y)
	{
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			if (temp.focusable)
				focused = temp;
			temp.onMouseDown(x, y);
		}
	}
		
	public void onMouseMove(int x, int y)
	{
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			if (last!=null)
			{
				if (temp!=last)
				{
					last.onMouseExit();
					last = temp;
				}
			}
			else
			{
				last = temp;
				last.onMouseEnter();
			}
			
			temp.onMouseMove(x, y);		
		}
		else
		if (last!=null)
		{
			last.onMouseExit();
			last = null;
		}
	}
	
	public void onMouseDragged(int x, int y)
	{
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			temp.onMouseDragged(x, y);		
		}
	}
		
	public void onMouseUp(int x, int y)
	{
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
			temp.onMouseUp(x, y);		
	}
	
	public void onMouseClick(int x, int y)
	{
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
			temp.onMouseClick(x, y);		
	}
	
	public void onKeyDown(char key)
	{
		if (focused!=null)
			focused.onKeyDown(key);
	}
		
	public void onKeyTyped(char key)
	{
		if (focused!=null)
			focused.onKeyTyped(key);
	}
	
	public void onKeyUp(char key)
	{
		if (focused!=null)
			focused.onKeyUp(key);
	}
	
}
