package GUI;

import Platform.CrossRes;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa realizujaca liste opcji wybierana strzalkami.
 */
public class GUIList extends GUIComponent
{

	private List<String[]> options = new ArrayList<>();
	public  List<String> additional = new ArrayList<>();
	private boolean langable = false;
	private int selected = -1;
	private int active = 0;
	private boolean showProgress = false;
	
	public int maxCharacters = 0;
	public boolean visible = true;
	public boolean enabled = true;
	
	public GUIList(String id, double x, double y, double width, double height)
	{
		super(id, x, y, width, height);
		focusable = true;
	}

	public int getSelectedIndex()
	{
		return selected;
	}
	
	public void setSelectedIndex(int index)
	{
		if (index>=-1 && index<options.size())
		{	
			selected = index;
			Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction(id, GUIAction.LIST_CHANGE, selected));
		}
	}
	
	public int getCount()
	{
		return options.size();
	}
	
	public List<String[]> getOptionsList()
	{
		return options;
	}
	
	public void setOptionsList(List<String[]> optionsList)
	{
		options = optionsList;
	}	
	
	public List<String> getAdditionalList()
	{
		return additional;
	}
	
	public void setAdditionalList(List<String> additionalList)
	{
		additional = additionalList;
	}
	
	public boolean isLangable()
	{
		return langable;
	}
	
	public void cloneList(GUIList other)
	{
		setOptionsList(other.getOptionsList());
		setAdditionalList(other.getAdditionalList());
		langable = other.isLangable();
	}
	
	
					
					
	/*
	public void setOptions(List<String> optionsList)
	{
		options.clear();
		langable = false;
		String temp[];
		for (String s : optionsList)
		{
			temp = new String[1];
			temp[0] = s;
			options.add(temp);
		}
	}

	public void setOptionsWithLang(List<String[]> optionsList)
	{
		options = optionsList;
		langable = true;
	}*/

	public void clearOptions()
	{
		options.clear();
		additional.clear();
	}

	public void addOption(String newOption, String additinalInfo)
	{
		langable = false;
		String temp[] = new String[1];
		temp[0] = newOption;
		options.add(temp);
		additional.add(additinalInfo);
	}

	public void addOptionWithLang(String newOption[], String additinalInfo)
	{
		langable = true;
		options.add(newOption);
		additional.add(additinalInfo);		
	}
	public void addOption(String newOption)
	{
		addOption(newOption, "");
	}

	public void addOptionWithLang(String newOption[])
	{
		addOptionWithLang(newOption, "");
	}
	
	public String getSelected()
	{
		if (langable)
			return Str.get(options.get(getSelectedIndex()));
		else
			return options.get(getSelectedIndex())[0];
	}
	
	public String getAdditional()
	{
		return additional.get(getSelectedIndex());
	}

	public void setLangable(boolean status)
	{
		langable = status;
	}
	
	public void pack()
	{
		
	}

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (!visible)
			return;
		
		int img;
		
		if (active==1)
			img = Comm.R_ARR_L_ON;
		else
			img = Comm.R_ARR_L_OFF;
		
		if (enabled)		
			g.gStretchAlignBitmap(getX(), getY(), Dim.W(0.1*w), Dim.H(h), Dim.W(0.09*w), Dim.H(h*0.95), CrossRes.GUI[img], Comm.STRETCH_V_PROP, Comm.ALIGN_VCENTER);

		if (active==2)
			img = Comm.R_ARR_R_ON;
		else
			img = Comm.R_ARR_R_OFF;		
		
		if (enabled)
			g.gStretchAlignBitmap(getX()+Dim.W(0.9*w), getY(), Dim.W(0.1*w), Dim.H(h), Dim.W(0.09*w), Dim.H(h*0.95), CrossRes.GUI[img], Comm.STRETCH_V_PROP, Comm.ALIGN_RIGHT | Comm.ALIGN_VCENTER);
		
		
		g.gSetFontName(Skin.FONT);
		
		if (active!=0)
		{
			g.gSetColor(Skin.BUTTON_ON);
		}
		else
			g.gSetColor(Skin.BCG_L_TEXT);
		
		String text;
		if (selected<0 || selected>=options.size())
		{
			text = Str.get(Str.LIST_CHOOSE);
			if (active==0)
				g.gSetColor(Colors.RGB(180, 180, 180));
		}
		else
		{
			if (langable)
				text = Str.get(options.get(selected));
			else
				text = options.get(selected)[0];
		}
		
		if (maxCharacters>0)
		{
			if (text.length()>maxCharacters)
			{
				text = text.substring(0, text.length()-3);
				text = text + "...";
			}
		}
		
		g.gSetFontSize(text, Dim.W(w*0.7), Dim.H(h));
				
		if (!enabled)
			g.gSetColor(g.gGetColor(), 80);
		
		g.gDrawText(getX()+Dim.W(0.1*w), getY(), Dim.W(w*0.8, minX), Dim.H(h, minY), text, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
		
		if (showProgress)
		{
			g.gSetColor(Skin.BCG_D);
			g.gDrawLine(getX()+Dim.W(0.1*w), getY()+Dim.H(h, minY), getX()+Dim.W(0.9*w), getY()+Dim.H(h, minY));
			g.gSetColor(Skin.BUTTON_ON);
			g.gDrawLine(getX()+Dim.W(0.1*w), getY()+Dim.H(h, minY), getX()+Dim.W(0.1*w)+Dim.W(0.8*w*(double)(selected+1) / options.size()), getY()+Dim.H(h, minY));
	
			g.gSetColor(Skin.BCG_L);
			for (int i=1; i < options.size(); i++)
				g.gFillRectangle(getX()+Dim.W(0.1*w)+Dim.W(0.8*w*(double)(i) / options.size())-1, getY()+Dim.H(h, minY), 2, 1);
		}
		
//		g.gDrawRectangle(getX(), getY(), Dim.W(w), Dim.H(h));
//		g.gDrawRectangle(getX()+Dim.W(0.1*w), getY(), Dim.W(w*0.8, minX), Dim.H(h));
	}

	public void onMouseEnter()
	{
	}

	public void onMouseExit()
	{
		if (!visible || !enabled)
			return;
		
		active = 0;
		showProgress = false;
		changeCursor(Comm.CURSOR_DEF);	
		Main.main.canvas.paint();		
	}

	public void onMouseDown(double x, double y)
	{
	}

	public void onMouseMove(double x, double y)
	{
		if (!visible || !enabled)
			return;		
		
		if (x<=getX()+Dim.W(0.1*w))
		{
			showProgress = true;
			if (selected>0 || selected==-1)
				active = 1;
		}
		else
		if (x>=getX()+Dim.W(0.9*w))
		{
			showProgress = true;
			if (selected!=options.size()-1 || selected==-1)
				active = 2;
		}
		else
		{
			showProgress = false;
			active = 0;
		}
		
		if (active!=0)
			changeCursor(Comm.CURSOR_HAND);
		else
			changeCursor(Comm.CURSOR_DEF);	
		
		Main.main.canvas.paint();
	}

	public void onMouseDragged(double x, double y)
	{
	}

	public void onMouseUp(double x, double y)
	{
		if (!visible || !enabled)
			return;
		
		if (active!=0 && selected==-1)
			setSelectedIndex(0);
		else
		if (active==1 && selected>0)
		{
			setSelectedIndex(selected-1);
			if (!(selected>0 || selected==-1))
			{
				active = 0;			
				changeCursor(Comm.CURSOR_DEF);
			}
		}
		else
		if (active==2)
		{
			setSelectedIndex(selected+1);		
			if (!(selected!=options.size()-1 || selected==-1))
			{
				active = 0;			
				changeCursor(Comm.CURSOR_DEF);
			}
		}
				
		Main.main.canvas.paint();
	}

	public void onMouseClick(double x, double y)
	{
	}

	public void onKeyDown(int key)
	{
		if (!visible || !enabled)
			return;
		
		if (key==Comm.VK_DOWN || key==Comm.VK_RIGHT)
		{
			if (selected==-1)
				setSelectedIndex(0);
			else
			{
				setSelectedIndex(selected+1);
				if (!(selected!=options.size()-1 || selected==-1))
				{
					active = 0;			
					changeCursor(Comm.CURSOR_DEF);
				}
			}
		}
		else
		if (key==Comm.VK_UP || key==Comm.VK_LEFT)
		{
			if (selected==-1)
				setSelectedIndex(0);
			else if (selected!=0)
			{
				setSelectedIndex(selected-1);
				if (!(selected>0 || selected==-1))
				{
					active = 0;			
					changeCursor(Comm.CURSOR_DEF);
				}				
			}
		}			
		
		Main.main.canvas.paint();
	}

	public void onKeyTyped(int key)
	{
	}

	@Override
	public void onKeyUp(int key)
	{
	}

}
