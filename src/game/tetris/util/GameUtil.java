/**
 * 
 */
package game.tetris.util;

import com.game.input.Input.TouchEvent;

/**
 * This class contains the various utility functions required by the Game.
 * 
 * @author Gautam
 * @since 26/11/2011 
 */
public class GameUtil {

	public static boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if((event.x >= x && event.x <= x+width) && (event.y >=y && event.y <= y+height))
			return true;
		return false;
	}
	
	public static float nanoToSec(long nanoTime) {
		return (float)(nanoTime)/1000000000.0f;
	}
}
