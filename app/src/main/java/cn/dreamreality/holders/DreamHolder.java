package cn.dreamreality.holders;

import android.widget.TextView;

/**
 * Created by liuhaibao on 15/3/25.
 */
public class DreamHolder {

    private TextView dreamTextView;
    private TextView realityTextView;
    private TextView idTextView;

    public TextView getRealityTextView() {
        return realityTextView;
    }

    public void setRealityTextView(TextView realityTextView) {
        this.realityTextView = realityTextView;
    }

    public TextView getIdTextView() {
        return idTextView;
    }

    public void setIdTextView(TextView idTextView) {
        this.idTextView = idTextView;
    }



    public TextView getDreamTextView() {
        return dreamTextView;
    }

    public void setDreamTextView(TextView dreamTextView) {
        this.dreamTextView = dreamTextView;
    }
}
