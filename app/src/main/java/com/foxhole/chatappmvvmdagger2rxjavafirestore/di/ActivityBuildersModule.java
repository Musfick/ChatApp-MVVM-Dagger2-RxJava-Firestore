package com.foxhole.chatappmvvmdagger2rxjavafirestore.di;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.account.AccountViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.account.AccountModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.login.LoginViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.MainFragmentBuildersModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.MainModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.MainViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.message.MessageModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.message.MessageViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.profile.ProfileViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.di.register.RegisterViewModelModule;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.account.AccountActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.login.LoginActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.MainActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.message.MessageActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.profile.ProfileActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.register.RegisterActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {
            RegisterViewModelModule.class
    })
    abstract RegisterActivity contributeAuthActivity();

    @ContributesAndroidInjector(modules = {
            LoginViewModelModule.class
    })
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector
    abstract SplashActivity contributeSplashActivity();

    @ContributesAndroidInjector(modules = {
            MainFragmentBuildersModule.class,
            MainViewModelModule.class,
            MainModule.class
    })
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {
            AccountViewModelModule.class,
            AccountModule.class
    })
    abstract AccountActivity contributeAccountActivity();

    @ContributesAndroidInjector(modules ={
            ProfileViewModelModule.class
    })
    abstract ProfileActivity contributeProfileActivity();

    @ContributesAndroidInjector(modules = {
            MessageViewModelModule.class,
            MessageModule.class
    })
    abstract MessageActivity contributeMessageActivity();
}
