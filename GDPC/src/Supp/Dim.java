package Supp;

import Platform.Main;

/**
 * Pomocnicza klasa zapewniajaca operacje na wymiarach i responsywanosc.
 */
public class Dim
{
	public static int w = 100;
	public static int h = 100;
  public static int m = 0;
	
	/**Aktualizuje informacje o szerokosci i wysokosci okna programu. */	
	public static void update(int width, int height)
	{
		w = width;
		h = height;
	}
	
	/**Aktualizuje informacje o marginesie na menu. */	
	public static void updateMargin(int width, int height, int margin)
	{
		m = margin;
    w = width - margin;
    h = height - margin;
	}
	
	/**Zamienia wymiary X podane w pikselach na wartosc w procentach. */
	public static double dblX(double dimX)
	{
		return (double)dimX/w*100.0;
	}
	
	/**Zamienia wymiary Y podane w pikselach na wartosc w procentach. */	
	public static double dblY(double dimY)
	{
		return (double)dimY/h*100.0;
	}			
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach. */
	public static int X(double dimX)
	{
    return (int)((double)dimX/100.0*w)+m;
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach. */	
	public static int Y(double dimY)
	{
		return (int)((double)dimY/100.0*h)+m;
	}	
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach. */
	public static int W(double dimX)
	{
    return (int)((double)dimX/100.0*w);
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach. */	
	public static int H(double dimY)
	{
		return (int)((double)dimY/100.0*h);
	}	
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue. */
	public static int W(double dimX, int minValue)
	{
		int res = (int)((double)dimX/100.0*w);
		if (res<minValue)
			res = minValue;
	
		return res;
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue.. */	
	public static int H(double dimY, int minValue)
	{
		int res = (int)((double)dimY/100.0*h);
		if (res<minValue)
			res = minValue;
	
		return res;		
	}
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue. */
	public static int XMax(double dimX, int maxValue)
	{
		int res = (int)((double)dimX/100.0*w);
		if (res>maxValue)
			res = maxValue;
	
		return res;
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue.. */	
	public static int YMax(double dimY, int maxValue)
	{
		int res = (int)((double)dimY/100.0*h);
		if (res>maxValue)
			res = maxValue;
	
		return res;		
	}	
	


	
}
