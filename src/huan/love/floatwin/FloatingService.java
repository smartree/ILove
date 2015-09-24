package huan.love.floatwin;

import huan.love.manager.MyPrefereManager;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.IBinder;

public class FloatingService extends Service
{

	public static final String ACTION_HOME_DISPLAY = "love.floating.ACTION_HOME_DIAPLY";
	public static final String ACTION_HOME_HIDDEN 	= "love.floating.ACTION_HOME_HIDDEN";
	
//	private static final String TAG = "FloatingService";
	
	private FloatingService mServices = null;
	private ListenHomeTask mListenTask =null;
	
	private BroadcastReceiver mHomeReceview = null;
	private MyPrefereManager mPreferenceManager = null;
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		mServices = this;
		mPreferenceManager = new MyPrefereManager(mServices);
		mListenTask = new ListenHomeTask();
		mListenTask.execute();
		mHomeReceview = new HomeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_HOME_DISPLAY);
		filter.addAction(ACTION_HOME_HIDDEN);
		registerReceiver(mHomeReceview, filter);
	}

	@Override
	public void onDestroy() {
		mListenTask.stopTask();
		mListenTask=  null;
		unregisterReceiver(mHomeReceview);
		super.onDestroy();
	}
	class HomeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent arg1)
		{
			// TODO Auto-generated method stub
			if (!mPreferenceManager.onlyDisplayOnHome()) return;
			String action = arg1.getAction();
			FloatApplication application = (FloatApplication) getApplicationContext();
			if (ACTION_HOME_DISPLAY.equals(action))
			{
				application.createView();
			}else {
				application.removeView();
			}
		}
	}
	
	class ListenHomeTask extends AsyncTask<Object, Integer, Boolean>
	{

		private boolean stop = false;
		private List<String> homes = null;
		private boolean lastIsHome = false;
		
		public ListenHomeTask()
		{
			stop = false;
			homes = getHomes();
			lastIsHome = isHome();
			sendBroadcast(lastIsHome);
		}

		public void stopTask()
		{
			stop = true;
		}

		private void sendBroadcast(boolean isHome)
		{
			if (isHome)
			{
				mServices.sendBroadcast(new Intent(ACTION_HOME_DISPLAY));
			}else {
				mServices.sendBroadcast(new Intent(ACTION_HOME_HIDDEN));
			}
		}
		
		
		
		@Override
		protected Boolean doInBackground(Object... arg0)
		{
			while (!stop)
			{
				boolean isHome = isHome();
				//当目前状态不同与上一个状态时，发送一个广播
				if (lastIsHome != isHome)
				{
					sendBroadcast(isHome);
					lastIsHome = isHome;
				}
				try
				{
					Thread.sleep(200);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			return true;
		}
		public boolean isHome()
		{
			ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> runingTaskInfos = mActivityManager.getRunningTasks(Integer.MAX_VALUE);
			
			return homes.contains(runingTaskInfos.get(0).topActivity.getPackageName());
		}
		
		private List<String> getHomes()
		{
			List<String> packages = new ArrayList<String>();
			PackageManager packageManager = mServices.getPackageManager();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 
					packageManager.MATCH_DEFAULT_ONLY);
			for (ResolveInfo info : resolveInfos)
			{
				packages.add(info.activityInfo.packageName);
			}
			return packages;
		}
	}
}












