package Supp;

/**
 * Pomocnicza klasa zapewniajaca operacje na kolorach.
 */
public class Colors 
{
  public static int RGB(int r, int g, int b)
  {
    return (int)((((r << 8)|g) << 8)|b);
  }

  public static int getR(int rgb)
  {
    return (int)(rgb >> 16) & 0xff;
  }
  
  public static int getG(int rgb) 
  {
    return (int)(rgb >> 8) & 0xff;
  }
  
  public static int getB(int rgb) 
  {
    return (int)(rgb) & 0xff;
  }
  
  public final static int WHITE      = RGB(255, 255, 255);
  public final static int LIGHT_GRAY = RGB(192, 192, 192);
  public final static int GRAY       = RGB(128, 128, 128);
  public final static int DARK_GRAY  = RGB(64, 64, 64);
  public final static int BLACK      = RGB(0, 0, 0);
  public final static int RED        = RGB(255, 0, 0);
  public final static int PINK       = RGB(255, 175, 175);
  public final static int ORANGE     = RGB(255, 200, 0);
  public final static int YELLOW     = RGB(255, 255, 0);
  public final static int GREEN      = RGB(0, 255, 0);
  public final static int MAGENTA    = RGB(255, 0, 255);
  public final static int CYAN       = RGB(0, 255, 255);
  public final static int BLUE       = RGB(0, 0, 255);

}
