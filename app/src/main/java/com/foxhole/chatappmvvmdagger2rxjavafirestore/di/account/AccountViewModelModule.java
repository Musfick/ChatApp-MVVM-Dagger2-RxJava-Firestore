package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.account;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.account.AccountViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AccountViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    public abstract ViewModel binViewModel(AccountViewModel viewModel);
}
