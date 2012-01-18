package gautambverma.com.game;

import gautambverma.com.game.audio.Audio;
import gautambverma.com.game.fileio.FileIO;
import gautambverma.com.game.graphics.Graphics;
import gautambverma.com.game.input.Input;
import android.content.Context;
import android.os.Handler;

import com.skiller.api.operations.SKApplication;

/**
 * @author Gautam
 * @since 17/10/2011  dd/mm/yyyy
 *
 */
public interface Game {

	Input getInput();
	FileIO getFileIO();
	Graphics getGraphics();
	Audio getAudio();
	SKApplication getSKApplication();
	Handler getHandler();
	Context getContext();
	
	void setScreen(Screen screen);
	Screen getCurrentScreen();
	Screen getStartScreen();
	
	boolean login();
	void openLeaderBoard();
	void openDashBoard();
	void openCoinStore();
	boolean openAchievement(final int achievementID);
	boolean openTournament();
	boolean isTournamentMatch();
	void endTournament(int score, int level);
	void startPracticeGame();
	void startSavedGame(boolean b);
	void showStandAlonePracticeGameToast();
	boolean isSaved();
	boolean islogged();
	void clearGame();
	void unblockThreadBlocked();
	
}
