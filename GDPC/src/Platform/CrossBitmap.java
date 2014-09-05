package Platform;

import Supp.Colors;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Klasa opakowujaca dla Bitmapy lub BufferedImage w zaleznosci od platformy. Ma
 * dostarczac wspolny interface niezalezny od platformy.
 */
public class CrossBitmap {

    /**
     * Przechowuje bitmape, typ zmiennej zmienny w zaleznosci od platformy
     */
    private BufferedImage img;

		public static BufferedImage newFrom(CrossBitmap image)
		{
      return new BufferedImage(image.getWidth(), image.getHeight(), image.img.getType());
    }	
	
		public static BufferedImage newBlank(int w, int h)
		{
      return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    }	
			
    public CrossBitmap(BufferedImage image) {
        img = image;
    }

    public BufferedImage getBitmap() {
        return img;
    }

    public CrossCanvas getCanvas() {
      return new CrossCanvas(img.createGraphics());
    }
    
    public void setBitmap(BufferedImage image) {
        img = image;
    }

    public int getRGB(int x, int y) {
        return img.getRGB(x, y);
    }
    
    public int getIntComponent0(int x, int y) {
        return Colors.getR(img.getRGB(x, y));
    }
    
    public int getIntComponent1(int x, int y) {
        return Colors.getG(img.getRGB(x, y));
    }
    
    public int getIntComponent2(int x, int y) {
        return Colors.getB(img.getRGB(x, y));
    }
    
    public void setIntColor(int x, int y, int r, int g, int b) {
        img.setRGB(x, y, Colors.RGB(r, g, b));
    }

    public void setRGB(int x, int y, int color) {
        img.setRGB(x, y, color);
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }
  
    public CrossBitmap getSubImage(int x, int y, int w, int h) {
        BufferedImage bitmap = new BufferedImage(w, h, img.getType());
        
        for (int i = x; i < x+w; i++) {
            for (int j = y; j < y+h; j++) {
                bitmap.setRGB(i-x, j-y, img.getRGB(i, j));
            }
        }
        
        return new CrossBitmap(bitmap);
    }
    
    public BufferedImage getBufferedImage(){
      return img;
    }


		public CrossBitmap clone() 
		{
      BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
			
			for (int y = 0; y< img.getHeight(); y++)
				for (int x = 0; x< img.getWidth(); x++)
					out.setRGB(x, y, img.getRGB(x, y));
			
			return new CrossBitmap(out);
		}		
		
		public CrossBitmap scale(int width, int height, boolean smooth)
		{	
			BufferedImage rescaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)rescaled.createGraphics();

			Object type;
			if (smooth)
				type = RenderingHints.VALUE_RENDER_QUALITY;
			else
				type = RenderingHints.VALUE_RENDER_SPEED;

			g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,type));
			g2d.drawImage(img, 0, 0, width, height, null);

			return new CrossBitmap(rescaled);
		}

		public CrossBitmap scale(double ratio, boolean smooth)
		{	
			return scale((int)(img.getWidth()*ratio), (int)(img.getHeight()*ratio), smooth);
		}	
	
		
		
		
		
		
}   
