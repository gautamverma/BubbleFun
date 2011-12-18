package game.tetris;

import game.tetris.screen.LoadingScreen;

import com.game.Screen;
import com.game.impl.AndroidGame;

public class Tetris extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}  
}