package processing;

import Platform.CrossBitmap;
import Supp.DoubleArea;


/**
 *  ||||||||||| Returns left upper corner, width and height in DoubleArea class
 * @author pawel
 */
public class FrameDetecting {

    private double[] sinTable;
    private double[] cosTable;

    /*
    public CrossBitmap process(CrossBitmap image){
        CrossBitmap transformedImage =  lineDetecting(image);
        image = getFrame(image, transformedImage);
        return setOpaque(image);
    }
    */
    
    public DoubleArea process(CrossBitmap image){
        CrossBitmap transformedImage =  lineDetecting(image);
        return getFrame(image, transformedImage);
    }
    
    private DoubleArea getFrame(CrossBitmap originalImage, CrossBitmap transformedImage) {
        int halfHeight = (int) (transformedImage.getHeight()/2.0);
        int halfWidth = (int) (transformedImage.getWidth()/2.0);
        
        int x0 = 0;
        int x1 = 0;
        int y0 = 0;
        int y1 = 0;
        int i;
        
        
        for(i=0;i<transformedImage.getWidth();++i){
            if(transformedImage.getIntComponent0(i,halfHeight)==0){
                x0=i;
                break;
            }
        }
        for(i=i+1;i<transformedImage.getWidth();++i){
            if(transformedImage.getIntComponent0(i, halfHeight)==255){
                break;
            }
        }
        
        for(i=i+1;i<transformedImage.getWidth();++i){
            if(transformedImage.getIntComponent0(i,halfHeight)==0){
                x1=i;
                break;
            }
        }
        ///////////////
        
        for(i=0;i<transformedImage.getHeight();++i){
            if(transformedImage.getIntComponent0(halfWidth,i)==0){
                y0=i;
                break;
            }
        }
        for(i=i+1;i<transformedImage.getHeight();++i){
            if(transformedImage.getIntComponent0(halfWidth, i)==255){
                break;
            }
        }
        
        for(i=i+1;i<transformedImage.getHeight();++i){
            if(transformedImage.getIntComponent0(halfWidth,i)==0){
                y1=i;
                break;
            }
        }
        ////////////
        int width = Math.abs(x1-x0); // get width
        int height = Math.abs(y1-y0); // get height
        // get upper left corner
        int X = x1<x0 ? x1 : x0;
        int Y = y1<y0 ? y1 : y0;
        
        //return originalImage.getSubImage(X, Y, width, height);
        return new DoubleArea(X, Y, width, height); // returns startPoint, width and height of frame
    }

    private CrossBitmap lineDetecting(CrossBitmap imageIn) {
        // get copy of imageIn
        CrossBitmap imageOut = imageIn.clone();
        // do the gray scale on a picture
        doGrayScale(imageIn);
        // amount of votes needed to recognise line
        int w = imageIn.getWidth();
        int h = imageIn.getHeight();
        final int T = (int) (0.4 * (h > w ? h : w));
        //System.err.println(T + " <---");

        // get range of parameters
        final int maxRo = (int) Math.hypot(imageIn.getWidth(), imageIn.getHeight());
        final int maxTheta = 360;
        // creat table of values for diffrent thetas in orded to improve performance
        sinTable = new double[maxTheta];
        cosTable = new double[maxTheta];
        for (int i = 0; i < maxTheta; ++i) {
            sinTable[i] = Math.sin(Math.toRadians(i));
            cosTable[i] = Math.cos(Math.toRadians(i));
        }

        //System.err.println(maxRo + "<--maxRo,maxTheta-->" + maxTheta);
        // create table for possible lines on image
        int[][] lines = new int[maxRo][maxTheta];
        // creat table saying if particular line is recognised
        boolean[][] isLine = new boolean[maxRo][maxTheta];

        // go through entire image and collect votes from every pixel for every possible line
        int theta, ro;
        for (int x = 0; x < imageIn.getWidth(); x++) {
            for (int y = 0; y < imageIn.getHeight(); y++) {
                int r = imageIn.getIntComponent0(x, y);
                if (r < 200) {
                    for (theta = 0; theta < maxTheta; ++theta) {
                        ro = (int) (x * cosTable[theta] + y * sinTable[theta]);
                        if (ro > 0 && ro < maxRo) {
                            //System.err.println(theta + "<--theta,ro--> " + ro);
                            ++lines[ro][theta];
                        }
                    }
                }
            }
        }

        // if votes for line > T then line is recognised
        for (theta = 0; theta < maxTheta; ++theta) {
            for (ro = 0; ro < maxRo; ++ro) {
                if (lines[ro][theta] > T) {
                    isLine[ro][theta] = true;
                }
            }
        }
        //We have lines, now on white picture draw all the lines
        // first make the picture white
        for (int i = 0; i < imageOut.getWidth(); ++i) {
            for (int j = 0; j < imageOut.getHeight(); ++j) {
                imageOut.setIntColor(i, j, 255, 255, 255);
            }
        }

        // change color of lines to red
        colorLines(lines, isLine, imageOut);
        //set alpha to 255
        setOpaque(imageOut);
        
        return imageOut;
    }

    private void colorLines(int[][] lines, boolean[][] isLine, CrossBitmap image) {
        int maxRo = lines.length;
        int maxTheta = lines[0].length;
        //System.err.println(maxTheta + "<--maxTheta,maxRo-->" + maxRo);

        int theta, ro;
        for (theta = 0; theta < maxTheta; ++theta) {
            for (ro = 0; ro < maxRo; ++ro) {
                if (isLine[ro][theta]) {
                    colorSingleLine(ro, theta, 0, image);
                }
            }
        }
    }

    private void colorSingleLine(int ro, int theta, int color, CrossBitmap image) {
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                if (ro == (int) (x * cosTable[theta] + y * sinTable[theta])) {
                    image.setIntColor(x, y, color, color, color);
                }

            }
        }
    }

    private CrossBitmap setOpaque(CrossBitmap image) {
        int r, g, b;
        final int alpha = 255; // set alpha to 255
        int color;
        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                r = image.getIntComponent0(i, j);
                g = image.getIntComponent1(i, j);
                b = image.getIntComponent2(i, j);
                color = (alpha << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(i, j, color);
            }
        }
        return image;
    }

    private void doGrayScale(CrossBitmap image) {
        int r, g, b;
        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                r = image.getIntComponent0(i, j);
                g = image.getIntComponent1(i, j);
                b = image.getIntComponent2(i, j);

                image.setIntColor(i, j, (r + g + b) / 3, (r + g + b) / 3, (r + g + b) / 3);
            }
        }
    }

}
