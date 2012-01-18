package gautambverma.game.tetris.screen;

import java.util.List;

import gautambverma.com.game.Game;
import gautambverma.com.game.Screen;
import gautambverma.com.game.input.Input.TouchEvent;
import gautambverma.game.tetris.logic.Settings;
import gautambverma.game.tetris.util.Assets;
import gautambverma.game.tetris.util.ScreenConst;


/*
 * This screen will present to the user when he/she has finished the Game
 * */
public class FinishScreen extends Screen {

	public FinishScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		
		List<TouchEvent> event = game.getInput().getTouchEvent();
		game.getInput().getKeyEvents();
		
		for(int i = 0; i<event.size(); i++ ) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if (Settings.soundEnabled)
					Assets.click.play(1);
				game.setScreen(new MenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		game.getGraphics().drawPixmap(Assets.final_message, ScreenConst.FINAL_MESSAGE_X, ScreenConst.FINAL_MESSAGE_X);
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
