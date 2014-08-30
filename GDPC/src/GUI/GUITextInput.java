package GUI;

import Platform.CrossRes;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import Supp.Str;

/**
 * Klasa realizujaca pole wprowadzania tekstu.
 */
public class GUITextInput extends GUIComponent
{
	public String text = "";
	public String title[] = {"", ""};
	
	private int cursor = 0;
	private int[] textSize = null;
	private String lastText = "";
	
	public GUITextInput(String id, double x, double y, double width, double height, String[] defaultTitle)
	{
		super(id, x, y, width, height);
		title = defaultTitle;
		focusable = true;
	}

	@Override
	public void onKeyDown(int key)
	{	
		switch (key)
		{
			case Comm.VK_LEFT:
				cursor--;
			break;
				
			case Comm.VK_RIGHT:
				cursor++;
			break;
				
			case Comm.VK_DEL:
				if (!text.isEmpty() && cursor<text.length())
					text = text.substring(0,cursor)+text.substring(cursor+1,text.length());
			break;
				
			case Comm.VK_BCKSPC:
				cursor--;
				if (!text.isEmpty() && cursor<text.length() && cursor>=0)
					text = text.substring(0,cursor)+text.substring(cursor+1,text.length());
			break;	
				
		}

//		if ( (key>=(int)'a' && key<=(int)'z') || (key>=(int)'a' && key<=(int)'z') )
		
		if (cursor<0)
			cursor = 0;
		else
		if (cursor>text.length())
			cursor = text.length();
		
	}

	@Override
	public void onKeyTyped(char key)
	{
		if (Character.isLetterOrDigit(key) || Character.isSpaceChar(key))
		{
			if (text.isEmpty() || cursor==text.length())
				text += key;
			else
				text = text.substring(0,cursor)+ key + text.substring(cursor,text.length());
			
			onKeyDown(Comm.VK_RIGHT);
		}
	}
	
	@Override
	public void onMouseExit()
	{
		Main.main.canvas.changeCursor(Comm.CURSOR_DEF);		
	}

	@Override
	public void onMouseEnter()
	{
		Main.main.canvas.changeCursor(Comm.CURSOR_TEXT);
	}

	@Override
	public void onMouseDown(double x, double y)
	{
		if (textSize!= null && textSize.length>0)
		{
			int cx = getX() - 1 + (int)( Dim.W(w)/2.0 - textSize[textSize.length-1]/2.0 );
			if (x<cx)
				cursor = 0;
			else
			if (x>cx+textSize[textSize.length-1])
				cursor = text.length();
			else
			{
				double relX = x-cx;

				for (int i=0; i < textSize.length; i++)
				{		
					if (textSize[i] >= relX)
					{
						cursor = i+1;
						break;
					}
				}
			}
		}
	}
	

	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		boolean focused = (Main.main.GUI.getFocused() == this);
				
		if (focused)
			g.gSetColor(Skin.BUTTON_ON);
		else
			g.gSetColor(Skin.BCG_D);
		g.gDrawLine(getX(), getY()+Dim.H(h), getX()+Dim.W(w), getY()+Dim.H(h));
		g.gDrawLine(getX(), getY()+Dim.H(h), getX(), getY()+Dim.H(h)-(int)(Dim.H(h)*0.2));
		g.gDrawLine(getX()+Dim.W(w), getY()+Dim.H(h), getX()+Dim.W(w), getY()+Dim.H(h)-(int)(Dim.H(h)*0.2));	
		
		String s ="";
		if (text.isEmpty() && !focused)
		{
			g.gSetColor(Colors.RGB(180, 180, 180));
			s = Str.get(title);
		}
		else
		{
			g.gSetColor(Skin.BCG_L_TEXT);
			s = text;
		}
		
		g.gSetFontSize(s, Dim.W(w)*0.9, Dim.H(h)*0.9);
		g.gDrawText(getX()+Dim.W(0.05*w), getY(), Dim.W(w*0.9, minX), Dim.H(h, minY), s, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);			
		
		textSize = new int[text.length()];
		for (int i=0; i<text.length(); i++)
		{
			textSize[i] = g.gGetTextWidth(text.substring(0, i+1));

		}
	
		if ( focused )
		{
			g.gSetColor(Skin.BCG_D);
			int cx = getX() - 1 + (int)( Dim.W(w)/2.0 - g.gGetTextWidth(s)/2.0 );
			cx += g.gGetTextWidth(s.substring(0,cursor));
			g.gDrawLine(cx, getY()+Dim.H(h*0.1), cx, getY()+Dim.H(h*0.9));
		}
		
	//	g.gDrawText(getX()+Dim.W(0.1*w), getY(), Dim.W(w*0.8, minX), Dim.H(h, minY), text, Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER);
				
	}
		
	
	
	
}
