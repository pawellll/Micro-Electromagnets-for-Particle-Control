package GUI;

import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;

/**
 * Klasa realizujaca label.
*/
public class GUILabel extends GUIComponent
{
	private boolean active = false;
	public String longestText = "WWWWWW";
	
	public String text = "";
	public double font = 3;
	public int textAlign = Comm.ALIGN_VCENTER | Comm.ALIGN_LEFT;
		
	public GUILabel(double x, double y, double width, double height, String text, int minWidth, int minHeight)
	{
		super("", x, y, width, height);
		this.text = text;
		setMin(minWidth, minHeight);
	}
		
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{	
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
		
		
		g.gDrawText(getX(), getY(), Dim.X(w, minX), Dim.Y(h, minY), text, textAlign);
	}
	
		
}
