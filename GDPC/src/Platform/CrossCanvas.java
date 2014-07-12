package Platform;

import Supp.Comm;
import Supp.DrawingInterface;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Klasa opakowujaca dla Canvas lub Graphics2D w zaleznosci od platformy.
 * Ma dostarczac wspolne plotno do rysowania, niezalezne od platformy.
 */
public class CrossCanvas implements DrawingInterface
{
  private final Graphics2D c;
  
  public CrossCanvas(Graphics2D canvas)
  {
    c = canvas;
  }

  @Override
  public void gSetColor(int newColor) {
    c.setColor(new Color(newColor));
  }
	
  @Override
  public void gSetColor(int newColor, int Transparency){
		Color temp = new Color(newColor);
    c.setColor(new Color(temp.getRed(), temp.getGreen(),temp.getBlue(), Transparency ));
  }	
    
  @Override
  public int gGetColor() {
    return c.getColor().getRGB();
  }

  @Override
  public void gSetFontName(String newName) {
    c.setFont(new Font(newName, c.getFont().getStyle(), c.getFont().getSize()));
  }

  @Override
  public void gSetFontSize(int newSize) {
    c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), newSize));
  }

  @Override
  public void gSetFontStyle(int newStyle) {
    c.setFont(new Font(c.getFont().getName(), newStyle, c.getFont().getSize()));
  }

  @Override
  public int gGetFontSize() {
    return c.getFont().getSize();
  }

  @Override
  public int gGetTextWidth(String text) {
    return c.getFontMetrics().stringWidth(text);
  }

  @Override
  public int gGetTextHeight() {
    return c.getFontMetrics().getHeight();
  }

  @Override
  public void gSetPixel(int x, int y, int color) {
    c.drawLine(x, y, x, y);
  }

  @Override
  public void gDrawLine(int x1, int y1, int x2, int y2) {
    c.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void gDrawRectangle(int x, int y, int w, int h) {
    c.drawRect(x, y, w, h);
  }

  @Override
  public void gFillRectangle(int x, int y, int w, int h) {
    c.fillRect(x, y, w, h);
  }
    
	@Override
  public void gDrawRoundedRectangle(int x, int y, int w, int h, int arcW, int arcH)
	{
		c.drawRoundRect(x, y, w, h, arcW, arcH);
	}
	
	@Override
  public void gFillRoundedRectangle(int x, int y, int w, int h, int arcW, int arcH)
	{
		c.fillRoundRect(x, y, w, h, arcW, arcH);
	}
	
	
  @Override
  public void gDrawText(int x, int y, String text) {
    c.drawString(text, x, y);
  }

  @Override
  public void gDrawText(int x, int y, int width, int height, String text, int flags) {
    int nx = x;
    int ny = y;
    
    if ((flags & Comm.ALIGN_CENTER) == Comm.ALIGN_CENTER)
      nx = x + (int)(width/2.0) - (int)(gGetTextWidth(text)/2.0);
    else
    if ((flags & Comm.ALIGN_RIGHT) == Comm.ALIGN_RIGHT)
      nx = x + width - gGetTextWidth(text); 
    
    if ((flags & Comm.ALIGN_VCENTER) == Comm.ALIGN_VCENTER)
      ny = y + (int)(height/2.0) + (int)(gGetTextHeight()/4.0);
    else
    if ((flags & Comm.ALIGN_BOTTOM) == Comm.ALIGN_BOTTOM)
      ny = y + height - gGetTextHeight();
      
    c.drawString(text, nx, ny);    
  }

  @Override
  public void gDrawBitmap(int x, int y, CrossBitmap bitmap) {
    c.drawImage(bitmap.getBitmap(), null, x, y);
  }

  @Override
  public void gDrawBitmap(int x, int y, int width, int height, CrossBitmap bitmap, int flags) {
    int nx = x;
    int ny = y;
    
    if ((flags & Comm.ALIGN_CENTER) == Comm.ALIGN_CENTER)
      nx = x + (int)(width/2.0) - (int)(bitmap.getWidth()/2.0);
    else
    if ((flags & Comm.ALIGN_RIGHT) == Comm.ALIGN_RIGHT)
      nx = x + width - bitmap.getWidth(); 
    
    if ((flags & Comm.ALIGN_VCENTER) == Comm.ALIGN_VCENTER)
      ny = y + (int)(height/2.0) - (int)(bitmap.getHeight()/2.0);
    else
    if ((flags & Comm.ALIGN_BOTTOM) == Comm.ALIGN_BOTTOM)
      ny = y + height - bitmap.getHeight();
      
    c.drawImage(bitmap.getBitmap(), null, nx, ny);
  }

  @Override
  public void gStretchBitmap(int x, int y, int width, int height, CrossBitmap bitmap, int flag) {
		int nw = bitmap.getWidth();
    int nh = bitmap.getHeight();
    
		if (flag == Comm.STRETCH_H_PROP)
		{
			nh = (int)((double)nh*(double)width/nw);
			nw = width;
		}
		else
		if (flag == Comm.STRETCH_V_PROP)
		{
			nw = (int)((double)nw*(double)height/nh);
			nh = height;
		}
		else
		{		
			if (flag == Comm.STRETCH_H || flag == Comm.STRETCH_HV)
				nw = width;

			if (flag == Comm.STRETCH_V || flag == Comm.STRETCH_HV)
				nh = height;
		}
		
    c.drawImage(bitmap.getBitmap(), x, y, nw, nh, null);
	}

  @Override
  public void gStretchAlignBitmap(int x, int y, int width, int height, int bitWidth, int bitHeight, CrossBitmap bitmap, int flag, int align)
	{	
		int nw = bitmap.getWidth();
    int nh = bitmap.getHeight();
    
		if (flag == Comm.STRETCH_H_PROP)
		{
			nh = (int)((double)nh*(double)bitWidth/nw);
			nw = bitWidth;
		}
		else
		if (flag == Comm.STRETCH_V_PROP)
		{
			nw = (int)((double)nw*(double)bitHeight/nh);
			nh = bitHeight;
		}
		else
		{			
			if (flag == Comm.STRETCH_H || flag == Comm.STRETCH_HV)
				nw = bitWidth;

			if (flag == Comm.STRETCH_V || flag == Comm.STRETCH_HV)
				nh = bitHeight;
		}
    int nx = x;
    int ny = y;
    
    if ((align & Comm.ALIGN_CENTER) == Comm.ALIGN_CENTER)
      nx = x + (int)(width/2.0) - (int)(nw/2.0);
    else
    if ((align & Comm.ALIGN_RIGHT) == Comm.ALIGN_RIGHT)
      nx = x + width - nw; 
    
    if ((align & Comm.ALIGN_VCENTER) == Comm.ALIGN_VCENTER)
      ny = y + (int)(height/2.0) - (int)(nh/2.0);
    else
    if ((align & Comm.ALIGN_BOTTOM) == Comm.ALIGN_BOTTOM)
      ny = y + height - nh;
		
    c.drawImage(bitmap.getBitmap(), nx, ny, nw, nh, null);
  }
  
}
