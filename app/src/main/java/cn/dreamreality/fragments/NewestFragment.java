package cn.dreamreality.fragments;


import android.content.Context;
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
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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

    /**
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_newest, container, false);

        final Context context = rootView.getContext();

        mAdapter = new DreamListAdapter(this.getActivity().getApplicationContext(), dreamLists);
        pullToRefreshView = (PullToRefreshListView) rootView.findViewById(R.id.pull_to_refresh_listview);
        pullToRefreshView.setAdapter(mAdapter);

        //final RefreshHandler refreshHandler = new RefreshHandler(rootView.getContext(),pullToRefreshView,mAdapter);

        //final RefreshAddHandler refreshAddHandler = new RefreshAddHandler(rootView.getContext(),pullToRefreshView,mAdapter);


        RefreshTask refreshTask = new RefreshTask(rootView.getContext(),mAdapter,pullToRefreshView,RefreshTask.Type.REFRESH.ordinal());
        refreshTask.execute();

        pullToRefreshView.setMode(PullToRefreshBase.Mode.BOTH);


        pullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
                RefreshTask refreshTask = new RefreshTask(context,mAdapter,pullToRefreshView,RefreshTask.Type.REFRESH.ordinal());
                refreshTask.execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {

                RefreshTask refreshTask = new RefreshTask(context,mAdapter,pullToRefreshView,RefreshTask.Type.ADD.ordinal());
                refreshTask.execute();
            }
        });

        return rootView;
    }


}
