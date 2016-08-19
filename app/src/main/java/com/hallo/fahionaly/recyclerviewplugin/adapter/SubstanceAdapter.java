package com.hallo.fahionaly.recyclerviewplugin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;


import com.hallo.fahionaly.recyclerviewplugin.R;
import com.hallo.fahionaly.recycleviewplugin.adapter.CommonAdapter;
import com.hallo.fahionaly.recycleviewplugin.base.ViewHolder;

import java.util.List;



/**
 * Created by lvwenbo on 2016/8/15.
 */
public class SubstanceAdapter extends CommonAdapter<String> {

    public SubstanceAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.id_time_tv, s);
    }


    public class SwipeItemClick implements View.OnClickListener {

        public SwipeItemClick(int pos) {

        }

        @Override
        public void onClick(View view) {

        }
    }


}
