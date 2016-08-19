package com.hallo.fahionaly.recyclerviewplugin.presenter.home;

import android.util.Log;

import com.hallo.fahionaly.recyclerviewplugin.R;
import com.hallo.fahionaly.recyclerviewplugin.adapter.SubstanceAdapter;
import com.hallo.fahionaly.recyclerviewplugin.view.home.IDetail;
import com.hallo.fahionaly.recyclerviewplugin.presenter.base.BasePresenter;
import com.hallo.fahionaly.recycleviewplugin.utils.RecyclerPlugin;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lwb.
 */
public class DetailPresenter extends BasePresenter<IDetail> {
    private IDetail iview;

    public final static int REQUEST_COUNT = 15;
    public final static int Max_COUNT = 35;

    public SubstanceAdapter getSubstanceAdapter() {
        return substanceAdapter;
    }

    public void setSubstanceAdapter(SubstanceAdapter substanceAdapter) {
        this.substanceAdapter = substanceAdapter;
    }

    private SubstanceAdapter substanceAdapter;
    private List<String> substancesBeanList = null;

    public DetailPresenter(IDetail main) {
        this.attachView(main);
        iview = getView();
        if (substancesBeanList == null)
            substancesBeanList = new ArrayList<String>();
        if (substanceAdapter == null)
            substanceAdapter = new SubstanceAdapter(getView().getMContext(), R.layout.item_recyclerview_log, substancesBeanList);
    }

    public void refreshData() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                substancesBeanList.clear();
                for (int i = 0; i < 15; i++) {
                    substancesBeanList.add(String.valueOf(i + 1));
                }
                subscriber.onNext(15);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                getView().notifyDataSetChanged();
                RecyclerPlugin.setIsRequesting(false);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer s) {
                int requestCount = ((Integer) s).intValue();
                getView().changeLoadMoreByCount(Max_COUNT, requestCount);
            }
        });
    }

    public void addmore() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
                              @Override
                              public void call(Subscriber<? super Integer> subscriber) {
                                  try {
                                      Thread.sleep(3000);
                                  } catch (InterruptedException e) {
                                      e.printStackTrace();
                                  }
                                  int result = addMehtod();
                                  subscriber.onNext(result);
                                  subscriber.onCompleted();
                              }
                          }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                getView().notifyDataSetChanged();
                RecyclerPlugin.setIsRequesting(false);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                int requestCount = ((Integer) o).intValue();
                getView().changeLoadMoreByCount(Max_COUNT, requestCount);
            }
        });

    }

    private int addMehtod() {
        if (this.substancesBeanList.size() < 30) {
            for (int i = 0; i < 15; i++) {
                this.substancesBeanList.add(String.valueOf(i + 1));
            }
            return 15;
        } else {
            for (int i = 0; i < 5; i++) {
                this.substancesBeanList.add(String.valueOf(i + 1));
            }
            return 5;
        }
    }
}
