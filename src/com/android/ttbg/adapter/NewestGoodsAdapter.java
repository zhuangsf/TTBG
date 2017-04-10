package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.tools.AsyncImageLoader;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.GoodsProperty;
import com.android.ttbg.view.countdownview.CountdownView;
import com.android.ttbg.view.countdownview.CountdownView.OnCountdownEndListener;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewestGoodsAdapter extends BaseAdapter {

	private Context mContext;
    private List<GoodsProperty> goodsItems = null;
    private AsyncImageLoader imageLoader;
    long timeUnixNowTest;
    public NewestGoodsAdapter(Context context, List<GoodsProperty> goodsItems)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
        this.goodsItems = goodsItems;
        
        
    }

    public NewestGoodsAdapter(Context context)
    {
        mContext = context;
        imageLoader = new AsyncImageLoader(mContext);  
        timeUnixNowTest =  System.currentTimeMillis()/1000; 
    }

    
    public void setData(List<GoodsProperty> goodsItems)
    {
    	this.goodsItems = goodsItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != goodsItems)
        {
            count = goodsItems.size();
        }
        return count;
    }

    @Override
    public GoodsProperty getItem(int position)
    {
    	GoodsProperty item = null;

        if (null != goodsItems)
        {
            item = goodsItems.get(position);
        }

        return item;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_newest_raffling, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_pic_newest_countdown_grid);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_goodsname_newest_countdown_grid);
            viewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_price_newest_countdown_grid);
            viewHolder.item_rightline = (View) convertView.findViewById(R.id.item_rightline);
            viewHolder.newest_countdown_view = (CountdownView)convertView.findViewById(R.id.newest_countdown_view);

            viewHolder.iv_newest_pic_goods_result = (ImageView) convertView.findViewById(R.id.iv_newest_pic_goods_result);
            viewHolder.userHead = (ImageView) convertView.findViewById(R.id.iv_newest_pic_user_result);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tv_newest_name_user_result);
            viewHolder.buyTimes = (TextView) convertView.findViewById(R.id.tv_newest_luck_count_result);
            viewHolder.luck_number = (TextView) convertView.findViewById(R.id.tv_newest_luck_number_result);
            viewHolder.endTime = (TextView) convertView.findViewById(R.id.tv_newest_announce_time_result);
            
            viewHolder.rl_good_grid_countdown_view = (View) convertView.findViewById(R.id.rl_good_grid_countdown_view);
            viewHolder.rl_good_grid_result_view = (View) convertView.findViewById(R.id.rl_good_grid_result_view);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        GoodsProperty goodsItem = getItem(position);
        if (null != goodsItem)
        {
        	
    		long timeUnixNow =  System.currentTimeMillis();    //服务器也是用的这个接口,来获取unix时间
    		//1490327446.815   这个是服务端读出来的值,string类型
    		//1491407081699  这个是timeUnixNow的值
    		//以下都是以秒为单位,倒计时是以毫秒为单位
    		long countDownTimeLeft =goodsItem.getQ_end_time() + 180 - (timeUnixNow/1000);
    	//	long countDownTimeLeft =timeUnixNowTest - 10*position + 180 - (timeUnixNow/1000);  //要测试开奖效果就把这个打开,上一句屏蔽
    		Utils.Log("timeUnixNow = "+timeUnixNow/1000+ " timeUnixNowTest = "+timeUnixNowTest+" countDownTimeLeft = "+countDownTimeLeft);//1491837368
    		if(countDownTimeLeft > 180)
    		{
    			countDownTimeLeft = 180;
    		}
    		if(countDownTimeLeft <= 0)
    		{
    			//已经过了开奖后的3分钟.要显示开奖信息
    			viewHolder.rl_good_grid_countdown_view.setVisibility(View.GONE);
    			viewHolder.rl_good_grid_result_view.setVisibility(View.VISIBLE);
    			if(goodsItem.getDrawableUrl() != null)
            	{
            		if(imageLoader != null)
            		{
            			imageLoader.downloadImage(goodsItem.getDrawableUrl(), viewHolder.iv_newest_pic_goods_result);
            		}
            	}
            	else
            	{
            		viewHolder.imageView.setImageDrawable(goodsItem.getImageDrawable());
            	}
                if(goodsItem.getUserphoto() != null)
            	{
            		if(imageLoader != null)
            		{
            			imageLoader.downloadImage(goodsItem.getUserphoto(), viewHolder.userHead);
            		}
            	}
                viewHolder.username.setText(goodsItem.getUsername());
                
                int fstart,fend;
                String stringbuyTimes = goodsItem.getQ_buynum()+"人次获得";
                fstart=0;  
                fend=goodsItem.getQ_buynum().length(); 
                SpannableStringBuilder style=new SpannableStringBuilder(stringbuyTimes);     
                style.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
                
                viewHolder.buyTimes.setText(style);
                viewHolder.luck_number.setText("幸运云购码: "+goodsItem.getQ_user_code());
                viewHolder.endTime.setText("揭晓时间: "+goodsItem.getEndtime());
    		}
    		else{
    			//正在倒计时,要显示倒计时时间
    			viewHolder.rl_good_grid_result_view.setVisibility(View.GONE);
    			viewHolder.rl_good_grid_countdown_view.setVisibility(View.VISIBLE);
    			
    			if(goodsItem.getDrawableUrl() != null)
            	{
            		if(imageLoader != null)
            		{
            			imageLoader.downloadImage(goodsItem.getDrawableUrl(), viewHolder.imageView);
            		}
            	}
            	else
            	{
            		viewHolder.imageView.setImageDrawable(goodsItem.getImageDrawable());
            	}
                viewHolder.tv_goods_label.setText(goodsItem.getGoodsTitle());
                viewHolder.tv_goods_price.setText(goodsItem.getMoney());
                if(position%2!=0) {
                	viewHolder.item_rightline.setVisibility(View.GONE);
                }
                else
                {
                	viewHolder.item_rightline.setVisibility(View.VISIBLE);
                }
                //实际要传countDownTimeLeft这个值
                viewHolder.newest_countdown_view.setOnCountdownEndListener(new OnCountdownEndListener()
                {

					@Override
					public void onEnd(CountdownView cv) {
						// TODO Auto-generated method stub
						notifyDataSetChanged();

					}
                	
                });
                viewHolder.newest_countdown_view.start(countDownTimeLeft*1000);
                
    		}
        	
        	

            convertView.setTag(viewHolder);
        }

        return convertView;
	}

	
	
    private static class ViewHolder
    {
    	ImageView imageView;
        TextView tv_goods_label;
        TextView tv_goods_price;
        CountdownView newest_countdown_view;
        View item_rightline;
        
        ImageView iv_newest_pic_goods_result;
        ImageView userHead;
        TextView username;
        TextView buyTimes;
        TextView luck_number;
        TextView endTime;
        
        View rl_good_grid_countdown_view;
        View rl_good_grid_result_view;
    }
}
