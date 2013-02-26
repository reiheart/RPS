package kr.dev.parktrio.rps;

public class RPSUtilMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RPSRandomUtil rUtil = new RPSRandomUtil();
		
		for (int i = 0; i < 100; i++)
		{
			GameResultState state = rUtil.GenerateGameResult();
			System.out.println((String.format("%d, %d, %d", rUtil.GetWeightOfWin(), rUtil.GetWeightOfDraw(), rUtil.GetWeightOfDefeat())));
			System.out.println(state.toString());
			rUtil.AdjustGameResultWeight(state, 1);
		}
	}

}
