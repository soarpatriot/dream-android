package cn.dreamreality.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
public class RefreshDreamTask extends AsyncTask<Void, Void, Boolean>
{

    private Context context;
    private DreamListAdapter dreamAdapter;
    private ListView dreamListView;
    private ArrayList<DreamReality> dreamLists;

    private SwipyRefreshLayout mSwipyRefreshLayout;
    private int type;
    private long before;

    public enum Type{
        ADD, REFRESH
    }

    public RefreshDreamTask(Context context,SwipyRefreshLayout mSwipyRefreshLayout, DreamListAdapter dreamAdapter, ListView dreamListView, int type){
        this.context = context;
        this.dreamAdapter = dreamAdapter;
        this.dreamListView = dreamListView;
        this.mSwipyRefreshLayout = mSwipyRefreshLayout;
        this.type = type;
    }

    @Override
    protected void onPreExecute(){

        before = dreamAdapter.getLastItemId();
    }
    @Override
    protected Boolean doInBackground(Void...param)
    {
        String dreamUrl = Config.POST_URL;
        if(type == Type.ADD.ordinal()){
            dreamUrl = dreamUrl + "?before="+ before;
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
        Log.i(this.getClass().toString(), "result = "+ result); //获取响应码
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
        if(null != mSwipyRefreshLayout){
            mSwipyRefreshLayout.setRefreshing(false);
        }
    }


    private void dealList(int code, String result) throws Exception{
        JSONObject jsonObject = null;

        if ( code != HttpStatus.SC_OK ){
            try{
                jsonObject = new JSONObject(result);
                String message = jsonObject.getString("error");
                Toast.makeText(context, message,
                        Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{

            dreamLists = new ArrayList<DreamReality>();
            try{

                jsonObject = new JSONObject(result);
                JSONArray dreams = jsonObject.getJSONArray("data");
                int length = dreams.length();
                for(int i = 0; i < length; i++){//遍历JSONArray
                    DreamReality dreamReality = new DreamReality();
                    JSONObject oj = dreams.getJSONObject(i);

                    dreamReality.setId(oj.getLong("id"));
                    dreamReality.setReality(oj.getString("reality"));
                    dreamReality.setDream(oj.getString("dream"));

                    dreamLists.add(dreamReality);

                }



            }catch (Exception e){
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }
}
