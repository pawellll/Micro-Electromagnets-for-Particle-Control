/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocr;

import Digit.Digitalize;
import Platform.CrossBitmap;
import Supp.Colors;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Maciek
 */
public class OCRCharacter {
    
        /*
     Member variables
     */
    protected CrossBitmap letterImage; // obraz reprezentujący znak
    protected ArrayList<Double> features; // wektor cech
    protected char character; // znak
    
    public OCRCharacter() {
    
    }
    /**
     * Konstruktor przyjmujący tylko obraz, do tworzenia zbioru wejściowego
     * @param newImage obraz reprezentujący znak
     */
    public OCRCharacter(CrossBitmap newImage) {
        this.letterImage = newImage;
        this.features = new ArrayList();
        this.extractFeatures();
    }
    
    /**
     * Konstruktor przyjmujący obraz reprezentujący znak i odpowiadający mu znak, 
     * do tworzenia zbioru testowego
     * @param newImage obraz reprezentujący znak
     * @param character znak przypisany do obrazu
     */
    public OCRCharacter(CrossBitmap newImage, char character) {
        this.letterImage = newImage;
        this.features = new ArrayList();
        this.extractFeatures();
        this.character = character;
    }

    /*
     Setters, getters
     */
    public void setFeatureVector(ArrayList newFeatures) {
        this.features = newFeatures;
    }
    public void setImage(CrossBitmap newLetterImage) {
        this.letterImage = newLetterImage;
    }  
    public void setChar(char newChar) {
        this.character = newChar;
    }
    public char getChar() {
        return this.character;
    }
    public ArrayList getFeatureVector() {
        return this.features;
    }
    public CrossBitmap getImage() {
        return this.letterImage;
    }  
    
    /**
     * Funkcja przeskalowująca obraz do nowych rozmiarów
     * @param newWidth
     * @param newHeight 
     */
    private void resizeImage(int newWidth, int newHeight) {
        int r, g, b, oldX, oldY;

        CrossBitmap tempImage = new CrossBitmap(new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB));

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {

                oldX = (x * letterImage.getWidth()) / newWidth;
                oldY = (y * letterImage.getHeight()) / newHeight;

                r = ((int) letterImage.getIntComponent0(oldX, oldY));
                g = ((int) letterImage.getIntComponent1(oldX, oldY));
                b = ((int) letterImage.getIntComponent2(oldX, oldY));

                tempImage.setIntColor(x, y, r, g, b);
            }
        }
        
        this.letterImage = tempImage.clone();
    }    
    
    /**
     * Funkcja licząca ilość czarnych pikseli w segmencie obrazu o wymiarach 4x4
     * którego pierwszy piksel posiada współrzędne x, y
     * @param x pierwsza współrzędna
     * @param y druga współrzędna
     * @return 
     */
    private int subregionCount(int x, int y) {
        int localBcount = 0;
        for(int i = x; i < x+4; i++) {
            for(int j = y; j < y+4; j++) {
                if(letterImage.getIntComponent0(i, j) < 127) localBcount += 1;
            }
        }
        return localBcount;
    }
    
    /**
     * Metoda, która tworzy wektor cech 
     */
    private void extractFeatures() {
        this.resizeImage(20, 20);
        
        int blackCount = 0;
        
        for(int x = 0; x < letterImage.getWidth(); x++) {
            for(int y = 0; y < letterImage.getHeight(); y++) {
                if(Digitalize.RGBDistance(letterImage.getRGB(x, y), Colors.WHITE) < 10) {
                    blackCount++;
                }
            }
        }
        
        for(int x = 0; x < letterImage.getWidth(); x+=4) {
            for(int y = 0; y < letterImage.getHeight(); y+=4) {
                double localFeature = (double)subregionCount(x, y)/blackCount;
                features.add(localFeature);
            }
        }
    }
}
