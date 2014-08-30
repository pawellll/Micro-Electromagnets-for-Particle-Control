package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import GUI.GUILabel;
import GUI.GUIList;
import GUI.GUIMain;
import GUI.GUITextInput;
import Platform.CrossRes;
import static Platform.CrossRes.GUI;
import Platform.Main;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Str;
import java.util.List;
import sun.swing.FilePane;

/**
 * Klasa kontrolera opcji.
 */
public class OptionsCtrl extends Controller
{
	private static final String ID_BUT_CREATE = "ID_BUT_CREATE";
 	private static final String ID_BUT_EDIT   = "ID_BUT_EDIT"; 
	private static final String ID_LIST_TEMP  = "ID_LIST_TEMP";
	private static final String ID_NEW_TEMP   = "ID_NEW_TEMP";
	
	private GUIList newTempList = null;
	private GUIList editTempList = null;
	private GUITextInput newTempName = null;
	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.GUI_ICON_TITLES[2];		
		
		
		GUILabel lab = new GUILabel(10, 6, 70, 15, Str.OPT_NEW, 120, 25);
		gui.addComponent(lab);
			
		lab = new GUILabel(10, 22, 20, 8, Str.OPT_NAME, 120, 25);
		gui.addComponent(lab);
		
		lab = new GUILabel(10, 30, 20, 8, Str.OPT_BASE, 120, 25);
		gui.addComponent(lab);
		
		newTempName = new GUITextInput(ID_NEW_TEMP, 35, 22, 55, 5, Str.OPT_INPUT_DEF);
		gui.addComponent(newTempName);
		
		newTempList = new GUIList(ID_LIST_TEMP, 35, 31.5, 55, 5);
		newTempList.addOption(Str.get(Str.OPT_DEFAULT), Opts.optionsToString(Opts.createDefaultSet()));
		List<String> temps = CrossRes.getTemplates(false);
		List<String> tempsFull = CrossRes.getTemplates(true);
		String content;
		for (int i=0; i < temps.size(); i++)
		{
			content = CrossRes.loadText(tempsFull.get(i));
			if (Opts.stringToOptions(content, tempsFull.get(i)) != null)
				newTempList.addOption(temps.get(i), content);
		}
		gui.addComponent(newTempList);
		
		
		GUIButton but = new GUIButton(ID_BUT_CREATE, 70, 43, 20, 7, Str.OPT_CREATE);
		gui.addComponent(but);
		
		lab = new GUILabel(10, 48, 40, 15, Str.OPT_EDIT_TEMPLATE, 120, 25);
		gui.addComponent(lab);
		
		lab = new GUILabel(10, 62, 20, 8, Str.OPT_TEMPLATE, 120, 25);
		gui.addComponent(lab);
		
		editTempList = new GUIList(ID_LIST_TEMP, 35, 63.5, 55, 5);
		editTempList.cloneList(newTempList);
		gui.addComponent(editTempList);	
		
		but = new GUIButton(ID_BUT_EDIT, 70, 75, 20, 7, Str.OPT_EDIT);
		gui.addComponent(but);	
	
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		switch (e.id)
		{
			case ID_BUT_CREATE:	
				if (newTempName.text.isEmpty())
					gui.setInfoLongest(Str.get(Str.OPT_EMPTY_NAME_ERR), GUIMain.INFO_WARN);
				else				
				if (newTempList.getSelectedIndex()<0)
					gui.setInfoLongest(Str.get(Str.OPT_EMPTY_TEMP_ERR), GUIMain.INFO_WARN);
				else
					Main.main.mainCrtl.chageController(new OptionsEditCtrl(newTempName.text, newTempList.getAdditional()));
				

			break;
				
			case ID_BUT_EDIT:
				if (editTempList.getSelectedIndex() == 0)
					gui.setInfoLongest(Str.get(Str.OPT_EDIT_DEF_ERR), GUIMain.INFO_WARN);
				else
				if (editTempList.getSelectedIndex()<0)
					gui.setInfoLongest(Str.get(Str.OPT_EMPTY_TEMP_ERR), GUIMain.INFO_WARN);		
				else
					Main.main.mainCrtl.chageController(new OptionsEditCtrl(editTempList.getSelected(), editTempList.getAdditional()));
			break;
		}
		
		Main.main.canvas.paint();
	}

	
}
