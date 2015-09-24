package huan.love;

import huan.love.floatwin.FloatApplication;
import huan.love.floatwin.FloatingService;
import huan.love.manager.MyPrefereManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ActivityMain extends Activity implements OnCheckedChangeListener
{
	private CheckBox checkBox;
	private MyPrefereManager myPrefereManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_main);
		
		myPrefereManager = new MyPrefereManager(this);
		checkBox = (CheckBox) findViewById(R.id.checkbox);
		checkBox.setOnCheckedChangeListener(this);
		boolean display = myPrefereManager.onlyDisplayOnHome();
		checkBox.setChecked(display);
		
		//initFloat(!display);
		//startService(new Intent(this,FloatingService.class));
	}

	private void initFloat(boolean display)
	{	
		if (display)
		{		
			((FloatApplication)getApplicationContext()).removeView();
		}else {
			((FloatApplication)getApplicationContext()).createView();
		}
	}
	
	/*private void initFloat(boolean display)
	{
		if (display)
		{
			((FloatApplication)getApplicationContext()).createView();
		}else {
			((FloatApplication)getApplicationContext()).removeView();
		}
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.checkbox:
			myPrefereManager.setDisplayOnHome(isChecked);
			initFloat(!isChecked);
			if (isChecked)
			{
				Intent intent = new Intent(ActivityMain.this,ActivityPicture.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

}













