package cn.dreamreality.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import cn.dreamreality.R;
import cn.dreamreality.fragments.InformationFragment;
import cn.dreamreality.fragments.NewestFragment;
import cn.dreamreality.fragments.UncompletedFragment;

/**
 * Created by liuhaibao on 15/2/22.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        //Fragment fragment = null;
        switch (position) {
            case 0:
                return new InformationFragment();
            case 1:
                return new NewestFragment();
            case 2:
                return new UncompletedFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_tab_classic);
            case 1:
                return context.getString(R.string.title_tab_newest);
            case 2:
                return context.getString(R.string.title_tab_uncompleted);
        }
        return null;
    }
}
