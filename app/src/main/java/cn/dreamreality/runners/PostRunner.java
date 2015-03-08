package cn.dreamreality.runners;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
public class PostRunner extends Thread {

    private String dream;
    private String reality;
    private String token;
    private Handler handler;

    public PostRunner(String dream, String reality,String token, Handler handler){
        this.dream = dream;
        this.reality = reality;
        this.handler = handler;
        this.token = token;
    }

    public void run() {

        Message msg = new Message();
        Bundle data = new Bundle();


        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("auth_token", token));
        params.add(new BasicNameValuePair("dream", dream));
        params.add(new BasicNameValuePair("reality", reality));


        HttpPost postMethod = new HttpPost(Config.POST_URL);


        try {

            postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(postMethod);

            //Log.i(this.getClass().toString(), "resCode = " +  EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码

            JSONTokener jsonParser = new JSONTokener(entityStr);
            JSONObject result = (JSONObject) jsonParser.nextValue();

            if (!String.valueOf(statusCode).startsWith("20") && !String.valueOf(statusCode).startsWith("30")) {
                data.putString("code", String.valueOf(statusCode));
                data.putString("message", result.getString("error"));
            } else {
                data.putString("code", String.valueOf(statusCode));
            }

            msg.setData(data);
            handler.sendMessage(msg);


        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
