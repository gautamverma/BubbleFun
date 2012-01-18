package gautambverma.com.game.impl;

import gautambverma.com.game.graphics.Pixmap;
import gautambverma.com.game.graphics.Graphics.PixmapFormat;
import android.graphics.Bitmap;

 

/* 
 * This class wraps over our bitmap which act as framebuffer.
 * */

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
