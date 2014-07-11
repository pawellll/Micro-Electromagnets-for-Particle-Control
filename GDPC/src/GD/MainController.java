package GD;

import Platform.Main;

/**
 * Klasa, ktora jest glownym kontrolerem, obsluguje przekazywanie kontroli
 * innym kontrolerom.
 */
public class MainController extends Thread
{
	public Controller currentCtrl = null;
	
	
	private boolean runThread = true;

	private Boolean isChangeReady = true;
	private Controller crtlToChange = new MenuCrtl();
	
	@Override
	public void run()
	{
		while (runThread)
		{
			synchronized (isChangeReady)
			{
				if (isChangeReady)
				{
					isChangeReady = false;
					currentCtrl = crtlToChange;

					currentCtrl.setCurrent(Main.main.GUI);
				}
			}
		}
	}
	
	synchronized void quit()
	{
		runThread = false;
	}
	
	synchronized void chageController(Controller controller)
	{
		crtlToChange = controller;
		synchronized (isChangeReady)
		{
			isChangeReady = true;
		}
	}
	
}
