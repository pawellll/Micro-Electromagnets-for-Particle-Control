package Digit;

import GD.Opts;
import Platform.CrossBitmap;
import Supp.IntegerPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przechowujaca wynik digitalizacji. 
 */
public class DigitOutput
{
	private static final String DELIM = ":@;#";
	
	public String fileName = "graph"; //Nazwa pliku do zapisania
	
	public String title  = " ";	//Tytul
	public String titleX = " ";	//Podpis osi X
	public String titleY = " ";	//Podpis osi Y
	public String valueX = " ";	//Wartosci osi X
	public String valueY = " ";	//Wartosci osi Y

	public List<List<IntegerPoint>> points = new ArrayList<>();	//Podwykresy
	
	@Override
	public String toString()
	{
		String res;
		res = title + DELIM;
		res += titleX + DELIM;
		res += titleY + DELIM;
		res += titleY + DELIM;
		res += valueY;

		if (!points.isEmpty())
			res += DELIM;
		
		for (List<IntegerPoint> sub : points)
		{
			for (IntegerPoint point : sub)
			{
				res += point.X+":"+point.Y+";";
			}
			res += DELIM;
		}
		
		return res;
	}
	
	public boolean fromString(String text)
	{
		try
		{
			String[] parts = text.split(DELIM);
			title  = parts[0];	
			titleX = parts[1];	
			titleY = parts[2];	
			valueX = parts[3];	
			valueY = parts[4];	


			String[] subgraph;
			String[] str;

			List<IntegerPoint> sub;
			points.clear();		
			for (int i = 5; i < parts.length; i++)
			{
				if (!parts[i].isEmpty())
				{
					sub = new ArrayList<>();
					subgraph = parts[i].split(";");
					for (String point : subgraph)
					{
						str = point.split(":");
						sub.add(new IntegerPoint(Integer.valueOf(str[0]), Integer.valueOf(str[1])));
					}

					points.add(sub);
				}
			}
			
			return true;
		}
		catch (Exception ex)
		{
			System.err.println("Blad przy wczytywaniu wykresu");
			return false;
		}
	}	
}
