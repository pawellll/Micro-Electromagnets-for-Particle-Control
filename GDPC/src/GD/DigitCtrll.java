package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import GUI.GUILabel;
import Platform.CrossBitmap;
import Platform.CrossCanvas;
import Platform.CrossRes;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DoubleArea;
import Supp.DrawingInterface;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BABCIA ADHD
 */
public class DigitCtrll extends Controller
{
	private static final String ID_BUT_NORMAL = "DigitCrtlButNormal";
	private static final String ID_BUT_POINTS = "DigitCrtlButPoints";
	
	public static final int STATE_GRAPH_TYPE = 0; 
	public static final int STATE_GET_POINTS = 1; 
	public static final int STATE_DIGITALIZE = 2; 
	public int state = STATE_GRAPH_TYPE;
	
	private GUILabel statusLabels[] = null;
	private int status = 0;
	
	private CrossBitmap imgs[] = null;
	
	public DigitCtrll(CrossBitmap images[])
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
				statusLabels = new GUILabel[5];
				statusLabels[0] = new GUILabel(0, 15, 0, 10, "Zebranie danych", 120, 25);
				statusLabels[1] = new GUILabel(0, 25, 0, 10, "OCR podpisów osi", 120, 25);
				statusLabels[2] = new GUILabel(0, 35, 0, 10, "CR wartości osi", 120, 25);
				statusLabels[3] = new GUILabel(0, 45, 0, 10, "Analiza legendy", 120, 25);
				statusLabels[4] = new GUILabel(0, 55, 0, 10, "Digitalizacja", 120, 25);
			
				for (GUILabel lab: statusLabels)
				{
					gui.addComponent(lab);	
					lab.align = Comm.ALIGN_CENTER;
					lab.setContainer(100, 0);
				}
			break;
		}		
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (state == STATE_DIGITALIZE)
		{
			int i=0;
			CrossBitmap bit;
			if (statusLabels != null)
				for (GUILabel lab: statusLabels)
				{
					if (status>=i)
						bit = CrossRes.GUI[Comm.R_CHECK_ON];
					else
						bit = CrossRes.GUI[Comm.R_CHECK_OFF];

					g.gStretchAlignBitmap(0, Dim.Y(1) + lab.getY(), lab.getX()-10, Dim.Y(8), 1, Dim.Y(8), bit, Comm.STRETCH_V_PROP, Comm.ALIGN_RIGHT);
					i++;
				}
		}
		
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		if (e.action == GUIAction.BUTTON_CLICK)
		{
			if (e.id.equals(ID_BUT_NORMAL))
			{
				state = STATE_DIGITALIZE;
				onSetCurrent();
				
        int[] colors = new int[4];
        colors[0] = Colors.WHITE;
        colors[1] = Colors.RGB(0, 143, 100);
        colors[2] = Colors.RGB(213, 72, 1);
        colors[3] = Colors.RGB(107, 96, 174);
        
				CrossBitmap image = imgs[Comm.D_BIT_GRAPH];
				List<Point>[] out = Digitalize.digiNormalColors(image, colors);
				
        
        CrossBitmap outBit = new CrossBitmap(CrossBitmap.newFrom(image));
        
        CrossCanvas gr = outBit.getCanvas();
        
        int size;
        Point point1, point2;
        for (int i = 0; i<out.length; i++)
        {
          gr.gSetColor(colors[i+1]);
          size = out[i].size();
          for (int k=0; k<size-1; k++)
          {
            point1 = out[i].get(k);
            point2 = out[i].get(k+1);
            gr.gDrawLine((int)point1.getX(), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
          }
        }
        
				GUIImage img = new GUIImage("abc", 0, 0, 80, 80);
				img.setImage(outBit);
				img.setStretchFlag(Comm.STRETCH_H_PROP);
				img.align = Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER;
				img.setContainer(100, 100);
				gui.addComponent(img);				
			}
			else
			if (e.id.equals(ID_BUT_POINTS))
			{
				
			}				
				
		}
	}

}
