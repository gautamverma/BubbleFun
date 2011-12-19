package game.tetris.util;

/**
 * This class contains all the constants used in the game. So to produce any configuration chnage
 * kindly use this class. 
 * 
 * @author Gautam
 * TODO Break the constants present in this file & Assets into 3 files(For readability.)
 *  1) It will contain each file name used Name of Assets which holds it.
 *  2) Game Screens Constant which is used in Displaying i.e Displaying Constants
 *  3) All Other Constants
 */
public class AppConst {

	public static final int FRAMEBUFFER_WIDTH = 320;
	public static final int FRAMEBUFFER_HEIGHT = 480;
	
	public static final int BLOCK_WIDTH = 15;
	public static final int BLOCK_HEIGHT = 15;
	
	public static final int WRITING_BLOCK_WIDTH = 8;
	public static final int WRITING_BLOCK_HEIGHT = 8;
	
	public static final int SMALLBLOCK_WIDTH = 8;
	public static final int SMALLBLOCK_HEIGHT = 8;
	
	public static final int DIGIT_WIDTH = 7;
	public static final int DIGIT_HEIGHT = 7;
	
	public static final int BIG_DIGIT_WIDTH = 14;
	public static final int BIG_DIGIT_HEIGHT = 14;
	
	public static final int ALPHABET_WIDTH = 36;
	public static final int ALPHABET_HEIGHT = 36;
	
	public static final int ATLASING_START_X = 0;
	public static final int ATLASING_START_Y = 0;
	public static final int ATLASING_OFFSET = 36;
	
	public static final int ORIGIN_X = 0;
	public static final int ORIGIN_Y = 0;
	
	public static final int BTN_WIDTH = 60;
	public static final int BTN_HEIGHT = 60;
	
	public static final int ROTATE_BTN_WIDTH = 80;
	public static final int ROTATE_BTN_HEIGHT = 80;
	
	public static final int LEFT_LIMIT = 0;
	public static final int RIGHT_LIMIT = 13;
	
	public static final float UPDATE_INTERVAL = 0.5f;
	public static final float LEVEL_INTERVAL_DECREASE = 0.05f;
	
	public static final int ONE_LINE_POINT = 100;
	
	public static final int DEFAULT_SCORE = 0;
	
	// At 1 (x,y) coordinate a block can placed 
	public static final int ARENA_GRID_WIDTH = 14;
	public static final int ARENA_GRID_HEIGHT = 26;
	
	// it can take seven blocks of small blocks
	public static final int MAX_LETTER_HEIGHT = 7;
	public static final int LETTER_OFFSET = 2;
	
	// Help Screen Constants
	public static final int HELP1_X = 60;
	public static final int HELP1_Y = 10;
	
	public static final int HELP1_MSG_X = 60;
	public static final int HELP1_MSG_Y = 350;
	
	public static final int HELP2_X = 0;
	public static final int HELP2_Y = 30;
	
	public static final int HELP2_MSG1_X = 60;
	public static final int HELP2_MSG1_Y = 300;
	
	public static final int HELP2_MSG2_X = 45;
	public static final int HELP2_MSG2_Y = 315;
	
	public static final int HELP3_SCENE1_X = 20;
	public static final int HELP3_SCENE1_Y = 20;
	
	public static final int HELP3_SCENE2_X = 180;
	public static final int HELP3_SCENE2_Y = 20;
	
	public static final int HELP3_MSG1_X = 46;
	public static final int HELP3_MSG1_Y = 320;
	
	public static final int HELP3_MSG2_X = 80;
	public static final int HELP3_MSG2_Y = 340;
	
	public static final int TAP_HERE_X = 88;
	public static final int TAP_HERE_Y = 440;
	
	public static final int TAP_HERE_WIDTH = 144;
	public static final int TAP_HERE_HEIGHT = 21;
	
	// Menu Screen Constants
	public static final int MENU_OPTION_WIDTH = 19;
	public static final int MENU_OPTION_HEIGHT = 16;
	
	public static final int MENU_OPTION_SRC_X = 84;
	public static final int MENU_OPTION_SRC_Y = 146;
	
	public static final int SOUND_BUTTON_X = 10;
	public static final int SOUND_BUTTON_Y = 406;
	
	public static final int SOUND_MUTE_X = 0;
	public static final int SOUND_PLAY_X = 64;
	public static final int SOUND_BOTH_Y = 0;
	
	public static final int SOUND_BUTTON_WIDTH = 64;
	public static final int SOUND_BUTTON_HEIGHT = 64;
	
	// Start Screen Constants
	public static final int START_MSG_X = 70;
	public static final int START_MSG_Y = 180;
	
	// Running State Constants 
	public static final int RUNNING_PAUSE_WIDTH = 64;
	public static final int RUNNING_PAUSE_HEIGHT = 64;
	
	// Pause State Constants
	public static final int PAUSE_QUIT_X = 100;
	public static final int PAUSE_QUIT_Y = 195;
	
	public static final int PAUSE_RESUME_X = 100;
	public static final int PAUSE_RESUME_Y = 240;
	
	public static final int PAUSE_MSG_WIDTH = 120; 
	public static final int PAUSE_MEG_HEIGHT = 45;
	
	// Stopped State Constants
	public static final int STOP_TO_MENU_X = 85;
	public static final int STOP_TO_MENU_Y = 214;
	
	public static final int STOP_PLAY_AGAIN_X = 60;
	public static final int STOP_PLAY_AGAIN_Y = 250;
	
	public static final int STOP_MSG_PLAY_AGAIN_WIDTH = 200;
	public static final int STOP_MSG_MENU_WIDTH = 150;
	public static final int STOP_MEG_HEIGHT = 26;
	
	public static final int STOP_SCORE_X = 100;
	public static final int STOP_SCORE_Y = 150;
	
	public static final int STOP_POSITON_X = 60;
	public static final int STOP_POSITON_Y = 150;
	
	public static final int BIG_SCORE_START_X = 2;
	public static final int BIG_SCORE_START_Y = 4;
	
	public static final int BIG_SCORE_JUMP_X = 18;
	
	public static final int BIG_SCORE_WIDTH_JUMP = 15;
			
	// Game Screen Constants
	public static final int BTN_ROTATE_X = 230;
	public static final int BTN_ROTATE_Y = 400;
	
	public static final int BTN_LEFT_X = 0;
	public static final int BTN_LEFT_Y = 410;
	
	public static final int BTN_RIGHT_X = 80;
	public static final int BTN_RIGHT_Y = 410;
	
	public static final int HORIZONTAL_LINE_Y = 390;
	public static final int VERTICAL_LINE_X = 210;
	
	public static final int MSG_NEXT_X = 240;
	public static final int MSG_NEXT_Y = 70;
	
	public static final int MSG_SCORE_X = 235;
	public static final int MSG_SCORE_Y = 290;
	
	public static final int MSG_SCORE_WIDTH = 62;
	
	public static final int NEXT_BOX_HY = 50;
	public static final int NEXT_BOX_Y = 58;
	public static final int NEXT_BOX_LY = 160;
	
	public static final int SCORE_Y = 325;
	
	public static final int SCORE_START_X = 1;
	public static final int SCORE_START_Y = 3;
	
	public static final int SCORE_WIDTH_JUMP = 9;
	
    // Acceleration Value to update 
	public static final int ACCELERATION_CONSTANT = 2;
	// File Names
	public static final String DIGITS = "digits_astrik.png";
	public static final String ALPHABETS = "abcde_36x36_gap36_in_y.png";
	public static final String BLOCK = "block.png";
	public static final String WRITING_BLOCK = "writing_block.png";
	public static final String BACKGROUND = "background.png";
	public static final String SMALL_BLOCK = "smallblock.png";
	
	public static final String SOUNDBUTTON = "soundbuttons.png";
	public static final String BTN_LEFT = "btn_left.png";
	public static final String BTN_RIGHT = "btn_right.png";
	public static final String BTN_ROTATE = "btn_rotate.png";
	
	public static final String NEXT = "next_56x22.png";
	public static final String SCORE = "score_62x24.png";
	
	public static final String QUIT = "quit_120x45.png";
	public static final String RESUME = "resume_120x45.png";
	
	public static final String PLAY_AGAIN = "play_again_200x25.png";
	public static final String TO_MENU = "tomenu_150x26.png";
	
	public static final String NUMBERS = "numbers_90x11.png";
	public static final String NUMBERS_2X = "numbers_180x22.png";
	
	public static final String TAP_TO_START = "tap_to_start_180_19.png";
	public static final String TAP_HERE = "tap_here.png";
	
	public static final String HELP1 = "help1_200x299.png";
	public static final String MSG_HELP1 = "help1_message.png";
	
	public static final String HELP2 = "help2_scene.png";
	public static final String MSG_HELP2_PART1 = "help2_message1.png";
	public static final String MSG_HELP2_PART2 = "help2_message2.png";
	
	public static final String HELP3_SCENE1 = "help3_scene1.png";
	public static final String HELP3_SCENE2 = "help3_scene2.png";
	public static final String MSG_HELP3_PART1 = "help3_message1.png";
	public static final String MSG_HELP3_PART2 = "help3_message2.png";
	
	public static final String MUSIC_CLICK = "click.ogg";
	public static final String MUSIC_ROTATE_BTN = "rotate.mp3";
	public static final String MUSIC_LINE_COMPLETED = "linecompleted.mp3";
	public static final String MUSIC_BLOCK_SETTLED = "blocksettle.mp3";
}
