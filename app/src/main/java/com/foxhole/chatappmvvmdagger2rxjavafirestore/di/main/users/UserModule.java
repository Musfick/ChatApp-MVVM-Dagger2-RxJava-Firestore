package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.users;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter.UsersRecyclerAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {


    @Provides
    static FirestoreRecyclerOptions<User> provideOption(DatabaseRepository databaseRepository){
        return databaseRepository.getUserList();
    }

    @Provides
    static UsersRecyclerAdapter provideAdapter(FirestoreRecyclerOptions<User> options, RequestManager requestManager, Application application){
        return new UsersRecyclerAdapter(options,requestManager);
    }
}
