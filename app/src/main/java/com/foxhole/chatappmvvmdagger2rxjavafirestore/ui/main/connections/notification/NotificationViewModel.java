package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.notification;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends ViewModel {

    private static final String TAG = "NotificationViewModel";
    private DatabaseRepository databaseRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public NotificationViewModel(DatabaseRepository databaseRepository) {
        Log.d(TAG, "NotificationViewModel: working...");
        this.databaseRepository = databaseRepository;
    }

    //Accept friend request
    public void acceptFriendRequest(String uid){
        databaseRepository.acceptFriendRequest(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Friend request accepted...");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    //decline friend request
    public void declineFriendRequest(String uid){
        databaseRepository.cancelFriendRequest(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Friend request decline");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
