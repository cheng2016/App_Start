package com.huiyao.gamecenter.module.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huiyao.gamecenter.module.first.FirstPageFragment;
import com.huiyao.gamecenter.module.welfare.WelfareFragment;
import com.huiyao.gamecenter.module.user.UserFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = new Fragment[3];


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(mFragments[0] == null){
                    mFragments[0] = FirstPageFragment.newInstance();
                }
                return mFragments[0];
            case 1:
                if(mFragments[1] == null){
                    mFragments[1] = WelfareFragment.newInstance();
                }
                return mFragments[1];
            case 2:
                if(mFragments[2] == null){
                    mFragments[2] = UserFragment.newInstance();
                }
                return mFragments[2];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
