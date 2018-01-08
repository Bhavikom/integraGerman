package de.mateco.integrAMobile.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import de.mateco.integrAMobile.adapter.ProjectDetailPagerAdapter;
import de.mateco.integrAMobile.base.BaseFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;
import de.mateco.integrAMobile.model.ProjectTradeInsert;

public class ProjectDetailFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    ProjectDetailPagerAdapter adapter;
    ProjectTradeInsert projectTradeInsert = new ProjectTradeInsert();
    //public static String Tag = "Setting Fragment";
    /**
     * List of {link com.example.examples.CardViews.SlidingTabsColorsFragment.SamplePagerItem} which represent this sample's tabs.
     */
    private float mActionBarHeight;
    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // BEGIN_INCLUDE (populate_tabs)
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link de.mateco.integrAMobile.Helper.SlidingTabLayout}.
         */
        TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        mActionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        mTabs.add(new SamplePagerItem(
                language.getLabelGeneral(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelActivity(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelTrades(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelNotes(), // Title
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
        adapter = new ProjectDetailPagerAdapter(getChildFragmentManager(), mTabs);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(this);
//        mSlidingTabLayout.setSelectedIndicatorColors(android.R.color.holo_blue_bright);
//        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.red));
        // BEGIN_INCLUDE (tab_colorizer)
        // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
        // the tab at the position, and return it's set color
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }
        });
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Project Detail");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_project_detail, container, false);
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        mSlidingTabLayout.setText(language.getLabelGeneral(),0);
        mSlidingTabLayout.setText(language.getLabelActivity(),1);
        mSlidingTabLayout.setText(language.getLabelTrades(),2);
        mSlidingTabLayout.setText(language.getLabelNotes(),3);
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);

        menu.findItem(R.id.actionSearch).setVisible(false);
        menu.findItem(R.id.actionForward).setVisible(false);
        menu.clear();
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.actionSettings:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    getActivity().finish();
                }
                else
                {
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
