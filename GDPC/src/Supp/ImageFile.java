package Supp;

import Platform.CrossBitmap;
import java.util.List;

/**
 * Klasa przechowujaca wczytany obrazek oraz nazwe jego pliku. 
 */
public class ImageFile 
{
	public CrossBitmap img = null;
	public String name = "";
	
	public ImageFile(CrossBitmap image, String fileName)
	{
		img = image;
		name = fileName;
	}
	
	public static ImageFile[] toArrray(List<ImageFile> list)
	{
		ImageFile[] res = new ImageFile[list.size()];
		for (int i=0; i< list.size(); i++)
			res[i] = list.get(i);
		
		return res;			
	}
	
}
