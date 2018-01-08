package de.mateco.integrAMobile.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.fragment.ProjectDetailActivityFragment;
import de.mateco.integrAMobile.fragment.ProjectDetailGeneralFragment;
import de.mateco.integrAMobile.fragment.ProjectDetailNotesFragment;
import de.mateco.integrAMobile.fragment.ProjectDetailTradesFragment;
import de.mateco.integrAMobile.model.ProjectTradeInsert;

public class ProjectDetailPagerAdapter extends FragmentPagerAdapter
{

    private List<SamplePagerItem> mTabs;


    public ProjectDetailPagerAdapter(FragmentManager fm, List<SamplePagerItem> tabs)
    {
        super(fm);
        this.mTabs = tabs;
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new ProjectDetailGeneralFragment();
            case 1:
                return new ProjectDetailActivityFragment();
            case 2:
                return new ProjectDetailTradesFragment();
            case 3:
                return new ProjectDetailNotesFragment();
            default:
                return new ProjectDetailGeneralFragment();
        }
        //return mTabs.get(position).createFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}
