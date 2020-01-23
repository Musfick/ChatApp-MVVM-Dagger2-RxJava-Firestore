package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.profile;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindViewModel(ProfileViewModel profileViewModel);
}
