package GUI;

/**
 * Klasa przechowujaca informacje o zdarzeniu, ktore zaszlo na danym komponencie GUI.
*/
public class GUIAction
{
	public static final int BUTTON_CLICK = 0;
	public static final int KEY_TYPED = 1;
		
	public String id;
	public int action;
	public Object data;
	
	public GUIAction(String id, int action, Object data)
	{
		this.id = id;
		this.action = action;
		this.data = data;
	}
}
