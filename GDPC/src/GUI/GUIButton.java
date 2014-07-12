package GUI;

import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import java.awt.Font;

/**
 * Klasa realizujaca przycisk.
*/
public class GUIButton extends GUIComponent
{
	private boolean active = false;
	public String longestText = "WWWWWW";
	
	public String text = "";
	public double font = 3;
	
	public GUIButton(String id, double x, double y, double width, double height, String text)
	{
		super(id, x, y, width, height);
		this.text = text;
	}	
	
	public GUIButton(String id, double x, double y, double width, double height, String text, int minWidth, int minHeight)
	{
		this(id, x, y, width, height, text);
		setMin(minWidth, minHeight);
	}
		
	@Override
	public void onMouseClick(double x, double y)
	{
		Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction(id, GUIAction.BUTTON_CLICK, null));
	}

	@Override
	public void onMouseExit()
	{
		changeCursor(Comm.CURSOR_DEF);
		active = false;
		paintGUI();
	}

	@Override
	public void onMouseEnter()
	{
		changeCursor(Comm.CURSOR_HAND);		 
		active = true;
		paintGUI();
	}

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (active)
			g.gSetColor(GUIMain.C_GREEN);
		else
			g.gSetColor(GUIMain.C_GREEN_L);
		
		g.gFillRoundedRectangle(getX(), getY(), Dim.X(w, minX), Dim.Y(h, minY), (int)(Dim.X(height)*0.005), (int)(Dim.X(height)*0.005));

		g.gSetColor(GUIMain.C_BORDER);
		g.gDrawRoundedRectangle(getX(), getY(), Dim.X(w, minX), Dim.Y(h, minY), (int)(Dim.X(height)*0.005), (int)(Dim.X(height)*0.005));
	
		
		g.gSetFontName("Arial");
		
		int fontSize = 1;
		while (true)
		{
			g.gSetFontSize(fontSize);
			if (g.gGetTextHeight()>=Dim.Y(h, minY)*0.5 || g.gGetTextWidth(longestText)>=Dim.X(w, minX)*0.9)
				break;
			fontSize++;
		}
		
		
		if (active)
			g.gSetColor(GUIMain.C_GRAY_L);
		else
			g.gSetColor(Colors.DARK_GRAY);
		
		
		g.gDrawText(getX(), getY(), Dim.X(w, minX), Dim.Y(h, minY), text, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
	
	}
	
	
}
