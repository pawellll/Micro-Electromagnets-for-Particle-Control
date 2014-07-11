package Supp;

/**
 * Pomocnicza klasa pechowujaca wymiary obszaru prostokatnego wyznaczonego przez 2 punkty.
 */
public class DoubleArea
{
	public double startX, startY, endX, endY;
	
	public DoubleArea(double startX, double startY, double endX, double endY)
	{
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
}
