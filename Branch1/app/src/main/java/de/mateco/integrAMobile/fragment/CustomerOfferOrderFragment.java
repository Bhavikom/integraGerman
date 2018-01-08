package de.mateco.integrAMobile.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import de.mateco.integrAMobile.adapter.CustomerActivityPagerAdapter;
import de.mateco.integrAMobile.base.LoadedCustomerFragment;
import de.mateco.integrAMobile.base.MatecoPriceApplication;
import de.mateco.integrAMobile.model.Language;

public class CustomerOfferOrderFragment extends LoadedCustomerFragment implements ViewPager.OnPageChangeListener
{
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    //public static String Tag = "Setting Fragment";
    /**
     * List of {link com.example.examples.CardViews.SlidingTabsColorsFragment.SamplePagerItem} which represent this sample's tabs.
     */
    private Language language;
    private MatecoPriceApplication matecoPriceApplication;
    private CustomerActivityPagerAdapter adapter;
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
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        TypedArray styledAttributes = getActivity().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        mActionBarHeight = styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        mTabs.add(new SamplePagerItem(
                language.getLabelOpenOrders(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelCompletedOffers(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelOpenOffer(), // Title
                getResources().getColor(R.color.dark_grey), // Indicator color
                Color.BLACK // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                language.getLabelLostSale(), // Title
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
        adapter = new CustomerActivityPagerAdapter(getChildFragmentManager(), mTabs);
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

//        mSlidingTabLayout.setSelectedIndicatorColors(R.color.primary_dark);
//        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.red));
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
        mViewPager.setCurrentItem(0);
        setTitle(language.getLabelOpenOrders());
        //((HomeActivity)getActivity()).getSupportActionBar().setTitle(language.getLabelCompletedOffers());
        //((HomeActivity)getActivity()).getSupportActionBar().setTitle("Customer Offer Order");
    }

    @Override
    public void onResume() {
        matecoPriceApplication = (MatecoPriceApplication)getActivity().getApplication();
        language = matecoPriceApplication.getLanguage();
        mSlidingTabLayout.setText(language.getLabelOpenOrders(),0);
        mSlidingTabLayout.setText(language.getLabelCompletedOffers(),1);
        mSlidingTabLayout.setText(language.getLabelOpenOffer(),2);
        mSlidingTabLayout.setText(language.getLabelLostSale(),3);
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_customer_order_offer, container, false);
        super.initializeFragment(rootView);
//        TextView txtNameFragment = (TextView)rootView.findViewById(R.id.txtNameFragment);
//        String text = getArguments().getString("Name");
//        txtNameFragment.setText(text);
//        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main_menu, menu);
        menu.clear();
        //return true;
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.actionSettings:

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.content_frame, new SettingFragment(),"Setting");
                //transaction.addToBackStack(SettingFragment.Tag);
                transaction.addToBackStack("Setting");
                transaction.commit();
                return true;
            case R.id.actionBack:
                if (getFragmentManager().getBackStackEntryCount() == 0)
                {
                    //getActivity().finish();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.replace(R.id.content_frame, new HomeFragment());
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.commit();
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
    public void onPageSelected(int position)
    {
        setTitle(adapter.getPageTitle(position).toString());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setTitle(String title)
    {
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(title);
        Log.e("here at man tiel", title);
    }
}
