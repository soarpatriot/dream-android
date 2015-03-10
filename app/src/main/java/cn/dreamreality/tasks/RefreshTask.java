package cn.dreamreality.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.utils.Config;

/**
 * Created by liuhaibao on 15/3/9.
 */
public class RefreshTask extends AsyncTask<Void, Void, String>
{

    private Handler handler;

    public RefreshTask(Handler handler){
        this.handler = handler;

    }
    @Override
    protected String doInBackground(Void... params)
    {
        Message msg = new Message();
        Bundle data = new Bundle();
        HttpGet getMethod = new HttpGet(Config.POST_URL);
        try {


            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(getMethod);

            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码

            data.putInt("code",statusCode);
            data.putString("result",entityStr);

            msg.setData(data);
            handler.sendMessage(msg);

        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result)
    {

    }



}
