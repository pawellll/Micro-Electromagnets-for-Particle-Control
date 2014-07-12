package GD;

import GUI.GUIAction;
import GUI.GUIImage;
import Platform.CrossBitmap;
import Platform.Main;
import Supp.Colors;
import Supp.Comm;
import Supp.Dim;
import Supp.DoubleArea;
import Supp.DrawingInterface;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class CutCtrll extends Controller {

    GUIImage imgHolder;
    CrossBitmap image;
    int i = 0; // licznik wywoøañ funkcji GUIAction.IMG_SELECTED
    CrossBitmap[] subImages = new CrossBitmap[6];      //legena, wykres, tytuly osi x,y, wartosci na osiach,x,y

    CutCtrll(File file) throws IOException {
        image = new CrossBitmap(ImageIO.read(file));
    }

    @Override
    public void onPaint(DrawingInterface g, int width, int height) {
        g.gDrawRectangle(50, 50, 100, 100);

    }

    @Override
    public void onGUIAction(GUIAction e) {
        if (e.action == GUIAction.IMG_SELECTED) {
          switch (i+1)
          {
            case Comm.D_BIT_GRAPH:
              gui.setInfo("Prosze wybrac wykres.");
             break;
            case Comm.D_BIT_TITLE_X:
              gui.setInfo("Prosze wybrac tytul osi X.");
             break;
            case Comm.D_BIT_TITLE_Y:
              gui.setInfo("Prosze wybrac  tytul osi Y.");
             break;
            case Comm.D_BIT_VALUES_X:
              gui.setInfo("Prosze wybrac wartosci osi X.");
             break;
            case Comm.D_BIT_VALUES_Y:
              gui.setInfo("Prosze wybrac wartosci tytul osi Y.");
             break;
          }
          
            DoubleArea data = (DoubleArea) e.data;
//            System.out.println("Zaznaczono: " + data.startX + " , " + data.startY + " , " + data.endX + " , " + data.endY);

            //Tak aby punkt Start był bardziej lewo-gornym niz End
            int startX = (int) Math.min(data.startX, data.endX);
            int startY = (int) Math.min(data.startY, data.endY);
            int endX = (int) Math.max(data.startX, data.endX);
            int endY = (int) Math.max(data.startY, data.endY);

            //Pozycja wzgledem obrazka, nie okna programu
            startX = startX - imgHolder.getX();
            startY = startY - imgHolder.getY();
            endX = endX - imgHolder.getX();
            endY = endY - imgHolder.getY();

            //Pozycja wzgledem oryginalnego obrazka
            startX = (int) (image.getWidth() * (double) startX / Dim.X(imgHolder.w));
            startY = (int) (image.getHeight() * (double) startY / Dim.Y(imgHolder.h));
            endX = (int) (image.getWidth() * (double) endX / Dim.X(imgHolder.w));
            endY = (int) (image.getHeight() * (double) endY / Dim.Y(imgHolder.h));

//            System.out.println(startX + " , " + startY + " , " + endX + " , " + endY);

            subImages[i] = image.getSubImage(startX, startY, endX - startX, endY - startY);

            setColor(startX, startY, endX - startX, endY - startY, Colors.WHITE);
            Main.main.canvas.repaint();
            
            /*
            File outputfile = new File("saved" + i + ".jpg");
            System.err.println("Zapisuje" + i);
            try {
                ImageIO.write(subImages[i].getBufferedImage(), "jpg", outputfile);
            } catch (IOException ex) {
                Logger.getLogger(CutCtrll.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Hejo");
            }*/
            
            if (i==5)
            { 
              Main.main.mainCrtl.chageController(new DigitCtrll(subImages));
            }

            ++i; // zwieksza licznik akcji IMG_SELECTED
        }
    }

    @Override
    public void onSetCurrent() {
        gui.clearComponents();
        imgHolder = new GUIImage("trololo", 0, 0, 80, 80);
        imgHolder.setContainer(100, 100);
        imgHolder.align = Comm.ALIGN_CENTER | Comm.ALIGN_VCENTER;
        if (image.getWidth() > image.getHeight()) {
            imgHolder.setImage(image, Comm.STRETCH_H_PROP);
        } else {
            imgHolder.setImage(image, Comm.STRETCH_V_PROP);
        }
        imgHolder.setSelectable();
        gui.addComponent(imgHolder);
              
        ///System.out.println("Prosze wybrac legende.");
        gui.setInfo("Prosze wybrac legende.");
    }

    private void setColor(int x, int y, int w, int h, int color) {
        for (int ii = x; ii < x + w; ++ii) {
            for (int jj = y; jj < y + h; ++jj) {
                image.setRGB(ii, jj, color);
            }
        }
    }
}
