package com.hallo.fahionaly.recyclerviewplugin.fragment.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hallo.fahionaly.recyclerviewplugin.R;
import com.hallo.fahionaly.recyclerviewplugin.fragment.base.BaseFragment;
import com.hallo.fahionaly.recyclerviewplugin.presenter.home.DetailPresenter;
import com.hallo.fahionaly.recyclerviewplugin.view.home.IDetail;
import com.hallo.fahionaly.recycleviewplugin.adapter.LoadMoreAdapter;
import com.hallo.fahionaly.recycleviewplugin.utils.RecyclerPlugin;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * create by lwb.
 */
public class DetailFragment extends BaseFragment<IDetail, DetailPresenter> implements IDetail, LoadMoreAdapter.OnLoadMoreListener {


    @Bind(R.id.id_recyclerView)
    RecyclerView idRecyclerView;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private Activity mContext;
    private RecyclerPlugin plugin;
    private DetailPresenter detailPresenter;
    private Observable mrefresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);//注意不要指定父视图
        ButterKnife.bind(this, view);
        return view;
    }

    void initData() {
        detailPresenter.refreshData();
    }

    void loadMoreData() {
        detailPresenter.addmore();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                detailPresenter.refreshData();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        idRecyclerView.setLayoutManager(manager);
//        idRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        if (plugin == null)
            plugin = new RecyclerPlugin((mContext).getLayoutInflater(), mContext, idRecyclerView, detailPresenter.getSubstanceAdapter());
        /** 添加代码 创建Header*/
//        plugin.createHeader(R.layout.headview);
        /** 添加代码 创建加载更多视图*/
        plugin.createAddMore(false, null);
        plugin.setNoMoreView(R.layout.nomore_loading);

        idRecyclerView.setAdapter(plugin.getLastAdapter());
        detailPresenter.refreshData();
    }

    @Override
    protected DetailPresenter createPresenter() {
        return detailPresenter==null?detailPresenter=
                new DetailPresenter(this):detailPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void changeLoadMoreByCount(int maxCount, int requestCount) {
        if (maxCount < DetailPresenter.REQUEST_COUNT) {
            plugin.setAddMoreVisible(false);
        } else if (requestCount == DetailPresenter.REQUEST_COUNT) {
            plugin.setAddMoreVisible(true, this, R.layout.default_loading);
            plugin.setHasMoreData(true);
        } else if (requestCount < DetailPresenter.REQUEST_COUNT) {
            plugin.setAddMoreVisible(true, null, R.layout.nomore_loading);
            plugin.loadMoreAdapter.setOnLoadMoreListener(null);
            plugin.setHasMoreData(false);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        refresh.setRefreshing(false);
        detailPresenter.getSubstanceAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {
        detailPresenter.addmore();
    }
}
