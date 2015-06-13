package cn.dreamreality.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.dreamreality.DetailActivity;
import cn.dreamreality.DreamActivity;
import cn.dreamreality.MainActivity;
import cn.dreamreality.R;
import cn.dreamreality.entities.DreamReality;

import cn.dreamreality.tasks.UpDreamTask;
import cn.dreamreality.utils.SettingsUtils;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class DreamListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<DreamReality> dreamsRealities;

    private LayoutInflater mInflater;


    private Intent intent = new Intent();

    public DreamListAdapter(Context context, ArrayList<DreamReality> dreams) {
        mContext = context;
        dreamsRealities = dreams;
        //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater = LayoutInflater.from(mContext);
        intent.setClass(context,DetailActivity.class);
    }

    public void setData(List<DreamReality> list) {
        this.dreamsRealities.clear();
        this.dreamsRealities.addAll(list);
        this.notifyDataSetChanged();
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

    public Object getLastItem(){

        if (getCount()>0){
            return dreamsRealities.get(getCount() -1 );
        }

        return null;
    }

    public long getLastItemId(){

        if(null != getLastItem()) {
            return ((DreamReality) getLastItem()).getId();
        }
        return 0l;

    }


    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup parent) {
        DreamHolder dreamHolder = null;
        if (v == null) {

            dreamHolder = new DreamHolder();
            v = mInflater.inflate(R.layout.list_dream, null);
            dreamHolder.dreamTextView = ( (TextView)v.findViewById(R.id.dream_text_view) );
            dreamHolder.idTextView = (  (TextView)v.findViewById(R.id.id_text_view) );

            dreamHolder.realityTextView = ((TextView) v.findViewById(R.id.reality_text_view));
            dreamHolder.authorIdTextView = ((TextView) v.findViewById(R.id.author_id_text_view));
            dreamHolder.authorTextView = ((TextView) v.findViewById(R.id.author_text_view));

            dreamHolder.upButton = (  (ImageButton)v.findViewById(R.id.up_btn));
            v.setTag(dreamHolder);

        } else {
            dreamHolder = (DreamHolder) v.getTag();

        }
        initData(v, position,dreamHolder);


        return v;
    }


    /**
     * @param position
     *            初始化数据
     */
    private void initData(View view, int position, final DreamHolder dreamHolder) {
        final View view1 = view;
        final String token = SettingsUtils.getSettings(mContext, "token");

        if (dreamsRealities == null || dreamsRealities.size() == 0) {
            return;
        }

        final String id = String.valueOf(dreamsRealities.get(position)
                .getId());
        final String dream = dreamsRealities.get(position)
                .getDream();
        final String reality = dreamsRealities.get(position)
                .getReality();
        final String authorId = String.valueOf(dreamsRealities.get(position).getAuthorId());
        final String author = dreamsRealities.get(position).getAuthor();

        dreamHolder.dreamTextView.setText(dream);
        dreamHolder.realityTextView.setText(reality);
        dreamHolder.idTextView.setText(id);
        dreamHolder.authorIdTextView.setText(authorId);
        dreamHolder.authorTextView.setText(author);


        final Animation scaleAnimation =  AnimationUtils.loadAnimation(mContext, R.anim.myanim);
        //dreamHolder.getUpButton().startAnimation(scaleAnimation);
        //dreamHolder.getUpButton().setAnimation(scaleAnimation);
        Resources resources = mContext.getResources();
        final Drawable btnDrawable = resources.getDrawable(R.drawable.up);
        dreamHolder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dreamHolder.upButton.startAnimation(scaleAnimation);
                //dreamHolder.upButton.setImageState(R.drawable.thumb_up_selector,true);
                //imgBtn.startAnimation(scaleAnimation);
                dreamHolder.upButton.setImageDrawable(btnDrawable);
                if (TextUtils.isEmpty(token) || TextUtils.isEmpty(id)) {
                    Toast.makeText(v.getContext(), R.string.vote_after_login,
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    UpDreamTask upDreamTask = new UpDreamTask(v.getContext());
                    upDreamTask.execute(id, token);
                }

            }

        });

        dreamHolder.dreamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("author", author);
                intent.putExtra("id", id);
                intent.putExtra("dream", dream);
                intent.putExtra("reality", reality);
                intent.putExtra("authorId", authorId);

                //startActivities(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("id", id);
                mContext.startActivity(intent);

                //Intent intent = new Intent();
                //Intent传递参数
                //intent.putExtra("testIntent", "123");
                //intent.setClass(FirstActivity.this, SecondActivity.class);
                //FirstActivity.this.startActivity(intent);
            }
        });

    }


    static class DreamHolder {

        public TextView dreamTextView;
        public TextView realityTextView;
        public TextView idTextView;

        public ImageButton upButton;
        public TextView percentageTextView;
        public TextView authorIdTextView;
        public ImageButton userAvatarButton;
        public TextView authorTextView;
        public TextView votedTextView;
    }
}