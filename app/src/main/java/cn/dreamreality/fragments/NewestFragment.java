package cn.dreamreality.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import cn.dreamreality.R;
import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.handlers.DreamListHandler;
import cn.dreamreality.runners.DreamRunner;
import cn.dreamreality.tasks.PostTask;
import cn.dreamreality.utils.SettingsUtils;

/**
 * Created by liuhaibao on 15/3/9.
 */
public class NewestFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int REFRESH_COMPLETE = 0X110;

    private ListView mListView;
    private DreamListAdapter mAdapter;

    private ArrayList<DreamReality> dreamLists  = new ArrayList<DreamReality>();

    private int count = 0;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewestFragment newInstance(int sectionNumber) {
        NewestFragment fragment = new NewestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NewestFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newest, container, false);



        mAdapter = new DreamListAdapter(this.getActivity().getApplicationContext(), dreamLists);
        final PullToRefreshListView pullToRefreshView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
        pullToRefreshView.setAdapter(mAdapter);

        if(count++ <= 0){
            PostTask postTask = new PostTask(mAdapter,pullToRefreshView);
            postTask.execute();
        }


        pullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // Do work to refresh the list here.

                PostTask postTask = new PostTask(mAdapter,pullToRefreshView);
                postTask.execute();


            }
        });

        return rootView;
    }


}
