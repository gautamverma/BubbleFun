package game.tetris.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.game.fileio.FileIO;

/*
 * It contains various settings of the game.
 * 1) SoundEnabled
 * 2) HighScore
 * 
 * ** Save game level
 * */
public class Settings {

	public static boolean soundEnabled;
	public static int HighScore;
	public static int Level;
	public static int Lines;
	static String fileName;
	
	static
	{
		Lines = 0;
		Level = 1;
		HighScore = 0;
		soundEnabled = true;
		fileName = "tetris_settings";
	}
	
	public static void save(FileIO fileIO) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(fileIO.writeFile(fileName)));
			writer.write(Boolean.toString(soundEnabled)); writer.newLine();
			writer.write(HighScore); writer.newLine();
			writer.write(Level); writer.newLine();
			writer.write(Lines); writer.newLine();
			writer.flush();
		}
		catch(IOException ioException) {
			// If it is not saved then also game must continue
			ioException.printStackTrace();
		}
		finally {
			try {
				if(writer!=null) 
					writer.close();
			}
			catch(IOException ioex) {
				ioex.printStackTrace();
			}
		}
	}
	
	public static void load(FileIO fileIO) {
		BufferedReader reader = null;
		try {
			if(!fileIO.fileExist(fileName)) {
				// Game opened for first time so save the Default Settings
				save(fileIO); 
			}
			else {
				reader = new BufferedReader(new InputStreamReader(fileIO.readFile(fileName)));
				soundEnabled = Boolean.parseBoolean(reader.readLine());
				HighScore = Integer.parseInt(reader.readLine());
				Level = Integer.parseInt(reader.readLine());
				Lines = Integer.parseInt(reader.readLine());
			}
		}
		catch(IOException ioException) {
			// If it is loaded then also game must continue
		}
		finally {
			try {
				if(reader!=null)
					reader.close();
			}
			catch(IOException ioex) {
			}
		}
	}
	
	/*
	 * It tries adds a score to the High Scores 
	 * */
	public static void addScore(int score) {	
		if(score>HighScore) {
			HighScore = score;
		}
	}
	
	public static void toggleSound() {
		soundEnabled = !soundEnabled;
	}

	public static void addLevel(int level) {
		if(Level<level)
			Level = level;
	}

	public static void addLines(int lines) {
		if(Lines<lines)
			Lines = lines;
	}
}
