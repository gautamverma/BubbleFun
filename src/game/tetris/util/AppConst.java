package game.tetris.util;

/**
 * This class contains all the constants used in the game. So to produce any configuration change
 * kindly use this class. 
 * 
 * @author Gautam
 */
public class AppConst {

	public static final int FRAMEBUFFER_WIDTH = 320;
	public static final int FRAMEBUFFER_HEIGHT = 480;
	
	// Block Size & Offsets
	public static final int BLOCK_WIDTH = 15;
	public static final int BLOCK_HEIGHT = 15;
	
	public static final int BLACK_BLOCK_POS = 0;
	public static final int BLUE_BLOCK_POS = 1;
	public static final int PURPLE_BLOCK_POS = 2;
	public static final int GREEN_BLOCK_POS = 3;
	public static final int DARK_PURPLE_BLOCK_POS = 4;
	public static final int RED_BLOCK_POS = 5;
	
	public static final int WRITING_BLOCK_WIDTH = 8;
	public static final int WRITING_BLOCK_HEIGHT = 8;
	
	public static final int SMALLBLOCK_WIDTH = 8;
	public static final int SMALLBLOCK_HEIGHT = 8;
	
	// Accelerometer Constants
	// Phone is tilted more than 60* to get 5.5 So 5.9 is max
	public static float[] ACCELERATIONCONST = { .3f, .5f, .7f, 0.9f,
										1.1f, 1.2f, 1.3f, 1.35f, 1.4f,
										1.5f, 1.55f, 1.6f, 1.65f, 1.7f,
										1.75f, 1.8f, 1.85f, 1.9f, 1.95f, 2.0f, 2.05f,
										2.1f, 2.15f, 2.2f, 2.25f, 2.3f,
										2.35f, 2.4f, 2.45f, 2.5f, 2.55f,
										2.6f, 2.65f, 2.7f, 2.75f, 2.8f,
										2.85f, 2.9f, 2.95f, 3.0f, 3.05f,
										3.1f, 3.15f, 3.2f, 3.3f, 3.4f,
										3.5f, 3.6f, 3.7f, 3.8f, 3.9f,
										4.1f, 4.3f, 4.5f, 4.7f, 4.9f,
										5.1f, 5.3f, 5.5f, 5.7f, 5.9f,
										};
	
	// Digit Sizes & Offset
	public static final int SMALL_DIGIT_WIDTH = 18;
	public static final int SMALL_DIGIT_HEIGHT = 18;
	
	public static final int DIGIT_WIDTH = 27;
	public static final int DIGIT_HEIGHT = 27;
	
	public static final int DIGIT_OFFSET = 27;
	public static final int SMALL_DIGIT_OFFSET = 18;
	
	// Standard Offset Used
	public static final int ATLASING_OFFSET = 20;
	
	public static final int ORIGIN_X = 0;
	public static final int ORIGIN_Y = 0;
	
	public static final int LEFT_LIMIT = 0;
	public static final int RIGHT_LIMIT = 13;
	
	public static final float STARTING_UPDATE_INTERVAL = 0.5f;
	public static final float LEVEL_INTERVAL_DECREASE = 0.05f;

	// Score & Level Constants
	public static final int DEFAULT_SCORE = 0;
	public static final int ONE_LINE_POINTS = 10;
	public static final int DEFAULT_LINES_COMPLETED = 0;
	
	public static final int INITIAL_LEVEL = 1;
	public static final int HIGHEST_LEVEL = 10;
	
	public static final int SOUNDBUTTON_HT = 64;
	public static final int SOUNDBUTTON_WH = 64;
	
	// At 1 (x,y) coordinate a block can placed 
	public static final int ARENA_GRID_WIDTH = 14;
	public static final int ARENA_GRID_HEIGHT = 26;
	
	// it can take seven blocks of small blocks
	public static final int MAX_LETTER_HEIGHT = 7;
	public static final int LETTER_OFFSET = 2;
	
	public static final int TAP_HERE_X = 88;
	public static final int TAP_HERE_Y = 440;
	
	public static final int TAP_HERE_WIDTH = 144;
	public static final int TAP_HERE_HEIGHT = 21;
	
	// Start Screen Constants
	public static final int START_MSG_X = 70;
	public static final int START_MSG_Y = 180;
	
	// Running State Constants 
	public static final int RUNNING_PAUSE_WIDTH = 64;
	public static final int RUNNING_PAUSE_HEIGHT = 64;
	
	// Running
	public static final int HORIZONTAL_LINE_Y = 390;
	public static final int VERTICAL_LINE_X = 210;
	
	public static final int NEXT_BOX_HY = 50;
	public static final int NEXT_BOX_Y = 58;
	public static final int NEXT_BOX_LY = 160;
	
	public static final int SCORE_Y = 325;
	
	public static final int SCORE_START_X = 1;
	public static final int SCORE_START_Y = 3;
	
	public static final int SCORE_WIDTH_JUMP = 9;
	
    // Acceleration Value to update 
	public static final int ACCELERATION_CONSTANT = 2;
	
	// Intent Request 
	public static final int PRAC_GAME = 0;
	
	// Score Constants
	public static final int LINES_REQUIRED_UPDATE_LEVEL = 15;
	
	// Other Constants
	public static final String GAME_LEVEL = "level";
	public static final String GAME_SCORE = "score";
	
	public static final int GAME_LEVEL_ONE = 1;
	public static final int GAME_LEVEL_SECOND = 2;
	public static final int GAME_LEVEL_FIFTH = 5;
	public static final int GAME_LEVEL_SIXTH = 6;
	public static final int GAME_LEVEL_TENTH = 10;
	
	// Achievement ID
	public static final int LEVEL1_ACHIEVEMENT_ID = 10108;
	public static final int LEVEL5_ACHIEVEMENT_ID = 10109;
	public static final int LEVEL10_ACHIEVEMENT_ID = 10107;
}
