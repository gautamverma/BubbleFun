package com.game;

import android.content.Context;
import android.os.Handler;

import com.game.audio.Audio;
import com.game.input.Input;
import com.game.fileio.FileIO;
import com.game.graphics.Graphics;
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
	
	void login();
	void openLeaderBoard();
	void openDashBoard();
	void openCoinStore();
	boolean openAchievement(final int achievementID);
	boolean openTournament();
	boolean isTournamentMatch();
	void endTournament(int score, int level);
	void startSavedGame(boolean b);
	boolean isSaved();
	void clearGame();
}
