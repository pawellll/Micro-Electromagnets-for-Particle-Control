package processing;

import Platform.CrossBitmap;


/**
 * Progowanie globalne
 * 
 * @author Maciek
 */
public class ThresholdGlobal {

    /**
     * Ta funkcja przetwarza zdjęcie jej kod należy zmieniać
     *
     * @param imageIn
     * @param imageOut
     */
    public void process(
            CrossBitmap imageIn,
            CrossBitmap imageOut) {
        int r, g, b, grey, greySum, greyAv;
        
        greySum = 0;
        
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                r = ((int) imageIn.getIntComponent0(x, y));
                g = ((int) imageIn.getIntComponent1(x, y));
                b = ((int) imageIn.getIntComponent2(x, y));
                
                //grey = (int)((r*0.3)+(g*0.59)+b*(0.11));
                grey = (int)((r+g+b)/3);
                greySum += grey;
                
                imageIn.setIntColor(x, y, grey, grey, grey);
            }
        }
        
        greyAv = greySum/(imageIn.getWidth()*imageIn.getHeight());
        
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                grey = ((int) imageIn.getIntComponent0(x, y));

                if(grey <= greyAv) {
                    imageOut.setIntColor(x, y, 0, 0, 0);

                } else {
                    
                    imageOut.setIntColor(x, y, 255, 255, 255);
                }
            }
        }
    }
}
