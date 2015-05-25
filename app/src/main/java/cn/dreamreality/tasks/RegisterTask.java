package cn.dreamreality.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

import cn.dreamreality.MainActivity;
import cn.dreamreality.VerifyCodeActivity;
import cn.dreamreality.utils.Config;

/**
 * Created by liuhaibao on 15/5/24.
 */
public class RegisterTask extends AsyncTask<String, Void, Boolean> {

    private String message = "";
    private Context context;

    public RegisterTask(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String...param){

        String mobile = param[0];
        String nickname = param[1];
        String password = param[2];

        boolean optResult = true;

        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("mobile_number", mobile));
        params.add(new BasicNameValuePair("name", nickname));
        params.add(new BasicNameValuePair("password", password));


        HttpPost postMethod = new HttpPost(Config.REGISTER_URL);


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

            if (!String.valueOf(statusCode).startsWith("20") && !String.valueOf(statusCode).startsWith("30")) {
                message = result.getString("error");
                optResult = false;
            }


        } catch (ClientProtocolException e) {
            message = "网络错误!";
            optResult = false;
            e.printStackTrace();
        } catch (Exception e) {
            message = "其它错误!";
            optResult = false;
            e.printStackTrace();
        }
        return optResult;
        //return true;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        Log.i(this.getClass().toString(), "result = "+ result); //获取响应码
        if(result){

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);

        }else{
            Toast.makeText(context, "出错了",
                    Toast.LENGTH_SHORT).show();
        }
        //linearLayout.setVisibility(View.GONE);
        //((CircularProgressDrawable)circularProgressBar.getIndeterminateDrawable()).stop();

    }
}
