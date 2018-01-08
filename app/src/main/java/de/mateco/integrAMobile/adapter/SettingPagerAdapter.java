package de.mateco.integrAMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import de.mateco.integrAMobile.Helper.SamplePagerItem;

import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.fragment.HelpFragment;
import de.mateco.integrAMobile.fragment.SettingAccountFragment;
import de.mateco.integrAMobile.fragment.SettingAppFragment;
import de.mateco.integrAMobile.fragment.SettingOfflineFragment;
import de.mateco.integrAMobile.model.Language;

public class SettingPagerAdapter extends FragmentPagerAdapter
{

    private List<SamplePagerItem> mTabs;
    private Activity context;
    public SettingPagerAdapter(FragmentManager fm, List<SamplePagerItem> tabs,Activity context)
    {
        super(fm);
        this.mTabs = tabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new SettingAppFragment();
            case 1:
                return new SettingAccountFragment();
            case 2:
                return new SettingOfflineFragment();
            case 3:
                return new HelpFragment();
            default:
                return new SettingAppFragment();
        }
        //return mTabs.get(position).createFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}
