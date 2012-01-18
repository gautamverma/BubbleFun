package gautambverma.game.tetris.screen;

import gautambverma.com.game.Game;
import gautambverma.com.game.Screen;
import gautambverma.com.game.graphics.Graphics;
import gautambverma.com.game.input.Input.TouchEvent;
import gautambverma.game.tetris.logic.Arena;
import gautambverma.game.tetris.logic.Settings;
import gautambverma.game.tetris.util.AppConst;
import gautambverma.game.tetris.util.Assets;
import gautambverma.game.tetris.util.FileName;
import gautambverma.game.tetris.util.GameUtil;
import gautambverma.game.tetris.util.ScreenConst;

import java.util.List;


public class Tutorial extends Screen {

	enum TutorialState {
		START_PAGE1, START_PAGE2, RUNNING, PAUSED, FAIL, SUCCESSFUL
	};

	int blockNumber;
	TutorialState state;

	Arena arena;

	/****************** Accelerometer Constants *****************/
	int leftA = 0;
	int rightA = 0;

	static float MOVE_DOWN_ACCELERATION;
	static float RESUME_MOVE_DOWN = 0.7f;
	/************************************************************/

	public Tutorial(Game game) {
		super(game);

		arena = new Arena();
		state = TutorialState.START_PAGE1;
		MOVE_DOWN_ACCELERATION = AppConst.MOVE_DOWN_ACCELERATION_CONST;
	}

	@Override
	public void update(float deltaTime) {

		List<TouchEvent> events = game.getInput().getTouchEvent();
		game.getInput().getKeyEvents();

		float accelX = game.getInput().getAccelX();
		float accelY = game.getInput().getAccelY();

		if (state == TutorialState.START_PAGE1)
			updateStart(events);
		else if (state == TutorialState.START_PAGE2)
			updateStart(events);
		else if(state==TutorialState.PAUSED)
			updatePaused(events);
		else if (state == TutorialState.RUNNING)
			updateRunning(events, accelX, accelY, deltaTime);
		else if (state == TutorialState.SUCCESSFUL)
			updateSuccessful(events);
		else if (state == TutorialState.FAIL)
			updateFail(events);
	}

	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		// No Need to do it in multiple present methods
		g.drawPixmap(Assets.blue_background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		g.drawPixmap(Assets.background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);

		if (state == TutorialState.START_PAGE1)
			presentStart(g);
		else if (state == TutorialState.START_PAGE2)
			presentStart(g);
		else if (state == TutorialState.RUNNING)
			presentRunning(g);
		else if(state==TutorialState.PAUSED)
			presentPaused(g);
		else if (state == TutorialState.SUCCESSFUL)
			presentSuccessful(g);
		else if (state == TutorialState.FAIL)
			presentFail(g);
	}

	protected void updateStart(List<TouchEvent> events) {
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).type == TouchEvent.TOUCH_UP) {
				if (Settings.soundEnabled)	Assets.click.play(1);
				if (state == TutorialState.START_PAGE2) {
					if (GameUtil.inBounds(events.get(i), ScreenConst.MSG7_X,
							ScreenConst.MSG7_Y, FileName.TUTORIAL_MSG7_WH,
							FileName.TUTORIAL_MSG7_HT))
						state = TutorialState.RUNNING;
					else
						state = TutorialState.START_PAGE1;
				} 
				else state = TutorialState.START_PAGE2;
			}
		}
	}

	protected void presentStart(Graphics g) {

		g.drawPixmap(Assets.blue_background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		if (state == TutorialState.START_PAGE1) {
			g.drawPixmap(Assets.tutorial_msg1, ScreenConst.MSG_X,
					ScreenConst.MSG1_Y);
			g.drawPixmap(Assets.tutorial_msg2, ScreenConst.MSG_X,
					ScreenConst.MSG2_Y);
			g.drawPixmap(Assets.tutorial_msg3, ScreenConst.MSG_X,
					ScreenConst.MSG3_Y);
			g.drawPixmap(Assets.tutorial_msg4, ScreenConst.MSG_X,
					ScreenConst.MSG4_Y);
		} else {
			g.drawPixmap(Assets.tutorial_msg5, ScreenConst.MSG_X,
					ScreenConst.MSG5_Y);
			g.drawPixmap(Assets.tutorial_msg6, ScreenConst.MSG_X,
					ScreenConst.MSG6_Y);
			g.drawPixmap(Assets.tutorial_msg7, ScreenConst.MSG7_X,
					ScreenConst.MSG7_Y);
		}
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
					state = TutorialState.PAUSED;
				}
				else {
					if (Settings.soundEnabled)
						Assets.rotate.play(1);
					arena.rotate();
				}
			}
		}

		// For each more turn it will fired at higher acceleration
		if (AccelX >= AppConst.INITIALACCELERATIONCONST[leftA]) {
			arena.moveLeft();

			rightA = 0;
			if (leftA < (AppConst.INITIALACCELERATIONCONST.length - 1))
				leftA++;
		} else if (AccelX <= -AppConst.INITIALACCELERATIONCONST[rightA]) {
			arena.moveRight();

			leftA = 0;
			if (rightA < (AppConst.INITIALACCELERATIONCONST.length - 1))
				rightA++;
		}

		if (AccelY > AppConst.MOVE_DOWN_ACCELERATION_CONST) {
			if (!arena.shapePlaced) {
				MOVE_DOWN_ACCELERATION = AccelY;
				deltaTime += 0.5f;
				arena.update(deltaTime);
			}
		}
		if (arena.shapePlaced
				&& AccelY < (MOVE_DOWN_ACCELERATION - RESUME_MOVE_DOWN)) {
			arena.shapePlaced = false;

		}

		arena.update(deltaTime);
		if (arena.lines > AppConst.DEFAULT_LINES_COMPLETED) {
			state = TutorialState.SUCCESSFUL;
		}

		if (arena.gameOver()) {
			// set Again the Tutorial Screen with Msg
			state = TutorialState.FAIL;
		}
	}

	public void presentRunning(Graphics g) {

		drawArena(g);

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

		commonUI(g);
	}

	public void updatePaused(List<TouchEvent> event) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				if (GameUtil.inBounds(event.get(i), ScreenConst.RESUME_X,
						ScreenConst.RESUME_Y, FileName.TEXT_RESUME_WH,
						FileName.TEXT_RESUME_HT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					state = TutorialState.RUNNING;
				} else if (GameUtil.inBounds(event.get(i), ScreenConst.QUIT_X,
						ScreenConst.QUIT_Y, FileName.TEXT_QUIT_WH,
						FileName.TEXT_QUIT_HT)) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
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

	/**
	 * This state is presented to user if it successfully completes the tutorial
	 * */
	protected void updateSuccessful(List<TouchEvent> event) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				if (Settings.soundEnabled)
					Assets.click.play(1);
				game.setScreen(new GameScreen(game));
			}
		}
	}

	public void presentSuccessful(Graphics g) {

		drawArena(g);
		commonUI(g);
		g.drawPixmap(Assets.tutorial_msg_successful,
				ScreenConst.MSG_SUCCESSFUL_X, ScreenConst.MSG_SUCCESSFUL_Y);
	}

	/**
	 * This will be present to user if game gets over before he/she makes one
	 * line "oops something went wrong lets try again"
	 * */
	protected void updateFail(List<TouchEvent> event) {
		for (int i = 0; i < event.size(); i++) {
			if (event.get(i).type == TouchEvent.TOUCH_UP) {
				if (Settings.soundEnabled)
					Assets.click.play(1);
				game.setScreen(new Tutorial(game));
			}
		}
	}

	public void presentFail(Graphics g) {

		drawArena(g);
		commonUI(g);
		g.drawPixmap(Assets.tutorial_msg_fail, ScreenConst.MSG_FAIL_X,
				ScreenConst.MSG_FAIL_Y);
	}

	protected void commonUI(Graphics g) {

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

	protected void drawArena(Graphics g) {

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
