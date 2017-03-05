package com.android.yybg.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

import com.android.yybg.R;


public abstract class BaseAutoScrollTextView<T> extends ListView implements
		AutoScrollData<T> {

	private ArrayList<T> mDataList = new ArrayList<T>();
	private float mSize=16;
	private int mMax;
	private int position = -1;
	private int scroll_Y;
	private int mScrollY;
	private AutoScrollAdapter mAutoScrollAdapter = new AutoScrollAdapter();
	private OnItemClickListener mOnItemClickListener;
	private long mTimer = 1000;
	private Context mContext;

	protected abstract int getAdertisementHeight();

	private Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// 开启轮播
			switchItem();
			handler.postDelayed(this, mTimer);
		}
	};

	public interface OnItemClickListener {
		public void onItemClick(int position);
	}

	public BaseAutoScrollTextView(Context context, AttributeSet attrs,
								  int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		mScrollY = dip2px(getAdertisementHeight());
		init();

	}

	public BaseAutoScrollTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseAutoScrollTextView(Context context) {
		this(context, null);
	}

	private void init() {
		this.setDivider(null);
		this.setFastScrollEnabled(false);
		this.setDividerHeight(0);
		this.setEnabled(false);
	}

	private int dip2px(float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	private void switchItem() {
		if (position == -1) {
			scroll_Y = 0;
		} else {
			scroll_Y = mScrollY;
		}
		smoothScrollBy(scroll_Y, 2000);
		setSelection(position);
		position++;
	}

	private class AutoScrollAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			final int count = mDataList == null ? 0 : mDataList.size();
			return count > 1 ? Integer.MAX_VALUE : count;
		}

		@Override
		public Object getItem(int position) {
			return mDataList.get(position % mMax);
		}

		@Override
		public long getItemId(int position) {
			return position % mMax;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_lamp_layout, null);
				viewHolder.mInfoView = (TextView) convertView
						.findViewById(R.id.tv_info);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			T data = mDataList.get(position % mMax);
			initItemData(position, convertView, viewHolder, data);
			return convertView;
		}
	}

	private void initItemData(final int position, View convertView, ViewHolder viewHolder, T data) {
		viewHolder.mInfoView.setTextSize(mSize);
		viewHolder.mInfoView.setText(getTextInfo(data));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mOnItemClickListener != null)
				{
					mOnItemClickListener.onItemClick(position % mMax);
				}
			}
		});
	}

	static class ViewHolder {
		TextView mInfoView;// 内容
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	public void setData(ArrayList<T> _datas) {
		mDataList.clear();
		mDataList.addAll(_datas);
		mMax = mDataList == null ? 0 : mDataList.size();
		this.setAdapter(mAutoScrollAdapter);
		mAutoScrollAdapter.notifyDataSetChanged();
	}
	public void setTextSize(float _size){
		this.mSize=_size;
	}

	public void setOnItemClickListener(OnItemClickListener _listener) {
		this.mOnItemClickListener = _listener;
	}

	public void setTimer(long _time) {
		this.mTimer = _time;
	}

	public void start() {
		handler.postDelayed(runnable, 1000);
	}

	public void stop() {
		handler.removeCallbacks(runnable);
	}

}
