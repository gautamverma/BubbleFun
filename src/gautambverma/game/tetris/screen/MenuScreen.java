package gautambverma.game.tetris.screen;

import java.util.List;

import gautambverma.com.game.Game;
import gautambverma.com.game.Screen;
import gautambverma.com.game.graphics.Graphics;
import gautambverma.com.game.input.Input.TouchEvent;
import gautambverma.game.tetris.logic.Settings;
import gautambverma.game.tetris.util.AppConst;
import gautambverma.game.tetris.util.Assets;
import gautambverma.game.tetris.util.FileName;
import gautambverma.game.tetris.util.GameUtil;
import gautambverma.game.tetris.util.ScreenConst;


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
				if(GameUtil.inBounds(event.get(i), ScreenConst.NEW_GAME_X, ScreenConst.NEW_GAME_Y,
						FileName.TEXT_NEW_GAME_WH, FileName.TEXT_NEW_GAME_HT)) {
						if(Settings.soundEnabled)
							Assets.click.play(1);
						if(game.login())
							game.startPracticeGame();
						else
							game.showStandAlonePracticeGameToast();
						game.setScreen(new GameScreen(game));
					}
				else if(GameUtil.inBounds(event.get(i), ScreenConst.START_GAME_X, ScreenConst.START_GAME_Y,
						FileName.TEXT_START_GAME_WH, FileName.TEXT_START_GAME_HT)){
						if(Settings.soundEnabled)
							Assets.click.play(1);
						game.startSavedGame(true);
						if(game.login())
							game.startPracticeGame();
						else
							game.showStandAlonePracticeGameToast();
						game.setScreen(new GameScreen(game));
				
					}	
				else if(GameUtil.inBounds(event.get(i), ScreenConst.PLAY_ONLINE_X, ScreenConst.PLAY_ONLINE_Y,
						FileName.TEXT_PLAY_ONLINE_WH, FileName.TEXT_PLAY_ONLINE_HT)) {
						if(Settings.soundEnabled)
							Assets.click.play(1);
						if(game.login()) {
							if(game.openTournament()) {					
								game.setScreen(new GameScreen(game));
								}
						}
					}
				else if(GameUtil.inBounds(event.get(i), ScreenConst.LEADER_BOARD_X, ScreenConst.LEADER_BOARD_Y,
						FileName.TEXT_LEADER_BOARD_WH, FileName.TEXT_LEADER_BOARD_HT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					if(game.login())
						game.openLeaderBoard();
					}
				else if(GameUtil.inBounds(event.get(i), ScreenConst.DASH_BOARD_X, ScreenConst.DASH_BOARD_Y,
						FileName.TEXT_DASH_BOARD_WH, FileName.TEXT_DASH_BOARD_HT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					if(game.login())
						game.openDashBoard();
				}
				else if(GameUtil.inBounds(event.get(i), ScreenConst.COIN_STORE_X, ScreenConst.COIN_STORE_Y,
						FileName.TEXT_COIN_STORE_WH, FileName.TEXT_COIN_STORE_HT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					if(game.login())
						game.openCoinStore();
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
		
		g.drawPixmap(Assets.blue_background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		
		g.drawPixmap(Assets.text_new_game, ScreenConst.NEW_GAME_X, ScreenConst.NEW_GAME_Y);
		g.drawPixmap(Assets.text_start_game, ScreenConst.START_GAME_X, ScreenConst.START_GAME_Y);
		g.drawPixmap(Assets.text_play_online, ScreenConst.PLAY_ONLINE_X, ScreenConst.PLAY_ONLINE_Y);
		g.drawPixmap(Assets.text_leader_board, ScreenConst.LEADER_BOARD_X, ScreenConst.LEADER_BOARD_Y);
		g.drawPixmap(Assets.text_dash_board, ScreenConst.DASH_BOARD_X, ScreenConst.DASH_BOARD_Y);
		g.drawPixmap(Assets.text_coin_store, ScreenConst.COIN_STORE_X, ScreenConst.COIN_STORE_Y);
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
