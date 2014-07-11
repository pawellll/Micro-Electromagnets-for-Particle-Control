package GD;

import GUI.GUIAction;
import Platform.CrossBitmap;
import Supp.Comm;
import Supp.Dim;
import Supp.DrawingInterface;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class kontr extends Controller {

    CrossBitmap image;

    kontr(File file) throws IOException {
        image = new CrossBitmap(ImageIO.read(file));
    }

    @Override
    public void onPaint(DrawingInterface g, int width, int height) {
        g.gDrawRectangle(50, 50, 100, 100);
        g.gStretchBitmap(0, 0, width, Dim.Y(26.5), image, Comm.STRETCH_H_PROP);
    }

    @Override
    public void onGUIAction(GUIAction e) {
    }

    @Override
    public void onSetCurrent() {
        gui.clearComponents();
    }

}
