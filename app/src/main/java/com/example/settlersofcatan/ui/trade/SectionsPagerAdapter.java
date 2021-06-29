package com.example.settlersofcatan.ui.trade;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.settlersofcatan.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Fragment playerFragment = TradeFragment.newInstance();
    private final Fragment bankFragment = BankFragment.newInstance();
    private final Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = playerFragment;
                break;
            case 1:
                fragment = bankFragment;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return fragment;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.player);
            case 1:
                return context.getString(R.string.bank);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}