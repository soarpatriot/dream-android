package cn.dreamreality;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.dreamreality.tasks.RegisterTask;


public class NicknameActivity extends ActionBarActivity {

    private TextView mobileText;
    private TextView nickNameText;
    private Button finishBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

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

        nickNameText = (TextView)findViewById(R.id.nick_name);
        finishBtn = (Button) findViewById(R.id.finish_register);
        mobileText = (TextView) findViewById(R.id.mobile_text);

        Intent intent = getIntent();
        final String mobile = intent.getStringExtra("mobile");
        final String password = intent.getStringExtra("password");
        mobileText.setText(mobile);


        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = nickNameText.getText().toString().trim();
                if(TextUtils.isEmpty(nickname)){
                    Toast.makeText(v.getContext(), R.string.nick_name_blank,
                            Toast.LENGTH_SHORT).show();
                }else{
                    RegisterTask registerTask = new RegisterTask(v.getContext());

                    registerTask.execute(mobile,nickname,password);
                }


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nickname, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
