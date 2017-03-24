package com.android.ttbg.json;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.android.ttbg.util.Utils;

import android.os.Handler;
import android.os.Message;



public class JsonControl {
	
	public final static String HOME_PAGE = "http://www.1ybgo.com/";
	
	
	//msg define
	public static final int COUNT_DOWN_MSG=0x8001; //login count down msg
	public static final int GET_SUCCESS_MSG=0x8002; //get success msg
	public static final int POST_SUCCESS_MSG=0x8003; //post success msg
	public static final int PUT_SUCCESS_MSG=0x8004; //put success msg
	public static final int UPLOAD_SUCCESS_MSG=0x8005; //put success msg
	
	
	  @SuppressWarnings("deprecation")
	public static void httpGet(String url,Handler mHandler) {
		Utils.Log(" xxxxxxxxxxxxxxxxxxxxx http httpGet url:"+url);
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpClient httpClinet = new DefaultHttpClient();
				HttpResponse httpResponse = httpClinet.execute(httpGet);
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					Utils.Log(" httpGet status " + httpResponse.getStatusLine());
					Utils.Log(" xxxxxxxxxxxxxxxxxxxxx http httpGet start output 2");
					String result=EntityUtils.toString(entity, "UTF-8");
					// 下面这种方式写法更简单，可是没换行。
					Utils.Log("httpGet 2" + result);
					// 生成 JSON 对象
//					JSONArray jsonArray= new JSONArray(result);
					JSONObject jsonObject=new JSONObject(result);
					if (mHandler != null) {
					Message msg=new Message();
					msg.what=GET_SUCCESS_MSG;
					msg.arg1=1;
					msg.obj=jsonObject;
//					mHandler.sendEmptyMessage(1);
					mHandler.sendMessage(msg);
					}
					Utils.Log(" xxxxxxxxxxxxxxxxxxxxx http httpGet finish output 2"+jsonObject);
				}
			} catch (Exception e) {
				Utils.Log("httpGet error:" + e);
			}
		}
}
