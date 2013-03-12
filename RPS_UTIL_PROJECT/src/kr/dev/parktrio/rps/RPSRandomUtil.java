package kr.dev.parktrio.rps;

import java.io.Serializable;


public class RPSRandomUtil implements Serializable {
	private static final long serialVersionUID = 6056691123543462617L;

	static final int TOTAL_WEIGHT = 99;
	static final int DEFAULT_WEIGHT = 33;
	static final int MAX_WEIGHT = 70;
	static final int MAX_LEVEL = 100;

	private int weightOfWin = DEFAULT_WEIGHT;
	private int weightOfDraw = DEFAULT_WEIGHT;
	private int weightOfDefeat = DEFAULT_WEIGHT;

	RPSRandomUtil(int level) {

		int weightOfLevel = (MAX_LEVEL - level) / 10;

		weightOfWin = DEFAULT_WEIGHT + weightOfLevel;
		weightOfDraw = DEFAULT_WEIGHT - weightOfLevel;
		weightOfDefeat = DEFAULT_WEIGHT;
	}

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

	public void GenerateUniqueRandomNumber(int array[]) {

		int length = array.length;
		int random;
		int[] temp = new int[1];

		for (int i = 0; i < length; i++)
		{
			array[i] = i;
		}

		for (int i = 0; i < length; i++)
		{
			random = (int)(Math.random() * 1000) % length;

			// swap array
			temp[0] = array[i];
			array[i] = array[random];
			array[random] = temp[0];
		}
	}

	public void AdjustGameResultWeight(GameResultState state, int weight) {

		if (state ==  GameResultState.GAME_RESULT_STATE_WIN) {

			if (weightOfWin + weight > MAX_WEIGHT)
				return;

			weightOfWin += weight;
			weightOfDefeat -= weight;
			
			if (weightOfDefeat < 0) {
				weightOfDraw += weightOfDefeat;
				weightOfDefeat = 0;
			}
			if (weightOfDraw < 0) {
				weightOfWin += weightOfDraw;
				weightOfDraw = 0;
			}
		}
		else if (state == GameResultState.GAME_RESULT_STATE_DRAW) {

			if (weightOfDraw + weight > MAX_WEIGHT)
				return;

			weightOfDraw += weight;

			int pivot = weight / 2;
			weightOfWin -= pivot;
			weightOfDefeat -= (weight - pivot);
			
			if (weightOfWin < 0) {
				weightOfDefeat += weightOfWin;
				weightOfWin = 0;
			}
			if (weightOfDefeat < 0) {
				weightOfDraw += weightOfDefeat;
				weightOfDefeat = 0;
			}
		}
		else if (state == GameResultState.GAME_RESULT_STATE_DEFEAT) {

			if (weightOfDefeat + weight > MAX_WEIGHT)
				return;

			weightOfDefeat += weight;
			weightOfWin -= weight;
			
			if (weightOfWin < 0) {
				weightOfDraw += weightOfWin;
				weightOfWin = 0;
			}
			if (weightOfDraw < 0) {
				weightOfDefeat += weightOfDraw;
				weightOfDraw = 0;
			}
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
