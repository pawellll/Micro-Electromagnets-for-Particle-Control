package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import Platform.CrossRes;
import Platform.Main;
import Supp.Comm;
import Supp.DoubleArea;
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
		if (e.action == GUIAction.IMG_SELECTED)
		{
			DoubleArea data = (DoubleArea) e.data;
			System.out.println("Zaznaczono: "+data.startX+" , "+data.startY+" , "+data.endX+" , "+data.endY);
		}
	}

	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		
		GUIImage img = new GUIImage("trololo", 0, 0, 80, 0);
		img.setContainer(100, 100);
		img.align = Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER;
		img.setImage(CrossRes.GUI[Comm.R_LOGO], Comm.STRETCH_H_PROP);
		img.setSelectable();
		gui.addComponent(img);
		
	}
	
}
