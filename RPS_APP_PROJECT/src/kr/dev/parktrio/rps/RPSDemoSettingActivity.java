package kr.dev.parktrio.rps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RPSDemoSettingActivity extends Activity implements OnClickListener{

	private Button buttonStart;
	private EditText editGameCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_setting);

		buttonStart = (Button) findViewById(R.id.buttonStart);
		editGameCount = (EditText) findViewById(R.id.editGameCount);

		buttonStart.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(buttonStart))
		{
			RPSGameContext gameContext = new RPSGameContext();
			try
			{
				gameContext.setMaxGameCount(Integer.parseInt(editGameCount.getText().toString()));
			}
			catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}

			Intent gameIntent = new Intent(this, RPSGameActivity.class);
			gameIntent.putExtra(RPSGameContext.propertyKey, gameContext);
			startActivity(gameIntent);
		}
	}

}
