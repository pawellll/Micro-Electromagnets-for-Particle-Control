package GD;

import Platform.Main;

/**
 * Klasa, ktora jest glownym kontrolerem, obsluguje przekazywanie kontroli
 * innym kontrolerom.
 */
public class MainController
{
	public Controller currentCtrl = new MenuCrtl();
	
	public void start()
	{
		currentCtrl.setCurrent(Main.main.GUI);
	}
	
	void chageController(Controller controller)
	{
		currentCtrl = controller;
		currentCtrl.setCurrent(Main.main.GUI);
	}
	
}
