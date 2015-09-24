package huan.love.floatwin;

import huan.love.R;
import huan.love.manager.MyPrefereManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class FloatView extends ImageView
{

	//动态素材
//	private long movieStart;
//	private Movie movie=Movie.decodeStream(getResources().openRawResource(R.drawable.gif01)); 
	
	private float mTouchX,mTouchY;
	private float x,y;
	private OnClickListener mClickListener;
	private WindowManager windowManager = (WindowManager) getContext().getApplicationContext().
			getSystemService(Context.WINDOW_SERVICE);
	//此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
	private WindowManager.LayoutParams winLayoutParams = ((FloatApplication)getContext().getApplicationContext())
			.getLayoutParams();
	//保存当前是否为移动模式
	private boolean isMove = false;
	//保存当前view是咋左边还是右边
	private boolean isRight = false;
	
	private int defaultResource = R.drawable.defaul;
	private int focusLeftResource = R.drawable.left;
	private int focusRightResource = R.drawable.right;
	private int leftResource = R.drawable.leftdefal;
	private int rightResource = R.drawable.rightdefal;
	
	private MyPrefereManager myPrefereManager = null;
	
	public FloatView(Context context)
	{
		super(context);
		
		isMove = false;
		isRight = false;
		myPrefereManager = new MyPrefereManager(getContext());
		
		//设置window样式
		winLayoutParams.type = LayoutParams.TYPE_PHONE;
		winLayoutParams.format = PixelFormat.RGBA_8888;
		
		/*
		 * 注意，flag的值可以为：
		 * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE   不可聚焦
		 * LayoutParams.FLAG_NOT_TOUCHABLE   不可触摸
		 */
		winLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		winLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		//以屏幕左上角为原点，设置x，y初始值
		winLayoutParams.x = (int) myPrefereManager.getFloatX();
		winLayoutParams.y = (int) myPrefereManager.getFloatY();
		//设置悬浮窗长宽
		winLayoutParams.width = LayoutParams.WRAP_CONTENT;
		winLayoutParams.height = LayoutParams.WRAP_CONTENT;
		isRight = myPrefereManager.isDisplayRight();
		if (isRight)
		{
			setImageResource(rightResource);
		}else {
			setImageResource(leftResource);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// 获取到状态栏的高度
		Rect frameRect = new Rect();
		getWindowVisibleDisplayFrame(frameRect);
		int statusBarHeight = frameRect.top;
		
		//获取相对屏幕的坐标，即以屏幕坐上角为原点
		x = event.getRawX();
		y = event.getRawY() -statusBarHeight;
		
		int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			//捕获手指触摸按下动作，获取相对View的坐标，即以此View为左上角为原点
			mTouchX = event.getX();
			mTouchY = event.getY();
			isMove = false;
			if (isRight)
			{
				setImageResource(focusRightResource);
			}else {
				setImageResource(focusLeftResource);
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if(x > 35 && (screenWidth - x) >35) {
				isMove = true;
				setImageResource(defaultResource);
				upadteViewPosition();
			}
			break;
		case MotionEvent.ACTION_UP:
			if (isMove)
			{
				isMove = false;
				float halfScreen = screenWidth/2;
				if (x <= halfScreen)
				{
					setImageResource(leftResource);
					x = 0;
					isRight = false;
				}else {
					setImageResource(rightResource);
					x = screenWidth;
					isRight = true;
				}
				upadteViewPosition();
				myPrefereManager.setFloatX(x);
				myPrefereManager.setFloatY(y);
				myPrefereManager.setDisplayRight(isRight);
			}else {
				if (isRight)
				{
					setImageResource(rightResource);
				}else {
					setImageResource(leftResource);
				}
				
				if (mClickListener != null)
				{
					mClickListener.onClick(this);
				}
			}
		mTouchX = mTouchY= 0 ;
			break;
		}
	return true;
	}

	@Override
	public void setOnClickListener(OnClickListener l)
	{
		// TODO Auto-generated method stub
		this.mClickListener = l;
	}

	private void upadteViewPosition()
	{
		//更新浮动窗口位置参数
		winLayoutParams.x = (int) (x-mTouchX);
		winLayoutParams.y = (int) (y-mTouchY);
		windowManager.updateViewLayout(this, winLayoutParams);
	}

/*	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO Auto-generated method stub
		  long curTime=android.os.SystemClock.uptimeMillis();  
	        //第一次播放  
	        if (movieStart == 0) {  
	        movieStart = curTime;  
	        }  
	        if (movie != null) {  
	            int duraction = movie.duration();  
	            int relTime = (int) ((curTime-movieStart)%duraction);  
	            movie.setTime(relTime);  
	            movie.draw(canvas, 0, 0);  
	            //强制重绘  
	            invalidate();  
	        }  
	        super.onDraw(canvas); 
	}*/
}








