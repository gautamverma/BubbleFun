/*
 * Locks should not change in Java
 * */
package game.tetris;

/* Now with the Integration with the Skiller page this will not be 
 * our main game Activity. */
import game.tetris.screen.LoadingScreen;
import game.tetris.util.AppConst;
import game.tetris.util.GameUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
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
	
	float dialogTimeStamp = 0.0f;
	
	boolean logged;
	boolean savedGame;
	boolean playingTournament;
	boolean achievementOpened;
	boolean renderingThreadBlocked;

	final Integer simpleLock = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logged = false;
		savedGame = false;
		playingTournament = false;
		renderingThreadBlocked = false;
		if (haveInternet()) {
			getSKApplication().login(tetris,
					getWindowManager().getDefaultDisplay().getWidth(),
					getWindowManager().getDefaultDisplay().getHeight(), null,
					null, loginListener);
		} else {
			showNoConnectionDialog();
		}
	}

	@Override
	public void onResume() {
		Log.i("Tetris",
				"**********************************On Resume*******************************");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i("Tetris",
				"***********************************On Pause*******************************");
		super.onPause();
	}

	@Override
	public boolean login() {
		logged = false;
		if (haveInternet()) {
			getHandler().post(new Runnable() {
				public void run() {
					getSKApplication().login(tetris,
							getWindowManager().getDefaultDisplay().getWidth(),
							getWindowManager().getDefaultDisplay().getHeight(),
							null, null, loginListener);
				}
			});
			blockThreadUsingSimpleLock();
		} else {
			getHandler().post(new Runnable() {
				public void run() {
					showNoConnectionToast();
				}
			});
		}
		return logged;
	}

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
		if (logged) {
			getSKApplication().getUIManager().showScreen(this,
					SKUIManager.LEADERBOARD_SINGLEPLAYER);
		} else {
			Log.i("Click", "Leader Bard");
			getHandler().post(new Runnable() {
				public void run() {
					showUserNotLoggedToast();
				}
			});
		}
	}

	@Override
	public void openDashBoard() {
		if (logged) {
			getSKApplication().getUIManager().showScreen(this,
					SKUIManager.DASHBOARD);
		} else {
			getHandler().post(new Runnable() {
				public void run() {
					showUserNotLoggedToast();
				}
			});
		}
	}

	@Override
	public void startPracticeGame() {
		getHandler().post(new Runnable() {
			public void run() {
				getSKApplication().getGameManager().getSinglePlayerTools()
						.startPractice(null);
			}
		});
	}

	@Override
	public void openCoinStore() {
		if (logged) {
			getSKApplication().getUIManager().showScreen(this,
					SKUIManager.COINS_STORE);
		} else {
			getHandler().post(new Runnable() {
				public void run() {
					showUserNotLoggedToast();
				}
			});
		}
	}

	public boolean openAchievement(final int achievementID) {
		achievementOpened = false;
		if (logged) {
			getHandler().post(new Runnable() {
				public void run() {
					getSKApplication().getGameManager().unlockAchievement(
							achievementID, achievementListener);
				}
			});
			blockThreadUsingSimpleLock();
		}
		return achievementOpened;
	}

	@Override
	public boolean isTournamentMatch() {
		return playingTournament;
	}

	@Override
	public boolean openTournament() {
		playingTournament = false;
		if (logged) {
			getHandler().post(new Runnable() {
				public void run() {
					Log.i("On UI Thread", "Opening Tournament Screen");
					renderingThreadBlocked = true;
					getSKApplication().getUIManager()
							.showSinglePlayerTournamentsScreen(tetris,
									tournamentChoiceListener);
				}
			});
			blockThreadUsingSimpleLock();
		} else {
			getHandler().post(new Runnable() {
				public void run() {
					showUserNotLoggedToast();
				}
			});
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
			}
		});
		blockThreadUsingSimpleLock();
	}

	@Override
	public void showStandAlonePracticeGameToast() {
		getHandler().post(new Runnable() {
			public void run() {
				showPracticeGameToast();
				notifyThreadsWaitingOnSimpleLock();

			}
		});
		blockThreadUsingSimpleLock();
	}

	/***********************************************************************/
	// All Listener of Activities

	// Include this on Design Flow too
	SKBaseListener loginListener = new SKBaseListener() {
		@Override
		public void onResponse(SKBaseResponse arg0) {
			// got Response
			if (arg0.getStatusCode() == 0) {
				logged = true;
			}
			notifyThreadsWaitingOnSimpleLock();
		}
	};

	SKBaseListener achievementListener = new SKBaseListener() {
		@Override
		public void onResponse(SKBaseResponse arg0) {
			// got Response
			if (arg0.getStatusCode() == 0) {
				achievementOpened = true;
			}
			notifyThreadsWaitingOnSimpleLock();
		}
	};

	SKOnTournamentChosenListener tournamentChoiceListener = new SKOnTournamentChosenListener() {
		@Override
		public void onResponse(SKTournamentChosenResponse st) {
			if (st.getStatusCode() == 0) {
				getSKApplication()
						.getGameManager()
						.getSinglePlayerTools()
						.joinTournament(st.getTournamentId(),
								tournamentJoiningListener);
			} else {
				// if answer is no
				renderingThreadBlocked = false;
				notifyThreadsWaitingOnSimpleLock();
			}
		}
	};

	SKOnJoinTournamentListener tournamentJoiningListener = new SKOnJoinTournamentListener() {
		@Override
		public void onResponse(SKJoinTournamentResponse st) {
			if (st.getStatusCode() == 0) {
				// status OK
				setGameID(st.getGameId());
				setTourID(st.getTourId());
				playingTournament = true;
			} else {
				// status ERROR
				playingTournament = false;
			}
			renderingThreadBlocked = false;
			notifyThreadsWaitingOnSimpleLock();
		}
	};

	SKOnEndTournamentGameListener endTournamentListener = new SKOnEndTournamentGameListener() {
		@Override
		public void onResponse(SKEndTournamentGameResponse tournamentEndResponse) {
			if (tournamentEndResponse.getStatusCode() == 0) {
				StringBuilder message = new StringBuilder();
				if (tournamentEndResponse.isLeader()) {
					message.append(getString(R.string.tournament_winner));
				} else
					message.append(getString(R.string.tournament_loser));

				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						tetris);
				dialogBuilder.setMessage(message.toString())
						.setCancelable(false)
						.setNegativeButton("OK", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								notifyThreadsWaitingOnSimpleLock();
							}
						});
				AlertDialog alertDialog = dialogBuilder.create();
				alertDialog.show();
			}
		}
	};

	/***********************************************************************/

	private void setTourID(String tourID) {
		this.tourID = tourID;
	}

	private void setGameID(String gameID) {
		this.gameID = gameID;
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

	@Override
	public boolean islogged() {
		return logged;
	}

	protected boolean haveInternet() {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) tetris.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

		if (info == null || !info.isConnected()) {
			return false;
		}
		if (info.isRoaming()) {
			// here is the roaming option you can change it if you want to
			// To disable Internet while roaming, just return false
			return true;
		}
		// To deal with Limited Connectivity Issue
		if (info.isConnected())
			return true;
		return false;
	}

	protected void showNoConnectionDialog() {
		final Tetris fTetris = this;
		AlertDialog.Builder builder = new AlertDialog.Builder(fTetris);
		builder.setCancelable(true);
		builder.setMessage(R.string.no_connection_message);
		builder.setTitle(R.string.no_connection_title);
		builder.setPositiveButton(R.string.settings,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						fTetris.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});
		builder.show();
	}

	protected void showPracticeGameToast() {
		Toast noConnectionToast = Toast.makeText(tetris,
				R.string.practice_game, Toast.LENGTH_SHORT);
		noConnectionToast.show();
	}

	protected void showNoConnectionToast() {
		float currTime = GameUtil.nanoToSec(System.nanoTime());
		if((currTime+AppConst.TOAST_APPERANCE_INTERVAL)>dialogTimeStamp) {
			Toast noConnectionToast = Toast.makeText(tetris,
				R.string.no_connection_active, Toast.LENGTH_SHORT);
			noConnectionToast.show();
			dialogTimeStamp = GameUtil.nanoToSec(System.nanoTime());
		}
	}

	protected void showUserNotLoggedToast() {
		float currTime = GameUtil.nanoToSec(System.nanoTime());
		if((currTime+AppConst.TOAST_APPERANCE_INTERVAL)>dialogTimeStamp) {
			Toast userNotLoggedToast = Toast.makeText(tetris,
					R.string.no_user_logged, Toast.LENGTH_SHORT);
			userNotLoggedToast.show();
			dialogTimeStamp = GameUtil.nanoToSec(System.nanoTime());
		}
	}

	protected void blockThreadUsingSimpleLock() {
		synchronized (simpleLock) {
			try {
				simpleLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	protected void notifyThreadsWaitingOnSimpleLock() {
		synchronized (simpleLock) {
			simpleLock.notifyAll();
		}
	}

	/**
	 * Unblocks Rendering thread if it is blocked and changes the Flag
	 * */
	@Override
	public void unblockThreadBlocked() {
		if (renderingThreadBlocked) {
			renderingThreadBlocked = false;
			notifyThreadsWaitingOnSimpleLock();
		}
	}
}