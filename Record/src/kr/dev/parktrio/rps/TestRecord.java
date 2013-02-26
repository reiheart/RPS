package kr.dev.parktrio.rps;

import java.util.Random;

public class TestRecord {

	public static void main(String[] args) {
		System.out.println( "=====Record Test=====" );

		RPSGameRecord record = new RPSGameRecord();
		int combo = 0;

		for ( int i = 1; i <= 30; i++ ) {
			System.out.print( i + " : ");
			GameResultState state = getState();
			record.adjustGameResult( state );
			combo = record.getCombo();
			if ( state == GameResultState.GAME_RESULT_STATE_WIN && combo > 0 ) {
				System.out.println( record.getCombo() + "Combo!" );
			}
		}
		System.out.println( "\nWin : " + record.getWin() +
							", Draw : " + record.getDraw() +
							", Defeat : " + record.getDefeat() +
							", Max Combo : " + record.getMaxCombo() );
	}

	public static GameResultState getState() {
		Random random = new Random();
		int rand = random.nextInt( 3 ) + 1;
		GameResultState result = GameResultState.GAME_RESULT_STATE_NONE;

		switch ( rand ) {
		case 1:
			result = GameResultState.GAME_RESULT_STATE_WIN;
			System.out.println( "Win!!!" );
			break;
		case 2:
			result = GameResultState.GAME_RESULT_STATE_DRAW;
			System.out.println( "Draw~" );
			break;
		case 3:
			result = GameResultState.GAME_RESULT_STATE_DEFEAT;
			System.out.println( "Defeat..." );
			break;
		}
		
		return result;
	}

}
