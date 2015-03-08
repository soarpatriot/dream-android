package cn.dreamreality.runners;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.LinkedList;
import java.util.List;

import cn.dreamreality.utils.Config;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class DreamRunner extends Thread {

    private int before = 0;
    private String token;
    private Handler handler;

    public DreamRunner(int before,String token, Handler handler){
        this.before = before;
        this.handler = handler;
        this.token = token;
    }

    public void run() {

        Message msg = new Message();
        Bundle data = new Bundle();

        HttpGet getMethod = new HttpGet(Config.POST_URL+"?auth_token="+token);
        try {


            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(getMethod);

            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码

            //JSONTokener jsonParser = new JSONTokener(entityStr);
            //JSONObject result = (JSONObject) jsonParser.nextValue();

            data.putString("code", String.valueOf(statusCode));
            data.putString("result", entityStr);

            msg.setData(data);
            handler.sendMessage(msg);


        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
