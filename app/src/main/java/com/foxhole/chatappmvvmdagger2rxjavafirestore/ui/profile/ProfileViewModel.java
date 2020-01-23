package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Request;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.RequestResource;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    private DatabaseRepository databaseRepository;
    private MediatorLiveData<User> onUserInfo = new MediatorLiveData<>();
    private MediatorLiveData<RequestResource> onRequest = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private String PROFILE_UID;

    @Inject
    public ProfileViewModel(DatabaseRepository databaseRepository) {
        Log.d(TAG, "ProfileViewModel: working...");
        this.databaseRepository = databaseRepository;
    }

    public void setProfileUid(String profileUid) {
        PROFILE_UID = profileUid;
    }

    public void loadUserInfo() {
        databaseRepository.getUserinfo(PROFILE_UID)
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
                        onUserInfo.setValue(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //Send friend request
    public void sendFriendRequest() {
        databaseRepository.sendFriendRequest(PROFILE_UID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Friend request success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    //Cancel friend request
    public void cancelFriendRequest(){
        databaseRepository.cancelFriendRequest(PROFILE_UID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Friend request cancel");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    //Accept friend request
    public void acceptFriendRequest(){
        databaseRepository.acceptFriendRequest(PROFILE_UID)
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

    public void requestState() {
        databaseRepository.getRequestState(PROFILE_UID)
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
                        if (documentSnapshot.exists() && documentSnapshot!=null) {
                            Request request = documentSnapshot.toObject(Request.class);
                            if(request.getRequestType()!=null){
                                switch (request.getRequestType()) {
                                    case "received":
                                        onRequest.setValue(RequestResource.received());
                                        break;
                                    case "sender":
                                        onRequest.setValue(RequestResource.sender());
                                        break;
                                    case "friend":
                                        onRequest.setValue(RequestResource.friend());
                                        break;
                                }
                            }

                        } else {
                            onRequest.setValue(RequestResource.not_friend());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<User> observeOnUserInfo() {
        return onUserInfo;
    }

    public LiveData<RequestResource> observeRequestState() {
        return onRequest;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
