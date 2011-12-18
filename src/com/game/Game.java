package com.game;

import com.game.audio.Audio;
import com.game.input.Input;
import com.game.fileio.FileIO;
import com.game.graphics.Graphics;

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
	
	void setScreen(Screen screen);
	Screen getCurrentScreen();
	Screen getStartScreen();
}
