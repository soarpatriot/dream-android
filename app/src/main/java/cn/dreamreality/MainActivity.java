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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.adapter.SectionsPagerAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.interfaces.OnPrcessing;
import cn.dreamreality.listeners.InfiniteScrollListener;
import cn.dreamreality.tasks.RefreshDreamTask;
import cn.dreamreality.tasks.RefreshTask;
import cn.dreamreality.utils.SettingsUtils;
import cn.dreamreality.view.SlidingTabLayout;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import static com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;


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
    private PtrClassicFrameLayout ptrFrame;

    private OnPrcessing onPrcessing;
    private boolean pullUpLoading = false;
    private Menu menu;

    private PopupMenu popupMenu;

    private PullToRefreshListView mPullRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //menu = (Menu)findViewById(R.menu.menu_main);

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



        String footerDefaultText = context.getString(R.string.drop_down_list_footer_default_text);
        String footerLoadingText = context.getString(R.string.drop_down_list_footer_loading_text);
        String footerNoMoreText = context.getString(R.string.drop_down_list_footer_no_more_text);



        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //String footerLayout = (RelativeLayout) inflater.inflate(R.layout.drop_down_list_footer);
        //inflater.inflate()
        //String footerButton = (Button)footerLayout.findViewById (R.id.drop_down_list_footer_button);
        //String footerButton.setDrawingCacheBackgroundColor(0);
        //String footerButton.setEnabled(true);

        //String footerProgressBar = (ProgressBar)footerLayout.findViewById(R.id.drop_down_list_footer_progress_bar);
        //ptrFrame.addFooterView(footerLayout);

        // header
        //final StoreHouseHeader header = new StoreHouseHeader(context);
        //header.initWithStringArray(R.array.storehouse);
        //header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);

        //header.initWithString("梦想照近显示");
        // header
        //final StoreHouseHeader header = (StoreHouseHeader)this.findViewById(R.id.store_house_ptr_image_content);
        //ptrFrame = (PtrClassicFrameLayout) this.findViewById(R.id.dream_ptr_frame);

        //ptrFrame.setHeaderView(header);
        mAdapter = new DreamListAdapter(context, dreamLists);
        mPullRefreshListView = (PullToRefreshListView) this.findViewById(R.id.uncompleted_list_view);
        mPullRefreshListView.setAdapter(mAdapter);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //setPullAndLoadMoreListener();



        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, mPullRefreshListView,linearProcessLayout,RefreshTask.Type.REFRESH.ordinal());;
        refreshDreamTask.execute();


        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {


            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, mPullRefreshListView,null,RefreshTask.Type.REFRESH.ordinal());;
                refreshDreamTask.execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, mPullRefreshListView,null,RefreshTask.Type.ADD.ordinal());;
                refreshDreamTask.execute();
            }
        });




        //mUncopmletedListView.setOnScrollListener(new InfiniteScrollListener());
        //dialog = ProgressDialog.show(this,"",true);

        /*LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout footerLayout = (RelativeLayout) inflater.inflate(R.layout.drop_down_list_footer,null);
        mUncopmletedListView.addFooterView(footerLayout);

        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, null, mAdapter, mUncopmletedListView,linearProcessLayout, null,true,RefreshTask.Type.REFRESH.ordinal());;
        refreshDreamTask.execute();



        mUncopmletedListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            boolean loadMore;
            boolean loading = false;
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                loadMore = ((firstVisibleItem + visibleItemCount >= totalItemCount) && firstVisibleItem >0 && totalItemCount > 0);
                if(loadMore && !pullUpLoading){
                    pullUpLoading = true;
                    RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, null, mAdapter, mUncopmletedListView,linearProcessLayout, onPrcessing,loading,RefreshTask.Type.ADD.ordinal());
                    refreshDreamTask.execute();
                }


            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }
        });*/

        /**
        // load more container
        final LoadMoreListViewContainer loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();


        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, null, mAdapter, mUncopmletedListView,null,loadMoreListViewContainer, RefreshTask.Type.ADD.ordinal());;
                refreshDreamTask.execute();
            }
        });**/




        /*ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, frame, mAdapter, mUncopmletedListView,null,null,true, RefreshTask.Type.REFRESH.ordinal());;
                refreshDreamTask.execute();

                *//**
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);**//*
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return true;
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });*/









    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;


        //popupMenu.show();



        getMenuInflater().inflate(R.menu.menu_main, menu);

        String token =  SettingsUtils.getSettings(this, "token");
        MenuItem itemLogin = (MenuItem) menu.findItem(R.id.action_login);
        MenuItem itemRegister = (MenuItem) menu.findItem(R.id.action_register);
        MenuItem itemLoginOut = (MenuItem) menu.findItem(R.id.action_login_out);
        //MenuItem itemSettings = (MenuItem) menu.findItem(R.id.action_settings);

        //View menuItemView = findViewById(R.id.action_settings);
        //popupMenu = new PopupMenu(this, menuItemView);
        //popupMenu.inflate(R.menu.menu_main_popup);

        if(!TextUtils.isEmpty(token)){


            itemLogin.setVisible(false);
            itemRegister.setVisible(false);
            this.invalidateOptionsMenu();
        }else{
            itemLoginOut.setVisible(false);
            this.invalidateOptionsMenu();
        }



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
                intent.putExtra("password","22143521");
                startActivity(intent);
                return true;
            case R.id.action_login_out:
                SettingsUtils.removeString(this.getApplicationContext(), "token");

                return true;
            case R.id.action_settings:
                //popupMenu.show();
                //showSettings(item.getActionView());

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

    public void showSettings(View v){
        PopupMenu popup = new PopupMenu(this, v);
        //Inflating the Popup using xml file
        //popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main_popup);

        //registering popup with OnMenuItemClickListener


        popup.show(); //showing popup menu

    }




    /*private void setPullAndLoadMoreListener() {
        // 1.set listener
        // Set a listener to be invoked when the list should be refreshed.
        ((PullAndLoadListView) mPullRefreshListView)
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

                    public void onRefresh() {
                        // Do work to refresh the list here.
                        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, mPullRefreshListView,null,RefreshTask.Type.REFRESH.ordinal());;
                        refreshDreamTask.execute();

                    }
                });

        // set a listener to be invoked when the list reaches the end
        ((PullAndLoadListView) mPullRefreshListView)
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {

                    public void onLoadMore() {
                        // Do the work to load more items at the end of list
                        // here
                        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, mPullRefreshListView,null,RefreshTask.Type.ADD.ordinal());;
                        refreshDreamTask.execute();

                    }
                });
    }*/
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
