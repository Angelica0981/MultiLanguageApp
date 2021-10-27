package com.example.multilanguageapp.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.multilanguageapp.Fragments.ColorsFragment;
import com.example.multilanguageapp.Fragments.FamilyFragment;
import com.example.multilanguageapp.Fragments.NumbersFragment;
import com.example.multilanguageapp.Fragments.PhrasesFragment;
import com.example.multilanguageapp.R;

/**
 * CategoryAdapter provides the layout for
 * each list item based on a data source which is a list of Word objects.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    /** Context of the app */
    private final Context iContext;
    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        iContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     * @return
     */

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1) {
            return new FamilyFragment();
        } else if (position == 2) {
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {

        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return iContext.getString(R.string.category_numbers);
        } else if (position == 1) {
            return iContext.getString(R.string.category_family);
        } else if (position == 2) {
            return iContext.getString(R.string.category_colors);
        } else {
            return iContext.getString(R.string.category_phrases);
        }

    }
}
