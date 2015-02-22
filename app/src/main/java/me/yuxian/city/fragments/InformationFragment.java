package me.yuxian.city.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yuxian.city.R;

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
    private ArrayAdapter mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",
            "HTML"));

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };
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
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

        return rootView;
    }

    

    public void onRefresh()
    {
        // Log.e("xxx", Thread.currentThread().getName());
        // UI Thread

        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

    }


}
