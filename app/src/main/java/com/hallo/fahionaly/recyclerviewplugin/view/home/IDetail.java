package com.hallo.fahionaly.recyclerviewplugin.view.home;

import com.hallo.fahionaly.recyclerviewplugin.view.base.IBase;

/**
 * Created by lwb.
 */
public interface IDetail extends IBase {
    void changeLoadMoreByCount(int maxCount,int requestCount);
    void notifyDataSetChanged();
}
