package cn.dreamreality;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

import cn.dreamreality.utils.Config;
import cn.dreamreality.utils.MyCountTimer;
import cn.dreamreality.utils.Verify;


public class VerifyCodeActivity extends ActionBarActivity {

    private Button nextBtn;
    private Button reverifyBtn;
    private TextView verifyCodeText;
    private TextView mobileText;
    private MyCountTimer myCountTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //What to do on back clicked
            }
        });

        AVOSCloud.initialize(this, Config.LEANCLOUND_APPID,Config.LEANCLOUND_APPKEY);


        mobileText = (TextView)findViewById(R.id.mobile);
        reverifyBtn = (Button)findViewById(R.id.resend_verify);
        nextBtn  = (Button) findViewById(R.id.next);
        verifyCodeText = (TextView) findViewById(R.id.verify_code);

        myCountTimer = new MyCountTimer(reverifyBtn, 0xfff30008, 0xff969696);//传入了文字颜色值
        myCountTimer.start();


        Intent intent = getIntent();
        final String mobile = intent.getStringExtra("mobile");
        final String password = intent.getStringExtra("password");

        mobileText.setText(mobile);


        reverifyBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final View view = v;
                new AsyncTask<Void, Void, Void>() {
                    boolean res;

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            AVOSCloud.requestSMSCode(mobile, getString(R.string.app_name), getString(R.string.register_verify_code), 30);

                            res = true;
                        } catch (AVException e) {
                            e.printStackTrace();
                            res = false;
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if (!res) {
                            Toast.makeText(view.getContext(), R.string.verify_code_system_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute();



                myCountTimer.start();

            }
        });

         nextBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final Intent intent = null;
                 final View view = v;
                 final String mobile = mobileText.getText().toString().trim();
                 String verifyCode = verifyCodeText.getText().toString().trim();
                 if(TextUtils.isEmpty(verifyCode) ){
                     Toast.makeText(v.getContext(), R.string.verify_code_blank,
                             Toast.LENGTH_SHORT).show();
                     return;
                 }

                 AVOSCloud.verifySMSCodeInBackground(verifyCode,mobile, new AVMobilePhoneVerifyCallback() {

                     @Override
                     public void done(AVException e) {
                         //此处可以完成用户想要完成的操作
                         if(e == null){
                             System.out.println("sdfsdf");
                             Log.i("","sdfasdf");
                             Intent intent = new Intent(view.getContext(), NicknameActivity.class);
                             intent.putExtra("mobile",mobile);
                             intent.putExtra("password",password);
                             startActivity(intent);

                         }else{
                             e.printStackTrace();
                             Toast.makeText(view.getContext(), R.string.verify_code_error,
                                     Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
         });
        //mobileText = (TextView) findViewById(R.id.mobile);
        //nextBtn = (Button) findViewById(R.id.register_button);
        //reverifyBtn = (TextView) findViewById(R.id.password);

        /**
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileText.getText().toString().trim();
                //String password = passwordText.getText().toString().trim();
                if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)){
                    Toast.makeText(v.getContext(), R.string.mobile_password_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Verify.isMobileNO(mobile)){
                    Toast.makeText(v.getContext(), R.string.name_or_password_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    AVOSCloud.requestSMSCode(mobile, getString(R.string.app_name), getString(R.string.register_verify_code), 30);


                }catch (Exception e){
                    Toast.makeText(v.getContext(), R.string.name_or_password_blank,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });**/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


}
