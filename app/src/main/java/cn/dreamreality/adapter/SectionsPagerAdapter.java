package cn.dreamreality.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import cn.dreamreality.fragments.InformationFragment;
import cn.dreamreality.fragments.NewestFragment;
import cn.dreamreality.fragments.UncompletedFragment;

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
                return "商品信息".toUpperCase(l);
            case 1:
                return "吃喝玩乐".toUpperCase(l);
            case 2:
                return "传奇".toUpperCase(l);
        }
        return null;
    }
}
