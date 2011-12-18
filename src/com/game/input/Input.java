/**
 * 
 */
package com.game.input;

import java.util.List;

/**
 * @author Gautam
 * @since 17/10/2011  dd/mm/yyyy
 * 
 * This is general interface for input. It defines constant for all the 3 types of the input 
 * ie key,touch and accelerometer, that are present in a android device.
 *
 * Pointer ID is will same till the time same finger is on the Screen.
 */
public interface Input {
	public static class KeyEvent {
		public static int KEY_UP = 0;
		public static int KEY_DOWN  = 1;
		
		public int type;
		public int keyCode;
		public char keyChar;
	}
	
	public static class TouchEvent {
		public static int TOUCH_UP = 0;
		public static int TOUCH_DOWN = 1;
		public static int TOUCH_DRAGGED = 2;
		
		public int type;
		public int x,y;
		public int pointer;
	}
	
	boolean isKeyPressed(int keyCode);
	boolean isTouchDown(int pointer);
	
	int getTouchX(int pointer);
	int getTouchY(int pointer);
	
	float getAccelX();
	float getAccelY();
	float getAccelZ();
	
	List<KeyEvent> getKeyEvents();
	List<TouchEvent> getTouchEvent();
}
