package huan.love;

import huan.love.cardview.CardAdapter;
import huan.love.cardview.CardView;
import huan.love.cardview.CardView.OnCardClickListener;
import huan.love.manager.Utils;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLove extends FragmentActivity implements OnCardClickListener
{

	private TestFragment frag;
	private List<String> list;
	private List<Integer> image;
	
	private List<Integer> imageBG;
	
	//private SoundPool soundPool;
	private MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_love);
		initUI();
	}
	
	private void initUI() {
		
//		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
//		soundPool.load(this, R.raw.love,1);
//		soundPool.play(1,1, 1, 0, 0, 1);		
		//soundPool.pause(arg0);
		
		mediaPlayer = MediaPlayer.create(this, R.raw.love);
		try
		{
			mediaPlayer.prepare();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CardView cardView = (CardView) findViewById(R.id.cardView1);
		cardView.setOnCardClickListener(this);
		cardView.setItemSpace(Utils.convertDpToPixelInt(this, 20));
		
		MyCardAdapter adapter = new MyCardAdapter(this);
		adapter.addAll(initData());
		
		initImage();
		initImageBG();
		cardView.setAdapter(adapter);
		
		FragmentManager manager = getSupportFragmentManager();
		frag = new TestFragment();
		manager.beginTransaction().add(R.id.contentView, frag).commit();
	}
	
	public void onCardClick(final View view, final int position) {
		//Toast.makeText(ActivityLove.this, position + "", Toast.LENGTH_SHORT).show();
		Bundle bundle = new Bundle();
		bundle.putString("text", list.get(position%list.size()));
		bundle.putInt("image", imageBG.get(position%imageBG.size()).intValue());
		frag.show(view,bundle);
	}
	
	
	
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
        mediaPlayer.release();
        //stopSelf();
	}

	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		mediaPlayer.start();
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		mediaPlayer.pause();
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
	}

	private List<String> initData() {
		list = new ArrayList<String>();
		list.add("I");
		list.add("L");
		list.add("O");
		list.add("V");
		list.add("E");
		list.add("U");
		list.add("I LOVE YOU");
		return list;
	}

	private List<Integer> initImage() {
		image = new ArrayList<Integer>();
		image.add(R.drawable.bg01);
		image.add(R.drawable.bg02);
		image.add(R.drawable.bg03);
		image.add(R.drawable.bg04);
		image.add(R.drawable.bg05);
		image.add(R.drawable.bg06);
		image.add(R.drawable.bg07);
		return image;
	}
	
	private List<Integer> initImageBG() {
		imageBG = new ArrayList<Integer>();
		imageBG.add(R.drawable.bg1);
		imageBG.add(R.drawable.bg2);
		imageBG.add(R.drawable.bg3);
		imageBG.add(R.drawable.bg4);
		imageBG.add(R.drawable.bg5);
		imageBG.add(R.drawable.bg6);
		imageBG.add(R.drawable.bg7);
		return imageBG;
	}
	public class MyCardAdapter extends CardAdapter<String>{

		public MyCardAdapter(Context context) {
			super(context);
		}
		
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		
		@Override
		protected View getCardView(int position,
				View convertView, ViewGroup parent) {
			if(convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(ActivityLove.this);
				convertView = inflater.inflate(R.layout.item_layout, parent, false);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.textView1);
			ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
			String text = getItem(position%list.size());
			
			iv.setBackgroundResource(image.get(position%image.size()).intValue());
			tv.setText(text);
			return convertView;
		}
	}
}
