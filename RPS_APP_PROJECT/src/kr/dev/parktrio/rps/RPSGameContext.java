package kr.dev.parktrio.rps;

import java.io.Serializable;
import java.util.ArrayList;

public class RPSGameContext implements Serializable {
	private static final long serialVersionUID = -5391535440758549044L;

	public static String propertyKey = "_RPS_GAME_CONTEXT_";

	public enum GameSelectOption
	{
		R,
		P,
		S
	}

	private int maxGameCount;

	private int currentGameCount;

	private RPSGameRecord gameRecord;
	private ArrayList<GameSelectOption> userSelections;
	private ArrayList<GameResultState> gameResults;

	private final RPSRandomUtil resultGenerator;

	public RPSGameContext()
	{
		maxGameCount = 10;
		resultGenerator = new RPSRandomUtil();

		resetGame();
	}

	public void resetGame()
	{
		currentGameCount = 0;

		gameRecord = null;
		gameRecord = new RPSGameRecord();
		if (userSelections == null)
		{
			userSelections = new ArrayList<RPSGameContext.GameSelectOption>();
		}
		else
		{
			userSelections.clear();
		}

		if (gameResults == null)
		{
			gameResults = new ArrayList<GameResultState>();
		}
		else
		{
			gameResults.clear();
		}
	}

	public boolean hasNext()
	{
		return maxGameCount > currentGameCount;
	}

	public GameResultState doGame(GameSelectOption selection)
	{
		GameResultState result = resultGenerator.GenerateGameResult();
		gameRecord.adjustGameResult(result);

		userSelections.add(selection);
		gameResults.add(result);

		if (!result.equals(GameResultState.GAME_RESULT_STATE_DRAW)
				&& !result.equals(GameResultState.GAME_RESULT_STATE_NONE))
		{
			currentGameCount++;
		}

		return result;
	}

	public GameResultState getCurrentGameResult()
	{
		return gameResults.get(gameResults.size() -1);
	}

	public GameSelectOption getCurrentComSelection()
	{
		return getComSelection(gameResults.size() -1);
	}

	private GameSelectOption getComSelection(int gameCount)
	{
		if (gameCount >= userSelections.size()
				|| gameCount >= gameResults.size())
		{
			return null;
		}

		switch (userSelections.get(gameCount))
		{
		case P:
			switch (gameResults.get(gameCount))
			{
			case GAME_RESULT_STATE_DEFEAT:
				return GameSelectOption.S;
			case GAME_RESULT_STATE_DRAW:
				return GameSelectOption.P;
			case GAME_RESULT_STATE_WIN:
				return GameSelectOption.R;
			}
			break;
		case R:
			switch (gameResults.get(gameCount))
			{
			case GAME_RESULT_STATE_DEFEAT:
				return GameSelectOption.P;
			case GAME_RESULT_STATE_DRAW:
				return GameSelectOption.R;
			case GAME_RESULT_STATE_WIN:
				return GameSelectOption.S;
			}
			break;
		case S:
			switch (gameResults.get(gameCount))
			{
			case GAME_RESULT_STATE_DEFEAT:
				return GameSelectOption.R;
			case GAME_RESULT_STATE_DRAW:
				return GameSelectOption.S;
			case GAME_RESULT_STATE_WIN:
				return GameSelectOption.P;
			}
			break;
		}

		return null;
	}

	public String getGameResult() {
		StringBuilder sb = new StringBuilder();

		sb.append(gameRecord.getWin()).append(" ½Â\n");
		sb.append(gameRecord.getDraw()).append(" ¹«\n");
		sb.append(gameRecord.getDefeat()).append(" ÆÐ\n");
		sb.append("Max Combo ").append(gameRecord.getMaxCombo()).append(" ¹ø");

		return sb.toString();
	}

	public int getMaxGameCount() {
		return maxGameCount;
	}

	public void setMaxGameCount(int maxGameCount) {
		this.maxGameCount = maxGameCount;
	}

	public int getCurrentGameCount() {
		return currentGameCount;
	}

	public void setCurrentGameCount(int currentGameCount) {
		this.currentGameCount = currentGameCount;
	}

	public RPSGameRecord getGameRecord() {
		return gameRecord;
	}

	public void setGameRecord(RPSGameRecord gameRecord) {
		this.gameRecord = gameRecord;
	}

	public GameSelectOption[] getUserSelections() {
		GameSelectOption[] ret = new GameSelectOption[0];
		return userSelections.toArray(ret);
	}

	public GameResultState[] getGameResults() {
		GameResultState[] ret = new GameResultState[0];
		return gameResults.toArray(ret);
	}


}
