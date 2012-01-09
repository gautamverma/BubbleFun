/*
 * Locks should not change in Java
 * */
package game.tetris;

/* Now with the Integration with the Skiller page this will not be 
 * our main game Activity. */
import game.tetris.screen.LoadingScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.game.Screen;
import com.game.impl.AndroidGame;
import com.skiller.api.listeners.SKBaseListener;
import com.skiller.api.listeners.SKOnEndTournamentGameListener;
import com.skiller.api.listeners.SKOnJoinTournamentListener;
import com.skiller.api.listeners.SKOnTournamentChosenListener;
import com.skiller.api.operations.SKUIManager;
import com.skiller.api.responses.SKBaseResponse;
import com.skiller.api.responses.SKEndTournamentGameResponse;
import com.skiller.api.responses.SKJoinTournamentResponse;
import com.skiller.api.responses.SKTournamentChosenResponse;

public class Tetris extends AndroidGame {

	public Tetris tetris = this;

	String gameID;
	String tourID;
	boolean savedGame;
	boolean playingTournament;
	boolean achievementOpened;

	final Integer simpleLock = 0;
	final Integer tournamentLock = 1;
	JoinTournamentListener tournamentListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		playingTournament = false;
		tournamentListener = new JoinTournamentListener();
		getSKApplication().login(tetris,
				getWindowManager().getDefaultDisplay().getWidth(),
				getWindowManager().getDefaultDisplay().getHeight(), null, null,
				null);	
	}

	@Override
	public void login() {
		getHandler().post( new Runnable() {
			public void run() {
				getSKApplication().login(tetris,
						getWindowManager().getDefaultDisplay().getWidth(),
						getWindowManager().getDefaultDisplay().getHeight(), null, null,
						loginListener);	
			}
		});
		try {
			synchronized(simpleLock) {
				simpleLock.wait();
			}
		}
		catch(InterruptedException ex) {	
		}
	}
	
	SKBaseListener loginListener = new SKBaseListener() {
		@Override
		public void onResponse(SKBaseResponse arg0) {
			// got Response
			if(arg0.getStatusCode()!=0) {
				Toast toast = Toast.makeText(tetris, "Not logged in", Toast.LENGTH_SHORT);
				toast.show();
				}
			synchronized(simpleLock) {
				simpleLock.notifyAll();
			}
		}
	};
	
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

	@Override
	public Context getContext() {
		return this.getApplicationContext();
	}

	@Override
	public void openLeaderBoard() {
		getSKApplication().getUIManager().showScreen(this,
				SKUIManager.LEADERBOARD_SINGLEPLAYER);
	}

	@Override
	public void openDashBoard() {
		getSKApplication().getUIManager().showScreen(this,
				SKUIManager.DASHBOARD);
	}
	
	@Override
	public void openCoinStore() {
		getSKApplication().getUIManager().showScreen(this,
				SKUIManager.COINS_STORE);
	}
	
	public boolean openAchievement(final int achievementID) {
		achievementOpened = false;
		getHandler().post( new Runnable() {
			public void run() {
				getSKApplication().getGameManager().unlockAchievement(achievementID, achievementListener);
			}
		});
		synchronized (simpleLock) {
			try {
				simpleLock.wait();
			} catch (InterruptedException e) {
			}
		}
		return achievementOpened;
	}
	
	SKBaseListener achievementListener = new SKBaseListener() {
		@Override
		public void onResponse(SKBaseResponse arg0) {
			// got Response
			if(arg0.getStatusCode()==0) {
				achievementOpened = true;
				synchronized(simpleLock) {
					simpleLock.notifyAll();
				}
			}
		}
	};
	
	@Override
	public boolean isTournamentMatch() {
		return playingTournament;
	}

	@Override
	public boolean openTournament() {
		getHandler().post(new Runnable() {
			public void run() {
				Log.i("On UI Thread", "Post run");
				getSKApplication().getUIManager()
						.showSinglePlayerTournamentsScreen(tetris,
								tournamentChoiceListener);
			}
		});
		synchronized (tournamentLock) {
			try {
				tournamentLock.wait();
			} catch (InterruptedException e) {
			}
		}
		return playingTournament;
	}

	public void endTournament(final int score, final int level) {
		getHandler().post(new Runnable() {
			public void run() {
				getSKApplication()
						.getGameManager()
						.getSinglePlayerTools()
						.endTournamentGame(tourID, gameID, score, level,
								endTournamentListener);
				clearGame();
			}
		});
		synchronized (tournamentLock) {
			try {
				tournamentLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	SKOnTournamentChosenListener tournamentChoiceListener = new SKOnTournamentChosenListener() {
		@Override
		public void onResponse(SKTournamentChosenResponse st) {
			getSKApplication().getGameManager().getSinglePlayerTools()
					.joinTournament(st.getTournamentId(), tournamentListener);
		}
	};

	class JoinTournamentListener extends SKOnJoinTournamentListener {
		@Override
		public void onResponse(SKJoinTournamentResponse st) {
			Log.i("UI Thread", "Got Response");
			if (st.getStatusCode() == 0) {
				// status OK
				setGameID(st.getGameId());
				setTourID(st.getTourId());
				synchronized (tournamentLock) {
					playingTournament = true;
					tournamentLock.notifyAll();
				}
			} else {
				// status ERROR
				synchronized (tournamentLock) {
					playingTournament = false;
					tournamentLock.notifyAll();
				}
			}
		}
	}

	SKOnEndTournamentGameListener endTournamentListener = new SKOnEndTournamentGameListener() {
		@Override
		public void onResponse(SKEndTournamentGameResponse tournamentEndResponse) {
			if (tournamentEndResponse.getStatusCode() == 0) {
				StringBuilder message = new StringBuilder();
				if (tournamentEndResponse.isLeader()) {
					message.append("Congrats, You stood First!!!");
				} else
					message.append("Oops, you are short of few lines. Try again");

				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						tetris);
				dialogBuilder.setMessage(message.toString())
						.setCancelable(false)
						.setNegativeButton("OK", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								synchronized (tournamentLock) {
									tournamentLock.notifyAll();
								}
							}
						});
				AlertDialog alertDialog = dialogBuilder.create();
				alertDialog.show();
			}
		}
	};

	private void setTourID(String tourId) {
		tourID = tourId;
	}

	private void setGameID(String gameId) {
		gameID = gameId;
	}

	@Override
	public void startSavedGame(boolean b) {
		savedGame = b;
	}
	
	public boolean isSaved() {
		return savedGame;
	}
	
	public void clearGame() {
		gameID = "";
		tourID = "";
		savedGame = false;
		playingTournament = false;
	}
}