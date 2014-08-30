package Supp;

/**
 * Zawiera stale wykorzystywane przez rozne klasy.
 */
public class Comm 
{
  //Ogolne
    public static final String APP_NAME = "Graph Digitizer";
    public static final String APP_DIR_NAME = "GraphDigitizer";
  
	//Kody klawiszy
		public static final int VK_LEFT   = 37;
		public static final int VK_UP     = 38;
		public static final int VK_RIGHT  = 39;
		public static final int VK_DOWN   = 40;
		public static final int VK_ENTER  = 10;
		public static final int VK_ESC    = 27;
		public static final int VK_DEL    = 127;
		public static final int VK_BCKSPC = 8;
		public static final int VK_SPC    = 32;
		
  //Grafika
    public static final int ALIGN_NONE    = 0;
    public static final int ALIGN_LEFT    = 1;    
    public static final int ALIGN_CENTER  = 2;
    public static final int ALIGN_RIGHT   = 4;
    public static final int ALIGN_TOP     = 8;
    public static final int ALIGN_VCENTER = 16;
    public static final int ALIGN_BOTTOM  = 32;

    public static final int STRETCH_H      = 0;
    public static final int STRETCH_H_PROP = 1;
    public static final int STRETCH_V      = 2;
    public static final int STRETCH_V_PROP = 3;
    public static final int STRETCH_HV     = 4;
  
  //Kursor
    public static final int CURSOR_DEF  = 0;
    public static final int CURSOR_HAND = 1;    
    public static final int CURSOR_TEXT = 2;    
		
  //Identyfikatory zasobow
    public static final int R_COUNT_GUI = 26;
		
    public static final int R_LOGO       = 0;
    public static final int R_LOGOTYPE   = 1;
    public static final int R_ARR_L_ON   = 2;  		
    public static final int R_ARR_L_OFF  = 3;  		
    public static final int R_ARR_R_ON   = 4;  		
    public static final int R_ARR_R_OFF  = 5;  		
    public static final int R_CHECK_OFF  = 6;  		
    public static final int R_CHECK_ON   = 7;  	
    public static final int R_IMG_FRAME  = 8;  		
    public static final int R_INFO_INFO  = 9;  		
    public static final int R_INFO_WARN  = 10;  	
    public static final int R_INFO_OK    = 11;  		
    public static final int R_LANG_EN    = 12;  	
    public static final int R_LANG_PL    = 13;  		
    public static final int R_ICON_1_ON  = 14;  		
    public static final int R_ICON_2_ON  = 15;  	
    public static final int R_ICON_3_ON  = 16;  		
    public static final int R_ICON_4_ON  = 17;  	
    public static final int R_ICON_5_ON  = 18;  	
    public static final int R_ICON_6_ON  = 19;  				
    public static final int R_ICON_1_OFF = 20;  		
    public static final int R_ICON_2_OFF = 21;  	
    public static final int R_ICON_3_OFF = 22;  		
    public static final int R_ICON_4_OFF = 23;  	
    public static final int R_ICON_5_OFF = 24;  		
    public static final int R_ICON_6_OFF = 25;  		 		
    
			
  //Pliki zasobow
    public static final String R_F_LOGO      = "menu_logo.png";
    public static final String R_F_LOGOTYPE  = "menu_logotyp.png";
    public static final String R_F_ARR_L_ON  = "s_left_on.png";  		
    public static final String R_F_ARR_L_OFF = "s_left_off.png";  		
    public static final String R_F_ARR_R_ON  = "s_right_on.png";  		
    public static final String R_F_ARR_R_OFF = "s_right_off.png";  		
    public static final String R_F_CHECK_OFF = "check_off.png";  		
    public static final String R_F_CHECK_ON  = "check_on.png";  		
    public static final String R_F_IMG_FRAME = "ramka.png";  		
    public static final String R_F_INFO_INFO = "info.png";  		
    public static final String R_F_INFO_WARN = "warn.png";  		
    public static final String R_F_INFO_OK   = "ok.png";  		
    public static final String R_F_LANG_EN   = "lang_en.png";  		
    public static final String R_F_LANG_PL   = "lang_pl.png";  			
    public static final String R_F_ICON_1_ON  = "icon_digit_on.png";  		
    public static final String R_F_ICON_2_ON  = "icon_graph_on.png";  	
    public static final String R_F_ICON_3_ON  = "icon_opts_on.png";  		
    public static final String R_F_ICON_4_ON  = "icon_info_on.png";  	
    public static final String R_F_ICON_5_ON  = "icon_off_on.png";  	
    public static final String R_F_ICON_6_ON  = "icon_back_on.png"; 		
    public static final String R_F_ICON_1_OFF = "icon_digit_off.png";  		
    public static final String R_F_ICON_2_OFF = "icon_graph_off.png";  	
    public static final String R_F_ICON_3_OFF = "icon_opts_off.png";  		
    public static final String R_F_ICON_4_OFF = "icon_info_off.png";  	
    public static final String R_F_ICON_5_OFF = "icon_off_off.png"; 
    public static final String R_F_ICON_6_OFF = "icon_back_off.png"; 
		
	//legena, wykres, tytuly osi x,y, wartosci na osiach,x,y
		public static final int D_BIT_LEGEND   = 0;
		public static final int D_BIT_GRAPH    = 1;
		public static final int D_BIT_TITLE_X  = 2;
		public static final int D_BIT_TITLE_Y  = 3;
		public static final int D_BIT_VALUES_X = 4;
		public static final int D_BIT_VALUES_Y = 5;
}
