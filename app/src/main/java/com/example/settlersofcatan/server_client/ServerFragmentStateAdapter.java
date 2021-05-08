package com.example.settlersofcatan.server_client;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ServerFragmentStateAdapter extends FragmentStateAdapter {
    public ServerFragmentStateAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CreateServerFragment();
                break;
            case 1:
                fragment = new JoinServerFragment();
                break;
            default:
                throw new IllegalArgumentException();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
