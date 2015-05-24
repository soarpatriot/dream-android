package cn.dreamreality;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.adapter.SectionsPagerAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.tasks.RefreshDreamTask;
import cn.dreamreality.tasks.RefreshTask;
import cn.dreamreality.utils.SettingsUtils;
import cn.dreamreality.view.SlidingTabLayout;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    //SectionsPagerAdapter mSectionsPagerAdapter;
    //static final String LOG_TAG = "SlidingTabsBasicFragment";
    //private SlidingTabLayout mSlidingTabLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    //ViewPager mViewPager;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private DreamListAdapter mAdapter;
    private ListView mUncopmletedListView;
    private ArrayList<DreamReality> dreamLists  = new ArrayList<DreamReality>();
    private Context context;

    private LinearLayout linearProcessLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        linearProcessLayout = (LinearLayout) findViewById(R.id.progress_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setSupportActionBar(toolbar);
        // Set up the action bar.

        final ActionBar actionBar = getSupportActionBar();

        mAdapter = new DreamListAdapter(context, dreamLists);
        mUncopmletedListView = (ListView) this.findViewById(R.id.uncompleted_list_view);
        mUncopmletedListView.setAdapter(mAdapter);

        //dialog = ProgressDialog.show(this,"",true);
        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, null, mAdapter, mUncopmletedListView,linearProcessLayout, RefreshTask.Type.REFRESH.ordinal());;
        refreshDreamTask.execute();

        mSwipyRefreshLayout = (SwipyRefreshLayout) this.findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                RefreshDreamTask refreshDreamTask;
                if(direction == SwipyRefreshLayoutDirection.TOP) {
                    refreshDreamTask = new RefreshDreamTask(context, mSwipyRefreshLayout, mAdapter, mUncopmletedListView, null, RefreshTask.Type.REFRESH.ordinal());
                }else {
                    refreshDreamTask = new RefreshDreamTask(context, mSwipyRefreshLayout, mAdapter, mUncopmletedListView,null, RefreshTask.Type.ADD.ordinal());

                }

                refreshDreamTask.execute();
            }
        });


        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        //mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        //mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        //mSlidingTabLayout.setViewPager(mViewPager);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        switch (id ) {

            case R.id.action_login:

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                return true;
            case R.id.action_dream:

                String token =  SettingsUtils.getSettings(this.getApplicationContext(), "token");

                if(TextUtils.isEmpty(token)){
                    Toast.makeText(this.getApplicationContext(), R.string.no_login_tips,
                            Toast.LENGTH_SHORT).show();
                }else{
                    intent = new Intent(this, DreamActivity.class);
                    startActivity(intent);
                }


                return true;
            case R.id.action_register:

                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_verify:

                intent = new Intent(this, VerifyCodeActivity.class);
                intent.putExtra("mobile","18603331140");
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

        //noinspection SimplifiableIfStaØtement
        /**
        if (id == R.id.action_login) {
            return true;
        }
        **/
    }


    /***
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    **/


}
