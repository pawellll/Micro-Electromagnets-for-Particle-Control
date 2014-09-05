package Digit;

import GD.Opts;
import Platform.CrossBitmap;
import java.util.List;

public class DigitInput
{
	public Opts.Option[] options = null; //Szablon opcji
	
	public String name = ""; //Nazwa obrazka
	
	public CrossBitmap img    = null;	//Oryginalny obrazek
	public CrossBitmap graph  = null;	//Pole z wykresem
	public CrossBitmap title  = null;	//Tytul
	public CrossBitmap titleX = null;	//Podpis osi X
	public CrossBitmap titleY = null;	//Podpis osi Y
	public CrossBitmap valueX = null;	//Wartosci osi X
	public CrossBitmap valueY = null;	//Wartosci osi Y
	public CrossBitmap legend = null;	//Legenda
	
	public int background = 0;	//Kolor tla
	
	public List<Integer>     colors = null;		//Lista kolorow
	public List<CrossBitmap> elements = null;	//Lista ksztaltow
	
	public DigitInput(CrossBitmap inputImage)
	{
		img = inputImage.clone();
	}
}
