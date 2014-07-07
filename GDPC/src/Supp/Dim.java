package Supp;

/**
 * Pomocnicza klasa zapewniajaca operacje na wymiarach i responsywanosc.
 */
public class Dim
{
	public static int w = 100;
	public static int h = 100;
	
	/**Aktualizuje informacje o szerokosci i wysokosci okna programu. */	
	public static void update(int width, int height)
	{
		w = width;
		h = height;
	}
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach. */
	public static int X(double dimX)
	{
		return (int)((double)dimX/100.0*w);
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach. */	
	public static int Y(double dimY)
	{
		return (int)((double)dimY/100.0*h);
	}	
	
	/**Zamienia wymiary X podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue. */
	public static int X(double dimX, int minValue)
	{
		int res = (int)((double)dimX/100.0*w);
		if (res<minValue)
			res = minValue;
	
		return res;
	}
	
	/**Zamienia wymiary Y podane w procentach na wartosc w pikselach, ale nie mniejsza niz minValue.. */	
	public static int Y(double dimY, int minValue)
	{
		int res = (int)((double)dimY/100.0*h);
		if (res<minValue)
			res = minValue;
	
		return res;		
	}
	
}
