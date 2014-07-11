package Platform;

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

    public CrossBitmap(BufferedImage image) {
        img = image;
    }

    public BufferedImage getBitmap() {
        return img;
    }

    public void setBitmap(BufferedImage image) {
        img = image;
    }

    public int getRGB(int x, int y) {
        return img.getRGB(x, y);
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
        return new CrossBitmap(img.getSubimage(x, y, w, h));
    }
    
    public BufferedImage getBufferedImage(){
        return img;
    }

}   
