package GD;

import GUI.GUIAction;
import Platform.Main;
import Supp.DrawingInterface;

public class kontr extends Controller
{

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		g.gDrawRectangle(50, 50, 100, 100);
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
	}

	@Override
	public void onSetCurrent()
	{
				gui.clearComponents();
				Main.main.canvas.paint();
		
	}
	
}
