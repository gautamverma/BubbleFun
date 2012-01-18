package gautambverma.game.tetris.logic;

import gautambverma.game.tetris.util.AppConst;

import java.util.Random;

/*
 * This class represent one object used in the game.
 *  It has an array to hold 4 coordinates.
 *  
 *  1 Assumption -- We are rotating it about it's 3rd Coordinate, we have fill the starting values 
 *  				 accordingly 
 * */
public class Shape {
	
	enum Direction { DOWN, LEFT, RIGHT }
	Direction direction;
	
	enum ShapeType { L, INVERTEDL, T, LINE, SQUARE }
	static Random random = new Random();
	
	public int[][] coordinate;
	ShapeType type;
	public int colour;
	
	Shape() {
		direction = Direction.DOWN;
		type = getType(random.nextInt(5));
		colour = random.nextInt(5) + 1;
		coordinate = getEnteringCoordinate(getIntOnType(type));
	}
	
	public static Shape getInstance() {		
		return new Shape();		
	}

	/*
	 * It move the shape 1 unit DOWN, done with the user call.
	 * That is repeatdly
	 * */
	public void update() {
		for(int i = 0; i<coordinate.length; i++) {
			coordinate[i][1]++;
			}
		clearDirection();
	}
	
	/*
	 * It uses rotational matrix with theta=90 
	 *      				0 -1
	 * 						1  0
	 * */
	public int[][] rotate() {
		
		// Done to avoid translation of square block due to rotation
		if(type==ShapeType.SQUARE) return null;
		
		int[][] transformationCoordinates = new int[coordinate.length][];
		for(int i = 0; i<transformationCoordinates.length; i++) {
			transformationCoordinates[i] = new int[coordinate[i].length];
			for(int j = 0; j<transformationCoordinates[i].length; j++) {
				transformationCoordinates[i][j] = coordinate[i][j]; 
			}
		}
		// Translate it to Origin rotate it by 90 anti-clockwise and re-translate
		int tx = transformationCoordinates[2][0]; int ty = transformationCoordinates[2][1];
		
		int[][] rotationalMatrix = { { 0, 1},
									 { -1, 0} };
		
		int dx = 0, dy = 0;
		int deltaX = 0, deltaY=0;
		for(int i = 0; i<transformationCoordinates.length; i++) {
			transformationCoordinates[i][0] -= tx; transformationCoordinates[i][1] -= ty;
			
			int x = transformationCoordinates[i][0]; int y = transformationCoordinates[i][1];
			// Simplified as theta is 90
			transformationCoordinates[i][0] = rotationalMatrix[0][1]*y;
			transformationCoordinates[i][1] = rotationalMatrix[1][0]*x;
			
			transformationCoordinates[i][0] += tx; transformationCoordinates[i][1] += ty;
			if(transformationCoordinates[i][0]<dx) dx = transformationCoordinates[i][0];
			if(transformationCoordinates[i][1]<dy) dy = transformationCoordinates[i][1];
		}
		
		// In case some coordinates get outside of frame buffer
		for(int i = 0; i<transformationCoordinates.length; i++) {
			// out from up and left
			transformationCoordinates[i][0] += (-dx); transformationCoordinates[i][1] += (-dy);
			// out from right and below
			if(deltaX < (transformationCoordinates[i][0]-(AppConst.ARENA_GRID_WIDTH-1))) deltaX = transformationCoordinates[i][0]-(AppConst.ARENA_GRID_WIDTH-1);
			if(deltaY < (transformationCoordinates[i][1]-(AppConst.ARENA_GRID_HEIGHT-1))) deltaY = transformationCoordinates[i][1]-(AppConst.ARENA_GRID_HEIGHT-1); 
		}
		
		for(int i = 0; i<transformationCoordinates.length; i++) {
			transformationCoordinates[i][0] -= deltaX; transformationCoordinates[i][1] -= deltaY;
		}
		return transformationCoordinates;
	}
	
	
	private int[][] getEnteringCoordinate(int i) {
		int[][] position = new int[4][2];
		switch(i) {
		case 0:
			position[0][0] = 5; position[0][1] = 0; position[1][0] = 5; position[1][1] = 1;
			position[2][0] = 5; position[2][1] = 2; position[3][0] = 6; position[3][1] = 2;
			break;
		case 1:
			position[0][0] = 5; position[0][1] = 0; position[1][0] = 5; position[1][1] = 1;
			position[2][0] = 5; position[2][1] = 2; position[3][0] = 4; position[3][1] = 2;
			break;
		case 2:
			position[0][0] = 4; position[0][1] = 0; position[1][0] = 6; position[1][1] = 0;
			position[2][0] = 5; position[2][1] = 0; position[3][0] = 5; position[3][1] = 1;
			break;
		case 3:
			position[0][0] = 4; position[0][1] = 0; position[1][0] = 6; position[1][1] = 0;
			position[2][0] = 5; position[2][1] = 0; position[3][0] = 7; position[3][1] = 0;
			break;
		case 4:
			position[0][0] = 5; position[0][1] = 0;	position[1][0] = 6; position[1][1] = 0;
			position[2][0] = 5; position[2][1] = 1;	position[3][0] = 6; position[3][1] = 1;
			break;
		default:
			position[0][0] = 5; position[0][1] = 0;	position[1][0] = 6; position[1][1] = 0;
			position[2][0] = 5; position[2][1] = 1;	position[3][0] = 6; position[3][1] = 1;
		}
		return position;
	}
		



	public void setCoordinate(int[][] coordinate) {
		this.coordinate = coordinate;
	}
	
	/* Avoid writing switch without breaks */
	public static ShapeType getType(int i) {
		ShapeType type;
		
		switch(i) {
		case 0: type = ShapeType.L;
				break;
		case 1: type = ShapeType.INVERTEDL;
				break;
		case 2: type = ShapeType.T;
				break;
		case 3: type = ShapeType.LINE;
				break;
		case 4: type = ShapeType.SQUARE;
				break;
		default:type = ShapeType.LINE;
		}
		
		return type;
	}
	
	/* A Utility Method of its must be static as gets all
	 * need info and does not depend on object calling it*/
	public static int getIntOnType(ShapeType type) {
		
		if( type==ShapeType.L) return 0;
		if( type==ShapeType.INVERTEDL) return 1;
		if( type==ShapeType.T) return 2;
		if( type==ShapeType.LINE) return 3;
		if( type==ShapeType.SQUARE) return 4;
		return 3;
	}
	
	void moveLeft() {
		for(int i = 0; i<coordinate.length; i++) {
			coordinate[i][0]--;
		}
	}
	
	void moveRight() {
		for(int i = 0; i<coordinate.length; i++) {
			coordinate[i][0]++;
		}
	}
	
	void clearDirection() {
		direction = Direction.DOWN;
	}

	public int getX() {
		int UL_X = AppConst.ARENA_GRID_WIDTH;
		for(int i = 0; i<coordinate.length; i++) {
			if(coordinate[i][0]<UL_X) UL_X = coordinate[i][0]; 
		}
		return UL_X*AppConst.BLOCK_WIDTH;
	}

	public int getY() {
		int UL_Y = AppConst.ARENA_GRID_HEIGHT;
		for(int i = 0; i<coordinate.length; i++) {
			if(coordinate[i][1]<UL_Y) UL_Y = coordinate[i][1]; 
		}
		return UL_Y*AppConst.BLOCK_HEIGHT;
	}
	
	public int getWidth() {
		if( type==ShapeType.SQUARE) return AppConst.BLOCK_WIDTH*2;
		else {
			int diff = 0;
			for(int i = 0; i<coordinate.length; i++) {
				for(int j = i+1; j<coordinate.length; j++) {
					if(Math.abs(coordinate[i][0]-coordinate[j][0])>diff)
						diff = Math.abs(coordinate[i][0]-coordinate[j][0]);
				}
			}
			return (diff+1)*AppConst.BLOCK_WIDTH;
		}
	}
	
	public int getHeight() {
		if( type==ShapeType.SQUARE) return AppConst.BLOCK_HEIGHT*2;
		else {
			int diff = 0;
			for(int i = 0; i<coordinate.length; i++) {
				for(int j = i+1; j<coordinate.length; j++) {
					if(Math.abs(coordinate[i][1]-coordinate[j][1])>diff)
						diff = Math.abs(coordinate[i][1]-coordinate[j][1]);
				}
			}
			return (diff+1)*AppConst.BLOCK_HEIGHT;
		}
	}

	public void updateCoordinates(int[][] newCoordinates) {
		for(int i = 0; i<newCoordinates.length; i++) {
			for(int j = 0; j<newCoordinates[i].length; j++) {
				coordinate[i][j] = newCoordinates[i][j];
			}
		}
	}
}
