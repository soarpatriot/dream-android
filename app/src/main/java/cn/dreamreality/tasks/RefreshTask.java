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
public class RefreshTask extends AsyncTask<Integer, Void, Boolean>
{

    private Context context;
    private DreamListAdapter dreamAdapter;
    private PullToRefreshListView pullToRefreshView;
    private ArrayList<DreamReality> dreamLists = new ArrayList<DreamReality>();
    private int type;
    public enum Type{
        ADD, REFRESH
    }


    public RefreshTask(Context context, DreamListAdapter dreamAdapter,PullToRefreshListView pullToRefreshView, int type){
        this.context = context;
        this.dreamAdapter = dreamAdapter;
        this.pullToRefreshView = pullToRefreshView;
        this.type = type;
    }
    @Override
    protected Boolean doInBackground(Integer... params)
    {
        String dreamUrl = Config.POST_URL;
        if(params[0] > 0){
            dreamUrl = dreamUrl + "?before="+ params[0];
        }
        HttpGet getMethod = new HttpGet(dreamUrl);
        try {


            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(getMethod);

            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码

            dealList(statusCode, entityStr);
            //data.putInt("code",statusCode);
            //data.putString("result",entityStr);

            //msg.setData(data);


        } catch (ClientProtocolException e) {

            e.printStackTrace();
            return false;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if(result){
            if(type == Type.REFRESH.ordinal()){
                dreamAdapter.setData(dreamLists);
            }else{
                dreamAdapter.addData(dreamLists);
            }

        }else{
            Toast.makeText(context, "出错了",
                    Toast.LENGTH_SHORT).show();
        }
        pullToRefreshView.onRefreshComplete();
    }


    private void dealList(int code, String result) throws Exception{
        JSONObject jsonObject = null;

        if ( !(code >= 200 && code < 400) ){
            try{
                jsonObject = new JSONObject(result);
                String message = jsonObject.getString("error");
                Toast.makeText(context, message,
                        Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{


            try{

                jsonObject = new JSONObject(result);
                JSONArray dreams = jsonObject.getJSONArray("data");
                int length = dreams.length();
                String dream = "";
                for(int i = 0; i < length; i++){//遍历JSONArray
                    DreamReality dreamReality = new DreamReality();
                    JSONObject oj = dreams.getJSONObject(i);
                    dream = oj.getString("dream");

                    dreamReality.setDream(dream);

                    dreamLists.add(dreamReality);

                }



            }catch (Exception e){
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }
}
