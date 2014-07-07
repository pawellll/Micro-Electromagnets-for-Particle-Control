package GD;

import GUI.GUIAction;
import GUI.GUIMain;
import Supp.DrawingInterface;

/**
 * Klasa bazowa dla kontrolerów.
 */
public class Controller
{
	protected GUIMain gui;
	
	public void setCurrent(GUIMain GUI)
	{
		gui = GUI;
		onSetCurrent();
	}	
	
	//Zdarzenia zewnetrzne
	public void onSetCurrent()
	{}
	
	//Zdarzenia komponentow GUI
	public void onGUIAction(GUIAction e)
	{}
	
	public void onPaint(DrawingInterface g, int width, int height)
	{}
}
