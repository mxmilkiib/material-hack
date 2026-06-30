package io.github.mxmilkiib.materialistic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.core.util.Pair;

import io.github.mxmilkiib.materialistic.data.Item;
import io.github.mxmilkiib.materialistic.data.ItemManager;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class StoryListViewModel extends ViewModel {
    private ItemManager mItemManager;
    private Scheduler mIoThreadScheduler;
    private MutableLiveData<Pair<Item[], Item[]>> mItems; // first = last updated, second = current
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public void inject(ItemManager itemManager, Scheduler ioThreadScheduler) {
        mItemManager = itemManager;
        mIoThreadScheduler = ioThreadScheduler;
    }

    public LiveData<Pair<Item[], Item[]>> getStories(String filter, @ItemManager.CacheMode int cacheMode) {
        if (mItems == null) {
            mItems = new MutableLiveData<>();
            mDisposables.add(
                Observable.fromCallable(() -> mItemManager.getStories(filter, cacheMode))
                        .subscribeOn(mIoThreadScheduler)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setItems, throwable -> { /* keep LiveData silent on error */ })
            );
        }
        return mItems;
    }

    public void refreshStories(String filter, @ItemManager.CacheMode int cacheMode) {
        if (mItems == null || mItems.getValue() == null) {
            return;
        }
        mDisposables.add(
            Observable.fromCallable(() -> mItemManager.getStories(filter, cacheMode))
                    .subscribeOn(mIoThreadScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setItems, throwable -> { /* keep LiveData silent on error */ })
        );
    }

    void setItems(Item[] items) {
        mItems.setValue(Pair.create(mItems.getValue() != null ? mItems.getValue().second : null, items));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }
}
