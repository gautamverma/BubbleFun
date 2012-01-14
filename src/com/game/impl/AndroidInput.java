package com.game.impl;

import java.util.List;

import com.game.Game;
import com.game.input.Input;
import com.game.input.TouchHandler;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

/*
 * This class handles on the input class and acts a coordinator 
 * */

public class AndroidInput implements Input {    
    AccelerometerHandler accelHandler;
    KeyboardHandler keyHandler;
    TouchHandler touchHandler;

    public AndroidInput(Game game, Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccelerometerHandler(context);
        keyHandler = new KeyboardHandler(view);               
        if(Integer.parseInt(VERSION.SDK) < 5) 
            touchHandler = new SingleTouchHandler(game, view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(game, view, scaleX, scaleY);        
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }
    
    @Override
    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }

	@Override
	public List<TouchEvent> getTouchEvent() {
		return touchHandler.getTouchEvents();
	}
}
