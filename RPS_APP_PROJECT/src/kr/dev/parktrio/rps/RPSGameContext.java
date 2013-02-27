package kr.dev.parktrio.rps;

public class RPSGameContext {
	public enum GameSelectOption
	{
		R,
		P,
		S
	}

	private final int maxGameCount = 10;

	private int currentGameCount;

	private RPSGameRecord gameRecord;
	private GameSelectOption[] userSelections;
	private GameResultState[] gameResults;

	private final RPSRandomUtil resultGenerator;

	public RPSGameContext()
	{
		resultGenerator = new RPSRandomUtil();

		resetGame();
	}

	public void resetGame()
	{
		currentGameCount = 0;

		gameRecord = null;
		gameRecord = new RPSGameRecord();
		userSelections = null;
		userSelections = new GameSelectOption[10];
		gameResults = null;
		gameResults = new GameResultState[10];
	}

	public boolean hasNext()
	{
		return maxGameCount > currentGameCount;
	}

	public GameResultState doGame(GameSelectOption selection)
	{
		GameResultState result = resultGenerator.GenerateGameResult();
		gameRecord.adjustGameResult(result);

		userSelections[currentGameCount] = selection;
		gameResults[currentGameCount] = result;

		currentGameCount++;
		return result;
	}

	public GameResultState getCurrentGameResult()
	{
		return gameResults[currentGameCount - 1];
	}

	public GameSelectOption getCurrentComSelection()
	{
		return getComSelection(currentGameCount - 1);
	}

	public GameSelectOption getComSelection(int gameCount)
	{
		if (gameCount >= maxGameCount)
		{
			return null;
		}

		switch (userSelections[gameCount])
		{
		case P:
			switch (gameResults[gameCount])
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
			switch (gameResults[gameCount])
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
			switch (gameResults[gameCount])
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
		return userSelections;
	}

	public void setUserSelections(GameSelectOption[] userSelections) {
		this.userSelections = userSelections;
	}

	public GameResultState[] getGameResults() {
		return gameResults;
	}

	public void setGameResults(GameResultState[] gameResults) {
		this.gameResults = gameResults;
	}


}
