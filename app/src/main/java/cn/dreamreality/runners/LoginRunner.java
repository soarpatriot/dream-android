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


/**
 * Created by liuhaibao on 15/3/5.
 */
public class LoginRunner extends Thread {
    private String username;
    private String password;
    private Handler handler;

    public LoginRunner(String username, String password, Handler handler){
        this.username = username;
        this.password = password;
        this.handler = handler;
    }

    public void run(){

        Message msg = new Message();
        Bundle data = new Bundle();


        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("name", this.getUsername()));
        params.add(new BasicNameValuePair("password", this.getPassword()));


        String baseUrl = "http://api.dreamreality.cn/v1/user";

        //将URL与参数拼接
        HttpPost postMethod = new HttpPost(baseUrl + "/login");


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

            if ( !String.valueOf(statusCode).startsWith("20") && !String.valueOf(statusCode).startsWith("30") ){
                data.putString("code",String.valueOf(statusCode));
                data.putString("message",result.getString("error"));
            }else{
                data.putString("code",String.valueOf(statusCode));
                data.putString("token",result.getString("token"));
            }

            msg.setData(data);
            handler.sendMessage(msg);


        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
