package com.foxhole.chatappmvvmdagger2rxjavafirestore.di;

import androidx.lifecycle.ViewModelProvider;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.viewModels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory binViewModelFactory(ViewModelProviderFactory factory);
}
