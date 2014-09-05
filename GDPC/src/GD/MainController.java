package GD;

import Platform.CrossBitmap;
import Platform.CrossRes;
import Platform.Main;
import Supp.ImageFile;

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
		ImageFile[] abc = new ImageFile[1];
		abc[0] = new ImageFile(CrossRes.loadImg("F:\\\\Studia\\\\9 biometria wakacje\\\\2 Java\\\\GDPC\\\\wykres.png"),
											"looool");
		
		String s = "0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;1:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0;0:0";
		
		currentCtrl = new MenuCtrl(); //new DigitCtrl(abc, s);
		
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
