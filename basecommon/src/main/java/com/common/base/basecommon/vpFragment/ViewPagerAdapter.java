package com.common.base.basecommon.vpFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/6/2.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
     private  String[]mTitles;
    private List<Fragment> mFragments;
    public ViewPagerAdapter(FragmentManager fm, String[] mTitles) {
        super(fm);
        this.mTitles=mTitles;
        mFragments=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mTitles.length>mFragments.size()){
            return mFragments.size();
        }else {
            return mTitles.length;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
    public void addFrament(Fragment fragment){
        mFragments.add(fragment);
    }
}
