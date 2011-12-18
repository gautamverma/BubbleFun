package game.tetris.screen;

import game.tetris.logic.Settings;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;
import game.tetris.util.GameUtil;

import java.util.List;

import com.game.Game;
import com.game.Screen;
import com.game.graphics.Graphics;
import com.game.input.Input.TouchEvent;

public class Help3 extends Screen {

	public Help3(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> event = game.getInput().getTouchEvent();
		game.getInput().getKeyEvents();

		for(int i = 0; i < event.size(); i++) {
			if(event.get(i).type == TouchEvent.TOUCH_UP) {
				if(GameUtil.inBounds(event.get(i),AppConst.TAP_HERE_X, AppConst.TAP_HERE_Y,
						AppConst.TAP_HERE_WIDTH, AppConst.TAP_HERE_HEIGHT)) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					game.setScreen(new MenuScreen(game));
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, AppConst.ORIGIN_X, AppConst.ORIGIN_Y);
		
		g.drawPixmap(Assets.help3_scene1, AppConst.HELP3_SCENE1_X, AppConst.HELP3_SCENE1_Y);
		g.drawPixmap(Assets.help3_scene2, AppConst.HELP3_SCENE2_X, AppConst.HELP3_SCENE2_Y);
		g.drawPixmap(Assets.msg_help3_part1, AppConst.HELP3_MSG1_X, AppConst.HELP3_MSG1_Y);
		g.drawPixmap(Assets.msg_help3_part2, AppConst.HELP3_MSG2_X, AppConst.HELP3_MSG2_Y);
		
		g.drawPixmap(Assets.msg_taphere, AppConst.TAP_HERE_X, AppConst.TAP_HERE_Y);
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
