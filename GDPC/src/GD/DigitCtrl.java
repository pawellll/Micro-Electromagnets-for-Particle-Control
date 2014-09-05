package GD;

import Digit.CIELab;
import Digit.ColorsUtils;
import Digit.DigitInput;
import Digit.DigitOutput;
import Digit.Digitalize;
import Digit.FrameDetecting;
import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIImage;
import GUI.GUILabel;
import GUI.GUIList;
import GUI.GUIMain;
import GUI.GUIProgress;
import Platform.CrossBitmap;
import Platform.CrossRes;
import static Platform.CrossRes.GUI;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DoubleArea;
import Supp.DoublePoint;
import Supp.DrawingInterface;
import Supp.ImageFile;
import Supp.Skin;
import Supp.Str;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Klasa realizujaca kontroler digitalizacji wykresu.
 */
public class DigitCtrl extends Controller
{
	//STALE STANOW KONTROLERA
	private static final int STATE_INIT            = 0;	
	private static final int STATE_TEMP_ONCE       = 1;	
	private static final int STATE_TEMP_ALWAYS     = 2;	
	private static final int STATE_EDIT_TRASH      = 3;	
	private static final int STATE_CUT_TITLE       = 4;	
	private static final int STATE_CUT_TITLE_X     = 5;	
	private static final int STATE_CUT_TITLE_Y     = 6;	
	private static final int STATE_CUT_VALUE_X     = 7;	
	private static final int STATE_CUT_VALUE_Y     = 8;	
	private static final int STATE_CUT_LEGEND      = 9;	
	private static final int STATE_CUT_GRAPH       = 10;	
	private static final int STATE_EDIT_COLORS_CUT = 11;	
	private static final int STATE_PICK_COLORS     = 12;	
	private static final int STATE_DETECT_COLORS   = 13;	
	private static final int STATE_PICK_ELEMENTS   = 14;		
	private static final int STATE_FINALIZE        = 15;		

//STALE ID KOMPONENTOW
	private static final String ID_IMG_VIEW = "ID_IMG_VIEW";	
	private static final String ID_IMG_LIST = "ID_IMG_LIST";	
	private static final String ID_BUT_PREV = "ID_BUT_PREV";	
	private static final String ID_BUT_NEXT = "ID_BUT_NEXT";	
	
	
//ZMIENNE KOMPONENTOW
	private GUILabel    titleLabel = null, 
									    subTitleLabel = null, 
									    listTitle = null,
									    statusLabels = null,
									    thrLabel = null;
	private GUIImage    img = null;
	private GUIList     list = null,
											thr1 = null,
											thr2 = null;
	private GUIButton   prevBut = null,
									    nextBut = null;
	private GUIProgress progressMain = null,
											progress = null;
	
//POZOSTALE ZMIENNE
	private int currentImg = -1;
	private int currentOptionsIndex = -1;
	private int currrentColorPicker = -1;
	
	private final List<Integer> askOnceOpts = new ArrayList<>();
	private final List<Integer> askAlwaysOpts = new ArrayList<>();
	
	private Opts.Option[] options;	
	private ImageFile imgs[] = null;
	
	private DigitInput digitInput;
	private List<DigitOutput> digitOutput = new ArrayList<>();
	private int state = STATE_INIT;
	
//KONSTRUKTOR
	public DigitCtrl(ImageFile images[], String template)
	{
		imgs = images;
		options = Opts.stringToOptions(template);
		options = Opts.updateEnabledOptions(options);
		
		for (Opts.Option opt : options)
		{
			if (opt.ask == Opts.Option.ASK_ONCE)
				askOnceOpts.add(opt.ID());
			else
			if (opt.ask == Opts.Option.ASK_ALWAYS)
				askAlwaysOpts.add(opt.ID());
			
			if (opt.askable)
			{
				opt.opts.remove(opt.opts.size()-1);
				opt.opts.remove(opt.opts.size()-1);
			}
		}
		
	}

	
//METODY KONTROLERA	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		gui.title = Str.DIGIT_TITLE;
		gui.setIconsEnabled(false);		
		
		progressMain = new GUIProgress(10, 6, 80, 2);
		progressMain.max = 1 + 2*imgs.length;
		gui.addComponent(progressMain);
					
		titleLabel = new GUILabel(6, 10, 80, 11, Str.DIGIT_TEMP, 120, 25);
		gui.addComponent(titleLabel);
		
		img = new GUIImage(ID_IMG_VIEW, 0, 20, 30, 30);
		img.setContainer(100, 100);
		img.align = Comm.ALIGN_CENTER;
		img.setImage(GUI[Comm.R_IMG_FRAME], Comm.STRETCH_V_PROP);
		gui.addComponent(img);
	
		subTitleLabel = new GUILabel(6, 55, 80, 8, Str.DIGIT_TEMP_ONCE, 120, 25);
		gui.addComponent(subTitleLabel);	
		
		listTitle = new GUILabel(8, 65, 70, 7, Str.STR_EMPTY, 120, 25);
		gui.addComponent(listTitle);
		
		list = new GUIList(ID_IMG_LIST, 15, 75, 70, 5);
		gui.addComponent(list);
		
		prevBut= new GUIButton(ID_BUT_PREV, 50, 87, 20, 7, Str.OPTE_PREV);
		gui.addComponent(prevBut);		
		
		nextBut = new GUIButton(ID_BUT_NEXT, 75, 87, 20, 7, Str.OPTE_NEXT);
		gui.addComponent(nextBut);		
		
		moveState();
	}
	
	@Override
	public void onLostControl()
	{
		executeOnLost = false;
		gui.setIconsEnabled(true);
		Main.main.mainCrtl.chageController(new MenuCtrl());
	}	
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		if (state == STATE_TEMP_ONCE || state == STATE_TEMP_ALWAYS)
		{
			g.gSetColor(Skin.LINE);
			g.gDrawLine(Dim.X(6), Dim.Y(53), Dim.X(6) + Dim.W(88), Dim.Y(53));
		}
		else
		if ((state == STATE_PICK_COLORS || state == STATE_DETECT_COLORS) && digitInput.colors != null)
		{
			int i = 0;
			for (Integer col : digitInput.colors)
			{
				i += 4;
				g.gSetColor(col);
				g.gFillRectangle(Dim.X(5+i), img.getY()+Dim.H(img.h+3), Dim.W(3), Dim.H(5));
			}
		}
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		if (e.action == GUIAction.LIST_CHANGE)
		{
			if (state == STATE_DETECT_COLORS)
			{
				if (e.id.equals("thr1") || e.id.equals("thr2"))
					digitInput.colors = Digitalize.detectColorsWithThr(digitInput, thr1.getSelectedIndex()/2.0, thr2.getSelectedIndex()/2.0);
			}
			else
			{
				options[Integer.valueOf(e.id)].set((Integer)e.data);
				options = Opts.updateEnabledOptions(options);
			}
		}
		else
		if (e.action == GUIAction.IMG_SELECTED)
		{
			DoubleArea data = (DoubleArea) e.data;

			//Tak aby punkt Start był bardziej lewo-gornym niz End
			int startX = (int) Math.min(data.startX, data.endX);
			int startY = (int) Math.min(data.startY, data.endY);
			int endX = (int) Math.max(data.startX, data.endX);
			int endY = (int) Math.max(data.startY, data.endY);

			//Pozycja wzgledem obrazka, nie okna programu
			startX = startX - img.getX();
			startY = startY - img.getY();
			endX = endX - img.getX();
			endY = endY - img.getY();

			//Pozycja wzgledem oryginalnego obrazka
			startX = (int) (img.getImage().getWidth() * (double) startX / Dim.W(img.w));
			startY = (int) (img.getImage().getHeight() * (double) startY / Dim.H(img.h));
			endX = (int) (img.getImage().getWidth() * (double) endX / Dim.W(img.w));
			endY = (int) (img.getImage().getHeight() * (double) endY / Dim.H(img.h));

			CrossBitmap tempImg = img.getImage().getSubImage(startX, startY, Math.max(endX - startX,1), Math.max(endY - startY,1));
		
			switch (state)
			{
				case STATE_CUT_TITLE:
					digitInput.title = tempImg; break;

				case STATE_CUT_TITLE_X:
					digitInput.titleX = tempImg; break;

				case STATE_CUT_TITLE_Y:
					digitInput.titleY = tempImg; break;

				case STATE_CUT_VALUE_X:
					digitInput.valueX = tempImg; break;

				case STATE_CUT_VALUE_Y:
					digitInput.valueY = tempImg; break;

				case STATE_CUT_LEGEND:
					digitInput.legend = tempImg; break;

				case STATE_CUT_GRAPH:
					digitInput.graph = tempImg; break;
					
			}

				for (int ii = startX; ii < startX + (endX - startX); ++ii) 
					for (int jj = startY; jj < startY + (endY - startY); ++jj) 
						img.getImage().setRGB(ii, jj, digitInput.background);

				if (state != STATE_EDIT_TRASH)
					moveState();
				
			Main.main.canvas.repaint();			
		}
		else
		if (e.action == GUIAction.IMG_CLICK)
		{
			DoublePoint xy = (DoublePoint)e.data;
			//Pozycja wzgledem obrazka, nie okna programu
			int posX = (int)xy.X - img.getX();
			int posY = (int)xy.Y - img.getY();

			//Pozycja wzgledem oryginalnego obrazka
			posX = (int) (img.getImage().getWidth() * (double) posX / Dim.W(img.w));
			posY = (int) (img.getImage().getHeight() * (double) posY / Dim.H(img.h));
			
			if (currrentColorPicker == 0)
			{
				CrossBitmap bigger = new CrossBitmap(CrossBitmap.newBlank(500, 500));
				for (int ix = -5; ix < 5; ix++)
					if (posX+ix >= 0 && posX+ix < img.getImage().getWidth() )
						for (int iy = -5; iy < 5; iy++)
							if (posY+iy >= 0 && posY+iy < img.getImage().getHeight())
							{
								for (int bx = (ix+5)*50; bx < (ix+5)*50+50; bx++ )
									for (int by = (iy+5)*50; by < (iy+5)*50+50; by++ )
										bigger.setRGB(bx, by, img.getImage().getRGB(posX+ix, posY+iy));
							}
				img.setImage(bigger);
				currrentColorPicker++;
			}
			else
			if (currrentColorPicker == 1)
			{
				int col = img.getImage().getRGB(posX, posY);
				currrentColorPicker = 0;
				
				if (state == STATE_EDIT_COLORS_CUT)
				{
					if (col != digitInput.background)
						for (int bx = 0; bx < digitInput.graph.getWidth(); bx++)
							for (int by = 0; by < digitInput.graph.getHeight(); by++)
								if (CIELab.delta(digitInput.graph.getRGB(bx, by), col) < 4)
									digitInput.graph.setRGB(bx, by, digitInput.background);
				}
				else
				if (state == STATE_PICK_COLORS)
				{
					if (digitInput.colors != null)
						if (digitInput.colors.indexOf(col) < 0 && digitInput.background != col)
							digitInput.colors.add(col);
				}
				
				img.setImage(digitInput.graph);						
			}							
		}		

		switch (e.id)
		{
			case ID_BUT_PREV:
				completeOptionsUpdate(-1);
			break;
				
			case ID_BUT_NEXT:
				if (state == STATE_TEMP_ONCE || state == STATE_TEMP_ALWAYS)
					completeOptionsUpdate(1);
				else
				if (state == STATE_EDIT_TRASH || state == STATE_EDIT_COLORS_CUT || 
						state == STATE_PICK_COLORS || state == STATE_DETECT_COLORS ||
						state == STATE_PICK_ELEMENTS)
				{
					moveState();
				}
			break;
				
				
		}
		
		Main.main.canvas.paint();
	}	
	
	
	//METODY
	private void moveState()
	{
		switch (state)
		{
			case STATE_INIT:
				state = STATE_TEMP_ONCE;	
				
				completeOptionsUpdate(1);
			break;
				
			case STATE_TEMP_ONCE:   
				state = STATE_TEMP_ALWAYS;
				
				progressMain.value++;
				subTitleLabel.text = Str.DIGIT_TEMP_ALWYAS;
				nextImg();				
			break;
				
			case STATE_TEMP_ALWAYS:  
				state = STATE_EDIT_TRASH;
				progressMain.value++;
				
				showTemplateComponents(false);
				nextBut.onMouseExit();
				
				img.w = 95;
				img.h = 72;
				img.y = 12;
				
				img.setImage(digitInput.img);
				img.setSelectable();
				nextBut.visible = true;
				
				digitInput.options = options;
				
				if (options[Opts.oRemoveTrash.id].is(Opts.oRemoveTrash.YES))
					gui.setInfoLongest(Str.get(Str.DIGIT_REMOVE_TRASH), GUIMain.INFO_INFO);
				else
					moveState();
			break;
				
			case STATE_EDIT_TRASH:   
				state = STATE_CUT_TITLE;
				
				nextBut.onMouseExit();
				nextBut.visible = false;
				if (options[Opts.oTitleDetect.id].is(Opts.oTitleDetect.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_TITLE), GUIMain.INFO_INFO);
				else
					moveState();			
			break;
				
			case STATE_CUT_TITLE:    
				state = STATE_CUT_TITLE_X;
				
				if (options[Opts.oAxisXDetect.id].is(Opts.oAxisXDetect.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_TITLE_X), GUIMain.INFO_INFO);
				else
					moveState();					
			break;
				
			case STATE_CUT_TITLE_X:     
				state = STATE_CUT_TITLE_Y;
				
				if (options[Opts.oAxisYDetect.id].is(Opts.oAxisYDetect.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_TITLE_Y), GUIMain.INFO_INFO);
				else
					moveState();					
			break;
				
			case STATE_CUT_TITLE_Y:    
				state = STATE_CUT_VALUE_X;
				
				if (options[Opts.oAxisXValue.id].is(Opts.oAxisXValue.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_VALUE_X), GUIMain.INFO_INFO);
				else
					moveState();					
			break;
				
			case STATE_CUT_VALUE_X:     
				state = STATE_CUT_VALUE_Y;
				
				if (options[Opts.oAxisYValue.id].is(Opts.oAxisYValue.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_VALUE_Y), GUIMain.INFO_INFO);
				else
					moveState();					
			break;
				
			case STATE_CUT_VALUE_Y:  
				state = STATE_CUT_LEGEND;

				if ((options[Opts.oColorsDetectOn.id].is(Opts.oColorsDetectOn.LEGEND)) ||
						(options[Opts.oDetectElements.id].is(Opts.oDetectElements.LEGEND)))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_LEGEND), GUIMain.INFO_INFO);
				else
					moveState();					
			break;
				
			case STATE_CUT_LEGEND:
				state = STATE_CUT_GRAPH;
				
				if (options[Opts.oGraphDetect.id].is(Opts.oGraphDetect.MANUAL))
					gui.setInfoLongest(Str.get(Str.DIGIT_CUT_GRAPH), GUIMain.INFO_INFO);
				else
				{
					FrameDetecting fd = new FrameDetecting();
					DoubleArea res = fd.process(digitInput.img.clone());
					digitInput.graph = digitInput.img.getSubImage((int)(Math.max(res.startX+5,1)), (int)(Math.max(res.startY+5,1)), (int)(Math.max(res.endX-5,1)), (int)(Math.max(res.endY-5,1)));

					moveState();		
				}
			break;
				
			case STATE_CUT_GRAPH:       
				state = STATE_EDIT_COLORS_CUT;
				
				img.setImage(digitInput.graph);
				img.setNonSelectable();
				img.setClickable(true);
				nextBut.visible = true;
				currrentColorPicker = 0;
				
				if (options[Opts.oRemoveColors.id].is(Opts.oRemoveColors.YES))
					gui.setInfoLongest(Str.get(Str.DIGIT_COLORS_CUT), GUIMain.INFO_INFO);
				else
					moveState();						
			break;
				
			case STATE_EDIT_COLORS_CUT:
				state = STATE_PICK_COLORS;

				currrentColorPicker = 0;
				img.setClickable(true);				
				digitInput.colors = new ArrayList<>();
				
				if (options[Opts.oColorsDetectOn.id].is(Opts.oColorsDetectOn.MANUAL) &&
						(!options[Opts.oSubgraphsColors.id].is(Opts.oSubgraphsColors.MONO)) )
					gui.setInfoLongest(Str.get(Str.DIGIT_PICK_COLORS), GUIMain.INFO_INFO);
				else
					moveState();				
			break;
				
			case STATE_PICK_COLORS: 	
				state = STATE_DETECT_COLORS;
				
				img.setClickable(false);
				currrentColorPicker = -1;				
				
				if ((!options[Opts.oColorsDetectOn.id].is(Opts.oColorsDetectOn.MANUAL)) &&
						(!options[Opts.oSubgraphsColors.id].is(Opts.oSubgraphsColors.MONO)) )
				{
					if (options[Opts.oColorsThreshold.id].is(Opts.oColorsThreshold.FIXED))
					{
						digitInput.colors = Digitalize.detectColors(digitInput);
						moveState();
					}
					else
					{
						img.y = img.y + 8;
						img.h = img.h - 8;
			
						thrLabel = new GUILabel(10, 12, 10, 6, Str.DIGIT_THRESHOLD, 125, 25);
						
						thr1 = new GUIList("thr1", 25, 12, 25, 6);
						thr2 = new GUIList("thr2", 60, 12, 25, 6);
						
						for (int i=1; i<40; i++)
						{
							thr1.addOption((0.5*i)+"");
							thr2.addOption((0.5*i)+"");
						}
						thr1.setSelectedIndex(5);
						thr2.setSelectedIndex(19);
						
						gui.addComponent(thrLabel);
						gui.addComponent(thr1);
						gui.addComponent(thr2);
					}
				}
				else
					moveState();					
				
			break;
				
			case STATE_DETECT_COLORS: 	
				if (thr1 != null)
					thr1.visible = false;
				
				if (thr2 != null)
				thr2.visible = false;
				
				if (thrLabel != null)
				thrLabel.visible = false;
				
				img.y = img.y - 8;
				img.h = img.h + 8;
				
				if (options[Opts.oGraphType.id].is(Opts.oGraphType.POINT))
				{
					if (options[Opts.oDetectElements.id].is(Opts.oDetectElements.DONT))
						state = STATE_FINALIZE;
					else
					{
						if (!(options[Opts.oDetectElements.id].is(Opts.oDetectElements.MANUAL)))								
							digitInput.elements = Digitalize.autoDetectElements(digitInput);
						else
						{
							state = STATE_PICK_ELEMENTS;
							img.setSelectable();
						}
					}
				}
				else
					state = STATE_FINALIZE;
					
				moveState();
				
			break;
					
			case STATE_PICK_ELEMENTS: 	
				state = STATE_FINALIZE;
				moveState();				
			break;
					
			case STATE_FINALIZE: 	
				DigitOutput out = Digitalize.digitalize(digitInput);
				
				if (options[Opts.oSaveResToFile.id].is(Opts.oSaveResToFile.SAVE))
				{
					String fileName = digitInput.name.substring(0,  digitInput.name.length()-CrossRes.getFileExt(digitInput.name).length()-1);
					if (options[Opts.oSaveFileName.id].is(Opts.oSaveFileName.DATE))
						fileName += "_" + CrossRes.getDate();
					
					if (options[Opts.oSaveFileName.id].is(Opts.oSaveFileName.DATE_TIME))
						fileName += "_" + CrossRes.getTime();
					
					List<String> graphs = CrossRes.getGraphs(false);
					if ((options[Opts.oSaveFileExists.id].is(Opts.oSaveFileExists.ADD_DATATIME)) &&
							graphs.indexOf(fileName)>-1)
						fileName += fileName + CrossRes.getTime()+"_"+CrossRes.getDate();
					
					CrossRes.saveText(CrossRes.getHomeDir()+fileName+".gdgraph", out.toString());
				}					
				
				digitOutput.add(out);
				nextImg();
			break;
				
		}
	}
	
	private void nextImg()
	{
		currentImg++;
		if (currentImg >= imgs.length)
		{
			gui.setIconsEnabled(true);
			gui.iconSelected = 1;
			if (options[Opts.oAfterEnd.id].is(Opts.oAfterEnd.GO_TO_MENU))
				Main.main.mainCrtl.chageController(new MenuCtrl());
			else
				Main.main.mainCrtl.chageController(new DrawGraphCtrl(digitOutput));
		}
		else
		{
			showTemplateComponents(true);
			digitInput = new DigitInput(imgs[currentImg].img);
			digitInput.name = imgs[currentImg].name;
			digitInput.background = ColorsUtils.detectBackground(imgs[currentImg].img, 8);
			img.setImage(imgs[currentImg].img, Comm.STRETCH_V_PROP);			
			currentOptionsIndex=-1;
			completeOptionsUpdate(1);
		}
	}
		
	private void completeOptions()
	{

	}
		
	private void completeOptionsUpdate(int move)
	{
		currentOptionsIndex+= move;
		
		List<Integer> optionsList = null;
		
		if (state == STATE_TEMP_ONCE)
			optionsList = askOnceOpts;
		else
			optionsList = askAlwaysOpts;
							
		
		prevBut.visible = true;
		if (currentOptionsIndex >= optionsList.size())
		{
			moveState();
			return;
		}
		else
		if (currentOptionsIndex <= 0)
		{
			currentOptionsIndex = 0;
			prevBut.onMouseExit();
			prevBut.visible = false;
		}
		
		
		String s = " ("+(currentOptionsIndex+1)+"/"+optionsList.size()+")";
		subTitleLabel.text = new String[2];
		subTitleLabel.text[0] = (state == STATE_TEMP_ONCE? Str.DIGIT_TEMP_ONCE[0] : Str.DIGIT_TEMP_ALWYAS[0]) + s;
		subTitleLabel.text[1] = (state == STATE_TEMP_ONCE? Str.DIGIT_TEMP_ONCE[1] : Str.DIGIT_TEMP_ALWYAS[1]) + s; 
		
		int index = optionsList.get(currentOptionsIndex);
		
		listTitle.enabled = options[index].enabled;
		list.enabled = options[index].enabled;				

		listTitle.text = options[index].getName();	

		list.id = options[index].ID()+"";
		list.clearOptions();
		for (String[] opt: options[index].opts)
		{
			list.addOptionWithLang(opt);
		}

		list.setSelectedIndex(options[index].selected);				
	}
	
	
	
	
	//METODY POMOCNICZE
	private void showTemplateComponents(boolean status)
	{
		titleLabel.visible = status;
		subTitleLabel.visible = status;
		listTitle.visible = status;
		prevBut.visible = status;
		nextBut.visible = status;
		list.visible = status;
		img.setImage(null);
	}

	

}
