package cn.dreamreality;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.dreamreality.handlers.LoginHandler;
import cn.dreamreality.handlers.PostHandler;
import cn.dreamreality.runners.LoginRunner;
import cn.dreamreality.runners.PostRunner;
import cn.dreamreality.utils.SettingsUtils;


public class DreamActivity extends ActionBarActivity {

    private EditText dream_edit_text;
    private EditText reality_edit_text;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream);

        dream_edit_text = (EditText)findViewById(R.id.dream_edit_text);
        reality_edit_text = (EditText)findViewById(R.id.reality_edit_text);
        submit_btn = (Button)findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dream_text = dream_edit_text.getText().toString();
                String reality_text = reality_edit_text.getText().toString();

                PostHandler postHandler = new PostHandler(v);
                String token = SettingsUtils.getSettings(v.getContext().getApplicationContext(),"token");
                PostRunner runner = new PostRunner(dream_text,reality_text,token,postHandler);
                runner.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dream, menu);
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
