package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main;

import androidx.lifecycle.ViewModel;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.ViewModelKey;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.chat.ChatViewModel;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.notification.NotificationViewModel;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.users.UsersViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel.class)
    public abstract ViewModel bindProfileViewModel(UsersViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel.class)
    public abstract ViewModel bindNotificationViewModel(NotificationViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    public abstract ViewModel bindChatViewModel(ChatViewModel viewModel);
}
