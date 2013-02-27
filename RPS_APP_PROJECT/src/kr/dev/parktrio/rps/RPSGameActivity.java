package kr.dev.parktrio.rps;

import kr.dev.parktrio.rps.RPSGameContext.GameSelectOption;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RPSGameActivity extends Activity implements OnClickListener, Runnable {

	private enum GameState {
		GAME_STATE_NONE,
		GAME_STATE_STARTED,
		GAME_STATE_SELECTED,
		GAME_STATE_JUDGED
	}

	private RelativeLayout layoutWhole;

	private TextView textCom;
	private TextView textPlayer;
	private TextView textResult;

	private Button buttonStart;
	private Button buttonR;
	private Button buttonP;
	private Button buttonS;

	private Thread comThread;
	private ComThreadHandler comThreadHandler;
	private boolean comThreadEnable;

	private GameState gameState = GameState.GAME_STATE_NONE;
	private RPSGameContext gameContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rpsgame);

		layoutWhole = (RelativeLayout) findViewById(R.id.layoutGame);

		textCom = (TextView) findViewById(R.id.textViewCom);
		textPlayer = (TextView) findViewById(R.id.textViewPlayer);
		textResult = (TextView) findViewById(R.id.textViewResult);

		buttonStart = (Button) findViewById(R.id.buttonStart);
		buttonR = (Button) findViewById(R.id.buttonR);
		buttonP = (Button) findViewById(R.id.buttonP);
		buttonS = (Button) findViewById(R.id.buttonS);

		layoutWhole.setOnClickListener(this);
		buttonStart.setOnClickListener(this);
		buttonR.setOnClickListener(this);
		buttonP.setOnClickListener(this);
		buttonS.setOnClickListener(this);

		gameContext = new RPSGameContext();

		comThreadHandler = new ComThreadHandler();
		comThreadEnable = false;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(buttonStart))
		{
			buttonStart.setVisibility(View.INVISIBLE);
			gameContext.resetGame();

			startGame();
		}
		else if (v.equals(layoutWhole))
		{
			if (gameState == GameState.GAME_STATE_JUDGED)
			{
				textPlayer.setText(R.string.string_before);

				if (gameContext.hasNext())
				{
					startGame();
				}
				else
				{
					buttonStart.setVisibility(View.VISIBLE);
					gameState = GameState.GAME_STATE_NONE;
				}

			}
		}
		else if (v.equals(buttonR))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_R);
				gameContext.doGame(GameSelectOption.R);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}
		else if(v.equals(buttonP))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_P);
				gameContext.doGame(GameSelectOption.P);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}
		else if(v.equals(buttonS))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_S);
				gameContext.doGame(GameSelectOption.S);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}

		if (gameState == GameState.GAME_STATE_SELECTED)
		{
			showResult();
		}
	}

	private void startGame() {
		textResult.setText(R.string.string_result);

		if (comThread != null && comThread.isAlive())
		{
			comThread.interrupt();
		}

		comThread = new Thread(this);
		comThreadEnable = true;
		comThread.start();

		gameState = GameState.GAME_STATE_STARTED;
	}

	private void showResult() {
		comThreadEnable = false;
		if (comThread != null && comThread.isAlive())
		{
			comThread.interrupt();
		}

		switch (gameContext.getCurrentComSelection()) {
		case P:
			textCom.setText(R.string.string_P);
			break;
		case R:
			textCom.setText(R.string.string_R);
			break;
		case S:
			textCom.setText(R.string.string_S);
			break;
		}

		StringBuilder sb = new StringBuilder();
		switch (gameContext.getCurrentGameResult()) {
		case GAME_RESULT_STATE_DEFEAT:
			sb.append(getResources().getString(R.string.string_lose));
			break;
		case GAME_RESULT_STATE_DRAW:
			sb.append(getResources().getString(R.string.string_nogame));
			break;
		case GAME_RESULT_STATE_NONE:
			sb.append(getResources().getString(R.string.string_null));
			break;
		case GAME_RESULT_STATE_WIN:
			sb.append(getResources().getString(R.string.string_win));
			break;
		}

		if (!gameContext.hasNext())
		{
			sb.append("\n");
			sb.append(gameContext.getGameResult());
		}
		else if (gameContext.getGameRecord().getCombo() > 0)
		{
			sb.append("\n");
			sb.append(gameContext.getGameRecord().getCombo()).append(" Combo");
		}

		textResult.setText(sb.toString());
		gameState = GameState.GAME_STATE_JUDGED;
	}

	@Override
	public void run() {
		while (comThreadEnable)
		{
			try {
				Thread.sleep(150);

				comThreadHandler.sendMessage(new Message());
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	@SuppressLint("HandlerLeak")
	private class ComThreadHandler extends Handler {
		private int count = 0;

		@Override
		public void handleMessage(Message msg) {
			count++;

			switch (count % 3)
			{
			case 0:
				textCom.setText(R.string.string_S);
				resetCount();
				break;
			case 1:
				textCom.setText(R.string.string_R);
				break;
			case 2:
				textCom.setText(R.string.string_P);
				break;
			default:
				textCom.setText(R.string.string_R);
				resetCount();
				break;
			}

			super.handleMessage(msg);
		}

		public void resetCount() {
			count = 0;
		}
	}
}
