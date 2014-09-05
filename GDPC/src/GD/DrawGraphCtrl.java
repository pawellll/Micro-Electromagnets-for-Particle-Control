package GD;

import Digit.DigitOutput;
import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUILabel;
import GUI.GUIList;
import GUI.GUIMain;
import Platform.CrossRes;
import Platform.Main;
import Supp.DrawingInterface;
import Supp.Str;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa kontrolera rysowania wykresow.
 */
public class DrawGraphCtrl extends Controller
{
	public List<DigitOutput> graph = new ArrayList<>();
	
	private GUIList list = null;
	
	public DrawGraphCtrl()
	{
	}
	
	public DrawGraphCtrl(List<DigitOutput> digitOutput)
	{
		graph = digitOutput;
	}	
	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.DRAW_TITLE;
	
		if (graph.isEmpty())
		{
			GUILabel lab = new GUILabel(10, 10, 30, 8, Str.DRAW_LIST_TITLE, 120, 25);
			gui.addComponent(lab);

			list = new GUIList("lista", 10, 22, 80, 5);
			List<String> temps = CrossRes.getGraphs(false);
			List<String> tempsFull = CrossRes.getGraphs(true);
			String content;

			DigitOutput test = new DigitOutput();
			for (int i=0; i < temps.size(); i++)
			{
				content = CrossRes.loadText(tempsFull.get(i));			
				if (test.fromString(content))
					list.addOption(temps.get(i), content);
			}

			list.setSelectedIndex(0);
			gui.addComponent(list);	

			GUIButton but = new GUIButton("load", 40, 35, 20, 8, Str.DRAW_LOAD, 125, 25);
			gui.addComponent(but);

			but = new GUIButton("draw", 70, 70, 20, 8, Str.DRAW_DRAW, 125, 25);
			gui.addComponent(but);
		}
		
	}

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		if (e.id.equals("load"))
		{
			DigitOutput temp = new DigitOutput();
			if (!list.getOptionsList().isEmpty())
				if (temp.fromString(list.getAdditional()))
				{
					graph.add(temp);
					int old = list.getSelectedIndex();
					list.setSelectedIndex(0);
					list.getOptionsList().remove(old);
				}
		}
			
		if (e.id.equals("draw"))
		{
			if (graph.isEmpty())
				gui.setInfoLongest(Str.get(Str.DRAW_NO_GRAPHS), GUIMain.INFO_WARN);
			else
			{
				onSetCurrent();
			}
		}
		
		Main.main.canvas.paint();
	}



}
