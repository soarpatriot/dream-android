package cn.dreamreality.holders;

import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by liuhaibao on 15/3/25.
 */
public class DreamHolder {

    private TextView dreamTextView;
    private TextView realityTextView;
    private TextView idTextView;

    private ImageButton upButton;
    private TextView percentageTextView;
    private TextView authorIdTextView;
    private ImageButton userAvatarButton;
    private TextView authorTextView;
    private TextView votedTextView;

    public TextView getVotedTextView() {
        return votedTextView;
    }

    public void setVotedTextView(TextView votedTextView) {
        this.votedTextView = votedTextView;
    }

    public ImageButton getUpButton() {
        return upButton;
    }

    public void setUpButton(ImageButton upButton) {
        this.upButton = upButton;
    }

    public TextView getPercentageTextView() {
        return percentageTextView;
    }

    public void setPercentageTextView(TextView percentageTextView) {
        this.percentageTextView = percentageTextView;
    }

    public TextView getAuthorIdTextView() {
        return authorIdTextView;
    }

    public void setAuthorIdTextView(TextView authorIdTextView) {
        this.authorIdTextView = authorIdTextView;
    }

    public ImageButton getUserAvatarButton() {
        return userAvatarButton;
    }

    public void setUserAvatarButton(ImageButton userAvatarButton) {
        this.userAvatarButton = userAvatarButton;
    }

    public TextView getAuthorTextView() {
        return authorTextView;
    }

    public void setAuthorTextView(TextView authorTextView) {
        this.authorTextView = authorTextView;
    }

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
