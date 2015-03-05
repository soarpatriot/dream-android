package me.yuxian.city.handlers;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import me.yuxian.city.LoginActivity;
import me.yuxian.city.MainActivity;

/**
 * Created by liuhaibao on 15/3/5.
 */
public class LoginHandler extends Handler {

    private View v;

    public LoginHandler(View v){
        this.v = v;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Bundle data = msg.getData();
        String code = data.getString("code");

        if ( !code.startsWith("20") && !code.startsWith("30") ){
            String message = data.getString("message");
            Toast.makeText(v.getContext(), message,
                    Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        }
    }

}
