/*
 * Remember a point of the these game that screen x is lower variable of the array i.e columns and y is 
 * higher variable ie rows of the screen area.
 * */
package game.tetris.logic;

import android.util.Log;
import game.tetris.logic.Shape;
import game.tetris.util.AppConst;
import game.tetris.util.Assets;

/*
 * This is the main class of the Game. It will update game based on the UserInput events.
 * It will contain the whole arena and the shape falling.
 * It will check for the line completion event and  game over conditions.
 * 
 * @author Gautam
 * */
public class Arena {
	
	public Shape shape;
	public Shape nextShape;
	
	public boolean shapePlaced;
	float accumulator = 0.0f;
	
	public boolean[][] gameArea;
	public int score = AppConst.DEFAULT_SCORE;
	public int level = AppConst.INITIAL_LEVEL;
	public int lines = AppConst.DEFAULT_LINES_COMPLETED;
	
	public float UPDATE_INTERVAL = AppConst.STARTING_UPDATE_INTERVAL;
	
	public Arena() {
		shapePlaced = false;
		gameArea = new boolean[AppConst.ARENA_GRID_HEIGHT][AppConst.ARENA_GRID_WIDTH];
		
		for(int i = 0; i<gameArea.length; i++) {
			for(int j = 0; j<gameArea[i].length; j++)
				gameArea[i][j] = false;
		}
		
		nextShape = Shape.getInstance();
		getShape();
	}
	
	/* NextShape is already initialized So it is given to the shape and it set to
	 * a new Shape and this invariant holds true for all remaining loops.         */
	void getShape() {
		shape = nextShape;
		nextShape = null;
		nextShape = Shape.getInstance();
		
		Log.d("getShape", Shape.getIntOnType(shape.type)+" : "+Shape.getIntOnType(nextShape.type));
	}
	
	public void rotate() {
		int[][] newCoordinates = shape.rotate();
		if(newCoordinates!=null) {
			if(isCoordinatesEmpty(newCoordinates))
				shape.updateCoordinates(newCoordinates);
		}
	}
	
	protected boolean isCoordinatesEmpty(int[][] newCoordinates) {
		
		for(int i = 0; i<newCoordinates.length; i++) {
				if(gameArea[newCoordinates[i][1]][newCoordinates[i][0]]) return false;
		}
		return true;
	}

	public void update(float time) {
		accumulator += time;
		while(accumulator > UPDATE_INTERVAL) {
			if(isEmpty()) shape.update();
			else {
				if(Settings.soundEnabled)
					Assets.blockSettled.play(1);
				placeShape();           /* Placing the shape as it can't move anymore down */
				
				boolean callAgain = true;
				while( callAgain ) {
					callAgain = clearCompletedLine();  /* Checking if any line is completed*/
				}
				
				getShape();             /* Getting a new shape to continue the Game        */
				}
			accumulator -= UPDATE_INTERVAL;
		}
	}
	
	/*
	 * It only checks empty downwards as it can move still if it is empty blocks lie below
	 * */
	boolean isEmpty() {
		Log.d("is Empty", "Started");
		
		
		int[][] xy = shape.coordinate;
		System.out.println(xy);
		
		int ll = AppConst.ARENA_GRID_HEIGHT;
		if(xy[0][1]>=(ll-1) || xy[1][1]>=(ll-1) || xy[2][1]>=(ll-1) || xy[3][1]>=(ll-1)) return false;
		
		
		for(int i = 0; i<xy.length; i++) {
			if(gameArea[xy[i][1]+1][xy[i][0]]) return false;
		}
		
		return true;
	}
	
	/*
	 * It will return true if it found 1 completed line, otherwise false.
	 *  if its true then we can call it again to check for more lines
	 * */
	boolean clearCompletedLine() {
		boolean isFound = false;
		
		int i = AppConst.ARENA_GRID_HEIGHT-1;
		while(i>-1 && !isFound) {
			isFound = true;
		 
			for(int j = 0; j < AppConst.ARENA_GRID_WIDTH; j++) {
				if(!gameArea[i][j]) isFound = false;
			}
			if(!isFound) i--;
		}
		
		if(i!=-1) {
			// Erase the Completed line and bring every other drawn block x a unit down. (i.e increase y value)
			// Where x is equal to the number of the empty below the column
			if(Settings.soundEnabled)
				Assets.lineCompleted.play(1);
			for(int j = 0; j<AppConst.ARENA_GRID_WIDTH; j++) {
				gameArea[i][j] = false;
			}
			
			for( int k = 0; k<AppConst.ARENA_GRID_WIDTH; k++) {
					
				int emptyBlocks = 0;
				int l = i;
				while(l>-1 &&  !gameArea[l][k]) 
					l--;
				// Atleast one block is present
				if(l!=-1) {
					emptyBlocks += (i-l);
					int h = i;
					while(h<AppConst.ARENA_GRID_HEIGHT && !gameArea[h][k]) {
						emptyBlocks++;
						h++;
						}
					for(int j = l; j>-1; j--) {
						gameArea[j+(emptyBlocks-1)][k] = gameArea[j][k];
						gameArea[j][k] = false;
					}
				}
			}
			score += AppConst.ONE_LINE_POINTS;
			lines++;
			return true;
		}
		return false;
	}
	
	// Revised Once
	// Check from 4 to 7 becoz these are entry points of that Block
	public boolean gameOver() {
		// Right now it checks first row and if it filled it assumpes the game over
		for(int i = AppConst.GAME_OVER_MINX; i<=AppConst.GAME_OVER_MAXX; i++) {
			if(gameArea[AppConst.ORIGIN_Y][i]) return true;
		}
		return false;
	}
	
	void placeShape() {
		/* This will ensure that next shape will not be come down */
		shapePlaced = true;
		// Now Shape can't move so place in the gameArea Buffer
		for(int i = 0; i<shape.coordinate.length; i++) {
			gameArea[shape.coordinate[i][1]][shape.coordinate[i][0]] = true;
		}
	}
	
	/* The Next shape is drawn out of the game area so these will return absolute values
	 * TODO try to get these type of methods in one class as the configuration issues */
	public int[][] getNextShapeCoordinate() {
		int[][] position = new int[4][2];
		switch(Shape.getIntOnType(nextShape.type)) {
		case 0:
			position[0][0] = 255; position[0][1] = 95; position[1][0] = 255; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 125; position[3][0] = 270; position[3][1] = 125;
			break;
		case 1:
			position[0][0] = 255; position[0][1] = 95; position[1][0] = 255; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 125; position[3][0] = 240; position[3][1] = 125;
			break;
		case 2:
			position[0][0] = 240; position[0][1] = 110; position[1][0] = 270; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 110; position[3][0] = 255; position[3][1] = 125;
			break;
		case 3:
			position[0][0] = 240; position[0][1] = 110; position[1][0] = 270; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 110; position[3][0] = 285; position[3][1] = 110;
			break;
		case 4:
			position[0][0] = 255; position[0][1] = 110;	position[1][0] = 270; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 125;	position[3][0] = 270; position[3][1] = 125;
			break;
		default:
			position[0][0] = 240; position[0][1] = 110; position[1][0] = 270; position[1][1] = 110;
			position[2][0] = 255; position[2][1] = 110; position[3][0] = 285; position[3][1] = 110;
		}
		return position;
	}
	
	public void moveLeft() {
		int[][] coordinate = shape.coordinate;
		if(coordinate[0][0]==AppConst.LEFT_LIMIT || coordinate[1][0]==AppConst.LEFT_LIMIT
				|| coordinate[2][0]==AppConst.LEFT_LIMIT || coordinate[3][0]==AppConst.LEFT_LIMIT )
			clearDirection();
		else {
			boolean moveLeft = true;
			for(int i=0; i<shape.coordinate.length; i++) {
				if(gameArea[coordinate[i][1]][coordinate[i][0]-1]) moveLeft = false;
			}
			if(moveLeft) shape.moveLeft();
		}
	}
	public void moveRight() {
		int[][] coordinate = shape.coordinate;
		if(coordinate[0][0]==AppConst.RIGHT_LIMIT || coordinate[1][0]==AppConst.RIGHT_LIMIT 
				|| coordinate[2][0]==AppConst.RIGHT_LIMIT || coordinate[3][0]==AppConst.RIGHT_LIMIT )
			clearDirection();
		else {
			boolean moveRight = true;
			for(int i=0; i<shape.coordinate.length; i++) {
				if(gameArea[coordinate[i][1]][coordinate[i][0]+1]) moveRight = false;
			}
			if(moveRight) shape.moveRight();
		} 
	}
	
	void clearDirection() { shape.clearDirection(); }	
}
