/**
 * 
 */
package com.game;

/**
 * @author Gautam
 * @since 17/10/2011  dd/mm/yyyy
 *
 */
public abstract class Screen {

	/* In this way is accessible to all inherited classes and we can work on low level*/
	protected final Game game;
	
	public Screen(Game game){
		this.game = game;
	}
	
	/* First the update will called by which Screen will updated itself
	 * and then the present method will be called so screen can present itself. */
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
}
