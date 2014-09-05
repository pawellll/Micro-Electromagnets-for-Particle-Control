package GD;

import Platform.CrossRes;
import Platform.Main;
import Supp.Str;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa realizujaca obsluge szablonow opcji.
 */
public class Opts
{
	public static class Option
	{
		public static final int ASK_ONCE   = 1;
		public static final int ASK_ALWAYS = 2;		
		public int ask = 0;
		
		public String[] name = {"", ""};
		public List<String[]> opts = new ArrayList<>(); 
		
		public int selected = 0;
		
		public boolean enabled = true;
		public boolean askable = false;
		
		public boolean is(int value)
		{
			return (selected==value);
		}
		
		public void set(int value)
		{
			selected = value;
		}	
		
		public void setName(String pl, String en)
		{
			name = new String[2];
			name[0] = pl+":";
			name[1] = en+":";
		}
			
		public String[] getName()
		{
			if (!enabled)
			{
				String[] temp = new String[2];
				temp[0] = name[0] + " (" + Str.OPTS_IRRELEVANT[0] + ")";
				temp[1] = name[1] + " (" + Str.OPTS_IRRELEVANT[1] + ")";
				return temp;
			}
			else
				return name;
		}
		
		public void addOpt(String pl, String en)
		{
			String temp[] = new String[2];
			temp[0] = pl;
			temp[1] = en;			
			opts.add(temp);
		}
		
		public void addAsk()
		{
			addOpt("zapytaj raz", "ask once");
			addOpt("zapytaj zawsze", "ask always");
			askable = true;
		}
		
		public int ID()
		{
			return -1;
		}
	}
	
	
	//************************************************
	//Opcje
	//************************************************

	
	public static class oSaveResToFile extends Option
	{
		public static final int id = 0;
		
		public static final int SAVE = 0;
		public static final int DONT_SAVE = 1;
			
		public oSaveResToFile()
		{
			setName("Zapisuj wynik do pliku", "Save results to file");
			addOpt("zawsze zapisuj", "always save");
			addOpt("nie zapisuj", "never save");
			addAsk();
		}		

		@Override	public int ID()	{ return id; }
	}
	
	public static class oSaveFileName extends Option
	{
		public static final int id = 1;
		
		public static final int INPUT_NAME = 0;
		public static final int DATE = 1;
		public static final int DATE_TIME = 2;
			
		public oSaveFileName()
		{
			setName("Nazwa pliku wyjściowego", "Output file name");
			addOpt("jak pliku wejściowego", "like input file ");
			addOpt("jak wejściowy + data", "like input file + date");
			addOpt("jak wejściowy + data + czas", "like input file + date + time");
			addAsk();
		}		

		@Override	public int ID()	{ return id; }
	}
	
	public static class oSaveFileExists extends Option
	{
		public static final int id = 2;
		
		public static final int ADD_NUMBER = 0;
		public static final int OVERWRITE = 1;
			
		public oSaveFileExists()
		{
			setName("Jeśli taki plik już istnieje", "If such file exists");
			addOpt("dodaj kolejny numer", "add successive number");
			addOpt("nadpisz", "overwrite");
			addAsk();
		}		

		@Override	public int ID()	{ return id; }
	}
	
	public static class oAfterEnd extends Option
	{
		public static final int id = 3;
		
		public static final int SHOW_GRAPH = 0;
		public static final int GO_TO_MENU = 1;
			
		public oAfterEnd()
		{
			setName("Po zakończeniu digitalizacji", "After Digitization is done");
			addOpt("przedstaw wykres", "show a graph");
			addOpt("wróć do menu", "go back to menu");
//			addAsk();
		}		

		@Override	public int ID()	{ return id; }
	}
	
	public static class oGraphType extends Option
	{
		public static final int id = 4;
		
		public static final int LINE = 0;
		public static final int POINT = 1;
			
		public oGraphType()
		{
			setName("Typ wykresu", "Graph type");
			addOpt("ciagły", "continuous");
			addOpt("punktowy", "punctual");
			addAsk();
		}		

		@Override	public int ID()	{ return id; }
	}
		
	public static class oSubgraphsColors extends Option
	{
		public static final int id = 5;
		
		public static final int DETECT = 0;
		public static final int MONO   = 1;
		public static final int COLOR  = 2;
		
		public oSubgraphsColors()
		{
			setName("Kolory podwykresów", "Subgraphs colors");
			addOpt("wykryj kolory", "detect colors");			
			addOpt("monochromatyczny", "monochrome");
			addOpt("kolorowy", "color");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
		
	public static class oMonoSegmentation extends Option
	{
		public static final int id = 6;
		
		public static final int YES = 0;
		public static final int NO  = 1;
		
		public oMonoSegmentation()
		{
			setName("Separuj monochromatyczne podwykresy przez grupowanie pikseli", "Separate monochromatic subgraphs by grouping pixels");
			addOpt("włącz", "on");
			addOpt("wyłącz", "off");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
			
	public static class oColorsDetectOn extends Option
	{
		public static final int id = 7;
		
		public static final int GRAPH  = 0;
		public static final int LEGEND = 1;		
		public static final int MANUAL = 2;		
		
		public oColorsDetectOn()
		{
			setName("Wykrywaj kolory na podstawie", "Detect colors basing on");
			addOpt("wykresu", "graph");
			addOpt("legendy", "legend");			
			addOpt("ręcznie", "manually");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
				
	public static class oColorsThreshold extends Option
	{
		public static final int id = 8;
		
		public static final int FIXED  = 0;
		public static final int MANUAL = 1;			
		
		public oColorsThreshold()
		{
			setName("Włącz ręczne dobieranie progu podobieństwa kolorów", "Turn on manually adjusting color similarity threshold");
			addOpt("stały próg", "fixed threshold");			
			addOpt("ustawiaj próg ręcznie", "adjust threshold manually");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
				
	public static class oDetectElements extends Option
	{
		public static final int id = 9;
		
		public static final int MANUAL = 0;		
		public static final int GRAPH  = 1;
		public static final int LEGEND = 2;		
		
		public oDetectElements()
		{
			setName("Wykrywaj kształty punktów na podstawie", "Detect points shapes basing on");
			addOpt("ręcznie", "manually");
			addOpt("wykresu", "graph");
			addOpt("legendy", "legend");			
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
				
	/*
	public static class oLegendDetect extends Option
	{
		public static final int id = 10;
		
		public static final int AUTO = 0;
		public static final int MANUAL = 1;
		
		public oLegendDetect()
		{
			setName("Wykrywanie legendy", "Legend detection");
			addOpt("ręcznie", "manually");
			addOpt("nie wykrywaj", "don't detect");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
					
	public static class oLegendOCR extends Option
	{
		public static final int id = 11;
		
		public static final int DO = 0;
		public static final int DONT = 1;
		
		public oLegendOCR()
		{
			setName("OCR legendy", "Legend OCR");
			addOpt("odczytuj opisy", "read descriptions");
			addOpt("nie odczytuj", "don't read descriptions");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}	
	*/
					
	public static class oRemoveTrash extends Option
	{
		public static final int id = 10;
		
		public static final int NO  = 0;
		public static final int YES = 1;
		
		public oRemoveTrash()
		{
			setName("Ręczne usuwanie zbędnych elementów obrazka", "Manual removing of redundant parts of the image");
			addOpt("wyłącz", "off");
			addOpt("włącz", "on");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}					
					
	public static class oRemoveColors extends Option
	{
		public static final int id = 11;
		
		public static final int NO  = 0;
		public static final int YES = 1;
		
		public oRemoveColors()
		{
			setName("Ręczne usuwanie zbędnych kolorów na wykresie", "Manual removing of redundant colors from the graph");
			addOpt("wyłącz", "off");
			addOpt("włącz", "on");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}					
	
	public static class oGraphDetect extends Option
	{
		public static final int id = 12;
		
		public static final int AUTO = 0;
		public static final int MANUAL = 1;
		
		public oGraphDetect()
		{
			setName("Wykrywanie wykresu", "Graph detection");
			addOpt("automatyczne", "automatic");
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
					
	public static class oAxisXDetect extends Option
	{
		public static final int id = 13;
		
		public static final int DONT = 0;		
		public static final int MANUAL = 1;
		
		public oAxisXDetect()
		{
			setName("Wykrywanie podpisu osi X", "Axis X's description detection");
			addOpt("nie wykrywaj", "don't detect");			
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
					
	public static class oAxisYDetect extends Option
	{
		public static final int id = 14;
		
		public static final int DONT = 0;		
		public static final int MANUAL = 1;
		
		public oAxisYDetect()
		{
			setName("Wykrywanie podpisu osi Y", "Axis Y's description detection");
			addOpt("nie wykrywaj", "don't detect");			
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
						
	public static class oAxisXValue extends Option
	{
		public static final int id = 15;
		
		public static final int DONT = 0;		
		public static final int MANUAL = 1;
		
		public oAxisXValue()
		{
			setName("Wykrywanie wartości osi X", "Axis X's values detection");
			addOpt("nie wykrywaj", "don't detect");			
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
						
	public static class oAxisYValue extends Option
	{
		public static final int id = 16;
		
		public static final int DONT = 0;		
		public static final int MANUAL = 1;
		
		public oAxisYValue()
		{
			setName("Wykrywanie wartości osi Y", "Axis Y's values detection");
			addOpt("nie wykrywaj", "don't detect");			
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
						
	public static class oAxisXValueDir extends Option
	{
		public static final int id = 17;
		
		public static final int H = 0;
		public static final int V = 1;
		public static final int AUTO = 2;
		
		public oAxisXValueDir()
		{
			setName("Orientacja wartości osi X", "Axis X's values orientation");
			addOpt("pozioma", "horizontal");
			addOpt("pionowa", "vertical");
			addOpt("automatycznie", "automatically");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
						
	public static class oAxisYValueDir extends Option
	{
		public static final int id = 18;
		
		public static final int H = 0;
		public static final int V = 1;
		public static final int AUTO = 2;
		
		public oAxisYValueDir()
		{
			setName("Orientacja wartości osi Y", "Axis Y's values orientation");
			addOpt("pozioma", "horizontal");
			addOpt("pionowa", "vertical");
			addOpt("automatycznie", "automatically");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
						
	public static class oTitleDetect extends Option
	{
		public static final int id = 19;
		
		public static final int DONT = 0;		
		public static final int MANUAL = 1;
		
		public oTitleDetect()
		{
			setName("Wykrywanie tytułu", "Title detection");
			addOpt("nie wykrywaj", "don't detect");			
			addOpt("ręczne", "manual");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
				
	/*
	public static class oTitlePos extends Option
	{
		public static final int id = 20;
		
		public static final int TOP   = 0;
		public static final int RIGHT  = 1;
		public static final int BOTTOM = 2;
		public static final int LEFT = 3;
		
		public oTitlePos()
		{
			setName("Pozycja tytułu", "Title position");
			addOpt("góra", "top");
			addOpt("prawa strona", "right side");
			addOpt("dół", "bottom");
			addOpt("lewa strona", "left side");
			addAsk();
		}		
		
		@Override	public int ID()	{ return id; }		
	}		
	*/

	
	
	//************************************************
	//Metody
	//************************************************

	public static final int COUNT = 20;
	
	public static Option[] createDefaultSet()
	{
		Option[] options = new Option[COUNT];

		options[oSaveResToFile.id]   = new oSaveResToFile();
		options[oSaveFileName.id]    = new oSaveFileName();
		options[oSaveFileExists.id]  = new oSaveFileExists();
		
		options[oAfterEnd.id]        = new oAfterEnd();
		options[oGraphType.id]       = new oGraphType();
			options[oGraphType.id].ask = Option.ASK_ALWAYS;
		options[oMonoSegmentation.id] = new oMonoSegmentation();
		
		options[oSubgraphsColors.id] = new oSubgraphsColors();
		options[oColorsDetectOn.id]  = new oColorsDetectOn();
		options[oColorsThreshold.id] = new oColorsThreshold();
		
		options[oDetectElements.id]  = new oDetectElements();
		options[oGraphDetect.id]     = new oGraphDetect();
		options[oAxisXDetect.id]     = new oAxisXDetect();
		
		options[oAxisYDetect.id]     = new oAxisYDetect();
		options[oAxisXValue.id]      = new oAxisXValue();
		options[oAxisYValue.id]      = new oAxisYValue();
		
		options[oAxisXValueDir.id]   = new oAxisXValueDir();
		options[oAxisYValueDir.id]   = new oAxisYValueDir();
		options[oTitleDetect.id]     = new oTitleDetect();

		options[oRemoveTrash.id]    = new oRemoveTrash();
		options[oRemoveColors.id]   = new oRemoveColors();

		return options;
	}
	
	public static String optionsToString(Option[] options)
	{
		String str = "";
		for (Option opt : options)
		{
			str += ";" + opt.selected + ":" + opt.ask;
		}
		
		return str.substring(1);
	}
	
	public static Option[] stringToOptions(String str, String fileName)
	{
		Option[] options = createDefaultSet();
	
		try
		{
			String[] strOptions = str.split(";");
			String[] strOption;

			for (int i=0; i < strOptions.length; i++)
			{
				strOption = strOptions[i].split(":");

				options[i].selected = Integer.valueOf(strOption[0]);
				options[i].ask = Integer.valueOf(strOption[1]);
				if (options[i].ask > 0)
					options[i].askable = true;
			}
		}
		catch (Exception ex)
		{
			String s = "";
			if (!fileName.equals(""))
				s = " ("+CrossRes.getFileName(fileName)+")";
			Main.main.GUI.setInfoLongest(Str.get(Str.OPTS_STRING_ERROR)+s, GUI.GUIMain.INFO_WARN);
			return null;
		}
		
		return options;
	}
	
	public static Option[] stringToOptions(String str)
	{
		return stringToOptions(str, "");
	}	
		
	public static Option[] updateEnabledOptions(Option[] opts)
	{
		for (int i=0; i < opts.length; i++)
			opts[i].enabled = true;
		
		if (opts[oGraphType.id].is(oGraphType.LINE))
		{
			opts[oDetectElements.id].enabled = false;
		}	
				
		if (opts[oColorsDetectOn.id].is(oColorsDetectOn.MANUAL))
		{
			opts[oColorsThreshold.id].enabled = false;
		}	
	
		return opts;
	}	
	
	
	
}
