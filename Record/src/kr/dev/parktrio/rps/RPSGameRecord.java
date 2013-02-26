package kr.dev.parktrio.rps;

enum GameResultState {
	GAME_RESULT_STATE_NONE,
	GAME_RESULT_STATE_WIN,
	GAME_RESULT_STATE_DRAW,
	GAME_RESULT_STATE_DEFEAT
}

public class RPSGameRecord {
	
	private int win = 0;
	private int draw = 0;
	private int defeat = 0;
	private int combo = 0;
	private int maxCombo = 0;

	RPSGameRecord() {}

	RPSGameRecord( int startCombo ) {
		combo = maxCombo = startCombo;
	}

	public int getWin() {
		return win;
	}

	public int getDraw() {
		return draw;
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

	public void adjustGameResult( GameResultState result ) {
		switch ( result ) {
		case GAME_RESULT_STATE_WIN:
			win++;
			increaseCombo();
			break;
		case GAME_RESULT_STATE_DRAW:
			draw++;
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
