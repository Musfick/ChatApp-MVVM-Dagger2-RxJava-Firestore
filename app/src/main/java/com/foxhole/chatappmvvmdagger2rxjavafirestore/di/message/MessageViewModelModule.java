package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.message;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.message.MessageViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MessageViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel.class)
    public abstract ViewModel bindMessageViewModel(MessageViewModel viewModel);
}
