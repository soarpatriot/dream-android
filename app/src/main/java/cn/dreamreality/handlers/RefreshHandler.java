package cn.dreamreality.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import cn.dreamreality.MainActivity;
import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.utils.SettingsUtils;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class RefreshHandler extends Handler {

    private Context context;
    private DreamListAdapter dreamAdapter;
    private PullToRefreshListView pullToRefreshView;
    private ArrayList<DreamReality> dreamLists;

    public RefreshHandler(Context context, PullToRefreshListView pullToRefreshView, DreamListAdapter dreamAdapter){
        this.pullToRefreshView = pullToRefreshView;
        this.context = context;
        this.dreamAdapter = dreamAdapter;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle data = msg.getData();
        int code = data.getInt("code");
        String result = data.getString("result");

        dealList(code,result);
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

                dreamAdapter.setData(dreamLists);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
