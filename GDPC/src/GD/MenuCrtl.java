package GD;

import GUI.GUIAction;
import GUI.GUIButton;
import GUI.GUIMain;
import Platform.CrossRes;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Klasa kontrolera glownego menu.
 */
public class MenuCrtl extends Controller
{
	private static final String ID_BUT_DIGIT = "MenuCrtlButDigit";
	private static final String ID_BUT_SETUP = "MenuCrtlButSetup";
	private static final String ID_BUT_EXIT  = "MenuCrtlButExit";
	
	@Override
	public void onSetCurrent()
	{
		gui.clearComponents();
		
		GUIButton but = new GUIButton(ID_BUT_DIGIT, 0, 35, 40, 10, "Digitalizuj", 120, 25);
		but.align = Comm.ALIGN_CENTER;
		but.setContainer(100, 0);
		but.longestText = "Ustawienia";
		gui.addComponent(but);
		
		but = new GUIButton(ID_BUT_SETUP, 0, 50, 40, 10, "Ustawienia", 120, 25);
		but.align = Comm.ALIGN_CENTER;
		but.setContainer(100, 0);
		but.longestText = "Ustawienia";		
		gui.addComponent(but);
				
		but = new GUIButton(ID_BUT_EXIT, 0, 65, 40, 10, "Wyjście", 120, 25);
		but.align = Comm.ALIGN_CENTER;
		but.setContainer(100, 0);
		but.longestText = "Ustawienia";		
		gui.addComponent(but);
		
		
	}
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		//Tlo gora
		g.gStretchBitmap(0, 0, width, Dim.Y(26.5), CrossRes.GUI[Comm.R_BCG], Comm.STRETCH_HV);
		
		//Logo
		g.gStretchAlignBitmap(0, 0, width, 0, 0, Dim.Y(26.5), CrossRes.GUI[Comm.R_LOGO], Comm.STRETCH_V_PROP, Comm.ALIGN_CENTER);
						
	}

	@Override
	public void onGUIAction(GUIAction e)
	{
		switch (e.action)
		{
			case GUIAction.BUTTON_CLICK:
				if (e.id.equals(ID_BUT_EXIT))
					System.exit(0);
				if (e.id.equals(ID_BUT_DIGIT))
				{
					openImage();
				}
				else
					Main.main.mainCrtl.chageController(new DigitCrtl(null));
			break;
		}
	}
	
	        private void openImage() {
        final JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("ImageFiles", "jpg", "jpeg", "png", "gif");
        fc.setFileFilter(filter);
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
            try {
                Main.main.mainCrtl.chageController(new kontr(fc.getSelectedFile()));
            } catch (IOException ex) {
                System.err.println("Wybieraj jakis lepszy ten obrazek");
                Logger.getLogger(MenuCrtl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("Wybieraj ten obrazek");
        }

    }
	
}
