package cn.dreamreality;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;

import cn.dreamreality.utils.Verify;


public class RegisterActivity extends ActionBarActivity {

    private Button registerBtn;
    private TextView mobileText;
    private TextView passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        mobileText = (TextView) findViewById(R.id.mobile);
        registerBtn = (Button) findViewById(R.id.register_button);
        passwordText = (TextView) findViewById(R.id.password);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = null;
                final String mobile = mobileText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();
                if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)){
                    Toast.makeText(v.getContext(), R.string.mobile_password_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Verify.isMobileNO(mobile)){
                    Toast.makeText(v.getContext(), R.string.mobile_number_format_error,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
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
                            }else{
                                Intent intent = null;
                                intent = new Intent(view.getContext(), VerifyCodeActivity.class);
                                intent.putExtra("mobile",mobile);
                                intent.putExtra("password",password);
                                startActivity(intent);
                            }
                        }
                    }.execute();




                }catch (Exception e){
                    Toast.makeText(v.getContext(), R.string.name_or_password_blank,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


}
