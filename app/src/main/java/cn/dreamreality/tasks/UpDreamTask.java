package cn.dreamreality.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.holders.DreamHolder;
import cn.dreamreality.utils.Config;

/**
 * Created by liuhaibao on 15/4/12.
 */
public class UpDreamTask  extends AsyncTask<String, Void, Boolean> {

    private Context context;

    public UpDreamTask(Context context){
        this.context = context;

    }

    @Override
    protected void onPreExecute(){

    }
    @Override
    protected Boolean doInBackground(String...param)
    {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("auth_token", param[1]));
        params.add(new BasicNameValuePair("post_id", param[0]));
        String url = Config.VOTE_POST_URL + param[0] + "/up";
        HttpPost postMethod = new HttpPost(url);


        try {

            postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(postMethod);

            //Log.i(this.getClass().toString(), "resCode = " +  EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码


            if (!String.valueOf(statusCode).startsWith("20") && !String.valueOf(statusCode).startsWith("30")) {
                return false;
            } else {
                return true;
            }
        } catch (ClientProtocolException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        Log.i(this.getClass().toString(), "result = "+ result); //获取响应码
        if(result){


        }else{
            Toast.makeText(context, "出错了",
                    Toast.LENGTH_SHORT).show();
        }
        //pullToRefreshView.onRefreshComplete();
    }

}
