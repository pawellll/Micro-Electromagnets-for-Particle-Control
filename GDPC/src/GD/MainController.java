package GD;

import Platform.CrossBitmap;
import Platform.CrossRes;
import Platform.Main;

/**
 * Klasa, ktora jest glownym kontrolerem, obsluguje przekazywanie kontroli
 * innym kontrolerom.
 */
public class MainController
{

	public Controller currentCtrl = null;
					
					//new OptionsEditCtrl("Testowy", "0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0");
	
	public void start()
	{
		CrossBitmap[] abc = new CrossBitmap[1];
		abc[0] = CrossRes.loadImg("F:\\\\Studia\\\\9 biometria wakacje\\\\2 Java\\\\GDPC\\\\wyk2.bmp");

		String s = "0:0;0:0;0:0;0:0;0:2;0:0;0:0;2:0;0:0;0:0;0:0;1:0;1:0;1:0;1:0;1:0;1:0;0:0;0:0;1:0";
		
		currentCtrl = new DigitCtrl(abc, s);
		
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
