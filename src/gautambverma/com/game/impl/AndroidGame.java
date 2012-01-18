package gautambverma.com.game.impl;

import gautambverma.com.game.Game;
import gautambverma.com.game.Screen;
import gautambverma.com.game.audio.Audio;
import gautambverma.com.game.fileio.FileIO;
import gautambverma.com.game.graphics.Graphics;
import gautambverma.com.game.input.Input;

import com.skiller.api.operations.SKApplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

/*
 * This is the main class which will deal with all window mangement
 * It is inherited by each type of game which we build 
 * 
 * ** Added Skiller SDK Support
 * */
public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    SKApplication skApplication;
    
    // Handler to run methods on UI Thread
    Handler handler;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        /* Setting the framework accordingly */
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, this, renderView, scaleX, scaleY);
        
        // Skiller SDk API Keys
        String app_key = "4436dc7cb4084bf4ae8dcee5b34a81fd"; 
		String app_secret = "461e1ba84570469199baadedab11aa33"; 
		String app_id = "198804347030"; 			
		String app_ver = "1";
  		int app_dist = 0;
  		
  		skApplication = new SKApplication(app_id, app_key, app_secret, app_ver, app_dist);
  		
  		handler = new Handler();
        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public SKApplication getSKApplication() {
    	return skApplication;
    }
    
    public Handler getHandler() {
    	return handler;
    }
    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume(); // it is called here as it the main thread will call the 
        screen.update(0);// the present method of this new screen immediately as it overs. 
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
        return screen;
    }
}