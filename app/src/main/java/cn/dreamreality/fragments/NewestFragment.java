package cn.dreamreality.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import cn.dreamreality.MainActivity;
import cn.dreamreality.R;
import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.handlers.PostHandler;
import cn.dreamreality.handlers.RefreshAddHandler;
import cn.dreamreality.handlers.RefreshHandler;
import cn.dreamreality.tasks.RefreshTask;

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


    private DreamListAdapter mAdapter;
    private PullToRefreshListView pullToRefreshView;
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
        pullToRefreshView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
        pullToRefreshView.setAdapter(mAdapter);

        final RefreshHandler refreshHandler = new RefreshHandler(rootView.getContext(),pullToRefreshView,mAdapter);

        final RefreshAddHandler refreshAddHandler = new RefreshAddHandler(rootView.getContext(),pullToRefreshView,mAdapter);
        RefreshTask refreshTask = new RefreshTask(refreshHandler);
        refreshTask.execute();

        pullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // Do work to refresh the list here.

                RefreshTask refreshTask = new RefreshTask(refreshHandler);
                refreshTask.execute();


            }
        });
        // Add an end-of-list listener
        pullToRefreshView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Log.i(this.getClass().toString(),"refresh last");
                RefreshTask refreshTask = new RefreshTask(refreshAddHandler);
                refreshTask.execute();
            }
        });
        return rootView;
    }


}
