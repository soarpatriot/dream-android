package cn.dreamreality.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import cn.dreamreality.R;
import cn.dreamreality.adapter.DreamListAdapter;
import cn.dreamreality.entities.DreamReality;
import cn.dreamreality.tasks.RefreshDreamTask;
import cn.dreamreality.tasks.RefreshTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UncompletedFragment} interface
 * to handle interaction events.
 * Use the {@link UncompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UncompletedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private DreamListAdapter mAdapter;
    private ListView mUncopmletedListView;
    private ArrayList<DreamReality> dreamLists  = new ArrayList<DreamReality>();

    private int count = 0;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UncompletedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UncompletedFragment newInstance(String param1, String param2) {
        UncompletedFragment fragment = new UncompletedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UncompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }**/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_uncompleted, container, false);
        final Context context = rootView.getContext();

        mAdapter = new DreamListAdapter(context, dreamLists);
        mUncopmletedListView = (ListView) rootView.findViewById(R.id.uncompleted_list_view);
        mUncopmletedListView.setAdapter(mAdapter);

        RefreshDreamTask refreshDreamTask = new RefreshDreamTask(context,null,mAdapter,mUncopmletedListView,RefreshTask.Type.REFRESH.ordinal());
        refreshDreamTask.execute();

        mSwipyRefreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

            Log.d("MainActivity", "Refresh triggered at "
                    + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                RefreshDreamTask refreshDreamTask;
                if(direction == SwipyRefreshLayoutDirection.TOP) {
                    refreshDreamTask = new RefreshDreamTask(context, mSwipyRefreshLayout, mAdapter, mUncopmletedListView, RefreshTask.Type.REFRESH.ordinal());
                }else {
                    refreshDreamTask = new RefreshDreamTask(context, mSwipyRefreshLayout, mAdapter, mUncopmletedListView, RefreshTask.Type.ADD.ordinal());

                }

                refreshDreamTask.execute();
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
           // mListener.onFragmentInteraction(uri);
        //}
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        //
        public void onFragmentInteraction(Uri uri);
    }*/

}
