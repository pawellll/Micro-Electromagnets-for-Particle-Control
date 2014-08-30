package GD;

import Platform.CrossRes;
import Platform.Main;

/**
 * Klasa, ktora jest glownym kontrolerem, obsluguje przekazywanie kontroli
 * innym kontrolerom.
 */
public class MainController
{
	public Controller currentCtrl = new MenuCtrl();
					
					//new OptionsEditCtrl("Testowy", "0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0");
	
	public void start()
	{
		currentCtrl.setCurrent(Main.main.GUI);
	}
	
	public void chageController(Controller controller)
	{
		if (currentCtrl.executeOnLost)
			currentCtrl.onLostControl();
		currentCtrl = controller;
		currentCtrl.setCurrent(Main.main.GUI);
	}

	
}
