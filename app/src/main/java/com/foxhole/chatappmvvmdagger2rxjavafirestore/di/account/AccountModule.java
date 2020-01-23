package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.account;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.InputDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class AccountModule {

    @Provides
    static InputDialog provideDialog(){
        return new InputDialog();
    }
}
