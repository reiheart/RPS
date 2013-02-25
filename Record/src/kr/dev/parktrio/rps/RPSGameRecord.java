package kr.dev.parktrio.rps;

enum GameResultState {
	GAME_RESULT_STATE_WIN,
	GAME_RESULT_STATE_DRAW,
	GAME_RESULT_STATE_DEFEAT
}

public class RPSGameRecord {

	private enum RecordState {
		RECORD_STATE_NONE,
		RECORD_STATE_WIN,
		RECORD_STATE_DRAW,
		RECORD_STATE_DEFEAT
	}

	private int win = 0;
	private int draw = 0;
	private int defeat = 0;
	private int combo = 0;
	private int maxCombo = 0;
	private RecordState currentState = RecordState.RECORD_STATE_NONE;

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

	public void setRecord( GameResultState result ) {
		switch ( result ) {
		case GAME_RESULT_STATE_WIN:
			win++;
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

	private void resetCombo() {
		if ( combo > maxCombo ) {
			maxCombo = combo;
		}

		combo = 0;
	}
}
