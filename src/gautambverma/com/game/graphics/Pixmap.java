/**
 * 
 */
package gautambverma.com.game.graphics;

import gautambverma.com.game.graphics.Graphics.PixmapFormat;

/**
 * @author Gautam
 *
 */
public interface Pixmap {

	int getHeight();
	int getWidth();
	PixmapFormat getFormat();
	void dispose();
}
