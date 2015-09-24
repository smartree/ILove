package huan.love;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class ActivityPicture extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_bg);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityPicture.this,ActivityLove.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 
				startActivity(intent);
			}
		}, 2000);
	}

}
