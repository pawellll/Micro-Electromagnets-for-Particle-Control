package Platform;

import GD.CutCtrl;
import GD.MenuCtrl;
import GUI.GUIAction;
import GUI.GUIMain;
import GUI.GUIProgress;
import Supp.Comm;
import Supp.Str;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Klasa obsluguje ladowanie zasobow i plikow.
 */
public class CrossRes
{
  public static CrossBitmap GUI[] = new CrossBitmap[Comm.R_COUNT_GUI];
	
	public static GUIProgress loadProgress = null;
	
	public static void init()
	{	
		GUI[Comm.R_LOGO]      = loadImg(Comm.R_F_LOGO);
		GUI[Comm.R_LOGOTYPE]  = loadImg(Comm.R_F_LOGOTYPE);
		GUI[Comm.R_ARR_L_ON]  = loadImg(Comm.R_F_ARR_L_ON);
		GUI[Comm.R_ARR_L_OFF] = loadImg(Comm.R_F_ARR_L_OFF);
		GUI[Comm.R_ARR_R_ON]  = loadImg(Comm.R_F_ARR_R_ON);
		GUI[Comm.R_ARR_R_OFF] = loadImg(Comm.R_F_ARR_R_OFF);
		GUI[Comm.R_CHECK_OFF] = loadImg(Comm.R_F_CHECK_OFF);
		GUI[Comm.R_CHECK_ON]  = loadImg(Comm.R_F_CHECK_ON);
		GUI[Comm.R_IMG_FRAME] = loadImg(Comm.R_F_IMG_FRAME);
		GUI[Comm.R_INFO_INFO] = loadImg(Comm.R_F_INFO_INFO);
		GUI[Comm.R_INFO_WARN] = loadImg(Comm.R_F_INFO_WARN);
		GUI[Comm.R_INFO_OK]   = loadImg(Comm.R_F_INFO_OK);
		GUI[Comm.R_LANG_EN]   = loadImg(Comm.R_F_LANG_EN);
		GUI[Comm.R_LANG_PL]   = loadImg(Comm.R_F_LANG_PL);
		
		GUI[Comm.R_ICON_1_ON]  = loadImg(Comm.R_F_ICON_1_ON);
		GUI[Comm.R_ICON_2_ON]  = loadImg(Comm.R_F_ICON_2_ON);
		GUI[Comm.R_ICON_3_ON]  = loadImg(Comm.R_F_ICON_3_ON);
		GUI[Comm.R_ICON_4_ON]  = loadImg(Comm.R_F_ICON_4_ON);
		GUI[Comm.R_ICON_5_ON]  = loadImg(Comm.R_F_ICON_5_ON);
		GUI[Comm.R_ICON_1_OFF] = loadImg(Comm.R_F_ICON_1_OFF);
		GUI[Comm.R_ICON_2_OFF] = loadImg(Comm.R_F_ICON_2_OFF);
		GUI[Comm.R_ICON_3_OFF] = loadImg(Comm.R_F_ICON_3_OFF);
		GUI[Comm.R_ICON_4_OFF] = loadImg(Comm.R_F_ICON_4_OFF);
		GUI[Comm.R_ICON_5_OFF] = loadImg(Comm.R_F_ICON_5_OFF);
		GUI[Comm.R_ICON_6_ON]  = loadImg(Comm.R_F_ICON_6_ON);
		GUI[Comm.R_ICON_6_OFF] = loadImg(Comm.R_F_ICON_6_OFF);
	}
	
//	public static int getSeconds()
//	{
//		return (int)System.currentTimeMillis()/1000;
//	}
	
	public static String getFileExt(String filePath)
	{
		try
		{
			int delim = filePath.lastIndexOf('.');
			if (delim < 0)
				return "";
			else
				return filePath.substring(delim + 1);
		}
		catch (Exception Ex)
		{
			return "";
		}
	}
		
	public static String getFileName(String filePath)
	{
		try
		{
			int delim = filePath.lastIndexOf('\\');
			if (delim<0)
				delim = filePath.lastIndexOf('/');
			return filePath.substring(delim + 1);
		}
		catch (Exception Ex)
		{
			return "";
		}		
	}
	
	public static String loadText(String fileName)
	{
		try
		{
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			return new String(encoded, Charset.defaultCharset());			
		} 
		catch (Exception ex)
		{
			Main.main.GUI.setInfoLongest(Str.get(Str.RES_LOAD_STR_ERROR)+" ("+getFileName(fileName)+").", GUIMain.INFO_WARN);
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
			return "";
		}
	}
	
	public static void saveText(String fileName, String text)
	{
		PrintWriter out = null;
		try
		{
			try		
			{
				out = new PrintWriter(fileName);
				out.print(text);
			} 
			catch (Exception ex)
			{
				Main.main.GUI.setInfoLongest(Str.get(Str.RES_SAVE_STR_ERROR)+" ("+getFileName(fileName)+").", GUIMain.INFO_WARN);
				Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		finally
		{
			if (out != null)
				out.close();
		}
	}
	
	public static CrossBitmap loadImg(String fileName)
	{
		try
		{
			return new CrossBitmap(ImageIO.read(new File(fileName)));
		}
		catch (IOException ex)
		{
			Main.main.GUI.setInfoLongest(Str.get(Str.RES_LOAD_IMG_ERROR), GUIMain.INFO_WARN);
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static CrossBitmap loadImg(File file)
	{
		try
		{
			CrossBitmap crb = new CrossBitmap(ImageIO.read(file));
			return crb;
		} 
		catch (Exception ex)
		{
			Main.main.GUI.setInfoLongest(Str.get(Str.RES_LOAD_IMG_ERROR)+" ("+getFileName(file.getName())+")", GUIMain.INFO_WARN);
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
		
	public static void saveImg(String fileName, CrossBitmap bitmap)
	{
		try
		{
			ImageIO.write(bitmap.getBitmap(),"png",new File(fileName));
		} 
		catch (IOException ex)
		{
			Main.main.GUI.setInfoLongest(Str.get(Str.RES_SAVE_IMG_ERROR), GUIMain.INFO_WARN);
			Logger.getLogger(CrossRes.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static File[] openImagePicker()
	{
		final JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("ImageFiles", "jpg", "jpeg", "png", "gif", "bmp");
		fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(true);
		fc.setDialogTitle(Str.get(Str.RES_CHOOSE_FILE));
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			return fc.getSelectedFiles();
		
		return null;
	}
	
	private static String openDirPicker()
	{
		final JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);
		fc.setDialogTitle(Str.get(Str.RES_CHOOSE_DIR));
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			return fc.getSelectedFile().getAbsolutePath();
		
		return "";
	}
	
	private static List<CrossBitmap> loadImagesFromFiles(File[] files)
	{
		if (files == null)
			return null;
		
		List<CrossBitmap> imgs = new ArrayList<>();
		
		if (loadProgress != null)
		{
			loadProgress.visible = true;			
			loadProgress.max = files.length;
			loadProgress.set(0);
		}
		
		for (File file : files)
		{
			CrossBitmap img = loadImg(file);
			if (img != null)
				imgs.add(img);
			
			if (loadProgress != null)
			{
				loadProgress.set(loadProgress.value+1);
				Main.main.canvas.paint();
			}			
		}
		
		return imgs;
	}
	
	public static List<CrossBitmap> chooseAndLoadImages()
	{
		File[] files = openImagePicker();
		
		return loadImagesFromFiles(files);
	}
	
	public static List<CrossBitmap> chooseAndLoadDir()
	{
		String dir = openDirPicker();
		
		if (dir.equals(""))
			return null;
		
		File imgs = new File(dir);
		File[] listOfFiles = imgs.listFiles();

		List<File> toLoad = new ArrayList<>();
		String ext;
		
		if (listOfFiles != null)
			for (File file : listOfFiles) 
				if (file.isFile()) 
				{
					ext = getFileExt(file.getName());
					if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif") || ext.equals("bmp"))
						toLoad.add(file);			
				}
		
		if (toLoad.isEmpty())
		{
			Main.main.GUI.setInfoLongest(Str.get(Str.RES_NO_IMGS_ERROR), GUIMain.INFO_WARN);
			return null;
		}
		else
		{
			File[] fileToLoad = new File[toLoad.size()];
			for (int i = 0; i<fileToLoad.length; i++)
				fileToLoad[i] = toLoad.get(i);

			return loadImagesFromFiles(fileToLoad);
		}
	}
	
	private static DropTarget drop = null;
	
	public static void setDropFileEnabled(boolean status)
	{
		if (status)
		{
			if (drop == null)
			{
				drop = new DropTarget(Main.main.canvas, new DropTargetListener()
				{
					@Override
					public void drop(DropTargetDropEvent event)
					{
						event.acceptDrop(DnDConstants.ACTION_COPY);

						Transferable transferable = event.getTransferable();

						DataFlavor[] flavors = transferable.getTransferDataFlavors();

						for (DataFlavor flavor : flavors) {
							try {
								if (flavor.isFlavorJavaFileListType()) {
									List<File> files = (List) transferable.getTransferData(flavor);

									if (files.size()>0)
									{
										List<File> toLoad = new ArrayList<>();
										String ext;

										for (File file : files) 
											if (file.isFile()) 
											{
												ext = getFileExt(file.getName());
												if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif") || ext.equals("bmp"))
													toLoad.add(file);			
											}
										
										if (toLoad.isEmpty())
											Main.main.GUI.setInfoLongest(Str.get(Str.RES_NO_DROP_ERROR), GUIMain.INFO_WARN);
										else
										{
											File[] fileToLoad = new File[toLoad.size()];
											for (int i = 0; i<fileToLoad.length; i++)
												fileToLoad[i] = toLoad.get(i);

											List<CrossBitmap> imgs = loadImagesFromFiles(fileToLoad);
											Main.main.mainCrtl.currentCtrl.onGUIAction(new GUIAction("", GUIAction.DROP_FILE, imgs));
										}
										
									}
								}
							} catch (Exception e) { }
						}

						event.dropComplete(true);			
					}

					@Override	public void dragEnter(DropTargetDragEvent event){}

					@Override	public void dragOver(DropTargetDragEvent dtde){}

					@Override	public void dropActionChanged(DropTargetDragEvent dtde){}

					@Override	public void dragExit(DropTargetEvent dte){}
				});
			}
			else
				drop.setActive(true);
		}
		else
		{
			drop.setActive(false);
		}
	}
	
	public static String getHomeDir()
	{
		String s = System.getProperty("user.dir");
		if (s.charAt(s.length()-1)!='\\' )
			s += '\\';
				
		return s+Comm.APP_DIR_NAME+"\\";
	}	
	
	public static void checkHomeDir()
	{			
		File folder = new File(getHomeDir());
		
		if (!folder.exists())
			folder.mkdir();
	}
	
	public static List<String> getFileList(boolean fullName)
	{
		String s = System.getProperty("user.dir");
		if (s.charAt(s.length()-1)!='\\' )
			s += '\\';
						
		File folder = new File(s+Comm.APP_DIR_NAME);
		File[] listOfFiles = folder.listFiles();

		List<String> names = new ArrayList<>();
		
		if (listOfFiles != null)
			for (File file : listOfFiles) 
				if (file.isFile()) 
				{
					if (fullName)
						names.add(file.getAbsolutePath());
					else
						names.add(file.getName());
				}
		
		return names;    
	}
	
	

	
	public static List<String> getTemplates(boolean fullName)
	{
		List<String> files = getFileList(fullName);
		List<String> templates = new ArrayList<>();
		
		for (String file : files)
			if (getFileExt(file).equals("gdtpl"))
			{
				if (fullName)
					templates.add(file);
				else
					templates.add(file.substring(0,file.lastIndexOf('.')));
			}
		
		return templates;
	}
	
	
	
}
