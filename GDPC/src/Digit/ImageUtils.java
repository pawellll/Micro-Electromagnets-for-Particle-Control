package Digit;

import Platform.CrossBitmap;
import Supp.Colors;

/**
 * Klasa realizujaca operacje na obrazach. 
 */
public class ImageUtils 
{
	public static Integer[][][] bitmapToArray(CrossBitmap img)
	{
		//     X Y R/G/B/RGB (0/1/2/3)
		Integer[][][] arr = new Integer[img.getWidth()][img.getHeight()][4];
		
		for (int x=0; x < arr.length; x++)
			for (int y=0; y < arr[0].length; y++)
			{
				arr[x][y][0] = img.getIntComponent0(x, y);
				arr[x][y][1] = img.getIntComponent1(x, y);
				arr[x][y][2] = img.getIntComponent2(x, y);
				arr[x][y][3] = img.getRGB(x, y);
			}
		
		return arr;
	}	
	
	public static CrossBitmap arrayToBitmap(Integer[][][] arr)
	{
		CrossBitmap img = new CrossBitmap(CrossBitmap.newBlank(arr.length, arr[0].length));
		
		for (int x=0; x < arr.length; x++)
			for (int y=0; y < arr[0].length; y++)
				img.setRGB(x, y, Colors.RGB(arr[x][y][0],arr[x][y][1],arr[x][y][2]));
		
		return img;
	}	
	
	private static Integer[][][] newArr(Integer[][][] oldArr)
	{
		return new Integer[oldArr.length][oldArr[0].length][4];
	}
	
	private static Integer[][][] copyArr(Integer[][][] oldArr)
	{
		Integer[][][] copy = new Integer[oldArr.length][oldArr[0].length][4];
	
		for (int x=0; x < oldArr.length; x++)
			for (int y=0; y < oldArr[0].length; y++)
				copy[x][y] = oldArr[x][y];
		
		return copy;
	}
	
	private static void col(Integer[][][] img, int x, int y, int r, int g, int b, boolean limit)
	{
		if (limit)
		{
			if (r>255) r=255;
			if (g>255) g=255;
			if (b>255) b=255;
			
			if (r<0) r=0;
			if (g<0) g=0;
			if (b<0) b=0;			
		}
		
		img[x][y][0] = r;
		img[x][y][1] = g;
		img[x][y][2] = b;
	}		
	
	public static Integer[][][] doLUT(Integer[][][] img, int[] LUT_R, int[] LUT_G, int[] LUT_B)
	{
		Integer[][][] out = newArr(img);
		
		for (int x=0; x<img.length; x++)
			for (int y=0; y<img[0].length; y++)
			{
				out[x][y][0] = LUT_R[img[x][y][0]];
				out[x][y][1] = LUT_G[img[x][y][1]];
				out[x][y][2] = LUT_B[img[x][y][2]];
			}
				
		return out;
	}

	public static Integer[][][] doLUT(Integer[][][] img, int[] LUT)
	{
		return doLUT(img, LUT, LUT, LUT);
	}
	
	public static Integer[][][] doBrightness(Integer[][][] img, int level)
	{
		int lut[] = new int[256];
		for (int i=0; i<256; i++)
			lut[i] = i + level;

		return doLUT(img, lut);
	}
	
	public static Integer[][][] doBrightnessIncLinear(Integer[][][] img, int level)
	{
		int lut[] = new int[256];
		for (int i=0; i<256; i++)
			lut[i] = (int)(i+(double)((i*level)/255));

		return doLUT(img, lut);
	}

	public static Integer[][][] doContrast(Integer[][][] img, double level)
	{
		int lut[] = new int[256];
		for (int i=0; i<256; i++)
			lut[i] = (int)((float)level*(i-127)+127);

		return doLUT(img, lut);
	}

	public static Integer[][][] doGamma(Integer[][][] img, double level)
	{
		int lut[] = new int[256];
		for (int i=0; i<256; i++)
			lut[i] = (int)(255*Math.pow((double)i/255, level));

		return doLUT(img, lut);
	}

	public static Integer[][][] doThresholding(Integer[][][] img, int threshold)
	{
		int lut[] = new int[256];
		for (int i=threshold; i<256; i++)
			lut[i]=255;

		return doLUT(img, lut);
	}
	
	
	public static double[][] makeMask(int size, double value)
	{
		double[][] out = new double[size][size];
		for (int x=0; x<size; x++)
			for (int y=0; y<size; y++)
				out[x][y]=value;

		return out;
	}
	
	public static double[][] makeMaskSquare(double[][] mask, boolean odd, double valueToFillIn)
	{
		int m = mask.length;
		int mh = 0;
		for (int i=0; i<m; i++)
			if (mask[i].length>mh)
				mh=mask[i].length;

		m=Math.max(m, mh);
		if ((odd) && (m%2==0))
			m++;

		double[][] out;
		if (valueToFillIn==0)
			out = new double[m][m];
		else
			out = makeMask(m, valueToFillIn);

		int offx = m / 2 - mask.length / 2;
		int offy = m / 2 - mh / 2;
		for (int x=0; x<mask.length; x++)
			System.arraycopy(mask[x], 0, out[x+offx], offy, mask[x].length);

		return out;		
	}
	
	public static double[][] makeMaskSquare(double[][] mask, boolean odd)
	{
		return makeMaskSquare(mask, odd, 0);
	}

	public static double[][] makeMaskSquare(double[][] mask)
	{
		return makeMaskSquare(mask, true, 0);
	}	
	
	public static Integer[][][]  doApplyMask(Integer[][][]  img, double[][]mask, boolean normalize)
	{
		Integer[][][] out = newArr(img);

		double r,g,b;

		if (mask.length!=mask[0].length)
			mask = makeMaskSquare(mask, true);

		int size = mask.length;

		int sum = 0;
		if (normalize)
		{
			for(int i=0; i<size; i++) 
				for(int j=0; j<size; j++) 
					sum+=mask[i][j];

			if (sum==0)
				sum=1;
		}

		Integer[][][] copy;
		int offset = 0;

		copy = img;
		offset = size / 2;
			
		for (int i = offset; i < img.length-offset; i++) 
			for (int j = offset; j < img[0].length-offset; j++)
			{
				r=0; g=0; b=0;
				for(int x=0; x<size; x++)
					for(int y=0; y<size; y++)
					{
						r+=(double)(mask[x][y]*copy[i+x][j+y][0]);
						g+=(double)(mask[x][y]*copy[i+x][j+y][1]);
						b+=(double)(mask[x][y]*copy[i+x][j+y][2]);
					}

				if (normalize)
				{
					r /=sum;
					g /=sum;
					b /=sum;
				}

				col(out, i, j, (int)r, (int)g, (int)b, true);
			}

		return out;
	}	
	
	public static Integer[][][] doApplyMask(Integer[][][] img, double[][]mask)
	{
		return doApplyMask(img, mask, true);
	}	

	public static Integer[][][] doBlur(Integer[][][] img, int blurMaskSize)
	{
		return doApplyMask(img, makeMask(blurMaskSize, 1), false);
	}

	public static Integer[][][] doBlur(Integer[][][] img)
	{
		return doApplyMask(img, makeMask(3, 1), false);
	}

	public static Integer[][][] doBlurStrong(Integer[][][] img, int level, int blurMaskSize)
	{
		double [][] mask = makeMask(blurMaskSize, level);
		mask[blurMaskSize/2][blurMaskSize/2] = 1;
		return doApplyMask(img, mask, false);
	}

}
