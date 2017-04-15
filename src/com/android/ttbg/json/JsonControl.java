package com.android.ttbg.json;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.android.ttbg.util.Utils;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;



public class JsonControl {
	
	public final static String HOME_PAGE = "http://www.1ybgo.com/";
	public final static String FILE_HEAD= "http://file.1ybgou.com/";
	public final static String LOGIN_PATH= "http://www.1ybgo.com/apps/login/userlogin";
	
	//msg define
	public static final int COUNT_DOWN_MSG=0x8001; //login count down msg
	public static final int GET_SUCCESS_MSG=0x8002; //get success msg
	public static final int POST_SUCCESS_MSG=0x8003; //post success msg
	public static final int PUT_SUCCESS_MSG=0x8004; //put success msg
	public static final int UPLOAD_SUCCESS_MSG=0x8005; //put success msg
	
	
	
	//从网络上获取的json类型,用来在同一个handler里面处理不同的回调
	//广告栏
	public static final int JSON_TYPE_BANNER=0x4001; 
	//最新揭晓的数据
	public static final int JSON_TYPE_NEWEST=0x4002; 
	//新品推荐的数据
	public static final int JSON_TYPE_ARRIVALS=0x4003; 
	//猜你喜欢的数据
	public static final int JSON_TYPE_RECOMMAND=0x4004; 
	//所有商品
	public static final int JSON_TYPE_ALLGOODS=0x4005; 
	
	
	
	/**
	 * Post
	 * 
	 * @param url
	 * @param paramList
	 */
	@SuppressWarnings("deprecation")
	public static void httpPost(String url, JSONObject jsonObj,Handler mHandler) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		long timestamp = System.currentTimeMillis();
		try {
			// 设置参数
//			httpPost.setEntity(new StringEntity(DesEncrypt.encrypt(jsonObj.toString(), DesEncrypt.KEY), HTTP.UTF_8));
			httpPost.setEntity(new StringEntity(jsonObj.toString(), HTTP.UTF_8));
			httpPost.addHeader("content-type", "application/json;text/html;charset:utf-8");
			//httpPost.addHeader("authorization","Basic "+Base64.encodeToString((jsonObj.getString("phone")+":phone").toString().getBytes(),Base64.NO_WRAP));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				Utils.Log(" httpPost status " + httpResponse.getStatusLine());
				String entitySrc = EntityUtils.toString(entity, "UTF-8");
				JSONObject jsonObject=new JSONObject(entitySrc);
				if (mHandler != null) {
				Message msg=new Message();
				msg.what=POST_SUCCESS_MSG;
				msg.arg1=1;
				msg.obj=jsonObject;
//				mHandler.sendEmptyMessage(1);
				mHandler.sendMessage(msg);
				}
				Utils.Log("entitySrc  " + entitySrc);
				Utils.Log("http httpPost finish output ");
			}
		} catch (ConnectTimeoutException e) {
			Utils.Log("httpPost time out error:" + e);
		} catch (Exception e) {
			Utils.Log("httpPost error:" + e);
		}
		Utils.Log("httpPost spend time:"
				+ (System.currentTimeMillis() - timestamp) + "ms");
	}
	
	
	
	/**
	 * Put
	 * 
	 * @param url
	 * @param paramList
	 */
	@SuppressWarnings("deprecation")
	public static void httpPut(String url, JSONObject jsonObj,Handler mHandler) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(url);
		long timestamp = System.currentTimeMillis();
		try {
			// 设置参数
			Utils.Log("httpPut jsonObj.toString() " + jsonObj.toString());
//			httpPost.setEntity(new StringEntity(DesEncrypt.encrypt(jsonObj.toString(), DesEncrypt.KEY), HTTP.UTF_8));
			httpPut.setEntity(new StringEntity(jsonObj.toString(), HTTP.UTF_8));
			httpPut.addHeader("content-type", "application/json");
//			httpPut.addHeader("authorization","Basic "+Base64.encodeToString((jsonObj.getString("phone")+":phone").toString().getBytes(),Base64.NO_WRAP));
			HttpResponse httpResponse = httpClient.execute(httpPut);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				Utils.Log(" httpPut status " + httpResponse.getStatusLine());
				Utils.Log(" http httpPut start output HTTP.UTF_8 = "+HTTP.UTF_8);
				String defaultCharset = EntityUtils.getContentCharSet(entity);
                // EntityUtils.toString将获得的消息体转换成String
				Utils.Log(" httpPut defaultCharset " + defaultCharset);
				String entitySrc = EntityUtils.toString(entity,"UTF-8");
				Utils.Log("entitySrc = "+entitySrc+" entity = "+entity);
				JSONObject jsonObject=new JSONObject(entitySrc);
				if (mHandler != null) {
				Message msg=new Message();
				msg.what=PUT_SUCCESS_MSG;
				msg.arg1=1;
				msg.obj=jsonObject;
				mHandler.sendMessage(msg);
				}
				Utils.Log("entitySrc  " + entitySrc);
				Utils.Log(" http httpPut finish output ");
			}
		} catch (ConnectTimeoutException e) {
			Utils.Log( "httpPut time out error:" + e);
		} catch (Exception e) {
			Utils.Log( "httpPut error:" + e);
		}
		Utils.Log("httpPut spend time:"
				+ (System.currentTimeMillis() - timestamp) + "ms");
	}	
	
	  @SuppressWarnings("deprecation")
	public static void httpGet(String url,Handler mHandler,int jsonType) {
		Utils.Log("http httpGet url:"+url);
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpClient httpClinet = new DefaultHttpClient();
				HttpResponse httpResponse = httpClinet.execute(httpGet);
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					Utils.Log(" httpGet status " + httpResponse.getStatusLine());
					Utils.Log(" http httpGet start output 2");
					String result=EntityUtils.toString(entity, "UTF-8");
					// 下面这种方式写法更简单，可是没换行。
					Utils.Log("httpGet 2" + result);
					// 生成 JSON 对象
//					JSONArray jsonArray= new JSONArray(result);
					JSONObject jsonObject=new JSONObject(result);
					if (mHandler != null) {
					Message msg=new Message();
					msg.what=GET_SUCCESS_MSG;
					msg.arg1=jsonType;
					msg.obj=jsonObject;
//					mHandler.sendEmptyMessage(1);
					mHandler.sendMessage(msg);
					}
					Utils.Log("http httpGet finish output 2"+jsonObject);
				}
			} catch (Exception e) {
				Utils.Log("httpGet error:" + e);
			}
		}
}
