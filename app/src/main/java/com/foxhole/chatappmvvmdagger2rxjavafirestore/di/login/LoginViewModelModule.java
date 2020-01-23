package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.login;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindViewModel(LoginViewModel viewModel);

}
