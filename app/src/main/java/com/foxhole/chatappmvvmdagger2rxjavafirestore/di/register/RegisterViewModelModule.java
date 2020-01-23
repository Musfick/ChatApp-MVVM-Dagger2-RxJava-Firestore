package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.register;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.register.RegisterViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RegisterViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    public abstract ViewModel binViewModel(RegisterViewModel viewModel);
}
