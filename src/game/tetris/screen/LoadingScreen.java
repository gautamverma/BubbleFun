package game.tetris.screen;

import game.tetris.logic.Settings;
import game.tetris.util.Assets;
import game.tetris.util.FileName;

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
		
		Assets.block = g.newPixmap(FileName.BLOCK, PixmapFormat.RGB565);
		Assets.writingblock = g.newPixmap(FileName.WRITING_BLOCK, PixmapFormat.RGB565);
		Assets.background = g.newPixmap(FileName.BACKGROUND, PixmapFormat.RGB565);
		Assets.blue_background = g.newPixmap(FileName.BLUE_BACKGROUND, PixmapFormat.RGB565);
		Assets.translucent_background = g.newPixmap(FileName.TRANSLUCENT_BACKGROUND, PixmapFormat.ARGB8888);
		
		Assets.soundbutton = g.newPixmap(FileName.SOUNDBUTTON, PixmapFormat.RGB565);
		
		Assets.text_start_game = g.newPixmap(FileName.TEXT_START_GAME, PixmapFormat.RGB565);
		Assets.text_new_game = g.newPixmap(FileName.TEXT_NEW_GAME, PixmapFormat.RGB565);
		Assets.text_tutorial = g.newPixmap(FileName.TEXT_TUTORIAL, PixmapFormat.RGB565);
		Assets.text_play_online = g.newPixmap(FileName.TEXT_PLAY_ONLINE, PixmapFormat.RGB565);
		Assets.text_leader_board = g.newPixmap(FileName.TEXT_LEADER_BOARD, PixmapFormat.RGB565);
		Assets.text_dash_board = g.newPixmap(FileName.TEXT_DASH_BOARD, PixmapFormat.RGB565);
		Assets.text_coin_store = g.newPixmap(FileName.TEXT_COIN_STORE, PixmapFormat.RGB565);
		Assets.text_high_score = g.newPixmap(FileName.TEXT_HIGH_SCORE, PixmapFormat.RGB565);
		Assets.text_your_score = g.newPixmap(FileName.TEXT_YOUR_SCORE, PixmapFormat.RGB565);
		Assets.text_back_to_menu = g.newPixmap(FileName.TEXT_BACK_TO_MENU, PixmapFormat.RGB565);
		Assets.text_quit = g.newPixmap(FileName.TEXT_QUIT, PixmapFormat.RGB565);
		Assets.text_resume = g.newPixmap(FileName.TEXT_RESUME, PixmapFormat.RGB565);
		
		Assets.text_score = g.newPixmap(FileName.TEXT_SCORE, PixmapFormat.RGB565);
		Assets.text_level = g.newPixmap(FileName.TEXT_LEVEL, PixmapFormat.RGB565);
		Assets.text_lines = g.newPixmap(FileName.TEXT_LINES, PixmapFormat.RGB565);
		Assets.text_next = g.newPixmap(FileName.TEXT_NEXT, PixmapFormat.RGB565);
		
		Assets.text_tap_to_start = g.newPixmap(FileName.TEXT_TAP_TO_START, PixmapFormat.RGB565);;
		
		Assets.tutorial_msg1 = g.newPixmap(FileName.TUTORIAL_MSG1, PixmapFormat.RGB565);
		Assets.tutorial_msg2 = g.newPixmap(FileName.TUTORIAL_MSG2, PixmapFormat.RGB565);
		Assets.tutorial_msg3 = g.newPixmap(FileName.TUTORIAL_MSG3, PixmapFormat.RGB565);
		Assets.tutorial_msg4 = g.newPixmap(FileName.TUTORIAL_MSG4, PixmapFormat.RGB565);
		Assets.tutorial_msg5 = g.newPixmap(FileName.TUTORIAL_MSG5, PixmapFormat.RGB565);
		Assets.tutorial_msg6 = g.newPixmap(FileName.TUTORIAL_MSG6, PixmapFormat.RGB565);
		Assets.tutorial_msg7 = g.newPixmap(FileName.TUTORIAL_MSG7, PixmapFormat.RGB565);
		Assets.tutorial_msg_successful = g.newPixmap(FileName.TUTORIAL_MSG_SUCCESSFUL, PixmapFormat.RGB565);
		Assets.tutorial_msg_fail = g.newPixmap(FileName.TUTORIAL_MSG_FAIL, PixmapFormat.RGB565);
		
		Assets.final_message = g.newPixmap(FileName.FINAL_MESSAGE, PixmapFormat.RGB565);
		
		Assets.digits = g.newPixmap(FileName.DIGITS_SP, PixmapFormat.RGB565);
		Assets.small_digits = g.newPixmap(FileName.SMALL_DIGITS_SP, PixmapFormat.RGB565);
		
		Audio audio = game.getAudio();
		
		Assets.click = audio.newSound(FileName.MUSIC_CLICK);
		Assets.blockSettled = audio.newSound(FileName.MUSIC_BLOCK_SETTLED);
		Assets.rotate = audio.newSound(FileName.MUSIC_ROTATE_BTN);
		Assets.lineCompleted = audio.newSound(FileName.MUSIC_LINE_COMPLETED);
		
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
