package GUI;

import GD.AboutCtrl;
import GD.DrawGraphCtrl;
import GD.MenuCtrl;
import GD.OptionsCtrl;
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
import org.eclipse.swt.SWT;

/**
 * Klasa bedaca glownym kontrolerem GUI.
 */
public class GUIMain
{
	//Definicje kolorów
	public final static int C_GRAY    = Colors.RGB(124, 124, 124); //7c7c7c;
	public final static int C_GRAY_L  = Colors.RGB(242, 242, 242); //f2f2f2;

	public final static int C_GREEN_D = Colors.RGB(135, 167, 66);  //87a742;
	public final static int C_GREEN   = Colors.RGB(144, 191, 73);  //90bf49;
	public final static int C_GREEN_L = Colors.RGB(202, 217, 169); //cad9a9;

	public final static int C_BORDER  = Colors.RGB(115, 115, 115); //cad9a9;

	//Definicje informacji
	public final static int INFO_INFO = 0;
	public final static int INFO_WARN = 1;
	public final static int INFO_OK   = 2;


	//Zmienne
	public int width = 100;
	public int height = 100;
	public int margin = 0;
	public int iconSelected;
	public String[] title = {"", ""};

	//Komponenty
	private final List<GUIComponent> components = new ArrayList<>();
	private GUIComponent focused = null;
	private GUIComponent last = null;
  private String info = "";
	private List<String> infoList = new ArrayList<>();
	private String infoLongest = "the really longest text goes here.";
	private int infoType = 0;
	private int langOver = -1;
	private int iconOver = -1;
	private boolean iconsEnabled = true;
	private boolean requestconfir = false;	
	private int requestOver = -1;

	public void clearComponents()
	{
		focused = null;
		components.clear();
	}

	public void addComponent(GUIComponent newComponent)
	{
		components.add(newComponent);
		Main.main.canvas.paint();
	}
	
	public GUIComponent getFocused()
	{
		return focused;
	}

	public void setIconsEnabled(boolean status)
	{
		iconsEnabled = status;
		if (!iconsEnabled)
			Main.main.canvas.changeCursor(Comm.CURSOR_DEF);
	}

  public void setInfo(String info, int type)
  {
		if (this.info.equals(""))
			this.info = info;
		else
			infoList.add(info);
    
		infoType = type;
  }
	
  public void setInfo(String info)
  {
    setInfo(info, INFO_INFO);
  }

  public void setInfoLongest(String info, int type)
  {
		setInfoLongestText(info);
    setInfo(info, type);
  }

  public void setInfoLongestText(int maxChar)
  {
    infoLongest = "";
		for (int i=0; i < maxChar; i++)
			infoLongest = infoLongest+"W";
  }

  public void setInfoLongestText(String infoLongest)
  {
    this.infoLongest = infoLongest;
  }

	private int getIconHeight()
	{
		return Math.min((int)((height-margin)*0.2), (int)(margin));
	}

  public void onDraw(DrawingInterface g, int w, int h)
  {
		//Aktualizacja
		width = w;
		height = h;
		Dim.updateMargin(width, height, 0);

    margin = Dim.XMax(13,80);
    Dim.updateMargin(width, height, margin);

		//Tlo
    g.gSetColor(Skin.BCG_L);
    g.gFillRectangle(0, 0, w, h);

    //Menu
    g.gSetColor(Skin.BCG_D);
    g.gFillRectangle(0, 0, margin, height);
    g.gFillRectangle(0, 0, width, margin);

    //Logo
		g.gStretchBitmap(5, 5, 0, Dim.X(0)-10, CrossRes.GUI[Comm.R_LOGO], Comm.STRETCH_V_PROP);

		//Tytul
		g.gSetFontSize(Str.get(title), (width-margin)*0.8, margin*0.5);
		g.gSetFontName(Skin.FONT);
		g.gSetColor(Skin.ICON);
		g.gDrawText((int)(margin*1.2), 0, width, margin, Str.get(title), Comm.ALIGN_VCENTER);

		//Rysuj controller
		if (Main.main.mainCrtl.currentCtrl!=null)
			Main.main.mainCrtl.currentCtrl.onPaint(g, width, height);

		//Rysuj komponenty
		for (GUIComponent componet: components)
		{
			componet.onPaint(g, width, height);
		}

		//Rysuj przelaczanie jezyka
		g.gStretchAlignBitmap(w-margin*2+(int)(margin/4.0), 0, margin, margin, (int)(margin*0.6), 0, CrossRes.GUI[Comm.R_LANG_PL], Comm.STRETCH_H_PROP, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
		g.gStretchAlignBitmap(w-margin, 0, margin, margin, (int)(margin*0.6), 0, CrossRes.GUI[Comm.R_LANG_EN], Comm.STRETCH_H_PROP, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);


		//Rysuj menu
		int iconH = getIconHeight();
		int iconID;

		for (int i=0; i<5; i++)
		{
			if (i==iconOver && (iconsEnabled || i == 4))
			{
				g.gSetColor(Skin.BCG_D);
				g.gFillRectangle(margin, margin+5+i*iconH+(int)(iconH*0.1), Dim.W(35), (int)(iconH*0.8));
				g.gSetColor(Skin.ICON);
				g.gSetFontSize(Str.get(Str.GUI_ICON_TITLES[4]), Dim.W(31), (int)(iconH*0.8*0.5));
				g.gDrawText(margin+Dim.W(2), margin+5+i*iconH+(int)(iconH*0.1), Dim.W(33), (int)(iconH*0.8), Str.get(Str.GUI_ICON_TITLES[(i == 4 && !iconsEnabled)?i+1:i]), Comm.ALIGN_VCENTER);
			}

			iconID = i + Comm.R_ICON_1_OFF - ((iconSelected==i || iconOver==i && (iconsEnabled || i == 4)) ? 6:0);
			if (i == 4 && !iconsEnabled)
				iconID++;
			g.gStretchAlignBitmap(0, margin+5+i*iconH, margin, iconH, 0, (int)(iconH*0.9), CrossRes.GUI[iconID], Comm.STRETCH_V_PROP, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);

		}

		//Rysuj controller after-paint
		if (Main.main.mainCrtl.currentCtrl!=null)
			Main.main.mainCrtl.currentCtrl.onAfterPaint(g, width, height);

		//Okno z info/warn/ok
    if (!info.isEmpty())
    {
      g.gSetColor(0, 150);
      g.gFillRectangle(0, 0, w, h);
			g.gSetColor(Skin.BCG_D);
			g.gFillRectangle(Dim.X(10), Dim.Y(30), Dim.W(80), Dim.H(40));
      g.gSetColor(Skin.BUTTON_OFF);
			if (requestconfir)
			{
				g.gFillRectangle(Dim.X(10), Dim.Y(60), Dim.W(40)-1, Dim.H(10));				
				g.gFillRectangle(Dim.X(50)+1, Dim.Y(60), Dim.W(40), Dim.H(10));				
				g.gSetColor(Skin.BUTTON_TEXT);
				g.gDrawText(Dim.X(10), Dim.Y(60), Dim.W(40), Dim.H(10), Str.get(Str.GUI_YES), Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
				g.gDrawText(Dim.X(50), Dim.Y(60), Dim.W(40), Dim.H(10), Str.get(Str.GUI_NO), Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
			}
			else
			{
				g.gFillRectangle(Dim.X(10), Dim.Y(60), Dim.W(80), Dim.H(10));
				g.gSetColor(Skin.BUTTON_TEXT);
				g.gDrawText(Dim.X(10), Dim.Y(60), Dim.W(80), Dim.H(10), Str.get(Str.GUI_INFO_OK), Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
			}
			
			//Wybor obrazka
			int infoImg = Comm.R_INFO_INFO;
			if (infoType == INFO_WARN)
				infoImg = Comm.R_INFO_WARN;
			else if (infoType == INFO_OK)
				infoImg = Comm.R_INFO_OK;

			int flag;
			if (w>h)
				flag = Comm.STRETCH_V_PROP;
			else
				flag = Comm.STRETCH_H_PROP;

			g.gStretchAlignBitmap(Dim.X(10), Dim.Y(30), Dim.W(20), Dim.H(30), Dim.W(19), Dim.H(19), CrossRes.GUI[infoImg], flag, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);

			if (infoType == INFO_WARN)
				g.gSetColor(Skin.ICON);
			else
				g.gSetColor(Skin.BCG_D_TEXT);
      g.gSetFontSize(infoLongest, Dim.W(50), Dim.H(10));
      g.gDrawText(Dim.X(30), Dim.Y(30), Dim.W(20), Dim.H(30), info, Comm.ALIGN_VCENTER);
    }


  }

	public GUIComponent getComponentAtXY(int x, int y)
	{
		for (GUIComponent componet: components)
		{
			if (componet.visible)
				if (x>=componet.getX() && x<=componet.getX()+Dim.W(componet.w) &&
						y>=componet.getY() && y<=componet.getY()+Dim.H(componet.h))
					return componet;
		}
		return null;
	}

	public void onMouseDown(int x, int y)
	{
    if (!info.isEmpty())
    {
			if (requestconfir)
			{
				if (requestOver == 0)
				{
					setIconsEnabled(true);
					iconOver = -1;
					Main.main.canvas.changeCursor(Comm.CURSOR_DEF);		
					Main.main.mainCrtl.currentCtrl.onLostControl();
				}
			}			
			
			requestconfir = false;			
			if (infoList.isEmpty())
				info = "";
			else
			{
				info = infoList.get(0);
				infoList.remove(0);
			}
			Main.main.canvas.paint();
      return;
    }

		if (langOver == 0)
			Str.currentLang = Str.LANG_EN;
		else
		if (langOver == 1)
			Str.currentLang = Str.LANG_PL;

		if (x<margin)
		{
			if (iconOver>=0)
			{
				Main.main.canvas.changeCursor(Comm.CURSOR_DEF);
				if (iconsEnabled)
				{
					iconSelected = iconOver;
					switch (iconOver)
					{
						case 0:	Main.main.mainCrtl.chageController(new MenuCtrl());	break;
						case 1:	Main.main.mainCrtl.chageController(new DrawGraphCtrl());			break;
						case 2:	Main.main.mainCrtl.chageController(new OptionsCtrl());	break;
						case 3: Main.main.mainCrtl.chageController(new AboutCtrl());	break;
						case 4:	Main.main.Terminate(); break;
					};
					iconOver = -1;
					Main.main.canvas.changeCursor(Comm.CURSOR_DEF);
				}
				else
					if (iconOver == 4)
					{
						requestconfir = true;
						setInfoLongest(Str.get(Str.GUI_CONFIRM), INFO_WARN);
						Main.main.canvas.changeCursor(Comm.CURSOR_DEF);						
					}
					else
						setInfoLongest(Str.get(Str.GUI_ICON_DIS), INFO_WARN);
			}
		}
		


		focused = null;
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			if (temp.focusable)
				focused = temp;
			temp.onMouseDown(x, y);
		}
		
		Main.main.canvas.paint();
	}

	public void onMouseMove(int x, int y)
	{
    if (!info.isEmpty())
		{
			requestOver = -1;
			if (requestconfir)
			{
				if (y>Dim.Y(60) && y<Dim.Y(60)+Dim.H(10))
				{
					if (x>Dim.X(10) && x<Dim.X(10)+Dim.W(40))
						requestOver = 0;
					else
					if (x>Dim.X(50) && x<Dim.X(50)+Dim.W(40))
						requestOver = 1;
				}	
			}			
			
			if (requestOver > -1 || (!requestconfir))
				Main.main.canvas.changeCursor(Comm.CURSOR_HAND);
			else
				Main.main.canvas.changeCursor(Comm.CURSOR_DEF);
			return;
		}

		boolean changeCur = (langOver>-1);
		langOver = -1;
		if (y<margin)
		{
			if (x>width-margin)
				langOver = 0;
			else
			if (x>width-margin*2+(int)(margin/4.0))
				langOver = 1;

			if (langOver>=0)
				Main.main.canvas.changeCursor(Comm.CURSOR_HAND);
		}

		if (changeCur && langOver<0)
			Main.main.canvas.changeCursor(Comm.CURSOR_DEF);

		int iconH = getIconHeight();
		changeCur = (iconOver>-1);
		iconOver = -1;

		if (x<margin)
		{
			for (int i=0; i<5; i++)
				if (y>=margin+5+i*iconH && y<margin+5+(i+1)*iconH && i!=iconSelected)
				{
					iconOver = i;
					if (iconsEnabled || i==4)
						Main.main.canvas.changeCursor(Comm.CURSOR_HAND);
					break;
				}
		}

		if (changeCur && iconOver<0)
			Main.main.canvas.changeCursor(Comm.CURSOR_DEF);


		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			if (last!=null)
			{
				if (temp!=last)
				{
					last.onMouseExit();
					last = temp;
				}
			}
			else
			{
				last = temp;
				last.onMouseEnter();
			}

			temp.onMouseMove(x, y);
		}
		else
		if (last!=null)
		{
			last.onMouseExit();
			last = null;
		}

//		if ((x<margin)&&((y>height-margin*2+(int)(margin/3.0) && y<height-margin) || (y>height-margin)))
//			Main.main.canvas.changeCursor(Comm.CURSOR_HAND);
//		else
//			Main.main.canvas.changeCursor(Comm.CURSOR_DEF);

		Main.main.canvas.paint();
	}

	public void onMouseDragged(int x, int y)
	{
    if (!info.isEmpty())
			return;
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
		{
			temp.onMouseDragged(x, y);
		}
	}

	public void onMouseUp(int x, int y)
	{
    if (!info.isEmpty())
			return;
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
			temp.onMouseUp(x, y);
	}

	public void onMouseClick(int x, int y)
	{
    if (!info.isEmpty())
			return;
		GUIComponent temp = getComponentAtXY(x, y);
		if (temp!=null)
			temp.onMouseClick(x, y);
	}

	public void onKeyDown(int key)
	{
    if (!info.isEmpty())
    {
			requestconfir = false;
      info = "";
			infoList.clear();
			Main.main.canvas.paint();			
      return;
    }

		if (focused!=null)
			focused.onKeyDown(key);
		
		Main.main.canvas.paint();
	}

	public void onKeyTyped(char key)
	{
    if (!info.isEmpty())
			return;
		if (focused!=null)
			focused.onKeyTyped(key);
	}

	public void onKeyUp(int key)
	{
    if (!info.isEmpty())
			return;
		if (focused!=null)
			focused.onKeyUp(key);
	}

}
