package GUI;

import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;
import java.awt.Color;

/**
 * Klasa realizujaca pasek postepu.
 */
public class GUIProgress extends GUIComponent
{
	public boolean showProgress = false;
	public double max = 100;
	public double value = 0;
	
	
	public GUIProgress( double x, double y, double width, double height)
	{
		super("", x, y, width, height);
	}

	public void set(double newValue)
	{
		if (newValue >= 0 && newValue <= max)
			value = newValue;	
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (!visible)
			return;
		
		g.gSetColor(Skin.BUTTON_OFF);
		g.gDrawRectangle(getX(), getY(), Dim.W(w), Dim.H(h));
		
		g.gSetColor(Skin.BUTTON_ON);
		int prog = Dim.W(w*(value / max));
		g.gFillRectangle(getX(), getY(), prog, Dim.H(h));
		
		if (showProgress)
		{
			g.gSetColor(Colors.BLACK);
			String s = String.format("%1$,.2f", (value/max)*100)+"%";
			g.gSetFontSize(s, Dim.W(w), Dim.H(h, minY)*0.8);
			g.gDrawText(getX(), getY(), Dim.W(w, minX), Dim.H(h, minY), s, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
		}
	}
	
	

}
