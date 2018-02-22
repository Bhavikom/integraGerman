package de.mateco.integrAMobile.Helper;

public class SamplePagerItem
{
    private final CharSequence mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;

    public SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor)
    {
        mTitle = title;
        mIndicatorColor = indicatorColor;
        mDividerColor = dividerColor;
    }

//
//    /**
//     * @return A new {@link android.support.v4.app.Fragment} to be displayed by a {@link android.support.v4.view.ViewPager}
//     */
//    public Fragment createFragment()
//    {
//        return ContentFragment.newInstance(mTitle, mIndicatorColor, mDividerColor);
//    }

    /**
     * @return the title which represents this tab. In this sample this is used directly by
     * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
     */
    public CharSequence getTitle() {
        return mTitle;
    }
    /**
     * @return the color to be used for indicator on the {@link SlidingTabLayout}
     */
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    /**
     * @return the color to be used for right divider on the {@link SlidingTabLayout}
     */
    public int getDividerColor() {
        return mDividerColor;
    }
}
