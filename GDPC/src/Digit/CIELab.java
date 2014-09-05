package Digit;

import Supp.Colors;

/**
 * Klasa realizujaca konwersje do przestrzeni CIELab oraz porownywanie kolorow w tej przestrzeni. 
 */
public class CIELab 
{
	public static class Lab
	{
		public double l, a, b;
		public Lab(double l, double a, double b)
		{
			this.l = l;
			this.a = a;
			this.b = b;
		}
	}
	
	public static Lab RGBtoLAB(int R, int G, int B)
	{
		// RGB to XYZ
		double r, g, b, X, Y, Z;
		

		r = R/255.0d;
		g = G/255.0d;
		b = B/255.0d; 

		r = (r <= 0.04045 ? r/12.92 : (double) Math.pow((r+0.055)/1.055,2.4)) * 100.0;
		g = (g <= 0.04045 ? g/12.92 : (double) Math.pow((g+0.055)/1.055,2.4)) * 100.0;
		b = (b <= 0.04045 ? b/12.92 : (double) Math.pow((b+0.055)/1.055,2.4)) * 100.0;

		//X = 0.436052025d*r + 0.385081593d*g + 0.143087414d *b;
		//Y = 0.222491598d*r + 0.71688606d *g + 0.060621486d *b;
		//Z = 0.013929122d*r + 0.097097002d*g + 0.71418547d  *b;

		X = r * 0.4124 + g * 0.3576 + b * 0.1805;
		Y = r * 0.2126 + g * 0.7152 + b * 0.0722;
		Z = r * 0.0193 + g * 0.1192 + b * 0.9505;
	
		//odniesienie dla bialego D65

		double x = xyzCalc(X / 95.047);
		double y = xyzCalc(Y / 100.0);
		double z = xyzCalc(Z / 108.883);

		return new Lab(Math.max(0, 116 * y - 16), 500 * (x - y), 200 * (y - z));		
	}
	
	private static double xyzCalc(double n)
	{
	return n > (double)(216.0d/24389.0d) ? Math.pow(n, 1.0/3.0) : ((double)(24389.0d/27.0d) * n + 16) / 116;
	}
	
	public static Lab RGBtoLAB(int rgb)
	{
		return RGBtoLAB(Colors.getR(rgb), Colors.getG(rgb), Colors.getB(rgb));
	}
	
	
	public static double delta(Lab a, Lab b)
	{
		return Math.sqrt(Math.pow(a.l-b.l, 2)+Math.pow(a.a-b.a, 2)+Math.pow(a.b-b.b, 2))/500*100;
	}
		
	public static double delta(int A, int B)
	{
		Lab a = RGBtoLAB(A);
		Lab b = RGBtoLAB(B);
		return delta(a, b);
	}
	

	
	
}
