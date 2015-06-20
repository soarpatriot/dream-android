package cn.dreamreality.listeners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import cn.dreamreality.R;

/**
 * Created by liuhaibao on 15/6/14.
 */
public class InfiniteScrollListener implements AbsListView.OnScrollListener {

    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private int currentScrollState;
    private boolean loadMore;
    private boolean scrollDown;

    private int oldTop = 0;
    private int oldFirstVisibleItem = 0;

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;

        loadMore = ((firstVisibleItem + visibleItemCount >= totalItemCount) && firstVisibleItem >0 && totalItemCount > 0);


        scrollDown = isScrollDown(view,firstVisibleItem,visibleItemCount,totalItemCount);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.currentScrollState = scrollState;

        if(scrollState == SCROLL_STATE_IDLE){
            if(loadMore && scrollDown){
                System.out.println("loading..........");
            }
        }

    }

    private void isScrollCompleted() {

    }

    private boolean isScrollDown(AbsListView absListView, int firstVisibleItem,int visibleItemCount, int totalItemCount){
        View view = absListView.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();

        //int distanceFromFirstCellToTop = .getFirstVisiblePosition() * firstCell.getHeight() - firstCell.getTop();

        System.out.println("top");
        boolean down = true;

        if (firstVisibleItem == oldFirstVisibleItem) {
            if (top > oldTop) {
                down = false;
                System.out.println("top > oldTop");
            } else if (top < oldTop) {
                down = true;
                System.out.println("top < oldTop");
            }
        } else {
            if (firstVisibleItem < oldFirstVisibleItem) {
                down = false;
                System.out.println("firstVisibleItem < oldFirstVisibleItem");
            } else {
                down = true;
                System.out.println("firstVisibleItem > oldFirstVisibleItem");

            }
        }

        oldTop = top;
        oldFirstVisibleItem = firstVisibleItem;

        return down;
    }

    private void initOnBottomStyle() {


    }


}
