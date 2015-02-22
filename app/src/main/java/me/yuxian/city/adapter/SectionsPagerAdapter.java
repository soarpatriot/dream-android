package me.yuxian.city.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import me.yuxian.city.R;
import me.yuxian.city.fragments.InformationFragment;

/**
 * Created by liuhaibao on 15/2/22.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        InformationFragment informationFragment = new InformationFragment();
        return informationFragment;
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
                return "aa".toUpperCase(l);
            case 1:
                return "fff".toUpperCase(l);
            case 2:
                return "fsdf".toUpperCase(l);
        }
        return null;
    }
}
