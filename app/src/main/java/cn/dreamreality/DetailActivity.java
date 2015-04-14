package cn.dreamreality;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.dreamreality.handlers.LoginHandler;
import cn.dreamreality.holders.DreamHolder;
import cn.dreamreality.runners.LoginRunner;
import cn.dreamreality.tasks.FetchDreamTask;
import cn.dreamreality.tasks.RefreshTask;
import cn.dreamreality.tasks.UpDreamTask;
import cn.dreamreality.utils.SettingsUtils;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class DetailActivity extends ActionBarActivity {

    LinearLayout linearProcessLayout = null;

    private ImageButton upButton;

    private TextView dreamTextView;
    private TextView realityTextView;
    private TextView authorTextView;
    private TextView authorIdTextView;

    //CircularProgressBar circularProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        linearProcessLayout = (LinearLayout) findViewById(R.id.progress_layout);
        //circularProgressBar = (CircularProgressBar) findViewById(R.id.progress_circular);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String dream = intent.getStringExtra("dream");
        final String reality = intent.getStringExtra("reality");
        final String authorId = intent.getStringExtra("authorId");
        final String author = intent.getStringExtra("author");

        final String token = SettingsUtils.getSettings(this, "token");
        dreamTextView     = (TextView) findViewById(R.id.dream_text_view);
        realityTextView   = (TextView) findViewById(R.id.reality_text_view);
        authorTextView    = (TextView) findViewById(R.id.author_text_view);
        authorIdTextView  = (TextView) findViewById(R.id.author_id_text_view);

        dreamTextView.setText(dream);
        realityTextView.setText(reality);
        authorIdTextView.setText(authorId);
        authorTextView.setText(author);

        //DreamHolder dreamHolder = new DreamHolder();
        //dreamHolder.setDreamTextView();
        //dreamHolder.setRealityTextView((TextView) findViewById(R.id.reality_text_view));
        //dreamHolder.setIdTextView((TextView) findViewById(R.id.id_text_view));
        upButton = (ImageButton) findViewById(R.id.up_btn);

        //FetchDreamTask fetchDreamTask = new FetchDreamTask(this.getApplicationContext(),dreamHolder,linearProcessLayout);
        //fetchDreamTask.execute(Long.valueOf(id));

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(token) || TextUtils.isEmpty(id)) {
                    Toast.makeText(v.getContext(), R.string.vote_after_login,
                            Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    UpDreamTask upDreamTask = new UpDreamTask(v.getContext());
                    upDreamTask.execute(id,token);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
