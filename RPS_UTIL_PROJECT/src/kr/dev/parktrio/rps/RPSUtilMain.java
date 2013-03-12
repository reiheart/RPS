package kr.dev.parktrio.rps;

public class RPSUtilMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RPSRandomUtil rUtil = new RPSRandomUtil(5);
		int win = 0;
		int draw = 0;
		int defeat = 0;
		
		for (int i = 0; i < 100;)
		{
			GameResultState state = rUtil.GenerateGameResult();
			System.out.println(String.format("%s : %d / %d / %d", state.toString(), rUtil.GetWeightOfWin(), rUtil.GetWeightOfDraw(), rUtil.GetWeightOfDefeat()));

			if (state == GameResultState.GAME_RESULT_STATE_DRAW)
			{
				draw++;
				continue;
			}
			
			if (state ==  GameResultState.GAME_RESULT_STATE_WIN) {
				win++;
			}
			else if (state == GameResultState.GAME_RESULT_STATE_DEFEAT) {
				defeat++;
			}

			rUtil.AdjustGameResultWeight(state, 1);
			i++;
		}
		
		System.out.println(String.format("Win(%d) / Draw(%d) / Defeat(%d)", win, draw, defeat)); 
		
		// generate unique random number
		int array[] = new int[10];
		rUtil.GenerateUniqueRandomNumber(array);
		
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(String.format("%d ", array[i]));
		}
	}
}
