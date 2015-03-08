package cn.dreamreality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.dreamreality.R;
import cn.dreamreality.entities.DreamReality;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class DreamListAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<DreamReality> dreamsRealities;

    private final LayoutInflater mInflater;

    private DreamHolder dreamHolder;

    public DreamListAdapter(Context context, ArrayList<DreamReality> dreams) {
        mContext = context;
        dreamsRealities = dreams;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addData(List<DreamReality> list) {
        this.dreamsRealities.addAll(list);
        this.notifyDataSetChanged();
    }

    public List<DreamReality> getData() {
        return dreamsRealities;
    }

    public int getCount() {
        return dreamsRealities.size();
    }

    public Object getItem(int position) {
        return dreamsRealities.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            dreamHolder = new DreamHolder();

            v = mInflater.inflate(R.layout.list_dream, null);
            dreamHolder.dreamTextView = (TextView)v.findViewById(R.id.dream_text_view);

            v.setTag(dreamHolder);

        } else {
            dreamHolder = (DreamHolder) v.getTag();
        }

        initData(v, position);

        return v;
    }


    /**
     * @param position
     *            初始化数据
     */
    private void initData(View v, int position) {
        if (dreamsRealities == null || dreamsRealities.size() == 0) {
            return;
        }
        dreamHolder.dreamTextView.setText(dreamsRealities.get(position)
                .getDream());

    }

    /**
     * 组件集合类
     */
    private class DreamHolder {

        private TextView dreamTextView;
        private TextView realityTextView;

    }

}