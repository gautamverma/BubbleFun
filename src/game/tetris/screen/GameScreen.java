package game.tetris.screen;

import java.util.List;

import game.tetris.logic.Arena;
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
 * This is the game Screen and it implements all the four game transitions
 *  1) Tap to start
 *  2) Pause
 *  3) Resume
 *  4) Over
 *  
 *  ** Adding Accelerometer Support
 * @author Gautam
 * */
public class GameScreen extends Screen {

	enum GameState {
		START, RUNNING, PAUSED, STOPPED
	}

	Arena arena;
	GameState state;
	
	int initialLines = 0;
	/****************** Accelerometer Constants *****************/
	int leftA = 0;
	int rightA = 0;

	static float MOVE_DOWN_ACCELERATION_CONST = 7.0f;
	static float MOVE_DOWN_ACCELERATION;
	static float RESUME_MOVE_DOWN = 0.7f;

	float[] AccelerometerConst;
	/************************************************************/

	public GameScreen(Game game) {
		super(game);

		arena = new Arena();
		state = GameState.START;
		MOVE_DOWN_ACCELERATION = MOVE_DOWN_ACCELERATION_CONST;
		
		// Now Load Save game Setting
		if(game.isSaved()) {
			Settings.load(game.getFileIO());
			arena.level = Settings.Level;
			arena.lines = Settings.Lines;
			arena.score = arena.lines*AppConst.ONE_LINE_POINTS;
		}
		// Min Speed .46sec and Max is .04
		arena.UPDATE_INTERVAL = AppConst.STARTING_UPDATE_INTERVAL - ((arena.level)*AppConst.LEVEL_INTERVAL_DECREASE);
		if(arena.level<AppConst.GAME_LEVEL_FIFTH)
			AccelerometerConst = AppConst.INITIALACCELERATIONCONST;
		else
			AccelerometerConst = AppConst.HIGHACCELERATIONCONST;
	}

	@Override
	public void update(float deltaTime) {

		List<TouchEvent> event = game.getInput().getTouchEvent();
		float accelX = game.getInput().getAccelX();
		float accelY = game.getInput().getAccelY();
		game.getInput().getKeyEvents();

		if (state == GameState.START)
			updateStart(event);
		else if (state == GameState.RUNNING)
			updateRunning(event, accelX, accelY, deltaTime);
		else if (state == GameState.PAUSED)
			updatePaused(event);
		else
			updateStopped(event);
	}

	@Override
	public void present(float deltaTime) {

		Graphics g = game.getGraphics();

		// No Need to do it in multiple present methods
		g.drawPixmap(Assets.blue_background,AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		g.drawPixmap(Assets.background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);

		if (state == GameState.START)
			presentStart(g);
		else if (state == GameState.RUNNING)
			presentRunning(g);
		else if (state == GameState.PAUSED)
			presentPaused(g);
		else
			presentStopped(g);

	}

	public void updateStart(List<TouchEvent> event) {
		if (event.size() > 0) {
			if (Settings.soundEnabled)
				Assets.click.play(1);
			state = GameState.RUNNING;
		}
	}

	public void presentStart(Graphics g) {
		commonPresent(g);
		g.drawPixmap(Assets.text_tap_to_start, ScreenConst.TAP_TO_START_X,
				ScreenConst.TAP_TO_START_Y);
	}

	public void updateRunning(List<TouchEvent> event, float AccelX,
			float AccelY, float deltaTime) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				if (GameUtil.inBounds(event.get(i), AppConst.ORIGIN_X,
						AppConst.ORIGIN_Y, AppConst.RUNNING_PAUSE_WIDTH,
						AppConst.RUNNING_PAUSE_HEIGHT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.PAUSED;
				} else {
					if (Settings.soundEnabled)
						Assets.rotate.play(1);
					arena.rotate();
				}
			}
		}

		// For each more turn it will fired at higher acceleration
		if (AccelX >= AccelerometerConst[leftA]) {
			arena.moveLeft();
			rightA = 0;
			if (leftA < (AccelerometerConst.length - 1))
				leftA++;
		} else if (AccelX <= -AccelerometerConst[rightA]) {
			arena.moveRight();
			leftA = 0;
			if (rightA < (AccelerometerConst.length - 1))
				rightA++;
		}

		if (AccelY > MOVE_DOWN_ACCELERATION_CONST) {
			if (!arena.shapePlaced) {
				MOVE_DOWN_ACCELERATION = AccelY;
				deltaTime += 0.5f;
				arena.update(deltaTime);
			}
		}
		if (arena.shapePlaced && AccelY < (MOVE_DOWN_ACCELERATION-RESUME_MOVE_DOWN)) {
			arena.shapePlaced = false;
		}

		arena.update(deltaTime);
		
		if(arena.lines > linesRequiredToUpdateLevel()) {
			initialLines = arena.lines;
			int previousLevel = arena.level;
			arena.level++;
			if(previousLevel==AppConst.GAME_LEVEL_ONE && arena.level==AppConst.GAME_LEVEL_SECOND) {
				presentingLevelChange();
				game.openAchievement(AppConst.LEVEL1_ACHIEVEMENT_ID);
			}
			else if(previousLevel==AppConst.GAME_LEVEL_FIFTH && arena.level==AppConst.GAME_LEVEL_SIXTH) {
				presentingLevelChange();
				game.openAchievement(AppConst.LEVEL5_ACHIEVEMENT_ID);
				changeAccelerometerConstants();
			}
			else if(previousLevel==AppConst.GAME_LEVEL_TWELVE) {
				presentingLevelChange();
				game.openAchievement(AppConst.LEVEL12_ACHIEVEMENT_ID);
			}
			if (arena.level > AppConst.HIGHEST_LEVEL) {
				endGame();
				game.setScreen(new FinishScreen(game));
				}	
			arena.UPDATE_INTERVAL -= AppConst.LEVEL_INTERVAL_DECREASE;
		}
		
		if (arena.gameOver()) {
			state = GameState.STOPPED;
			endGame();	
		}
	}

	// Higher Levels more sensitive values needed
	protected void changeAccelerometerConstants() {
		AccelerometerConst = AppConst.HIGHACCELERATIONCONST;
	}

	/**
	 * Saves and Ends the Game as it supposed to be
	 *   if tournament then calls endtournaments
	 *   if Practice match then end the Practice match
	 *   then clear all the Game Attributes
	 * */
	protected void endGame() {
		// Save Lines & Levels
		saveGame();
		if(game.islogged()) {
			if(game.isTournamentMatch())
				game.endTournament(arena.score, arena.level);
			else {
				game.getSKApplication().getGameManager().getSinglePlayerTools().endPractice(arena.score, arena.level, null);
				}
		}
		//Clear all the Game Data as game has ended
		game.clearGame();
	}
	
	private int linesRequiredToUpdateLevel() {
		int requiredLines = 0;
		for(int i = 1; i<=(arena.level+1); i++) {
			requiredLines += (AppConst.LINES_REQUIRED_UPDATE_LEVEL - i + 1);
		}
		return requiredLines;
	}

	public void presentRunning(Graphics g) {

		boolean[][] gameArea = arena.gameArea;
		for (int i = 0; i < gameArea.length; i++) {
			for (int j = 0; j < gameArea[i].length; j++) {
				if (gameArea[i][j])
					g.drawPixmap(Assets.block, j * AppConst.BLOCK_WIDTH, i
							* AppConst.BLOCK_HEIGHT, AppConst.ORIGIN_X,
							AppConst.ORIGIN_Y, AppConst.BLOCK_WIDTH,
							AppConst.BLOCK_HEIGHT);
			}
		}

		int[][] shapePosition = arena.shape.coordinate;
		for (int i = 0; i < shapePosition.length; i++) {
			g.drawPixmap(
					Assets.block,
					shapePosition[i][0] * AppConst.BLOCK_WIDTH,
					shapePosition[i][1] * AppConst.BLOCK_HEIGHT,
					AppConst.ORIGIN_X,
					AppConst.ORIGIN_Y
							+ arena.shape.colour
							* (AppConst.ATLASING_OFFSET + AppConst.BLOCK_HEIGHT),
					AppConst.BLOCK_WIDTH, AppConst.BLOCK_HEIGHT);
		}
		commonPresent(g);
	}
	
	public void updatePaused(List<TouchEvent> event) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				if (GameUtil.inBounds(event.get(i), ScreenConst.RESUME_X,
						ScreenConst.RESUME_Y, FileName.TEXT_RESUME_WH,
						FileName.TEXT_RESUME_HT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.RUNNING;
				} else if (GameUtil.inBounds(event.get(i), ScreenConst.QUIT_X,
						ScreenConst.QUIT_Y, FileName.TEXT_QUIT_WH,
						FileName.TEXT_QUIT_HT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					
					endGame();
					game.setScreen(new MenuScreen(game));
				}
			}
		}
	}

	public void presentPaused(Graphics g) {
		presentRunning(g);

		g.drawPixmap(Assets.text_resume, ScreenConst.RESUME_X,
				ScreenConst.RESUME_Y);
		g.drawPixmap(Assets.text_quit, ScreenConst.QUIT_X, ScreenConst.QUIT_Y);
	}

	public void updateStopped(List<TouchEvent> event) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				 if (GameUtil.inBounds(event.get(i),
						ScreenConst.BACK_TO_MENU_X, ScreenConst.BACK_TO_MENU_Y,
						FileName.TEXT_BACK_TO_MENU_WH,
						FileName.TEXT_BACK_TO_MENU_HT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MenuScreen(game));
				}
			}
		}
	}

	public void presentStopped(Graphics g) {

		g.drawPixmap(Assets.blue_background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		g.drawPixmap(Assets.text_your_score, ScreenConst.YOUR_SCORE_X,
				ScreenConst.YOUR_SCORE_Y);
		// Score of current Game
		String score = Integer.toString(arena.score);
		for (int i = 0; i < score.length(); i++) {
			g.drawPixmap(Assets.digits, ScreenConst.YOUR_SCORE_VALUE_X + i
					* AppConst.DIGIT_WIDTH, ScreenConst.YOUR_SCORE_VALUE_Y,
					AppConst.ORIGIN_X, ((score.charAt(i) - '0') + 1)
							* (AppConst.DIGIT_HEIGHT + AppConst.DIGIT_OFFSET),
					AppConst.DIGIT_WIDTH, AppConst.DIGIT_HEIGHT);
		}

		g.drawPixmap(Assets.text_high_score, ScreenConst.HIGH_SCORE_X,
				ScreenConst.HIGH_SCORE_Y);
		// HighScore
		String highscore = Integer.toString(Settings.HighScore);
		for (int i = 0; i < highscore.length(); i++) {
			g.drawPixmap(Assets.digits, ScreenConst.HIGH_SCORE_VALUE_X + i
					* AppConst.DIGIT_WIDTH, ScreenConst.HIGH_SCORE_VALUE_Y,
					AppConst.ORIGIN_X, ((highscore.charAt(i) - '0') + 1)
							* (AppConst.DIGIT_HEIGHT + AppConst.DIGIT_OFFSET),
					AppConst.DIGIT_WIDTH, AppConst.DIGIT_HEIGHT);
		}

		g.drawPixmap(Assets.text_back_to_menu, ScreenConst.BACK_TO_MENU_X,
				ScreenConst.BACK_TO_MENU_Y);
	}

	void commonPresent(Graphics g) {

		// Shape in the Next Box
		int[][] nsCoordinate = arena.getNextShapeCoordinate();
		for (int i = 0; i < nsCoordinate.length; i++) {
			g.drawPixmap(
					Assets.block,
					nsCoordinate[i][0],
					nsCoordinate[i][1],
					AppConst.ORIGIN_X,
					AppConst.ORIGIN_Y
							+ arena.nextShape.colour
							* (AppConst.ATLASING_OFFSET + AppConst.BLOCK_HEIGHT),
					AppConst.BLOCK_WIDTH, AppConst.BLOCK_HEIGHT);
		}

		// Score Update
		String score = Integer.toString(arena.score);
		int startX = ScreenConst.SCORE_VALUE_X
				+ ((FileName.TEXT_SCORE_WH / 2) - ((score.length() * AppConst.SMALL_DIGIT_WIDTH) / 2));
		for (int i = 0; i < score.length(); i++) {
			g.drawPixmap(
					Assets.small_digits,
					startX + (i * AppConst.SMALL_DIGIT_WIDTH),
					ScreenConst.SCORE_VALUE_Y,
					AppConst.ORIGIN_X,
					((score.charAt(i) - '0') + 1)
							* (AppConst.SMALL_DIGIT_HEIGHT + AppConst.SMALL_DIGIT_OFFSET),
					AppConst.SMALL_DIGIT_WIDTH, AppConst.SMALL_DIGIT_HEIGHT);
		}

		// level update
		String level = Integer.toString(arena.level);
		int levelStartX = ScreenConst.LEVEL_VALUE_X
				+ ((FileName.TEXT_LEVEL_WH / 2) - ((level.length() * AppConst.SMALL_DIGIT_WIDTH) / 2));
		for (int i = 0; i < level.length(); i++) {
			g.drawPixmap(
					Assets.small_digits,
					levelStartX + (i * AppConst.SMALL_DIGIT_WIDTH),
					ScreenConst.LEVEL_VALUE_Y,
					AppConst.ORIGIN_X,
					((level.charAt(i) - '0') + 1)
							* (AppConst.SMALL_DIGIT_HEIGHT + AppConst.SMALL_DIGIT_OFFSET),
					AppConst.SMALL_DIGIT_WIDTH, AppConst.SMALL_DIGIT_HEIGHT);
		}

		// Lines update
		String lines = Integer.toString(arena.lines);
		int linesStartX = ScreenConst.LINES_VALUE_X
				+ ((FileName.TEXT_LINES_WH / 2) - ((lines.length() * AppConst.SMALL_DIGIT_WIDTH) / 2));
		for (int i = 0; i < lines.length(); i++) {
			g.drawPixmap(
					Assets.small_digits,
					linesStartX + (i * AppConst.SMALL_DIGIT_WIDTH),
					ScreenConst.LINES_VALUE_Y,
					AppConst.ORIGIN_X,
					((lines.charAt(i) - '0') + 1)
							* (AppConst.SMALL_DIGIT_HEIGHT + AppConst.SMALL_DIGIT_OFFSET),
					AppConst.SMALL_DIGIT_WIDTH, AppConst.SMALL_DIGIT_HEIGHT);
		}

		g.drawPixmap(Assets.text_next, ScreenConst.NEXT_X, ScreenConst.NEXT_Y);
		g.drawPixmap(Assets.text_level, ScreenConst.LEVEL_X,
				ScreenConst.LEVEL_Y);
		g.drawPixmap(Assets.text_score, ScreenConst.SCORE_X,
				ScreenConst.SCORE_Y);
		g.drawPixmap(Assets.text_lines, ScreenConst.LINES_X,
				ScreenConst.LINES_Y);
	}

	public void presentingLevelChange() {
		
		Graphics g = game.getGraphics();
		presentRunning(g);
		// Now Dim the UI by background
		// Show Level Changing message
		g.drawPixmap(Assets.translucent_background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
	}
	
	private void saveGame() {
		// Load the most Recent Values
		Settings.load(game.getFileIO());

		// Add all the Game Values
		Settings.addScore(arena.score);
		Settings.addLevel(arena.level);
		Settings.addLines(arena.lines);

		// Save the Values
		Settings.save(game.getFileIO());
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
