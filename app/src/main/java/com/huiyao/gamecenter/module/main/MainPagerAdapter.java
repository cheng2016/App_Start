package com.huiyao.gamecenter.module.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huiyao.gamecenter.module.first.FirstPageFragment;
import com.huiyao.gamecenter.module.user.UserFragment;
import com.huiyao.gamecenter.module.vip.VipFragment;
import com.huiyao.gamecenter.module.welfare.WelfareFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] mFragments = new Fragment[4];


    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(mFragments[position] == null){
                    mFragments[position] = FirstPageFragment.newInstance();
                }
                return mFragments[position];
            case 1:
                if(mFragments[position] == null){
                    mFragments[position] = VipFragment.newInstance();
                }
                return mFragments[position];
            case 2:
                if(mFragments[position] == null){
                    mFragments[position] = WelfareFragment.newInstance();
                }
                return mFragments[position];
            case 3:
                if(mFragments[position] == null){
                    mFragments[position] = UserFragment.newInstance();
                }
                return mFragments[position];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
