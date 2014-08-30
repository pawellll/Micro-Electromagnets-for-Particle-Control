package GD;

import GUI.GUIAction;
import GUI.GUIMain;
import Supp.DrawingInterface;

/**
 * Klasa bazowa dla kontrolerów.
 */
public class Controller
{
	public boolean executeOnLost = false;
	protected GUIMain gui;
	
	public void setCurrent(GUIMain GUI)
	{
		gui = GUI;
		onSetCurrent();
	}	
	
	//Zdarzenia zewnetrzne
	public void onSetCurrent()
	{}
	
	public void onLostControl()
	{}
	
	//Zdarzenia komponentow GUI
	public void onGUIAction(GUIAction e)
	{}
	
	public void onPaint(DrawingInterface g, int width, int height)
	{}
	
	public void onAfterPaint(DrawingInterface g, int width, int height)
	{}
	
}
