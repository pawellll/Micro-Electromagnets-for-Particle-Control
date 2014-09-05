package GUI;

import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;
import java.awt.Font;

/**
 * Klasa realizujaca przycisk.
*/
public class GUIButton extends GUIComponent
{
	private boolean active = false;
	public String longestText[] = {"Longest text","Longest text"};
	
	public String text[] = {""};
	public double font = 3;
	
	public GUIButton(String id, double x, double y, double width, double height, String text[])
	{
		super(id, x, y, width, height);
		this.text = text;
	}	
	
	public GUIButton(String id, double x, double y, double width, double height, String text[], int minWidth, int minHeight)
	{
		this(id, x, y, width, height, text);
		setMin(minWidth, minHeight);
	}
		
	@Override
	public void onMouseUp(double x, double y)
	{
		if (!visible)
			return;		
		Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction(id, GUIAction.BUTTON_CLICK, null));
	}

	@Override
	public void onMouseExit()
	{
		if (!visible)
			return;	
		
		changeCursor(Comm.CURSOR_DEF);
		active = false;
		paintGUI();
	}

	@Override
	public void onMouseEnter()
	{
		if (!visible)
			return;
		
		changeCursor(Comm.CURSOR_HAND);		 
		active = true;
		paintGUI();
	}

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (!visible)
			return;
		
		if (active)
			g.gSetColor(Skin.BCG_L);
		else
			g.gSetColor(Skin.BUTTON_OFF);
		
		g.gFillRoundedRectangle(getX(), getY(), Dim.W(w, minX), Dim.H(h, minY), 3, 3);

		g.gSetColor(Skin.BUTTON_OFF);
   
		g.gDrawRoundedRectangle(getX(), getY(), Dim.W(w, minX), Dim.H(h, minY), 3, 3);
		g.gDrawRectangle(getX()+1, getY()+1, Dim.W(w, minX)-2, Dim.H(h, minY)-2);
 
		g.gSetFontName(Skin.FONT);
		/*
		int fontSize = 1;
		while (true)
		{
			g.gSetFontSize(fontSize);
			if (g.gGetTextHeight()>=Dim.H(h, minY)*0.5 || g.gGetTextWidth(longestText)+6>=Dim.W(w, minX)*0.9)
				break;
			fontSize++;
		}*/
		
		g.gSetFontSize(Str.get(longestText), Dim.W(w, minX)*0.9-6, Dim.H(h, minY)*0.5);
		
		if (active)
			g.gSetColor(Skin.BCG_L);
		else
			g.gSetColor(Skin.BUTTON_TEXT_SHADOW);
    
		g.gDrawText(getX()+1, getY()+1, Dim.W(w, minX), Dim.H(h, minY), Str.get(text), Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
		
		if (active)
			g.gSetColor(Skin.BUTTON_OFF);
		else
			g.gSetColor(Skin.BUTTON_TEXT);
				
		g.gDrawText(getX(), getY(), Dim.W(w, minX), Dim.H(h, minY), Str.get(text), Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
	
	}
	
	
}
