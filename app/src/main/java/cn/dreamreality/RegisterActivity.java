package cn.dreamreality;

import android.content.Intent;
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
                Intent intent = null;
                String mobile = mobileText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
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
                    AVOSCloud.requestSMSCode(mobile, getString(R.string.app_name), getString(R.string.register_verify_code), 30);

                    intent = new Intent(v.getContext(), VerifyCodeActivity.class);
                    startActivity(intent);


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
