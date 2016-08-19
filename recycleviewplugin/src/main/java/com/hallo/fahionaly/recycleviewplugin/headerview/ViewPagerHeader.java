package com.hallo.fahionaly.recycleviewplugin.headerview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ViewPagerHeader{
    ViewPager viewPager;

    public ViewPagerHeader(Context context, FragmentManager manager, final Fragment... fragments) {
        viewPager = new ViewPager(context);
        viewPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
    }

    public ViewPager create() {
        return viewPager;
    }

    /*private Fragment[] fragments;


    public ViewPagerHeader(Context context, FragmentManager manager, final Fragment... fragments) {
        super(context);
        this.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
    }


    public ViewPagerHeader(Context context, AttributeSet attrs, Fragment[] fragments) {
        super(context, attrs);
        this.fragments = fragments;
    }*/
}
