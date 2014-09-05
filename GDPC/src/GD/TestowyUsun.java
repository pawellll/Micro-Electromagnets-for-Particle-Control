package GD;

import Digit.CIELab;
import Digit.ColorsUtils;
import Digit.ImageUtils;
import GUI.GUILabel;
import Platform.CrossBitmap;
import Platform.CrossRes;
import Platform.Main;
import Supp.Colors;
import Supp.Dim;
import Supp.DrawingInterface;
import Supp.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestowyUsun extends Controller
{
	private CrossBitmap img;	
	
	private int green1, green2, red, orange;
	
	String lab = "";
	
	public TestowyUsun()
	{
		String s = "wykres_alone.jpg";
		img = CrossRes.loadImg("F:\\Studia\\9 biometria wakacje\\2 Java\\GDPC\\"+s);
	
		green1 = Colors.RGB(49, 185, 27);
		green2 = Colors.RGB(112, 216, 95);
		red = Colors.RGB(185, 27, 27);
		orange = Colors.RGB(248, 161, 17);
	}
	
	void out(Object x)
	{
		System.out.println(x);
	}
	
	void set(String s)
	{
		Main.main.setTitle(s);
	}
	
	List<ColorsUtils.Color> colors = new ArrayList<>();
	int bcg = 0;

	@Override
	public void onSetCurrent()
	{
		System.out.println(CIELab.delta(0x8e243b, 0xa24b5e));
		
		Integer[][][] image = ImageUtils.bitmapToArray(img);	
//		image = ImageUtils.doBrightness(image, 20);
		
		
		img = ImageUtils.arrayToBitmap(image);
		
		List<Integer> set = ColorsUtils.getColorSet(image, true);

		bcg = ColorsUtils.detectBackground(img, 8);
		
		List<Integer> cols = new ArrayList<>();

		for (Integer x : set)
		{
			if (CIELab.delta(bcg, x) > 10)
				cols.add(x);				
		}
		
		colors = ColorsUtils.detectColors(cols, 3);
//		colors = ColorsUtils.detectColorsPlus(cols);
		
		cols.clear();
		for (ColorsUtils.Color c : colors)
		{
			cols.add(c.mean);
		}
		colors = ColorsUtils.detectColorsBetter(cols, 10);
//		ColorsUtils.reduceMeanColors(colors);
		
		
	
//		for (int x = 0; x<img.getWidth(); x++)
//			for (int y = 0; y<img.getHeight(); y++)
////				if (colors.get(1).colors.indexOf(img.getRGB(x, y)) < 0)
//				if (!(CIELab.delta(img.getRGB(x, y), colors.get(1).mean)<10))
//					img.setRGB(x, y, Colors.WHITE);
//					
		
	
		
		
//		out(CIELab.delta(Colors.RGB(135, 101, 141), Colors.RGB(180, 163, 168)));


		
//		Main.main.Terminate();
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void onPaint(DrawingInterface g, int width, int height)
	{
		g.gDrawBitmap(Dim.X(10), Dim.Y(10), img);
		
		
		g.gSetColor(bcg);
		g.gFillRectangle(700, 100, 200, 50);
		g.gSetColor(Colors.BLACK);
		g.gDrawRectangle(700, 100, 200, 50);		
		
		for (int i=0; i< colors.size(); i++)
		{
			g.gSetColor(colors.get(i).mean);
			g.gFillRectangle(700, 200+(50*i), 200, 50);
			
			g.gSetColor(Colors.RGB(180, 180, 180));
			g.gDrawText(720, 230+(50*i), colors.get(i).colors.size()+"");
		}
		set(colors.size()+"");
	}
	
	
	
}
