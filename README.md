# RecyclerViewPlugin
support: addmore refresh +rxJava+MVP(weakRefrenceVersion)
作者亲情奉献 在网上找了好多recylerview loadMore相关的例子 总会遇到这样那样的问题 后来总结了一些大神的开源项目（鸿洋、肖芳等）
在这里集成了一个例子。
先说一下使用：
1.mvp 请借鉴我上一个MVP Demo的例子，里面有模板。
2.rxJava 主要应用于异步加载数据。
3.refresh 我在项目里用了秋百万那个Framelayout 在示例中用google自带的SwipeRefreshLayout代替。
4.说到我们的重点插件了：recyclerPlugin。
a.插件可以使用简单的语句完成头部以及LoadMore的布局初始化。
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        idRecyclerView.setLayoutManager(manager);
        /** 添加代码 创建Header*/
        plugin.createHeader(R.layout.headview);
        /** 添加代码 创建加载更多视图*/
        plugin.createAddMore(false, null);
        /**
         * 设置没有更多数据时的展示布局
         */
        plugin.setNoMoreView(R.layout.nomore_loading);
       idRecyclerView.setAdapter(plugin.getLastAdapter());

b.我们每次请求数据有三种情况：1.页面容量<请求总数 2.页面容量=请求数 3.页面容量<每次请求数
根据不同的数量 控制loadMore不同的布局 如下代码：
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
    c.请求进行时 没多说的Rx  记住请求完了调用 RecyclerPlugin.setIsRequesting(false); 要不然不能进行下一次请求
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
d.请求结束了 该更新了吧 记住要使用最里层（请求数据组成的）的adapter 来更新 不明白就看下一节
        refresh.setRefreshing(false);
        detailPresenter.getSubstanceAdapter().notifyDataSetChanged();
        
再说一下封装思想：
1.RecyclerPlugin 目的就是把Adapter分层，最外层是LoadMoreAdapter，里面是HeaderAndFooterAdapter，再里面是我们的数据adapter。
当然你如果不需要LoadMore那就直接用里面是HeaderAndFooterAdapter。
2.如何改变LoadMore布局？
遇到一种情况，改了布局不更新，为啥呢？因为他的ItemViewType没变啊！
    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            if (hasMoreData) {
                return ITEM_TYPE_LOAD_MORE;
            } else {
                return ITEM_TYPE_NO_MORE;
            }
        }
        return mInnerAdapter.getItemViewType(position);
    }
    通过更改不同的ItemViewType 来更改LoadMore的外观。
3.如何让这3个Adaper同步更新？
LoadMoreAdapter：
    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.registerAdapterDataObserver(observer);
    }
HeaderAndFooterAdapter:
    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.registerAdapterDataObserver(observer);
    }
而我们自己的数据Adapter里不写，那么就是用我们数据进行驱动整个Adapter联合体来更新了。为啥用最里层的notifydataasetChange知道了吧。

先写到这里。
