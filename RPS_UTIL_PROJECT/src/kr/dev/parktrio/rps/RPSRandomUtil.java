package kr.dev.parktrio.rps;

import java.util.Random;

enum GameResultState {
	GAME_RESULT_STATE_WIN,
	GAME_RESULT_STATE_DRAW,
	GAME_RESULT_STATE_DEFEAT
}

public class RPSRandomUtil {

	static final int TOTAL_WEIGHT = 99;
	static final int DEFAULT_WEIGHT = 33;
	static final int MAX_WEIGHT = 50;

	private int weightOfWin = DEFAULT_WEIGHT;
	private int weightOfDraw = DEFAULT_WEIGHT;
	private int weightOfDefeat = DEFAULT_WEIGHT;

	RPSRandomUtil() {}

	public int GetWeightOfWin() {
		return weightOfWin;
	}

	public void SetWeightOfWin(int weight) {
		weightOfWin = weight;
	}

	public int GetWeightOfDraw() {
		return weightOfDraw;
	}

	public void SetWeightOfDraw(int weight) {
		weightOfDraw = weight;
	}

	public int GetWeightOfDefeat() {
		return weightOfDefeat;
	}

	public void SetWeightOfDefeat(int weight) {
		weightOfDefeat = weight;
	}

	public GameResultState GenerateGameResult() {
		
		/* Game range : 0 ~ 98 */

		return GetGameResultState((int)(Math.random() * 1000) % (TOTAL_WEIGHT-1));
	}
	
	public void AdjustGameResultWeight(GameResultState state, int weight) {
		
		int pivot = TOTAL_WEIGHT;
		
		if (state ==  GameResultState.GAME_RESULT_STATE_WIN) {
			
			if (weightOfWin + weight > MAX_WEIGHT)
				return;
			
			weightOfWin += weight;

			pivot -= weightOfWin;
			weightOfDraw = pivot / 2;
			weightOfDefeat = pivot - weightOfDraw;
		}
		else if (state == GameResultState.GAME_RESULT_STATE_DRAW) {
			
			if (weightOfDraw + weight > MAX_WEIGHT)
				return;
			
			weightOfDraw += weight;
			
			pivot -= weightOfDraw;
			weightOfWin = pivot / 2;
			weightOfDefeat = pivot - weightOfWin;
		}
		else { /* (state == GameResultState.GAME_RESULT_STATE_DEFEAT) */
			
			if (weightOfDefeat + weight > MAX_WEIGHT)
				return;
			
			weightOfDefeat += weight;
			
			pivot -= weightOfDefeat;
			weightOfDraw = pivot / 2;
			weightOfWin = pivot - weightOfDraw;
		}
	}
	
	private GameResultState GetGameResultState(int randomNumber) {

		/*
		 * Win range : 0 ~ weightOfWin-1
		 * Draw range : weightOfWin ~ weightOfDraw-1
		 * Defeat range : weightOfDraw ~ TOTAL_WEIGHT-1
		 */

		GameResultState result;

		if ((randomNumber >= 0) && (randomNumber < weightOfWin)) {
			result = GameResultState.GAME_RESULT_STATE_WIN;
		}
		else if ((randomNumber >= weightOfWin) && (randomNumber < weightOfWin + weightOfDraw)) {
			result = GameResultState.GAME_RESULT_STATE_DRAW;
		}
		else {
			result = GameResultState.GAME_RESULT_STATE_DEFEAT;
		}

		return result;
	}
}
