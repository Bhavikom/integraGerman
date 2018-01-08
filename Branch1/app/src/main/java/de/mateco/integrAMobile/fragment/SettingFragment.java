package de.mateco.integrAMobile.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.mateco.integrAMobile.Helper.SamplePagerItem;
import de.mateco.integrAMobile.Helper.SlidingTabLayout;
import de.mateco.integrAMobile.HomeActivity;
import de.mateco.integrAMobile.R;
import de.mateco.integrAMobile.adapter.SettingPagerAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;

public class SettingFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    //public static String Tag = "Setting Fragment";
    /**
     * List of {link com.example.examples.CardViews.SlidingTabsColorsFragment.SamplePagerItem} which represent this sample's tabs.
     */
    private float mActionBarHeight;
    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();
    private MatecoPriceApplication application;
    private SettingPagerAdapter adapter;
    private Language language;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // BEGIN_INCLUDE (populate_tabs)
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link de.mateco.integrAMobile.Helper.SlidingTabLayout}.
         */
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        mActionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        mTabs.add(new SamplePagerItem(
                language.getLabelAppSetting(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelAccountSetting(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelofflineSettings(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelHelp(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));
        // END_INCLUDE (populate_tabs)
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter = new SettingPagerAdapter(getChildFragmentManager(), mTabs,getActivity());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setOnPageChangeListener(this);

        // BEGIN_INCLUDE (tab_colorizer)
        // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
        // the tab at the position, and return it's set color
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }

        });
        setTitle(language.getLabelAppSetting());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Setting Fragment");
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.clear();
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSettings:
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                transaction.replace(R.id.content_frame, new SettingFragment());
//                //transaction.addToBackStack(SettingFragment.Tag);
//                transaction.addToBackStack("Setting");
//                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        setTitle(adapter.getPageTitle(position).toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    private void setTitle(String title)
    {
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(title);
    }

    public void updateTabs()
    {
        application = (MatecoPriceApplication)getActivity().getApplication();
        language = application.getLanguage();
        mSlidingTabLayout.setText(language.getLabelAppSetting(),0);
        mSlidingTabLayout.setText(language.getLabelAccountSetting(),1);
        mSlidingTabLayout.setText(language.getLabelofflineSettings(),2);
        mSlidingTabLayout.setText(language.getLabelHelp(),3);
    }
}
