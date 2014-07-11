package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import Platform.CrossBitmap;
import Platform.CrossRes;
import Supp.Comm;
import Supp.DoubleArea;
import Supp.DrawingInterface;

/**
 *
 * @author BABCIA ADHD
 */
public class DigitCrtl extends Controller
{
	private static final String ID_BUT_NORMAL = "DigitCrtlButNormal";
	private static final String ID_BUT_POINTS = "DigitCrtlButPoints";
	
	public static final int STATE_GRAPH_TYPE = 0; 
	public static final int STATE_GET_POINTS = 1; 
	public static final int STATE_DIGITALIZE = 2; 
	public int state = STATE_GRAPH_TYPE;
	
	private CrossBitmap imgs[] = null;
	
	public DigitCrtl(CrossBitmap images[])
	{
		imgs = images;
	}

	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		
		switch (state)
		{
			case STATE_GRAPH_TYPE:
				GUIButton but = new GUIButton(ID_BUT_NORMAL, 0, 35, 40, 10, "Wykres normalny", 120, 25);
				but.align = Comm.ALIGN_CENTER;
				but.setContainer(100, 0);
				but.longestText = "Wykres normalny";
				gui.addComponent(but);

				but = new GUIButton(ID_BUT_POINTS, 0, 55, 40, 10, "Wykres punktowy", 120, 25);
				but.align = Comm.ALIGN_CENTER;
				but.setContainer(100, 0);
				but.longestText = "Wykres normalny";
				gui.addComponent(but);
			break;
				
			case STATE_DIGITALIZE:
			
				
			break;
		}		
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		if (e.action == GUIAction.BUTTON_CLICK)
		{
			if (e.id.equals(ID_BUT_NORMAL))
			{
				
			}
			else
			if (e.id.equals(ID_BUT_POINTS))
			{
				
			}				
				
		}
	}

}
