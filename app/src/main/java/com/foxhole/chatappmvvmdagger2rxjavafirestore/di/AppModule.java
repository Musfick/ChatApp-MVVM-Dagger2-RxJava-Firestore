package com.foxhole.chatappmvvmdagger2rxjavafirestore.di;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.AuthRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.dataSource.remote.FirebaseAuthSource;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.dataSource.remote.FirebaseDataSource;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.ConnectionsPageAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.GetTimeAgo;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    static FirebaseAuth getAuthInstance(){
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseFirestore provideFirebaseInstance(){
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    static FirebaseAuthSource getAuthSource(FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore){
        return new FirebaseAuthSource(firebaseAuth,firebaseFirestore);
    }
    @Singleton
    @Provides
    static AuthRepository provideAuthRepository(FirebaseAuthSource authSource){
        return  new AuthRepository(authSource);
    }

    @Singleton
    @Provides
    static StorageReference provideStorageReference(){
        return FirebaseStorage.getInstance().getReference();
    }

    @Singleton
    @Provides
    static LoadingDialog provideLoadingDialog(){
        return new LoadingDialog();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions.placeholderOf(R.drawable.avater)
                .error(R.drawable.avater);
    }
    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Provides
    static FirebaseDataSource provideFirebaseDataSource(FirebaseAuthSource firebaseAuthSource, FirebaseFirestore firebaseFirestore, StorageReference storageReference){
        return new FirebaseDataSource(firebaseAuthSource,firebaseFirestore,storageReference);
    }

    @Provides
    static DatabaseRepository provideDatabaseRepository(FirebaseDataSource firebaseDataSource){
        return new DatabaseRepository(firebaseDataSource);
    }

    @Provides
    static GetTimeAgo provideGetTimeAgo(){
        return new GetTimeAgo();
    }
}
