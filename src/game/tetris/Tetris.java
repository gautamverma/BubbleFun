package game.tetris;

import game.tetris.screen.LoadingScreen;

import android.content.Context;

import com.game.Screen;
import com.game.impl.AndroidGame;

public class Tetris extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}  
	
	@Override
	public Context getContext() {
		return getApplicationContext();
	}
}