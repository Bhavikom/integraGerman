package de.mateco.integrAMobile.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.fragment.VisitPlanDailyAgendaOthersFragment;
import de.mateco.integrAMobile.fragment.VisitPlanDailyAgendaTodayFragment;
import de.mateco.integrAMobile.fragment.VisitPlanDailyAgendaWeeklyFragment;

public class VisitPlanDailyAdapter extends FragmentPagerAdapter
{

    private List<SamplePagerItem> mTabs;

    public VisitPlanDailyAdapter(FragmentManager fm, List<SamplePagerItem> tabs)
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
                return new VisitPlanDailyAgendaTodayFragment();
            case 1:
                return new VisitPlanDailyAgendaOthersFragment();
            case 2:
                return new VisitPlanDailyAgendaWeeklyFragment();
            default:
                return new VisitPlanDailyAgendaTodayFragment();
        }
        //return mTabs.get(position).createFragment();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
