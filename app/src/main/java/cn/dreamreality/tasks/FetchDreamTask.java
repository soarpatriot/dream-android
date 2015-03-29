package cn.dreamreality.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
import cn.dreamreality.holders.DreamHolder;
import cn.dreamreality.utils.Config;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

/**
 * Created by liuhaibao on 15/3/25.
 */
public class FetchDreamTask extends AsyncTask<Long, Void, Boolean> {

    private Context context;

    private ArrayList<DreamReality> dreamLists;

    private DreamReality dreamReality = new DreamReality();
    private DreamHolder dreamHolder;

    private LinearLayout linearLayout;


    public FetchDreamTask(Context context, DreamHolder dreamHolder,LinearLayout linearLayout ){
        this.context = context;
        this.dreamHolder = dreamHolder;
        this.linearLayout = linearLayout;

    }

    @Override
    protected void onPreExecute(){
        linearLayout.setVisibility(View.VISIBLE);
        //((CircularProgressDrawable)circularProgressBar.getIndeterminateDrawable()).start();
    }
    @Override
    protected Boolean doInBackground(Long...param)
    {
        String dreamUrl = Config.POST_URL+"/"+param[0];

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
            dreamHolder.getDreamTextView().setText(dreamReality.getDream());
            dreamHolder.getRealityTextView().setText(dreamReality.getReality());
            dreamHolder.getIdTextView().setText(String.valueOf(dreamReality.getId()));

        }else{
            Toast.makeText(context, "出错了",
                    Toast.LENGTH_SHORT).show();
        }
        linearLayout.setVisibility(View.GONE);
        //((CircularProgressDrawable)circularProgressBar.getIndeterminateDrawable()).stop();

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


            try{

                jsonObject = new JSONObject(result);


                long id = jsonObject.getLong("id");
                String reality = jsonObject.getString("reality");
                String dream = jsonObject.getString("dream");

                dreamReality.setReality(reality);
                dreamReality.setDream(dream);
                dreamReality.setId(id);


            }catch (Exception e){
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }
}
