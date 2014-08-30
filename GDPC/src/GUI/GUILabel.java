package GUI;

import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;

/**
 * Klasa realizujaca label.
*/
public class GUILabel extends GUIComponent
{
	private boolean active = false;
	public String longestText = "WWWWWW";
	
	public String[] text = {"", ""};
	public double font = 3;
	public int textAlign = Comm.ALIGN_VCENTER | Comm.ALIGN_LEFT;
		
	public boolean visible = true;
	public boolean enabled = true;
	
	public GUILabel(double x, double y, double width, double height, String text[], int minWidth, int minHeight)
	{
		super("", x, y, width, height);
		this.text = text;
		setMin(minWidth, minHeight);
	}
		
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{	
		if (!visible)
			return;
		
		g.gSetFontName(Skin.FONT);
		
		int fontSize = 1;
		while (true)
		{
			g.gSetFontSize(fontSize);
			if (g.gGetTextHeight()>=Dim.H(h, minY)*0.5 || g.gGetTextWidth(longestText)>=Dim.W(w, minX)*0.9)
				break;
			fontSize++;
		}
				
		if (active)
			g.gSetColor(GUIMain.C_GRAY_L);
		else
			g.gSetColor(Colors.DARK_GRAY);
		
		if (!enabled)
			g.gSetColor(g.gGetColor(), 80);		

		g.gDrawText(getX(), getY(), Dim.W(w, minX), Dim.H(h, minY), Str.get(text), textAlign);
	}
	
		
}
