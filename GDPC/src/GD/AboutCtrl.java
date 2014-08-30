package GD;

import GUI.GUILabel;
import GUI.GUIMain;
import Supp.Str;

/**
 * Klasa kontrolera glownego menu.
 */

public class AboutCtrl extends Controller
{
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.GUI_ICON_TITLES[3];
		
		GUILabel lab = new GUILabel(10, 10, 70, 10, Str.ABOUT_APP1, 120, 25);
		gui.addComponent(lab);
		
		lab = new GUILabel(10, 20, 70, 10, Str.ABOUT_APP2, 120, 25);
		gui.addComponent(lab);
		
		lab = new GUILabel(10, 35, 70, 7, Str.ABOUT_US, 120, 25);
		gui.addComponent(lab);
	}
	
}
