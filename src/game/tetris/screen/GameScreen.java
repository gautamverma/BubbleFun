package game.tetris.screen;

import java.util.List;

import game.tetris.logic.Arena;
import game.tetris.logic.Settings;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;
import game.tetris.util.GameUtil;

import android.util.Log;

import com.game.Game;
import com.game.Screen;
import com.game.graphics.Graphics;
import com.game.input.Input.TouchEvent;

/*
 * This is the game Screen and it implements all the four game transitions
 *  1) Tap to start
 *  2) Pause
 *  3) Resume
 *  4) Over
 *  
 *  ** Adding Acclerometer Support
 * @author Gautam
 * */
public class GameScreen extends Screen {

	enum GameState { START, RUNNING, PAUSED, STOPPED }
	
	Arena arena;
	GameState state;
	
	
	public GameScreen(Game game) {
		super(game);
		
		arena = new Arena();
		state = GameState.START;
	}

	@Override
	public void update(float deltaTime) {

		List<TouchEvent> event = game.getInput().getTouchEvent();
		float accelX = game.getInput().getAccelX();
		game.getInput().getKeyEvents();
		
		if(state==GameState.START) updateStart(event);
		else if(state==GameState.RUNNING) updateRunning(event, accelX, deltaTime);
		else if(state==GameState.PAUSED) updatePaused(event);
		else updateStopped(event);
	}

	@Override
	public void present(float deltaTime) {
		
		Graphics g = game.getGraphics();
		
		// No Need to do it in multiple present methods
		g.drawPixmap(Assets.background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		
		if(state==GameState.START) presentStart(g);
		else if(state==GameState.RUNNING) presentRunning(g);
		else if(state==GameState.PAUSED) presentPaused(g);
		else presentStopped(g);
		
	}

	public void updateStart(List<TouchEvent> event) {
		if(event.size()>0) {
			if(Settings.soundEnabled)
				Assets.click.play(1);
			state = GameState.RUNNING;
		}
			
	}
	public void presentStart(Graphics g) {
		commonPresent(g);
		g.drawPixmap(Assets.msg_toptostart, AppConst.START_MSG_X, AppConst.START_MSG_Y);
	}
	
	public void updateRunning(List<TouchEvent> event, float AccelX, float deltaTime) {
		for(int i = 0; i<event.size(); i++) {	
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i), AppConst.BTN_LEFT_X, AppConst.BTN_LEFT_Y,
						AppConst.BTN_WIDTH, AppConst.BTN_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					arena.moveLeft();
				}
				else if(GameUtil.inBounds(event.get(i), AppConst.BTN_RIGHT_X, AppConst.BTN_RIGHT_Y,
						AppConst.BTN_WIDTH, AppConst.BTN_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					arena.moveRight();
				}
				else if(GameUtil.inBounds(event.get(i), AppConst.BTN_ROTATE_X, AppConst.BTN_ROTATE_Y,
						AppConst.ROTATE_BTN_HEIGHT, AppConst.ROTATE_BTN_WIDTH)) {
					if(Settings.soundEnabled)
						Assets.rotate.play(1);
					arena.rotate();	
				}
				else if(GameUtil.inBounds(event.get(i),AppConst.ORIGIN_X, AppConst.ORIGIN_Y,
							AppConst.RUNNING_PAUSE_WIDTH, AppConst.RUNNING_PAUSE_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.PAUSED;
				}
			}
			else if(event.get(i).type == TouchEvent.TOUCH_DRAGGED) {
					if(GameUtil.inBounds(event.get(i), arena.shape.getX(), arena.shape.getY(),
							arena.shape.getWidth(), arena.shape.getHeight())) {
						// increase the deltatime so screen will get refreshed multiple times
						// Rather then increasing time by 2 sec increase with .5 and call update.
						// This Guarantee at least one update
						Log.i("Drag", arena.shape.getX()+","+arena.shape.getY());
						deltaTime += 0.5;
						arena.update(deltaTime);
					}
			}
		}
		// positive means left side and negative means right side
		// Too fast and does not let the shape move down
		if(AccelX>1) {
			arena.moveLeft();
		}
		else if(AccelX<-1){
			arena.moveRight();
		}
		arena.update(deltaTime);
		
		if(arena.gameOver()) {
			state = GameState.STOPPED;
			Settings.addScore(arena.highScore);
			Settings.save(game.getFileIO());
		}
	}
	
	public void presentRunning(Graphics g) {
		
		boolean[][] gameArea = arena.gameArea;
		for(int i = 0; i<gameArea.length; i++) {
			for(int j = 0; j<gameArea[i].length; j++) {
				if(gameArea[i][j]) 
					g.drawPixmap(Assets.block, j*AppConst.BLOCK_WIDTH, i*AppConst.BLOCK_HEIGHT);
			}
		}
		
		int[][] shapePosition = arena.shape.coordinate;
		for(int i = 0; i<shapePosition.length; i++) {
			g.drawPixmap(Assets.block, shapePosition[i][0]*AppConst.BLOCK_WIDTH,
									   shapePosition[i][1]*AppConst.BLOCK_HEIGHT);
		}
				
		commonPresent(g);
	}
	
	public void updatePaused(List<TouchEvent> event) {
		for(int i = 0; i<event.size(); i++) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i), AppConst.PAUSE_QUIT_X, AppConst.PAUSE_QUIT_Y, AppConst.PAUSE_MSG_WIDTH, AppConst.PAUSE_MEG_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MenuScreen(game));
				}
				else if(GameUtil.inBounds(event.get(i), AppConst.PAUSE_RESUME_X, AppConst.PAUSE_RESUME_Y, AppConst.PAUSE_MSG_WIDTH, AppConst.PAUSE_MEG_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.RUNNING;
				}
			}
		}
	}
	
	public void presentPaused(Graphics g) {
		presentRunning(g);
		
		g.drawPixmap(Assets.msg_resume, AppConst.PAUSE_RESUME_X, AppConst.PAUSE_RESUME_Y);
		g.drawPixmap(Assets.msg_quit, AppConst.PAUSE_QUIT_X, AppConst.PAUSE_QUIT_Y);
	}
	
	public void updateStopped(List<TouchEvent> event) {
		for(int i = 0; i<event.size(); i++) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i), AppConst.STOP_PLAY_AGAIN_X, AppConst.STOP_PLAY_AGAIN_Y,
											AppConst.STOP_MSG_PLAY_AGAIN_WIDTH, AppConst.STOP_MEG_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new GameScreen(game));
				}
				else if(GameUtil.inBounds(event.get(i), AppConst.STOP_TO_MENU_X, AppConst.STOP_TO_MENU_Y,
												AppConst.STOP_MSG_MENU_WIDTH, AppConst.STOP_MEG_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MenuScreen(game));
				}
			}
		}
	}
	
	public void presentStopped(Graphics g) {
		
		// Position
		int position = Settings.scorePosition(arena.highScore);
		if(position!=0) {
			// Draw position
			String sPosition = Integer.toString(++position);
			int startX = AppConst.STOP_POSITON_X;
			for(int i=0; i<sPosition.length(); i++) {
				g.drawPixmap(Assets.numbers2x,  startX+(i*AppConst.BIG_SCORE_WIDTH_JUMP), AppConst.STOP_POSITON_Y,
						AppConst.BIG_SCORE_START_X+((sPosition.charAt(i)-'0')*AppConst.BIG_SCORE_JUMP_X),AppConst.BIG_SCORE_START_Y,
						AppConst.BIG_DIGIT_WIDTH, AppConst.BIG_DIGIT_HEIGHT);
			}
			
			// Final Score Update With Position
			String score = Integer.toString(arena.highScore);
			int sStartX = AppConst.STOP_SCORE_X;
			for(int i=0; i<score.length(); i++) {
				g.drawPixmap(Assets.numbers2x, sStartX+(i*AppConst.BIG_SCORE_WIDTH_JUMP), AppConst.STOP_SCORE_Y,
						AppConst.BIG_SCORE_START_X+((score.charAt(i)-'0')*AppConst.BIG_SCORE_JUMP_X),AppConst.BIG_SCORE_START_Y,
						AppConst.BIG_DIGIT_WIDTH, AppConst.BIG_DIGIT_HEIGHT);
					}
		}
		
		g.drawPixmap(Assets.msg_tomenu, AppConst.STOP_TO_MENU_X, AppConst.STOP_TO_MENU_Y);
		g.drawPixmap(Assets.msg_playagain, AppConst.STOP_PLAY_AGAIN_X, AppConst.STOP_PLAY_AGAIN_Y);
	}
	
	void commonPresent(Graphics g) {
		
		// Vertical Line
		for(int i = 0; i<(AppConst.FRAMEBUFFER_HEIGHT/AppConst.SMALLBLOCK_HEIGHT); i++ ) {
			g.drawPixmap(Assets.smallBlock, AppConst.VERTICAL_LINE_X, i*AppConst.SMALLBLOCK_HEIGHT);
		}
		
		// Horizontal Line
		for(int i = 0; i<(AppConst.FRAMEBUFFER_WIDTH/AppConst.SMALLBLOCK_WIDTH); i++ ) {
			g.drawPixmap(Assets.smallBlock, i*AppConst.SMALLBLOCK_WIDTH, AppConst.HORIZONTAL_LINE_Y);
		}
		
		// Next Box
		for(int i = 0; i<=((AppConst.FRAMEBUFFER_WIDTH-AppConst.VERTICAL_LINE_X)/AppConst.SMALLBLOCK_WIDTH); i++) {
			if(i>2 && i<((AppConst.FRAMEBUFFER_WIDTH-AppConst.VERTICAL_LINE_X)/AppConst.SMALLBLOCK_WIDTH)-2) {
				g.drawPixmap(Assets.smallBlock, AppConst.VERTICAL_LINE_X+i*AppConst.SMALLBLOCK_WIDTH,
						AppConst.NEXT_BOX_HY);
			}
			else {	
				g.drawPixmap(Assets.smallBlock, AppConst.VERTICAL_LINE_X+i*AppConst.SMALLBLOCK_WIDTH,
							AppConst.NEXT_BOX_Y);
			}
			g.drawPixmap(Assets.smallBlock, AppConst.VERTICAL_LINE_X+i*AppConst.SMALLBLOCK_WIDTH,
							AppConst.NEXT_BOX_LY);
		}
		
		// Shape in the Next Box
		int[][] nsCoordinate = arena.getNextShapeCoordinate();
		for(int i = 0; i<nsCoordinate.length; i++) {
			g.drawPixmap(Assets.block, nsCoordinate[i][0], nsCoordinate[i][1]);
		}
		
		// Score Update
		String score = Integer.toString(arena.highScore);
		int startX = AppConst.MSG_SCORE_X+((AppConst.MSG_SCORE_WIDTH/2)-((score.length()*AppConst.SCORE_WIDTH_JUMP)/2));
		for(int i=0; i<score.length(); i++) {
			g.drawPixmap(Assets.numbers, startX+(i*AppConst.SCORE_WIDTH_JUMP), AppConst.SCORE_Y,
					AppConst.SCORE_START_X+((score.charAt(i)-'0')*AppConst.SCORE_WIDTH_JUMP),AppConst.SCORE_START_Y,
					AppConst.DIGIT_WIDTH, AppConst.DIGIT_HEIGHT);
		}
		
		g.drawPixmap(Assets.msg_score, AppConst.MSG_SCORE_X, AppConst.MSG_SCORE_Y);
		g.drawPixmap(Assets.msg_next, AppConst.MSG_NEXT_X, AppConst.MSG_NEXT_Y);
		g.drawPixmap(Assets.btn_left, AppConst.BTN_LEFT_X, AppConst.BTN_LEFT_Y);
		g.drawPixmap(Assets.btn_right, AppConst.BTN_RIGHT_X, AppConst.BTN_RIGHT_Y);
		g.drawPixmap(Assets.btn_rotate, AppConst.BTN_ROTATE_X, AppConst.BTN_ROTATE_Y);
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
