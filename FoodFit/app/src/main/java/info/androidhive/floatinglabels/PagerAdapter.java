package info.androidhive.floatinglabels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by root on 26/10/16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
//    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
//        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment_search tab1 = new Fragment_search();
                return tab1;
            case 1:
                Fragment_custom tab2 = new Fragment_custom();
                return tab2;
            case 2:
                Fragment_recom tab3 = new Fragment_recom();
                return tab3;
            case 3:
                FragmentResturntRecom tab4 = new FragmentResturntRecom();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
