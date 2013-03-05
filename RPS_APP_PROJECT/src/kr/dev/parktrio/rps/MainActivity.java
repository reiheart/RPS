package kr.dev.parktrio.rps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent demoIntent = new Intent(this, RPSDemoSettingActivity.class);
		startActivity(demoIntent);
	}

}
