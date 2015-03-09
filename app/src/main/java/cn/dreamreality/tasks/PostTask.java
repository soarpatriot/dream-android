package cn.dreamreality.tasks;

import android.content.Context;
import android.os.AsyncTask;
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
public class PostTask extends AsyncTask<Void, Void, String>
{

    private PullToRefreshListView pullToRefreshView;


    private Context context;
    private DreamListAdapter dreamAdapter;
    private ArrayList<DreamReality> dreamLists;

    public PostTask(DreamListAdapter dreamAdapter, PullToRefreshListView pullToRefreshView){
        this.dreamAdapter = dreamAdapter;
        this.pullToRefreshView = pullToRefreshView;
    }
    @Override
    protected String doInBackground(Void... params)
    {
        HttpGet getMethod = new HttpGet(Config.POST_URL);
        try {


            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(getMethod);

            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Log.i(this.getClass().toString(), "resCode = " + statusCode); //获取响应码
            Log.i(this.getClass().toString(), "resMessage = " + entityStr); //获取响应码

            dealList(statusCode,entityStr);


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
        pullToRefreshView.onRefreshComplete();
    }

    private void dealList(int code, String result){
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
                dreamLists = new ArrayList<DreamReality>();
                jsonObject = new JSONObject(result);
                JSONArray dreams = jsonObject.getJSONArray("data");
                int length = dreams.length();
                String dream = "";
                for(int i = 0; i < length; i++){//遍历JSONArray
                    DreamReality dreamReality = new DreamReality();
                    JSONObject oj = dreams.getJSONObject(i);
                    dream = oj.getString("dream");
                    Log.i("debugTest",dream);
                    dreamReality.setDream(dream);

                    dreamLists.add(dreamReality);

                }

                dreamAdapter.addData(dreamLists);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
