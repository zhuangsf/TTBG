package com.android.ttbg.adapter;

import java.util.List;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.ObtainGoodsItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ObtainGoodsAdapter extends BaseAdapter {

	private Context mContext;
    private List<ObtainGoodsItem> ObtainGoodsItems = null;
    public ObtainGoodsAdapter(Context context, List<ObtainGoodsItem> ObtainGoodsItems)
    {
        mContext = context;
        this.ObtainGoodsItems = ObtainGoodsItems;
    }

    public void setData(List<ObtainGoodsItem> ObtainGoodsItems)
    {
    	this.ObtainGoodsItems = ObtainGoodsItems;
    }

    @Override
    public int getCount()
    {
        int count = 0;
        if (null != ObtainGoodsItems)
        {
            count = ObtainGoodsItems.size();
        }
        return count+1;   //多一个是无更多提示的textview
    }

    @Override
    public ObtainGoodsItem getItem(int position)
    {
    	ObtainGoodsItem item = null;

        if (null != ObtainGoodsItems)
        {
            item = ObtainGoodsItems.get(position);
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
            convertView = mInflater.inflate(R.layout.item_obtain_goods, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_obtain_goods_pic);
            viewHolder.tv_goods_label = (TextView) convertView.findViewById(R.id.tv_obtain_goods_name);
            viewHolder.tv_goods_join_num = (TextView) convertView.findViewById(R.id.tv_obtain_goods_join_num);
            viewHolder.tv_goods_luck_code = (TextView) convertView.findViewById(R.id.tv_obtain_goods_code);
            viewHolder.tv_goods_pubilsh_time = (TextView) convertView.findViewById(R.id.tv_obtain_goods_end_time);
            viewHolder.tv_goods_order_num = (TextView) convertView.findViewById(R.id.tv_obtain_order_number);
            viewHolder.tv_goods_state = (TextView) convertView.findViewById(R.id.tv_obtain_state_success);
            
            
            viewHolder.rl_obtain_goods_body = (RelativeLayout) convertView.findViewById(R.id.rl_obtain_goods_body);
            viewHolder.tv_norecord_hint = (TextView) convertView.findViewById(R.id.tv_norecord_hint);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set item values to the viewHolder:

        if(position == (getCount() - 1))
        {	
        viewHolder.rl_obtain_goods_body.setVisibility(View.GONE);
        viewHolder.tv_norecord_hint.setVisibility(View.VISIBLE);
        return convertView;
        }
        
        ObtainGoodsItem ObtainGoodsItem = getItem(position);
        Utils.Log("ObtainGoodsItem = "+ObtainGoodsItem);
        if (null != ObtainGoodsItem)
        {
            viewHolder.imageView.setImageDrawable(ObtainGoodsItem.getImageDrawable());
            viewHolder.tv_goods_label.setText(ObtainGoodsItem.getGoods_lable());
            
            
/*            TextView tv_goods_luck_code;       //幸运云购码
            TextView tv_goods_pubilsh_time;//揭晓时间
            TextView tv_goods_join_num;   //参与人数
            TextView tv_goods_order_num;   //订单号
            TextView tv_goods_state;       //状态
*/            
            //在这里设置样式吧
            int fstart,fend;
            
            String goods_luck_code = "幸运必购码: "+ObtainGoodsItem.getGoods_luck_code();
            fstart="幸运必购码: ".length();  
            fend=goods_luck_code.length(); 
            SpannableStringBuilder style=new SpannableStringBuilder(goods_luck_code);     
            style.setSpan(new ForegroundColorSpan(0xffff7700),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);   
            
            viewHolder.tv_goods_luck_code.setText(style);
            
            
            viewHolder.tv_goods_pubilsh_time.setText(ObtainGoodsItem.getGoods_pubilsh_time());
            viewHolder.tv_goods_join_num.setText(ObtainGoodsItem.getGoods_join_num());
            viewHolder.tv_goods_order_num.setText(ObtainGoodsItem.getGoods_order_num());
            viewHolder.tv_goods_state.setText(ObtainGoodsItem.getGoods_state());
            
            viewHolder.rl_obtain_goods_body.setVisibility(View.VISIBLE);
            viewHolder.tv_norecord_hint.setVisibility(View.GONE);

        }

        return convertView;
	}


/*    private Drawable imageDrawable;
    private String goods_lable;       //商品名称
	private String goods_luck_code;       //幸运云购码
    private String goods_pubilsh_time;     //揭晓时间
    private String goods_join_num;   //参与人数
    private String goods_order_num;   //订单号
    private String goods_state;       //状态
*/    private static class ViewHolder
    {
    	ImageView imageView;
    	RelativeLayout rl_obtain_goods_body;
        TextView tv_goods_label;//商品名称
        TextView tv_goods_luck_code;       //幸运云购码
        TextView tv_goods_pubilsh_time;//揭晓时间
        TextView tv_goods_join_num;   //参与人数
        TextView tv_goods_order_num;   //订单号
        TextView tv_goods_state;       //状态
        TextView tv_norecord_hint;  //最近三个月的提示语
    }
}
