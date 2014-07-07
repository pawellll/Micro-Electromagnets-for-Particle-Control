package Supp;

/**
 * Zawiera stale wykorzystywane przez rozne klasy.
 */
public class Comm 
{
  //Ogolne
    public static final String APP_NAME = "Graph Digitizer";
  
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
		
  //Identyfikatory zasobow
    public static final int R_COUNT_GUI = 8;
		
    public static final int R_BCG       = 0;
    public static final int R_LOGO      = 1;
    public static final int R_ARR_UP    = 2;  		
    public static final int R_ARR_DOWN  = 3;  		
    public static final int R_ARR_LEFT  = 4;  		
    public static final int R_ARR_RIGHT = 5;  		
    public static final int R_CHECK_OFF = 6;  		
    public static final int R_CHECK_ON  = 7;  		
			
  //Pliki zasobow
    public static final String R_F_BCG       = "tlo.png";
    public static final String R_F_LOGO      = "menu_logo.png";
    public static final String R_F_ARR_UP    = "arrow_up.png";  		
    public static final String R_F_ARR_DOWN  = "arrow_down.png";  		
    public static final String R_F_ARR_LEFT  = "arrow_left.png";  		
    public static final String R_F_ARR_RIGHT = "arrow_right.png";  		
    public static final String R_F_CHECK_OFF = "check_off.png";  		
    public static final String R_F_CHECK_ON  = "check_on.png";  		
}
