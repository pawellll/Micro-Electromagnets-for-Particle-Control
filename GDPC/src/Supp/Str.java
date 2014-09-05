package Supp;

/**
 * Klasa przechowuje lancuchy tekstow w zaleznosci od jezyka.
 */
public class Str
{
  public static final int LANG_PL = 0;
  public static final int LANG_EN = 1;
	public static int currentLang = LANG_PL;

	
  public static final String[] STR_EMPTY = {"", ""};

	//GUIMain
  public static final String[]   GUI_INFO_OK     = {"Zamknij", "Close"};
  public static final String[]   GUI_ICON_DIS    = {"Zakończ obecne zadanie aby włączyć nowe.", "Finish current task to start new one."};
  public static final String[]   GUI_YES         = {"Tak", "Yes"};
  public static final String[]   GUI_NO          = {"Nie", "No"};
  public static final String[]   GUI_CONFIRM     = {"Czy na pewno zakończyć obecne zadanie?", "Do you really want to abort current task?"};
	public static final String[][] GUI_ICON_TITLES = { {"Digitalizuj", "Digitize"},
																							       {"Rysuj wykres", "Draw graph"},
																							       {"Ustawienia", "Settings"},
																							       {"O programie", "About"},
																							       {"Zamknij program", "Close application"},
																							       {"Przerwij zadanie", "Abort task"}};
	
	//MenuCtrl
  public static final String[] MENU_TAKE_IMG  = {"Wykonaj zdjęcie", "Take picture"};
  public static final String[] MENU_DROP_IMG  = {"Przeciągnij plik", "Drop a file"};
  public static final String[] MENU_LOAD_IMG  = {"Wczytaj zdjęcie", "Load image"};
  public static final String[] MENU_LOAD_DIR  = {"Wczytaj folder", "Load directory"};
  public static final String[] MENU_TEMPLATE  = {"Szablon ustawień:", "Options template:"};
	
	//OptionsCtrl
  public static final String[] OPT_TITLE          = {"Ustawienia", "Settings"};
  public static final String[] OPT_NEW            = {"Stwórz nowy szablon", "Create new template"};
  public static final String[] OPT_NAME           = {"Nazwa:", "Name:"};
  public static final String[] OPT_BASE           = {"Na podstawie:", "Based on:"};
  public static final String[] OPT_CREATE         = {"Stwórz", "Create"};
  public static final String[] OPT_EDIT_TEMPLATE  = {"Edytuj szablon", "Edit template"};
  public static final String[] OPT_TEMPLATE       = {"Szablon:", "Template:"};
  public static final String[] OPT_EDIT           = {"Edytuj", "Edit"};
  public static final String[] OPT_DEFAULT        = {"Domyślny szablon", "Default template"};
  public static final String[] OPT_EDIT_DEF_ERR   = {"Nie można edytować domyślnego szablonu.", "Default template cannot be edited."};
  public static final String[] OPT_INPUT_DEF      = {"wpisz nazwę", "enter name"};
  public static final String[] OPT_EMPTY_NAME_ERR = {"Podana nazwa jest pusta.", "Template name is empty."};
  public static final String[] OPT_EMPTY_TEMP_ERR = {"Proszę wybrać szablon.", "Please choose template."};
	
	//OptionsEditCtrl
  public static final String[] OPTE_TITLE = {"Edytuj szablon ustawień", "Edit settings template"};
  public static final String[] OPTE_INFO  = {"Edycja", "Editing"};
  public static final String[] OPTE_PREV  = {"Wstecz", "Previous"};
  public static final String[] OPTE_NEXT  = {"Dalej", "Next"};
  public static final String[] OPTE_SAVE  = {"Zapisz i zakończ", "Save and finish"};
	
	
	//AboutCtrl
  public static final String[] ABOUT_APP1 = {"Program do digitalizacji wykresów zwykłych i punktowych.", "Application created for regular and scatter plot digitization."};
  public static final String[] ABOUT_APP2 = {"AGH 2014.", "AGH 2014."};
  public static final String[] ABOUT_US = {"Autorzy: M. Janusz, P. Pęksa, D. Zieliński", "Authors: Autorzy: M. Janusz, P. Pęksa, D. Zieliński"};
	
	
	//DigitCtrl
  public static final String[] DIGIT_TITLE        = {"Digitalizacja w toku", "Digitalization in progress"};
  public static final String[] DIGIT_TEMP         = {"Uzupełnianie szablonu opcji", "Complementing options template"};
  public static final String[] DIGIT_TEMP_ONCE    = {"Proszę uzupełnić opcje gdzie zaznaczono 'zapytaj raz'.", "Please complete options where 'ask once' was chosen."};
  public static final String[] DIGIT_TEMP_ALWYAS  = {"Proszę uzupełnić opcje gdzie zaznaczono 'zapytaj zawsze', dla wykresu powyżej.", "Please complete options where 'ask always' was chosen, for the graph above."};
  public static final String[] DIGIT_REMOVE_TRASH = {"Proszę zaznaczyć obszary do usunięcia", "Please select regions to remove"};
  public static final String[] DIGIT_CUT_TITLE    = {"Proszę zaznaczyć tytuł wykresu", "Please select graph's title"};
  public static final String[] DIGIT_CUT_TITLE_X  = {"Proszę zaznaczyć podpis osi X", "Please select X asis's caption"};
  public static final String[] DIGIT_CUT_TITLE_Y  = {"Proszę zaznaczyć podpis osi Y", "Please select Y asis's caption"};
  public static final String[] DIGIT_CUT_VALUE_X  = {"Proszę zaznaczyć wartości osi X", "Please select X asis's values"};
  public static final String[] DIGIT_CUT_VALUE_Y  = {"Proszę zaznaczyć wartości osi Y", "Please select Y asis's values"};
  public static final String[] DIGIT_CUT_LEGEND   = {"Proszę zaznaczyć legendę", "Please select legend"};
  public static final String[] DIGIT_CUT_GRAPH    = {"Proszę zaznaczyć obszar z wykresem", "Please select regions with graph"};
  public static final String[] DIGIT_COLORS_CUT   = {"Proszę wybrać kolory do usunięcia z obrazka", "Please pick colors to remove"};
  public static final String[] DIGIT_PICK_COLORS  = {"Proszę wybrać kolory podwykresów", "Please select subgraphs' colors"};

	
	//GUIList
  public static final String[] LIST_CHOOSE = {"Wybierz opcje", "Choose option"};
		
	//Opts
	public static final String[] OPTS_STRING_ERROR = {"Nieprawidłowy format szablonu opcji", "Option template is not valid"};
	public static final String[] OPTS_IRRELEVANT   = {"niezwiązany", "irrelevant"};
	
	//CrossRes
  public static final String[] RES_LOAD_STR_ERROR = {"Błąd przy wczytywaniu pliku", "Error while loading file"};
  public static final String[] RES_SAVE_STR_ERROR = {"Błąd przy zapisywaniu pliku", "Error while saving file"};
  public static final String[] RES_LOAD_IMG_ERROR = {"Błąd przy wczytywaniu obrazka.", "Error while loading image."};
  public static final String[] RES_SAVE_IMG_ERROR = {"Błąd przy zapisywaniu obrazka.", "Error while saving image."};
  public static final String[] RES_NO_IMGS_ERROR  = {"Nie znaleziono żadnych obrazków w podanym folderze.", "No images were found in given directory."};
  public static final String[] RES_NO_DROP_ERROR  = {"Nie znaleziono żadnych obrazków upuszczonych plikach.", "No images were found in dropped files."};
  public static final String[] RES_CHOOSE_FILE    = {"Wybierz pliki", "Choose files"};
  public static final String[] RES_CHOOSE_DIR     = {"Wybierz folder", "Choose directory"};
					
	
	public static String get(String[] textID)
	{
		return textID[currentLang];
	}	
	
	public static void setLang(int lang)
	{
		currentLang = lang;
		
	}
	
	
}
