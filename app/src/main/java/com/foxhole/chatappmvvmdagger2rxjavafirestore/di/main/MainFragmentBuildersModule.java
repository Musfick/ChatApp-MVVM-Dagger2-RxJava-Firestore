package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.chat.ChatModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.notification.NotificationModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.users.UserModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.chat.ChatFragment;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.notification.NotificationFragment;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.users.UsersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector(modules = {
            UserModule.class
    })
    abstract UsersFragment contributeUsersFragment();

    @ContributesAndroidInjector(modules = {
            NotificationModule.class
    })
    abstract NotificationFragment contributeNotificationFragment();

    @ContributesAndroidInjector(modules = {
            ChatModule.class
    })
    abstract ChatFragment contributeChatFragment();
}
