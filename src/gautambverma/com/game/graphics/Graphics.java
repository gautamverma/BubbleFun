package gautambverma.com.game.graphics;

public interface Graphics {

	enum PixmapFormat {
		ARGB8888, ARGB4444, RGB565;
	}
	
	Pixmap newPixmap(String filename, PixmapFormat format);
	
	void drawPixel(int x,int y,int color);
	void drawLine(int x1,int y1,int x2,int y2,int color);
	void drawRectangle(int x,int y,int width,int height,int color);
	void drawPixmap(Pixmap pixmap,int x,int y);
	void drawPixmap(Pixmap pixmap,int x,int y,int srcX,int srcY,int srcWidth,int srcHeight);
	
	void clear(int color);
	
	/* It gets the Height and Width of the FrameBuffer*/
	int getWidth();
	int getHeight();
}
