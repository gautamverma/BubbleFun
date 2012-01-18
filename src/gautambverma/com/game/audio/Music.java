package gautambverma.com.game.audio;

public interface Music {
	
	void play();
	void stop();
	void pause();
	
	void setLooping(boolean looping);
	void setVolume(float volume);
	
	boolean isLooping();
	boolean isStopped();
	boolean isPaused();
	
	void dispose();
}
