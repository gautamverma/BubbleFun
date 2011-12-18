package game.tetris.screen;

import game.tetris.logic.Settings;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;

import com.game.Game;
import com.game.Screen;
import com.game.audio.Audio;
import com.game.graphics.Graphics;
import com.game.graphics.Graphics.PixmapFormat;

/*
 * This class loads all the assets for the Game
 * @author Gautam
 * */
public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
	
		Graphics g = game.getGraphics();
		
		Assets.alphabets = g.newPixmap(AppConst.ALPHABETS, PixmapFormat.RGB565);
		Assets.block = g.newPixmap(AppConst.BLOCK, PixmapFormat.RGB565);
		Assets.writingblock = g.newPixmap(AppConst.WRITING_BLOCK, PixmapFormat.RGB565);
		Assets.digits = g.newPixmap(AppConst.DIGITS, PixmapFormat.RGB565);
		Assets.smallBlock = g.newPixmap(AppConst.SMALL_BLOCK, PixmapFormat.RGB565);
		Assets.background = g.newPixmap(AppConst.BACKGROUND, PixmapFormat.RGB565);
		
		Assets.soundbutton = g.newPixmap(AppConst.SOUNDBUTTON, PixmapFormat.RGB565);
		
		Assets.btn_rotate = g.newPixmap(AppConst.BTN_ROTATE, PixmapFormat.RGB565);
		Assets.btn_left = g.newPixmap(AppConst.BTN_LEFT, PixmapFormat.RGB565);
		Assets.btn_right = g.newPixmap(AppConst.BTN_RIGHT, PixmapFormat.RGB565);
		
		Assets.msg_toptostart = g.newPixmap(AppConst.TAP_TO_START, PixmapFormat.RGB565);;
		
		Assets.msg_quit = g.newPixmap(AppConst.QUIT, PixmapFormat.RGB565); 
		Assets.msg_resume = g.newPixmap(AppConst.RESUME, PixmapFormat.RGB565);
		
		Assets.msg_playagain = g.newPixmap(AppConst.PLAY_AGAIN, PixmapFormat.RGB565);
		Assets.msg_tomenu = g.newPixmap(AppConst.TO_MENU, PixmapFormat.RGB565);
		Assets.msg_taphere = g.newPixmap(AppConst.TAP_HERE, PixmapFormat.RGB565);
		
		Assets.msg_next = g.newPixmap(AppConst.NEXT, PixmapFormat.RGB565);
		Assets.msg_score = g.newPixmap(AppConst.SCORE, PixmapFormat.RGB565);
		
		Assets.numbers = g.newPixmap(AppConst.NUMBERS, PixmapFormat.RGB565);
		Assets.numbers2x = g.newPixmap(AppConst.NUMBERS_2X, PixmapFormat.RGB565);
		
		Assets.msg_help1 = g.newPixmap(AppConst.MSG_HELP1, PixmapFormat.RGB565);
		Assets.msg_help2_part1 = g.newPixmap(AppConst.MSG_HELP2_PART1, PixmapFormat.RGB565);
		Assets.msg_help2_part2 = g.newPixmap(AppConst.MSG_HELP2_PART2, PixmapFormat.RGB565);
		Assets.msg_help3_part1 = g.newPixmap(AppConst.MSG_HELP3_PART1, PixmapFormat.RGB565);
		Assets.msg_help3_part2 = g.newPixmap(AppConst.MSG_HELP3_PART2, PixmapFormat.RGB565);
		
		Assets.help1 = g.newPixmap(AppConst.HELP1, PixmapFormat.RGB565);
		Assets.help2 = g.newPixmap(AppConst.HELP2, PixmapFormat.RGB565);
		Assets.help3_scene1 = g.newPixmap(AppConst.HELP3_SCENE1, PixmapFormat.RGB565);
		Assets.help3_scene2 = g.newPixmap(AppConst.HELP3_SCENE2, PixmapFormat.RGB565);
		
		Audio audio = game.getAudio();
		
		Assets.click = audio.newSound(AppConst.MUSIC_CLICK);
		Assets.blockSettled = audio.newSound(AppConst.MUSIC_BLOCK_SETTLED);
		Assets.rotate = audio.newSound(AppConst.MUSIC_ROTATE_BTN);
		Assets.lineCompleted = audio.newSound(AppConst.MUSIC_LINE_COMPLETED);
		
		// Loading Settings or default if its first time
		Settings.load(game.getFileIO());
		
		// All Assets is loaded so move to Menu Screen
		game.setScreen(new MenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub

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
