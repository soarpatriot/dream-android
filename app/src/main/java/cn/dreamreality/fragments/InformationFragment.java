package cn.dreamreality.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import cn.dreamreality.R;
import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.handlers.RefreshHandler;
import cn.dreamreality.runners.DreamRunner;

/**
 * Created by liuhaibao on 15/2/22.
 */
public class InformationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener   {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private DreamListAdapter mAdapter;

    private ArrayList<DreamReality> dreamLists  = new ArrayList<DreamReality>();
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InformationFragment newInstance(int sectionNumber) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public InformationFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListView = (ListView) rootView.findViewById(R.id.id_listview);
        mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.id_swipe_ly);


        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);



        /*mAdapter = new DreamListAdapter(this.getActivity().getApplicationContext(), dreamLists);
        mListView.setAdapter(mAdapter);

        RefreshHandler refreshHandler = new RefreshHandler(this.getActivity().getApplication(),mAdapter);
        DreamRunner runner = new DreamRunner(0, refreshHandler);
        runner.start();
*/
        //mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, mDatas);



        return rootView;
    }



    public void onRefresh()
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSwipeLayout.setRefreshing(false);

            }
        }, 3000);
    }

}
