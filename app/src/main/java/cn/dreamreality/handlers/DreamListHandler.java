package cn.dreamreality.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
public class DreamListHandler extends Handler {

    private Context context;
    private DreamListAdapter dreamAdapter;

    private ArrayList<DreamReality> dreamLists;

    public DreamListHandler(Context context, DreamListAdapter dreamAdapter){
        this.context = context;
        this.dreamAdapter = dreamAdapter;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle data = msg.getData();
        String code = data.getString("code");
        String result = data.getString("result");

        JSONObject jsonObject = null;




        if ( !code.startsWith("20") && !code.startsWith("30") ){
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

            }



        }
    }
}
