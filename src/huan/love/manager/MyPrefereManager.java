package huan.love.manager;

import huan.love.floatwin.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyPrefereManager
{
	private Context mContext;
	
	public MyPrefereManager(Context mContext)
	{
		super();
		this.mContext = mContext;
	}
	
	private SharedPreferences getSharedPreferences()
	{
		return mContext.getSharedPreferences(Constants.PERFERENCE_NAME, Context.MODE_PRIVATE);
	}

	private Editor getEditor()
	{
		return getSharedPreferences().edit();
	}
	
	public float getFloatX()
	{
		SharedPreferences loveSP = getSharedPreferences();
		
		return loveSP.getFloat(Constants.PREF_KEY_FLOAT_X, 0f);
	}
	
	public void setFloatX(float mX)
	{
		Editor editor = getEditor();
		editor.putFloat(Constants.PREF_KEY_FLOAT_X, mX);
		editor.commit();
	}
	
	public float getFloatY()
	{
		SharedPreferences loveSP = getSharedPreferences();
		
		return loveSP.getFloat(Constants.PREF_KEY_FLOAT_Y, 0f);
	}
	
	public void setFloatY(float mY)
	{
		Editor editor = getEditor();
		editor.putFloat(Constants.PREF_KEY_FLOAT_Y, mY);
		editor.commit();
	}
	
	public boolean onlyDisplayOnHome() {
		SharedPreferences swg = getSharedPreferences();
		
		return swg.getBoolean(Constants.PREF_KEY_DISPLAY_ON_HOME,false);
		//return swg.getBoolean(Constants.PREF_KEY_DISPLAY_ON_HOME,true);
	}

	public void setDisplayOnHome(boolean onlyDisplayOnHome) {
		Editor editor = getEditor();
		editor.putBoolean(Constants.PREF_KEY_DISPLAY_ON_HOME, onlyDisplayOnHome);
		editor.commit();
	}
	
	public boolean isDisplayRight() {
		SharedPreferences swg = getSharedPreferences();
		
		return swg.getBoolean(Constants.PREF_KEY_IS_RIGHT,false);
	}

	public void setDisplayRight(boolean isRight) {
		Editor editor = getEditor();
		editor.putBoolean(Constants.PREF_KEY_IS_RIGHT, isRight);
		editor.commit();
	}
}















