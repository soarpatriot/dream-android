package cn.dreamreality.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

import org.apache.http.client.methods.HttpGet;

import cn.dreamreality.R;
import cn.dreamreality.utils.Config;

/**
 * Created by liuhaibao on 15/5/24.
 */
public class SendVerfiyCodeTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String...param) {
        String mobile = param[0];
        String appName = param[1];
        String type = param[2];
        try{
            AVOSCloud.requestSMSCode(mobile, appName, type, 30);

        }catch (Exception e){
            e.printStackTrace();

        }
        return true;
    }

}


