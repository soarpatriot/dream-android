package cn.dreamreality;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.dreamreality.holders.DreamHolder;
import cn.dreamreality.tasks.FetchDreamTask;
import cn.dreamreality.tasks.RefreshTask;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class DetailActivity extends ActionBarActivity {

    LinearLayout linearProcessLayout = null;
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
        String id = intent.getStringExtra("id");

        DreamHolder dreamHolder = new DreamHolder();
        dreamHolder.setDreamTextView((TextView) findViewById(R.id.dream_text_view));
        dreamHolder.setRealityTextView((TextView) findViewById(R.id.reality_text_view));
        dreamHolder.setIdTextView((TextView) findViewById(R.id.id_text_view));

        FetchDreamTask fetchDreamTask = new FetchDreamTask(this.getApplicationContext(),dreamHolder,linearProcessLayout);
        fetchDreamTask.execute(Long.valueOf(id));

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
