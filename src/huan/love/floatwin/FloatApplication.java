package huan.love.floatwin;

import huan.love.ActivityPicture;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class FloatApplication extends Application
{

	private WindowManager.LayoutParams winParams = new WindowManager.LayoutParams();
	private FloatView floatView = null;
	private boolean isDisplay = false;
	
	public WindowManager.LayoutParams getLayoutParams()
	{
		return winParams;
	}
	
	public void createView()
	{
		if (isDisplay) return;
		floatView = new FloatView(this);
		floatView.setOnClickListener(floatViewClick);
		WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		//设置layoutparams（全局变量）相关参数;显示myFloatView图像
		windowManager.addView(floatView, winParams);
		isDisplay = true;
	}
	
	public void removeView()
	{
		if (!isDisplay) return;
		WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		windowManager.removeView(floatView);
		isDisplay = false;
	}
	
	private OnClickListener floatViewClick = new OnClickListener()
	{		
		@Override
		public void onClick(View arg0)
		{
			// 点击浮窗事件
			Intent intent = new Intent(getBaseContext(),ActivityPicture.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			//Toast.makeText(getApplicationContext(), "点我啊", Toast.LENGTH_LONG).show();		
		}
	}; 
	
}












