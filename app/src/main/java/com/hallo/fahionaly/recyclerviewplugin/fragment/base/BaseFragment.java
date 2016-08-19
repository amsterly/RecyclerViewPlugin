package com.hallo.fahionaly.recyclerviewplugin.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.hallo.fahionaly.recyclerviewplugin.presenter.base.BasePresenter;
import com.hallo.fahionaly.recyclerviewplugin.view.base.IBase;
import com.trello.rxlifecycle.components.support.RxFragment;


/**
 * Created by fancy on 2016/6/23.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends RxFragment implements IBase {
    protected T mPresenter;
    protected boolean nowRequest = false;

    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            //1.不detttach 弱引用无视泄露 2.dettach 每次getview前判断null
            mPresenter.dettachView();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Override
    public void requestTimeout() {
        if (getContext() != null)
            Toast.makeText(this.getContext(), "请求超时", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String s) {
        if (this.getContext() != null)
            Toast.makeText(this.getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessToast(String s) {
        //
        Toast.makeText(getMContext(), "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerError(String s) {

    }

    @Override
    public void showNoNetTextView() {

    }

    @Override
    public void showTextView(String s) {

    }

    @Override
    public boolean hasNet() {
        return true;
    }

    @Override
    public void showProgress(boolean visible) {

    }

    @Override
    public Context getMApplicationContext() {
        return getContext().getApplicationContext();
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void setNowRequest(boolean nowRequest) {
        this.nowRequest = nowRequest;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.dettachView();
    }

}
