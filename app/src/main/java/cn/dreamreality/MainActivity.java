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
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import cn.dreamreality.widget.DropDownListView;
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

    private DropDownListView dropDownListView;

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








        mAdapter = new DreamListAdapter(context, dreamLists);
        dropDownListView = (DropDownListView)  this.findViewById(R.id.list_view);
        dropDownListView.setAdapter(mAdapter);

        RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, null,linearProcessLayout,RefreshTask.Type.REFRESH.ordinal());;
        refreshDreamTask.execute();

        dropDownListView.setOnDropDownListener(new DropDownListView.OnDropDownListener() {

            @Override
            public void onDropDown() {
                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, dropDownListView,null,RefreshTask.Type.REFRESH.ordinal());;
                refreshDreamTask.execute();

            }
        });

        // set on bottom listener
        dropDownListView.setOnBottomListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RefreshDreamTask refreshDreamTask  = new RefreshDreamTask(context, mAdapter, dropDownListView,null,RefreshTask.Type.ADD.ordinal());;
                refreshDreamTask.execute();
            }
        });
        dropDownListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //mPullRefreshListView = (PullToRefreshListView) this.findViewById(R.id.list_view);
        //mPullRefreshListView.setAdapter(mAdapter);
        //mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);






        /*mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {


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

        mPullRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mListViewFirstItem = 0;
            //listView中第一项的在屏幕中的位置
            private int mScreenY = 0;
            //是否向上滚动
            private boolean mIsScrollToUp = false;

            private boolean isScrollToUp = false;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING ){
                    onScrollDirectionChanged(isScrollToUp);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getChildCount() > 0)
                {

                    View childAt = view.getChildAt(view.getChildCount() -1 );
                    int[] location = new int[2];
                    childAt.getLocationOnScreen(location);
                    Log.d("onScroll", "firstVisibleItem= "+firstVisibleItem+" , y="+location[1]);

                    if(firstVisibleItem!=mListViewFirstItem)
                    {
                        if(firstVisibleItem>mListViewFirstItem)
                        {
                            Log.e("--->", "向上滑动");
                            isScrollToUp = true;
                        }else{
                            Log.e("--->", "向下滑动");
                            isScrollToUp = false;
                        }
                        mListViewFirstItem = firstVisibleItem;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1])
                        {
                            Log.i("--->", "->向上滑动");
                            isScrollToUp = true;
                        }
                        else if(mScreenY<location[1])
                        {
                            Log.i("--->", "->向下滑动");
                            isScrollToUp = false;
                        }
                        mScreenY = location[1];
                    }



                    if(mIsScrollToUp!=isScrollToUp)
                    {
                        //onScrollDirectionChanged(isScrollToUp);
                    }

                }
            }

            private void onScrollDirectionChanged(boolean isScrollToUp)
            {

            }
        });
*/

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

                intent = new Intent(this, NicknameActivity.class);
                intent.putExtra("mobile","18603331140");
                intent.putExtra("password","22143521");
                startActivity(intent);
                return true;
            case R.id.action_login_out:
                SettingsUtils.removeString(this.getApplicationContext(), "token");

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
