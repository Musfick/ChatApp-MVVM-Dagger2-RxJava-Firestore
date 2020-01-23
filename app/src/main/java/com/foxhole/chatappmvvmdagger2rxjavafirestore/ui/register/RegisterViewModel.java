package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.register;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.AuthRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.StateResource;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {

    private static final String TAG = "RegisterViewModel";

    AuthRepository authRepository;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MediatorLiveData<StateResource> onRegister = new MediatorLiveData<>();

    @Inject
    public RegisterViewModel(AuthRepository authRepository) {
        Log.d(TAG, "RegisterViewModel: working...");
        this.authRepository = authRepository;

        if(authRepository.getCurrentUser() == null){
            Log.d(TAG, "RegisterViewModel: No user loged in");
        }
    }

    public void register(String email, String password, String name){
        authRepository.register(email, password, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        onRegister.setValue(StateResource.loading());
                    }

                    @Override
                    public void onComplete() {
                        onRegister.setValue(StateResource.success());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onRegister.setValue(StateResource.error(e.getMessage()));
                    }
                });
    }

    public LiveData<StateResource> observeRegister(){
        return onRegister;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
