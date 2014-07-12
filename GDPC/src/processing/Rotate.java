package processing;

import Platform.CrossBitmap;
import java.awt.image.BufferedImage;

/**
 * Obrót obrazu, przekształcanie z kopii
 * 
 * @author Maciek
 */
public class Rotate {

    public int newHeight(int height, int width) {
        return (int) (height * Math.cos(angle) + width * Math.sin(angle));
    }

    public int newWidth(int height, int width) {
        return (int) (width * Math.cos(angle) + height * Math.sin(angle));
    }

    public Rotate(double angle) {
        this.angle = Math.toRadians(angle);
    }
    
    private final double angle;
    /**
     * Ta funkcja przetwarza zdjęcie jej kod należy zmieniać
     *
     * @param imageIn
     * @param imageOut
     */
    public void process(
            CrossBitmap imageIn,
            CrossBitmap imageOut) {
        int r, g, b, newHeight, newWidth, oldX, oldY;

        newHeight = newHeight(imageIn.getHeight(), imageIn.getWidth());
        newWidth = newWidth(imageIn.getHeight(), imageIn.getWidth());

        System.out.println(angle);
        System.out.println(imageIn.getWidth() + " -> " + newWidth);
        System.out.println(imageIn.getHeight() + " -> " + newHeight);
        
        
        imageOut.setBitmap(new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB));

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {

                int actualX = (int)(x - imageIn.getHeight()*Math.sin(angle));
                
                oldX = (int) ((Math.cos(-angle) *actualX) - (Math.sin(-angle) * y));
                oldY = (int) ((Math.sin(-angle) *actualX) + (Math.cos(-angle) * y));

                if ((oldX < 0 || oldX >= imageIn.getWidth()) || (oldY < 0 || oldY >= imageIn.getHeight())) {
                    r = 0;
                    g = 0;
                    b = 0;
                } else {
                    r = imageIn.getIntComponent0(oldX, oldY);
                    g = imageIn.getIntComponent1(oldX, oldY);
                    b = imageIn.getIntComponent2(oldX, oldY);
                }
                imageOut.setIntColor(x, y, r, g, b);

            }
        }
    }
}
