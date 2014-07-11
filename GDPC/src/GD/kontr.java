package GD;

import GUI.GUIAction;
import GUI.GUIImage;
import Platform.CrossBitmap;
import Supp.Comm;
import Supp.DoubleArea;
import Supp.DrawingInterface;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class kontr extends Controller {

    GUIImage imgHolder;
    CrossBitmap image;
    int i = 0; // licznik wywoøañ funkcji GUIAction.IMG_SELECTED
    CrossBitmap[] subImages = new CrossBitmap[6];      //legena, wykres, tytuly osi x,y, wartosci na osiach,x,y

    kontr(File file) throws IOException {
        image = new CrossBitmap(ImageIO.read(file));
    }

    @Override
    public void onPaint(DrawingInterface g, int width, int height) {
        g.gDrawRectangle(50, 50, 100, 100);
    }

    @Override
    public void onGUIAction(GUIAction e) {
        if (e.action == GUIAction.IMG_SELECTED) {
            // komunikat do uzytkownika co ma zrobic gdzies na zewnatrz powinien byc, 6 razy, prowadzimy go za reke : )

            DoubleArea data = (DoubleArea) e.data;
            System.out.println("Zaznaczono: " + data.startX + " , " + data.startY + " , " + data.endX + " , " + data.endY);
            System.out.println(imgHolder.getX() + " " + imgHolder.getY());
            int x, y, w, h; // wzlgldem obrazka, x,y punkt od którego wycinamy, górny lewy rog to musi byc, w,h - rozmiar prostokata do wyciecia

            w = (int) Math.abs(data.startX - data.endX);
            h = (int) Math.abs(data.startY - data.endY);

            x = (int) Math.min(data.startX,data.endX); // x gorny lewy rog
            y = (int) Math.min(data.startY,data.endY); // y gorny lewy rog
            
            x-=imgHolder.getX();
            y-=imgHolder.getY();
            subImages[i] = image.getSubImage(x, y, w, h);
            
            
            File outputfile = new File("saved"+i+".jpg");
            System.err.println("Zapisuje"+i);
            try {
                ImageIO.write(subImages[i].getBufferedImage(), "jpg", outputfile);
            } catch (IOException ex) {
                Logger.getLogger(kontr.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Hejo");
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
				if (image.getWidth() > image.getHeight())
					imgHolder.setImage(image, Comm.STRETCH_H_PROP);
				else
					imgHolder.setImage(image, Comm.STRETCH_V_PROP);
        imgHolder.setSelectable();
        gui.addComponent(imgHolder);
    }

}
