package rina.module.widget;

// Util Turok.
import rina.util.TurokRect;

/**
 * @author Rina!
 *
 * Created by Rina in 28/07/2020.
 *
 **/
public abstract class OsirisPlusWidgetAbstract {
	public void setX(int x) {}
	public void setY(int y) {}	
	public void setWidth(int width) {}
	public void setHeight(int height) {}

	public TurokRect getRect() {
		return null;
	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}
	
	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public void keyboard(char char_, int key) {}
	public void click(int x, int y, int mouse) {}
	public void release(int x, int y, int mouse) {}
	public void render() {}
}
