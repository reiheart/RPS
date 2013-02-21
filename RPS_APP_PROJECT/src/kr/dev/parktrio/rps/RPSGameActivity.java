package kr.dev.parktrio.rps;

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

	private GameState gameState = GameState.GAME_STATE_NONE;
	private Thread comThread;
	private ComThreadHandler comThreadHandler;
	private boolean comThreadEnable;

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

		comThreadHandler = new ComThreadHandler();
		comThreadEnable = false;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(buttonStart))
		{
			textResult.setText(R.string.string_result);
			buttonStart.setVisibility(View.INVISIBLE);

			if (comThread != null && comThread.isAlive())
			{
				comThread.interrupt();
			}

			comThread = new Thread(this);
			comThreadEnable = true;
			comThread.start();

			gameState = GameState.GAME_STATE_STARTED;
		}
		else if (v.equals(layoutWhole))
		{
			if (gameState == GameState.GAME_STATE_JUDGED)
			{
				textResult.setText(R.string.string_result);
				textCom.setText(R.string.string_before);
				textPlayer.setText(R.string.string_before);
				buttonStart.setVisibility(View.VISIBLE);

				gameState = GameState.GAME_STATE_NONE;
			}
		}
		else if (v.equals(buttonR))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_R);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}
		else if(v.equals(buttonP))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_P);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}
		else if(v.equals(buttonS))
		{
			if (gameState == GameState.GAME_STATE_STARTED)
			{
				textPlayer.setText(R.string.string_S);
				gameState = GameState.GAME_STATE_SELECTED;
			}
		}

		if (gameState == GameState.GAME_STATE_SELECTED)
		{
			showResult();
		}
	}

	private void showResult() {
		comThreadEnable = false;
		if (comThread != null && comThread.isAlive())
		{
			comThread.interrupt();
		}

		textResult.setText(R.string.string_win);

		gameState = GameState.GAME_STATE_JUDGED;
	}

	@Override
	public void run() {
		while (comThreadEnable)
		{
			try {
				Thread.sleep(200);

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
