package Supp;

import Platform.CrossBitmap;

/**
 * Zawiera funkcje realizujace rysowanie po formie glownej programu
 */
public interface DrawingInterface 
{  
  //Kolory
    void gSetColor(int newColor);
    void gSetColor(int newColor, int Transparency);
    int gGetColor();
    
  //Czcionka
    void gSetFontName(String newName);
    void gSetFontSize(int newSize);
    void gSetFontStyle(int newStyle);
    int gGetFontSize();
    int gGetTextWidth(String text);
    int gGetTextHeight();
    
  //Piksele
    void gSetPixel(int x, int y, int color);
    
  //Figury
    void gDrawLine(int x1, int y1, int x2, int y2);
    void gDrawRectangle(int x, int y, int w, int h);
    void gFillRectangle(int x, int y, int w, int h);
    void gDrawRoundedRectangle(int x, int y, int w, int h, int arcW, int arcH);
    void gFillRoundedRectangle(int x, int y, int w, int h, int arcW, int arcH);
  
  //Tekst
    void gDrawText(int x, int y, String text);
    void gDrawText(int x, int y, int width, int height, String text, int flags);    
  
  //Bitmapy
    void gDrawBitmap(int x, int y, CrossBitmap bitmap);
    void gDrawBitmap(int x, int y, int width, int height, CrossBitmap bitmap, int flags);  
    void gStretchBitmap(int x, int y, int width, int height, CrossBitmap bitmap, int flag);  
    void gStretchAlignBitmap(int x, int y, int width, int height, int bitWidth, int bitHeight, CrossBitmap bitmap, int flag, int align);  
}
