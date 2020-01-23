package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.account;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.AuthRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.StateResource;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountViewModel extends ViewModel {

    private static final String TAG = "AccountViewModel";
    private AuthRepository authRepository;
    private DatabaseRepository databaseRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MediatorLiveData<User> onUser = new MediatorLiveData<>();
    private MediatorLiveData<StateResource> onStatusChange = new MediatorLiveData<>();
    private MediatorLiveData<StateResource> onDisplayImageChange = new MediatorLiveData<>();
    private String uid;

    @Inject
    public AccountViewModel(AuthRepository authRepository, DatabaseRepository databaseRepository) {
        Log.d(TAG, "AccountViewModel: working..");
        this.authRepository = authRepository;
        this.databaseRepository = databaseRepository;
        uid = authRepository.getCurrentUid();
        loadUserInfo(uid);
    }

    private void loadUserInfo(String uid) {
        databaseRepository.getUserinfo(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        onUser.setValue(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateStatus(String status) {
        databaseRepository.updateStatus(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        onStatusChange.setValue(StateResource.loading());
                    }

                    @Override
                    public void onComplete() {
                        onStatusChange.setValue(StateResource.success());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onStatusChange.setValue(StateResource.error(e.getMessage()));
                    }
                });
    }

    public void updateDisplayImage(Bitmap bitmap) {
        databaseRepository.updateDisplayImage(bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        onDisplayImageChange.setValue(StateResource.loading());
                    }

                    @Override
                    public void onComplete() {
                        onDisplayImageChange.setValue(StateResource.success());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDisplayImageChange.setValue(StateResource.error(e.getMessage()));
                    }
                });
    }

    public LiveData<User> getUserInfo() {
        return onUser;
    }

    public LiveData<StateResource> observerStatusChange() {
        return onStatusChange;
    }

    public LiveData<StateResource> observeDisplayImageChange() {
        return onDisplayImageChange;
    }

    public void logOut(){
        authRepository.signOut();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
