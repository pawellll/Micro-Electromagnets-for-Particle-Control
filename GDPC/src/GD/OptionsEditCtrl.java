package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUILabel;
import GUI.GUIList;
import Platform.CrossRes;
import Platform.Main;
import Supp.DrawingInterface;
import Supp.Str;

/**
 * Klasa kontrolera edycji szablonow opcji.
 */
public class OptionsEditCtrl extends Controller
{
	private static final String ID_BUT_PREV = "ID_BUT_PREV";
 	private static final String ID_BUT_NEXT = "ID_BUT_NEXT"; 	
 	private static final String ID_BUT_SAVE = "ID_BUT_SAVE"; 	
	
	private String tempName = "";
	private Opts.Option[] options = null;
	
	private GUILabel info;
	private final GUIList[] lists = new GUIList[4];
	private final GUILabel[] labels = new GUILabel[4];
	private int currentIndex = 0;
	private boolean allowOnChange = true;				
	
					
	public OptionsEditCtrl(String templateName, String optionsString )
	{
		executeOnLost = true;	
		tempName = templateName;
		options = Opts.stringToOptions(optionsString);
	}
	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.OPTE_TITLE;		
		gui.setIconsEnabled(false);
		
		info = new GUILabel(5, 3, 70, 15, Str.STR_EMPTY, 120, 25);
		changeInfo();
		gui.addComponent(info);
		
		labels[0] = new GUILabel(10, 17, 70, 8, Str.LIST_CHOOSE, 120, 25);
		lists[0] = new GUIList("Lista1", 15, 25, 70, 5);
			
		labels[1] = new GUILabel(10, 32, 70, 8, Str.LIST_CHOOSE, 120, 25);
		lists[1] = new GUIList("Lista2", 15, 40, 70, 5);
			
		labels[2] = new GUILabel(10, 47, 70, 8, Str.LIST_CHOOSE, 120, 25);
		lists[2] = new GUIList("Lista3", 15, 55, 70, 5);
			
		labels[3] = new GUILabel(10, 62, 70, 8, Str.LIST_CHOOSE, 120, 25);
		lists[3] = new GUIList("Lista4", 15, 70, 70, 5);
		
		for (int i=0; i<4; i++)//labels.length;
		{
			gui.addComponent(labels[i]);
			gui.addComponent(lists[i]);
		}	
		
		GUIButton but = new GUIButton(ID_BUT_PREV, 50, 86, 20, 7, Str.OPTE_PREV);
		gui.addComponent(but);
			
		but = new GUIButton(ID_BUT_NEXT, 75, 86, 20, 7, Str.OPTE_NEXT);
		gui.addComponent(but);
			
		but = new GUIButton(ID_BUT_SAVE, 5, 86, 20, 7, Str.OPTE_SAVE);
		gui.addComponent(but);
		
		update();	
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
	}

	@Override
	public void onLostControl()
	{
		executeOnLost = false;
		Main.main.mainCrtl.chageController(new OptionsCtrl());
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		if (e.action == GUIAction.LIST_CHANGE && allowOnChange)
		{
			options[Integer.valueOf(e.id)].set((Integer)e.data);
			options = Opts.updateEnabledOptions(options);
			update();
		}
		
		switch (e.id)
		{
			case ID_BUT_NEXT:
				if (currentIndex+4 < options.length)
					currentIndex += 4;

				if (currentIndex >= options.length)
					currentIndex = options.length - 1;
								
				update();
			break;
				
			case ID_BUT_PREV:
				currentIndex -= 4;
				if (currentIndex < 0)
					currentIndex = 0;
				update();
			break;
				
			case ID_BUT_SAVE:
				for (int i=0; i < options.length; i++)
				{
					if (options[i].askable)
					{
						if (options[i].is(options[i].opts.size()-2))
						{
							options[i].ask = Opts.Option.ASK_ONCE;
							options[i].set(0);
						}
						else
						if (options[i].is(options[i].opts.size()-1))
						{
							options[i].ask = Opts.Option.ASK_ALWAYS;
							options[i].set(0);
						}
					}
				}
			
				CrossRes.saveText(CrossRes.getHomeDir()+tempName+".gdtpl", Opts.optionsToString(options));
				onLostControl();
			break;
			
		}
	}

	
	
	private void changeInfo()
	{	
		String s = " ("+((currentIndex /4 )+1)+"/"+((int)Math.ceil(options.length / 4.0))+")";
		info.text[0] = Str.OPTE_INFO[0]+ s;
		info.text[1] = Str.OPTE_INFO[1]+ s; 
	}
	
	private void update()
	{
		options = Opts.updateEnabledOptions(options);
		
		changeInfo();
		allowOnChange = false;
		for (int i=0; i < 4; i++)
		{
			if (currentIndex+i>=options.length)
			{
				lists[i].visible = false;
				labels[i].visible = false;
			}
			else
			{
				lists[i].visible = true;
				labels[i].visible = true;		
				
				if (options[currentIndex+i].ask == Opts.Option.ASK_ONCE)
					options[currentIndex+i].set(options[currentIndex+i].opts.size()-2);
				else
				if (options[currentIndex+i].ask == Opts.Option.ASK_ALWAYS)
					options[currentIndex+i].set(options[currentIndex+i].opts.size()-1);
				
				if (options[currentIndex+i].ask > 0)
					options[currentIndex+i].ask = 0;				
				
				labels[i].enabled = options[currentIndex+i].enabled;
				lists[i].enabled = options[currentIndex+i].enabled;				
				
				labels[i].text = options[currentIndex+i].name;	
				
				lists[i].id = options[currentIndex+i].ID()+"";
				lists[i].clearOptions();
				for (String[] opt: options[currentIndex+i].opts)
				{
					lists[i].addOptionWithLang(opt);
				}

				lists[i].setSelectedIndex(options[currentIndex+i].selected);			
			}
		}
		
		options = Opts.updateEnabledOptions(options);		
		allowOnChange = true;
		
		Main.main.canvas.paint();
	}
	
	
	
}
