package Platform;

import GD.MainController;
import GUI.GUIMain;
import Supp.Comm;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author SBA
 */
public class Main extends JFrame
{                
	public final MainController mainCrtl;
	
  public final CrossSurface canvas;                
  public final GUIMain GUI;                

  public Main() 
  {
    super(Comm.APP_NAME);
    setTitle(Comm.APP_NAME);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setPreferredSize(new java.awt.Dimension(500, 300));
    
		//Ladowanie zasobow
		CrossRes.init();
		
		//Ustawienie ikony
		ImageIcon img = new ImageIcon("logo.png");
		setIconImage(img.getImage()); 
		
    //Tworzenie GUI
    GUI = new GUIMain();
    
    canvas = new CrossSurface(GUI);
    
    setLayout(new BorderLayout());
    setContentPane(canvas);
    
		//Uruchomienie glownego kontrolera
		mainCrtl = new MainController();

    pack(); 
  }
  
	public void init()
	{
		mainCrtl.start();
	}
  
        
	public static Main main;
  
  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
      public void run() {
				main = new Main();
				main.init();
        main.setVisible(true);
      }
    });
  }
  
}
