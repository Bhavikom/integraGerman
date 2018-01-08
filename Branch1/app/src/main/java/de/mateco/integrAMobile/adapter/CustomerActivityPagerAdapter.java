package de.mateco.integrAMobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.fragment.CustomerOffersCompletedOrdersFragment;
import de.mateco.integrAMobile.fragment.CustomerOffersLostSaleFragment;
import de.mateco.integrAMobile.fragment.CustomerOffersOpenOffersFragment;
import de.mateco.integrAMobile.fragment.CustomerOffersOpenOrdersFragment;

public class CustomerActivityPagerAdapter extends FragmentPagerAdapter
{

    private List<SamplePagerItem> mTabs;

    public CustomerActivityPagerAdapter(FragmentManager fm, List<SamplePagerItem> tabs)
    {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position)
    {
        Log.e("position customer offer", position + "");
        switch (position)
        {
            case 0:
                return new CustomerOffersOpenOrdersFragment();
            case 1:
                return new CustomerOffersCompletedOrdersFragment();
            case 2:
                return new CustomerOffersOpenOffersFragment();
            case 3:
                return new CustomerOffersLostSaleFragment();
            default:
                return new CustomerOffersOpenOrdersFragment();
        }
        //return mTabs.get(position).createFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTabs.get(position).getTitle();
    }
}
