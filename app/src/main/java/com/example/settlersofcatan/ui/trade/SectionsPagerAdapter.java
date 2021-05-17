package com.example.settlersofcatan.ui.trade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Fragment playerFragment = TradeFragment.newInstance();
    private final Fragment bankFragment = BankFragment.newInstance();


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
                return "Player";
            case 1:
                return "Bank";
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