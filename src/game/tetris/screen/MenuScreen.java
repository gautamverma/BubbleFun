package game.tetris.screen;

import java.util.List;

import game.tetris.logic.Settings;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;
import game.tetris.util.FileName;
import game.tetris.util.GameUtil;
import game.tetris.util.ScreenConst;

import com.game.Game;
import com.game.Screen;
import com.game.graphics.Graphics;
import com.game.input.Input.TouchEvent;

/*
 * This is the main menu screen and allow users to start to a new game, go to help and
 * to change game settings.
 * 
 * @author Gautam
 * */
public class MenuScreen extends Screen {

	boolean[][] ScreenPixmap;
	
	public MenuScreen(Game game) {
		super(game);
	}
	
	
	@Override
	public void update(float deltaTime) {
		
		List<TouchEvent> event = game.getInput().getTouchEvent();
		game.getInput().getKeyEvents();
		
		for(int i = 0; i<event.size(); i++) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i), ScreenConst.START_X, ScreenConst.START_Y,
							FileName.TEXT_START_WH, FileName.TEXT_START_HT)){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new GameScreen(game));
				}
			else if(GameUtil.inBounds(event.get(i), ScreenConst.TUTORIAL_X, ScreenConst.TUTORIAL_Y,
							FileName.TEXT_TUTORIAL_WH, FileName.TEXT_TUTORIAL_HT)) {
				if(Settings.soundEnabled)
					Assets.click.play(1);
				game.setScreen(new Tutorial(game));
				}
			else if(GameUtil.inBounds(event.get(i), ScreenConst.SOUNDBUTTON_X, ScreenConst.SOUNDBUTTON_Y,
					AppConst.SOUNDBUTTON_WH, AppConst.SOUNDBUTTON_HT)) {
				Settings.toggleSound();
				Settings.save(game.getFileIO());
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		
		g.drawPixmap(Assets.text_start, ScreenConst.START_X, ScreenConst.START_Y);
		g.drawPixmap(Assets.text_tutorial, ScreenConst.TUTORIAL_X, ScreenConst.TUTORIAL_Y);
		
		if(Settings.soundEnabled) {
			g.drawPixmap(Assets.soundbutton, ScreenConst.SOUNDBUTTON_X, ScreenConst.SOUNDBUTTON_Y,
					AppConst.ORIGIN_X, AppConst.ORIGIN_Y,
					AppConst.SOUNDBUTTON_WH, AppConst.SOUNDBUTTON_HT);
		}
		else {
			g.drawPixmap(Assets.soundbutton, ScreenConst.SOUNDBUTTON_X, ScreenConst.SOUNDBUTTON_Y,
					AppConst.ORIGIN_X, AppConst.ORIGIN_Y + AppConst.SOUNDBUTTON_HT + AppConst.ATLASING_OFFSET,
					AppConst.SOUNDBUTTON_WH, AppConst.SOUNDBUTTON_HT);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
