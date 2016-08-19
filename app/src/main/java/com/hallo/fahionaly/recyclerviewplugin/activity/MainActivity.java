package com.hallo.fahionaly.recyclerviewplugin.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.hallo.fahionaly.recyclerviewplugin.R;
import com.hallo.fahionaly.recyclerviewplugin.fragment.home.DetailFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.id_layout)
    FrameLayout idLayout;
    DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        detailFragment = new DetailFragment();
        ft.replace(R.id.id_layout,detailFragment , detailFragment.getClass().getName()).commit();
    }
}
