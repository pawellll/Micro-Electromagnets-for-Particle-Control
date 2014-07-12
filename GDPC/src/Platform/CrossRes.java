package Platform;

import Supp.Comm;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Klasa obsluguje ladowanie zasobow i plikow.
 */
public class CrossRes
{
  public static CrossBitmap GUI[] = new CrossBitmap[Comm.R_COUNT_GUI];
	
	
	public static void init()
	{	
		GUI[Comm.R_BCG]       = loadImg(Comm.R_F_BCG);
		GUI[Comm.R_LOGO]      = loadImg(Comm.R_F_LOGO);
		GUI[Comm.R_ARR_UP]    = loadImg(Comm.R_F_ARR_UP);
		GUI[Comm.R_ARR_DOWN]  = loadImg(Comm.R_F_ARR_DOWN);
		GUI[Comm.R_ARR_LEFT]  = loadImg(Comm.R_F_ARR_LEFT);
		GUI[Comm.R_ARR_RIGHT] = loadImg(Comm.R_F_ARR_RIGHT);
		GUI[Comm.R_CHECK_OFF] = loadImg(Comm.R_F_CHECK_OFF);
		GUI[Comm.R_CHECK_ON]  = loadImg(Comm.R_F_CHECK_ON);
	}
	
	public static String loadText(String fileName)
	{
		return "";
	}
	
	public static void saveText(String fileName, String text)
	{

	}
	
	public static CrossBitmap loadImg(String fileName)
	{
		try
		{
			return new CrossBitmap(ImageIO.read(new File(fileName)));
		} catch (IOException ex)
		{
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static void saveImg(String fileName, CrossBitmap bitmap)
	{
		try
		{
			ImageIO.write(bitmap.getBitmap(),"png",new File(fileName));
		} catch (IOException ex)
		{
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	
	
}
