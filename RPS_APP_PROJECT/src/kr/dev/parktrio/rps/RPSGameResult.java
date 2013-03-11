package kr.dev.parktrio.rps;

import java.io.Serializable;

public class RPSGameResult implements Serializable {
	private static final long serialVersionUID = -5822254359433489515L;

	private int win = 0;
	private int defeat = 0;
	private int combo = 0;
	private int maxCombo = 0;
	private final RPSUserInfo user;

	RPSGameResult() {
		user = RPSUserInfo.getInstance();
	}

	RPSGameResult( int startCombo ) {
		user = RPSUserInfo.getInstance();
		combo = maxCombo = startCombo;
	}

	public int getWin() {
		return win;
	}

	public int getDefeat() {
		return defeat;
	}

	public int getCombo() {
		return combo;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public void resetResult() {
		win = defeat = combo = maxCombo = 0;
	}

	public void adjustGameResult( GameResultState result ) {
		switch ( result ) {
		case GAME_RESULT_STATE_WIN:
			win++;
			increaseCombo();
			break;
		case GAME_RESULT_STATE_DEFEAT:
			defeat++;
			resetCombo();
			break;
		}
	}

	private void increaseCombo() {
		combo++;
		if ( combo > maxCombo ) {
			maxCombo = combo;
		}
	}

	private void resetCombo() {
		if ( combo > maxCombo ) {
			maxCombo = combo;
		}

		combo = 0;
	}

}
