package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.chat;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter.ChatRecyclerAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Request;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatModule {

    @Provides
    static FirestoreRecyclerOptions<Request> provideRequestOptions(DatabaseRepository databaseRepository){
        return databaseRepository.getFriendList();
    }

    @Provides
    static ChatRecyclerAdapter provideRecyclerAdapter(FirestoreRecyclerOptions<Request> options, DatabaseRepository databaseRepository, RequestManager requestManager){
        return new ChatRecyclerAdapter(options,databaseRepository,requestManager);
    }
}
