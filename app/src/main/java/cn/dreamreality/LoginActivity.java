package cn.dreamreality;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.dreamreality.handlers.LoginHandler;
import cn.dreamreality.runners.LoginRunner;


public class LoginActivity extends ActionBarActivity {

    TextView register_txt;
    EditText username_txt;
    EditText password_txt;
    Button sign_in_button;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register_txt = (TextView) this.findViewById(R.id.register_txt);
        username_txt = (EditText) this.findViewById(R.id.username_txt);
        password_txt = (EditText) this.findViewById(R.id.password_txt);
        sign_in_button = (Button) this.findViewById(R.id.sign_in_button);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(context, R.string.name_or_password_blank,
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    LoginHandler loginHandler = new LoginHandler(v);
                    LoginRunner runner = new LoginRunner(username,password,loginHandler);
                    runner.start();
                }
            }
        });

        register_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
