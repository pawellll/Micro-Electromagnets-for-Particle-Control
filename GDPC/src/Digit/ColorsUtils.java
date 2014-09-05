package Digit;

import Platform.CrossBitmap;
import Supp.Colors;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

/**
 * Klasa realizujaca operacje na kolor wykresu, w tym ich wykrywanie i liczenie. 
 */
public class ColorsUtils 
{	
	public static List<Integer> getColorSet(Integer[][][] img, boolean unique)
	{
		List<Integer> set = new ArrayList<>();
		
		for (int x=0; x < img.length; x++)
			for (int y=0; y < img[0].length; y++)
				if (set.indexOf(img[x][y][3]) < 0 || (!unique))
					set.add(img[x][y][3]);	

		return set;
	}

  public static int RGB(int r, int g, int b)
  {
    return (int)((((r << 8)|g) << 8)|b);
  }	
		
	public static int mean(int a, int b)
	{
		return RGB(
		((Colors.getR(a)+Colors.getR(b))/2),
		((Colors.getG(a)+Colors.getG(b))/2),
		((Colors.getB(a)+Colors.getB(b))/2));
	}
	

	
	public static class Color
	{
		public int mean = 0;
		public List<Integer> colors = new ArrayList<>();
		
		public Color(int color)
		{
			mean = color;
		}
	}
			
	public static List<Color> detectColors(List<Integer> img, double threshold)
	{
		List<Color> res = new ArrayList<>();

		int  k;
		boolean ok, changed;
		int mean;
		List<Integer> group = new ArrayList<>();
		
		Color temp;
		int minIndex;
		double min;
		
		for (Integer col : img)
		{		
			min = Double.POSITIVE_INFINITY;
			minIndex = -1;
			for (int i = 0; i< res.size(); i++)
				if (CIELab.delta(col, res.get(i).mean) < min)
				{
					min = CIELab.delta(col, res.get(i).mean);
					minIndex = i;
				}
						
			if (minIndex >= 0)
			{
				if (CIELab.delta(col, res.get(minIndex).mean) <= threshold)
				{
					temp = res.get(minIndex);
					
					temp.mean = RGB(
							(Colors.getR(temp.mean)+Colors.getR(col))/2,
							(Colors.getG(temp.mean)+Colors.getG(col))/2,
							(Colors.getB(temp.mean)+Colors.getB(col))/2);
					
					temp.colors.add(col);
					
					res.set(minIndex, temp);			
				}
				else
					res.add(new Color(col));
			}
			else
				res.add(new Color(col));
		}

		return res;
	}
	
	public static List<Color> detectColorsBetter(List<Integer> img, double threshold)
	{
		List<Color> res = new ArrayList<>();		
		
		if (img.isEmpty())
			return res;

		double min, dist;
		int minX, minY;
		boolean changed = true;
		
		while (changed)
		{
			changed = false;
			min = Double.POSITIVE_INFINITY;
			minX = -1;
			minY = -1;			
			
			for (int x = 0; x < img.size(); x++)
				for (int y = 0; y < img.size(); y++)
					if (x != y)
					{
						dist = CIELab.delta(img.get(x), img.get(y));
						if (dist < min && dist <= threshold)
						{
							min = dist;
							minX = x;
							minY = y;
						}
					}
			
			if (minX > -1)
			{
				changed = true;
				
				img.set(minX, mean(img.get(minX), img.get(minY)));
				img.remove(minY);
			}
		}
	
		Color col;
		for (Integer img1 : img)
		{
			col = new Color(img1);
			res.add(col);
		}
		
		return res;
	}	

	public static Integer detectBackground(CrossBitmap img, double threshold)
	{
		img = img.scale((double)(150.0/img.getWidth()), false);
		
		List<ColorsUtils.Color> colors = detectColors(getColorSet(ImageUtils.bitmapToArray(img), false), threshold);
		
		int max = 0, maxI = 0;
		for (int i=0; i< colors.size(); i++)
			if (colors.get(i).colors.size() > max)
			{
				max = colors.get(i).colors.size();
				maxI = i;
			}
		
		return colors.get(maxI).mean;		
	}
	
	
	/*
	WYKRYWANIE KOLOROW PRZY POMOCY K-MEANS++
	
	private static class ColorsToDetect implements Clusterable
	{
		private double[] points;
		
		public ColorsToDetect(int r, int g, int b)
		{
			points = new double[] { r, g, b };
		}
		
		@Override
		public double[] getPoint()
		{
			return points;
		}
	}
	
	private static class LabDistance implements DistanceMeasure
	{
		@Override
		public double compute(double[] a, double[] b)
		{
			return CIELab.delta(RGB((int)(a[0]), (int)(a[1]), (int)(a[2])), RGB((int)(b[0]), (int)(b[1]), (int)(b[2])));
		}
	}
	
	public static List<Color> detectColorsPlus(List<Integer> img)
	{
		List<Color> res = new ArrayList<>();		
		
		List<ColorsToDetect> colors = new ArrayList<>();
		
		for (Integer col : img)
		{
			colors.add(new ColorsToDetect(Colors.getR(col), Colors.getG(col), Colors.getB(col)));
		}
			
		KMeansPlusPlusClusterer kpp = new KMeansPlusPlusClusterer(5, 100, new LabDistance());

		MultiKMeansPlusPlusClusterer kpp2 = new MultiKMeansPlusPlusClusterer(kpp, 20);
		
		List<CentroidCluster<ColorsToDetect>> clusterResults = kpp2.cluster(colors);

		double[] colPoint;
		Color colRes;
		for (int i=0; i<clusterResults.size(); i++) 
		{
			colPoint = clusterResults.get(i).getCenter().getPoint();
	
			colRes = new Color(RGB((int)(colPoint[0]), (int)(colPoint[1]), (int)(colPoint[2])));
			for (ColorsToDetect colToD : clusterResults.get(i).getPoints())
				colRes.colors.add(RGB((int)(colToD.getPoint()[0]), (int)(colToD.getPoint()[1]), (int)(colToD.getPoint()[2]) ));
						
			res.add(colRes);
		}
		
		return res;
	}	
	*/
	
	
}
