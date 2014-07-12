package GD;

import Platform.CrossBitmap;
import Supp.Colors;
import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Quantization1 
{
    public static CrossBitmap quantitize(CrossBitmap imageIn, Set<Color> palette, int colors){
				CrossBitmap out = imageIn.clone();
				applyPalette(imageIn, out, palette);
        HashMap<Color, Integer> hist = getColorHistogram(out);


        List<Map.Entry<Color, Integer>> list = new LinkedList<Map.Entry<Color, Integer>>( hist.entrySet() );

        Collections.sort( list, new Comparator<Map.Entry<Color, Integer>>()
        {
            @Override
            public int compare( Map.Entry<Color, Integer> o1, Map.Entry<Color, Integer> o2 )
            {
                return (o1.getValue() > o2.getValue() ? -1: 1);
            }
        } );

        Set<Color> newPalette = reducedPalette(list, colors);
        applyPalette(out.clone(), out, newPalette);
				
				return out;
    }

    /**
     * Apply a palette to an image.
     */
    private static void applyPalette(CrossBitmap imageIn, CrossBitmap imageOut, Set<Color> palette){
        Color color;
        for(int y=0; y<imageIn.getHeight(); y++){
            for(int x=0; x<imageIn.getWidth(); x++){
                int red = Colors.getR(imageIn.getRGB(x, y));
                int green = Colors.getG(imageIn.getRGB(x, y));
                int blue = Colors.getB(imageIn.getRGB(x, y));

                color = getNearestColor(red, green, blue, palette);
                imageOut.setRGB(x, y, Colors.RGB(color.getRed(), color.getGreen(), color.getBlue()));
            }
        }
    }

    /**
     * Reduce the palette colors to a given number. The list is sorted by usage.
     */
    private static Set<Color> reducedPalette(List<Map.Entry<Color, Integer>> palette, int colors){
        Set<Color> ret = new HashSet<Color>();
        for(int i=0; i<colors; i++){
            ret.add(palette.get(i).getKey());
        }
        return ret;
    }

    /**
     * Compute color histogram
     */
    private static HashMap<Color, Integer> getColorHistogram(CrossBitmap image){
        HashMap<Color, Integer> ret = new HashMap<Color, Integer>();

        for(int y=0; y<image.getHeight(); y++){
            for(int x=0; x<image.getWidth(); x++){
                Color c = new Color
                (
                    Colors.getR(image.getRGB(x, y)),
                    Colors.getG(image.getRGB(x, y)),
                    Colors.getB(image.getRGB(x, y))
                );

                if(ret.get(c) == null){
                    ret.put(c, 0);
                }
                ret.put(c, ret.get(c)+1);
            }
        }
        return ret;
    }

    private static Color getNearestColor(int red, int green, int blue, Set<Color> palette){
        Color nearestColor=null, c;
        double nearestDistance=Integer.MAX_VALUE;
        double tempDist;
        Iterator<Color> it = palette.iterator();

        while(it.hasNext()){
            c = it.next();
            tempDist = distance(red, green, blue, c.getRed(), c.getGreen(), c.getBlue());
            if(tempDist < nearestDistance){
                nearestDistance = tempDist;
                nearestColor = c;
            }
        }
        return nearestColor;
    }

    private static double distance(int r1, int g1, int b1, int r2, int g2, int b2){
        double dist= Math.pow(r1-r2,2) + Math.pow(g1-g2,2) + Math.pow(b1-b2,2);
        return Math.sqrt(dist);
    }
}
