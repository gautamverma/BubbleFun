package game.tetris.screen;

import java.util.List;

import game.tetris.logic.Settings;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;
import game.tetris.util.GameUtil;

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
		init();
	}

	private void init() {
		ScreenPixmap = new boolean[AppConst.MENU_OPTION_HEIGHT][AppConst.MENU_OPTION_WIDTH];
		
		for(int i = 0; i < AppConst.MENU_OPTION_HEIGHT; i++) {
			for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++)
				ScreenPixmap[i][j] = false;
		}
		
		for(int i = 0; i < AppConst.MENU_OPTION_HEIGHT; i++) {
			if(i==7 || i==8) continue;
			else if(i==0) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j+1)%4==0 || j==0 || j==2 || j==8 || j==10 || j==12 || j==14) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i<3) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j+1)%4==0 || j==1 || j==4 || j==6 || j==9 || j==13  
							|| j==16 || j==18) continue;
					if(i==2 && j==2) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i==3) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j+1)%4==0 || j==4 || j==6 || j==16 || j==18) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i<6) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j+1)%4==0 || j==1 || j==4 || j==6 || j==9 
							|| j==16 || j==18) continue;
					if((i==4 && j==14) || (i==5 && j==13) || (i==4 && j==0)) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i==6) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j+1)%4==0 || j==0 || j==2 || j==4 || j==6 || j==9 || j==13 || j==16 || j==18) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i==9) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j)%4==1 || j<2 || j==3 || j==11 || j==12 || j>16) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i<12) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j)%4==1 || j<2 || j==3 || j==7 || j==8 || j==11 
							|| j==12 || j==15 || j>16) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i==12) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j)%4==1 || j<2 || j==11 || j==12 || j>16) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else if(i<15) {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j)%4==1 || j<2 || j==3 || j==7 || j==8 || j==11
							|| j==12 || j>14) continue;
					ScreenPixmap[i][j] = true;
				}
			}
			else {
				for(int j = 0; j < AppConst.MENU_OPTION_WIDTH; j++) {
					if((j)%4==1 || j<2 || j==3 || j>14) continue;
					ScreenPixmap[i][j] = true;
				}		
			}
		}
	}
	
	@Override
	public void update(float deltaTime) {
		
		List<TouchEvent> event = game.getInput().getTouchEvent();
		game.getInput().getKeyEvents();
		
		for(int i = 0; i<event.size(); i++) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i), AppConst.MENU_OPTION_SRC_X, AppConst.MENU_OPTION_SRC_Y,
													AppConst.WRITING_BLOCK_WIDTH*AppConst.MENU_OPTION_WIDTH, 
													AppConst.WRITING_BLOCK_HEIGHT*AppConst.MAX_LETTER_HEIGHT)){
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new GameScreen(game));
				}
			else if(GameUtil.inBounds(event.get(i), AppConst.MENU_OPTION_SRC_X,
						AppConst.MENU_OPTION_SRC_Y+(AppConst.WRITING_BLOCK_HEIGHT*
										(AppConst.MAX_LETTER_HEIGHT+AppConst.LETTER_OFFSET)),				
													AppConst.WRITING_BLOCK_WIDTH*AppConst.MENU_OPTION_WIDTH, 
													AppConst.WRITING_BLOCK_HEIGHT*AppConst.MAX_LETTER_HEIGHT)) {
				if(Settings.soundEnabled)
					Assets.click.play(1);
				game.setScreen(new Help1(game));
				}
			else if(GameUtil.inBounds(event.get(i), AppConst.SOUND_BUTTON_X, AppConst.SOUND_BUTTON_Y,
					AppConst.SOUND_BUTTON_WIDTH, AppConst.SOUND_BUTTON_HEIGHT)) {
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
		
		for(int i = 0; i<AppConst.MENU_OPTION_HEIGHT; i++) {
			for(int j = 0; j<AppConst.MENU_OPTION_WIDTH; j++) {
				if(ScreenPixmap[i][j]) {
					g.drawPixmap(Assets.writingblock, AppConst.MENU_OPTION_SRC_X + (j*AppConst.WRITING_BLOCK_WIDTH),
													AppConst.MENU_OPTION_SRC_Y + (i*AppConst.WRITING_BLOCK_HEIGHT));
				}
			}
		}
		if(Settings.soundEnabled) {
			g.drawPixmap(Assets.soundbutton, AppConst.SOUND_BUTTON_X, AppConst.SOUND_BUTTON_Y,
					AppConst.SOUND_PLAY_X, AppConst.SOUND_BOTH_Y,
					AppConst.SOUND_BUTTON_WIDTH, AppConst.SOUND_BUTTON_HEIGHT);
		}
		else {
			g.drawPixmap(Assets.soundbutton, AppConst.SOUND_BUTTON_X, AppConst.SOUND_BUTTON_Y,
					AppConst.SOUND_MUTE_X, AppConst.SOUND_BOTH_Y,
					AppConst.SOUND_BUTTON_WIDTH, AppConst.SOUND_BUTTON_HEIGHT);
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
