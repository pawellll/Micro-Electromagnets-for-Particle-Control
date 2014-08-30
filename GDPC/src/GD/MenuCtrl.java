package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import GUI.GUILabel;
import GUI.GUIList;
import GUI.GUIProgress;
import Platform.CrossBitmap;
import Platform.CrossRes;
import static Platform.CrossRes.GUI;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;
import java.util.List;

/**
 * Klasa kontrolera glownego menu.
 */
public class MenuCtrl extends Controller
{
	private static final String ID_BUT_LOAD_IMG_PC = "ID_BUT_LOAD_IMG_PC";
 	private static final String ID_BUT_LOAD_DIR = "ID_BUT_LOAD_DIR"; 
	private static final String ID_BUT_LOAD_IMG_AND = "ID_BUT_LOAD_IMG_AND";
	private static final String ID_IMG_TAKE_IMG = "ID_BUT_TAKE_IMG";
	private static final String ID_LIST_TEMP = "ID_LIST_TEMP";
	
	private GUIImage ram = null;
	private GUIProgress loadProgress = null;
	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.STR_EMPTY;
		
		GUIButton but = new GUIButton(ID_BUT_LOAD_IMG_PC, 10, 15, 20, 8, Str.MENU_LOAD_IMG);
		but.longestText = Str.MENU_LOAD_IMG;
		gui.addComponent(but);	
    
		if (!Main.ANDROID)
    {
      but = new GUIButton(ID_BUT_LOAD_DIR, 10, 30, 20, 8, Str.MENU_LOAD_DIR);
      but.longestText = Str.MENU_LOAD_DIR;		
      gui.addComponent(but);	
		}
		
		if (Dim.w > Dim.h) 
		{
			ram = new GUIImage(ID_IMG_TAKE_IMG, 60, 10, 20, 20);
			ram.setImage(GUI[Comm.R_IMG_FRAME], Comm.STRETCH_H_PROP);
		}
		else 
		{
			ram = new GUIImage(ID_IMG_TAKE_IMG, 60, 10, 20, 35);
			ram.setImage(GUI[Comm.R_IMG_FRAME], Comm.STRETCH_V_PROP);
		}

		if (Main.ANDROID)
			ram.setClickable(true);
		
		gui.addComponent(ram);   
		
		
		GUILabel lab = new GUILabel(10, 65, 30, 9, Str.MENU_TEMPLATE, 120, 25);
		gui.addComponent(lab);
		
		GUIList list = new GUIList(ID_LIST_TEMP, 40, 67, 50, 5);
		list.addOption(Str.get(Str.OPT_DEFAULT), Opts.optionsToString(Opts.createDefaultSet()));
		List<String> temps = CrossRes.getTemplates(false);
		List<String> tempsFull = CrossRes.getTemplates(true);
		String content;
		for (int i=0; i < temps.size(); i++)
		{
			content = CrossRes.loadText(tempsFull.get(i));
			if (Opts.stringToOptions(content, tempsFull.get(i)) != null)
				list.addOption(temps.get(i), content);
		}
			
		list.setSelectedIndex(0);
		gui.addComponent(list);
		
		loadProgress = new GUIProgress(10, 80, 80, 3);
		loadProgress.showProgress=true;
		loadProgress.visible = false;
		gui.addComponent(loadProgress);

		CrossRes.loadProgress = loadProgress;
		CrossRes.setDropFileEnabled(true);
	}

	@Override
	public void onLostControl()
	{
		CrossRes.loadProgress = null;
		CrossRes.setDropFileEnabled(false);	
	}
	
	
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		//Logo
		g.gStretchBitmap(Dim.X(0), 0, 0, gui.margin, CrossRes.GUI[Comm.R_LOGOTYPE], Comm.STRETCH_V_PROP);
    
    //Linia
    g.gSetColor(Skin.LINE);
    g.gDrawLine(Dim.X(40), Dim.Y(10), Dim.X(40), Dim.Y(60));
	}
	
	@Override
	public void onAfterPaint(DrawingInterface g, int width, int height)
	{
		String s;
		if (Main.ANDROID)
			s = Str.get(Str.MENU_TAKE_IMG);
		else
			s = Str.get(Str.MENU_DROP_IMG);
		
		g.gSetFontName(Skin.FONT);
		g.gSetColor(Colors.RGB(190, 190, 190));
		g.gSetFontSize(s, Dim.W(ram.w)*0.7, Dim.H(ram.h/2)*0.5);
		g.gDrawText(ram.getX(), ram.getY()+Dim.H(ram.h/2), Dim.W(ram.w), Dim.H(ram.h/2) , s, Comm.ALIGN_VCENTER | Comm.ALIGN_CENTER);
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		List<CrossBitmap> imgs;
		
		switch (e.id)
		{
			case ID_BUT_LOAD_IMG_PC:
				imgs =  CrossRes.chooseAndLoadImages();
//				if (imgs != null)		
//					System.out.println(imgs);
				loadProgress.visible = false;					
			break;
				
			case ID_BUT_LOAD_DIR:					
				imgs =  CrossRes.chooseAndLoadDir();
//				if (imgs != null)		
//					System.out.println(imgs);
				loadProgress.visible = false;				
			break;
				
		}
		
		if (e.action == GUIAction.DROP_FILE)
		{
			imgs =  (List<CrossBitmap>)e.data;
			if (imgs != null)		
				System.out.println(imgs);
		}
		
	}
	

	
}
