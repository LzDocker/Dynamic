package com.docker.common.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class CommonpagerStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private String[] titles;
    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction = null;

    public CommonpagerStateAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.fragmentManager = fm;
    }

    public CommonpagerStateAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.fragmentManager = fm;
    }

    public void notifyChange(List<Fragment> fragments, String[] titles) {
        this.fragments = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    public CommonpagerStateAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        Fragment fragment = (Fragment) object;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.fragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment) object);
        this.mCurTransaction.remove(fragment);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles[position];
        } else {
            return super.getPageTitle(position);
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
