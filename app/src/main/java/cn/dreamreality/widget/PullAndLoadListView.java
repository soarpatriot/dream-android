package cn.dreamreality.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import cn.dreamreality.R;

/*
 * Copyright (C) 2012 Fabian Leon Ortega <http://orleonsoft.blogspot.com/,
 *  http://yelamablog.blogspot.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PullAndLoadListView extends PullToRefreshListView {

	protected static final String TAG = "LoadMoreListView";
	private View mFooterView;
	private OnScrollListener mOnScrollListener;
	private OnLoadMoreListener mOnLoadMoreListener;
	private onTopListener mOnTopListener;

	/**
	 * If is loading now.
	 */
	private boolean mIsLoading;

	private int mCurrentScrollState;
	private int lastItemIndex;
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition;// 标记上次滑动位置
	private boolean down = false;
	public PullAndLoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public PullAndLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullAndLoadListView(Context context) {
		super(context);
		init(context);
	}

	/**
	 * 判断它的loading状态
	 * @return
	 */
	public boolean getLoading() {
		return mFooterView.getVisibility() == View.VISIBLE;
	}

	private void init(Context context) {
		mFooterView = View.inflate(context, R.layout.main_load_more_footer, null);
		addFooterView(mFooterView);
		hideFooterView();
		/*
		 * Must use super.setOnScrollListener() here to avoid override when call
		 * this view's setOnScrollListener method
		 */
		super.setOnScrollListener(superOnScrollListener);
	}

	/**
	 * Hide the load more view(footer view)
	 */
	private void hideFooterView() {
		Log.e("test", "prepare hide foot view");
		if (mFooterView != null){
			mFooterView.setVisibility(View.GONE);
			Log.e("test", "hide foot view");
		}
			
		if (mFooterView != null && getCount() <= 0) {
			removeFooterView(mFooterView);
		}
	}

	public void removeFooter() {
		if (mFooterView != null)
			removeFooterView(mFooterView);
	}

	/**
	 * Show load more view
	 */
	public void showFooterView() {
		if (getFooterViewsCount() < 1)
			addFooterView(mFooterView);
		mFooterView.setVisibility(View.VISIBLE);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;
	}

	/**
	 * Set load more listener, usually you should get more data here.
	 * 
	 * @param listener OnLoadMoreListener
	 * @see cn.dreamreality.widget.PullAndLoadListView.OnLoadMoreListener
	 */
	public void setOnLoadMoreListener(OnLoadMoreListener listener) {
		mOnLoadMoreListener = listener;
	}

	public void setOnTopListener(onTopListener listener) {
		mOnTopListener = listener;
	}

	/**
	 * When complete load more data, you must use this method to hide the footer
	 * view, if not the footer view will be shown all the time.
	 */
	public void onLoadMoreComplete() {
		mIsLoading = false;
		hideFooterView();
	}

	private OnScrollListener superOnScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (!mIsLoading && scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastItemIndex == totalCount - 1) {

				mIsLoading = true;
				showFooterView();
				if (mOnLoadMoreListener != null) {
					mOnLoadMoreListener.onLoadMore();
				}
			}

			mCurrentScrollState = scrollState;
			// Avoid override when use setOnScrollListener
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_FLING:
				if (getLastVisiblePosition() == (getCount() - 1) && down) {
					if (mOnTopListener != null) {
						mOnTopListener.onTopListener(1);
					}
				} else {
					if (mOnTopListener != null) {
						mOnTopListener.onTopListener(2);
					}
				}
				break;
			case OnScrollListener.SCROLL_STATE_IDLE:
				scrollFlag = false;
				//				if (getLastVisiblePosition() == (getCount() - 1)&&down) {
				//					if (mOnTopListener != null) {
				//						mOnTopListener.onTopListener(1);
				//					}
				//				}else{
				//					if (mOnTopListener != null) {
				//						mOnTopListener.onTopListener(2);
				//					}
				//				}
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				// 判断滚动到底部  
				//				if (getLastVisiblePosition() == (getCount() - 1)) {
				//					if (mOnTopListener != null) {
				//						mOnTopListener.onTopListener(0);
				//					}
				//				}
				scrollFlag = true;
				// 判断滚动到顶部  
				if (getFirstVisiblePosition() == 0 && down) {
					if (mOnTopListener != null) {
						mOnTopListener.onTopListener(1);
					}
				} else {
					if (mOnTopListener != null) {
						mOnTopListener.onTopListener(2);
					}
				}

				break;
			}
		}

		private int totalCount;
		private int lvIndext;
		private int scrolled;

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			totalCount = totalItemCount;
			lastItemIndex = firstVisibleItem + visibleItemCount - 1;
			//			Logger.d("Vine", "lastItemIndex =" + lastItemIndex);
			//			Logger.d("Vine", "getCount =" + totalCount);
			//			Logger.d("Vine", "mIsLoading=" + mIsLoading);
			//			Logger.d("Vine", "mCurrentScrollState=" + mCurrentScrollState);
						if (mOnScrollListener != null) {
							mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
						}
			//			// The count of footer view will be add to visibleItemCount also are
			//			// added to totalItemCount
			//			if (visibleItemCount == totalItemCount) {
			//				// If all the item can not fill screen, we should make the
			//				// footer view invisible.
			//				hideFooterView();
			//			} else if (!mIsLoading && (firstVisibleItem + visibleItemCount >= totalItemCount) && mCurrentScrollState == SCROLL_STATE_IDLE) {
			//				mIsLoading = true;
			//				showFooterView();
			//				if (mOnLoadMoreListener != null) {
			//					mOnLoadMoreListener.onLoadMore();
			//				}
			//			}

			if (scrollFlag) {
				if (firstVisibleItem > lastVisibleItemPosition) {
					down = false;
				}

				if (firstVisibleItem < lastVisibleItemPosition) {
					down = true;
				}
				if (firstVisibleItem == lastVisibleItemPosition) {

					return;

				}

				lastVisibleItemPosition = firstVisibleItem;

			}
		}
	};
	

	/**
	 * Interface for load more
	 */
	public interface OnLoadMoreListener {
		/**
		 * Load more data.
		 */
		void onLoadMore();
	}

	public interface onTopListener {
		void onTopListener(int top);
	}
}
