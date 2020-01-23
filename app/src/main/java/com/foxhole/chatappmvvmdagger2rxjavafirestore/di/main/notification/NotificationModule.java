package com.foxhole.chatappmvvmdagger2rxjavafirestore.di.main.notification;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter.RequestRecyclerAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Request;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.GetTimeAgo;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {

    @Provides
    static FirestoreRecyclerOptions<Request> provideRequestOptions(DatabaseRepository databaseRepository){
        return databaseRepository.getRequestList();
    }

    @Provides
    static RequestRecyclerAdapter provideRequestAdapter(FirestoreRecyclerOptions<Request> options, DatabaseRepository databaseRepository, GetTimeAgo getTimeAgo){
        return new RequestRecyclerAdapter(options,databaseRepository,getTimeAgo);
    }
}
