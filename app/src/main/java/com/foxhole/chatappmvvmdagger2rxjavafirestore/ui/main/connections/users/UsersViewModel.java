package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.users;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;

import javax.inject.Inject;

public class UsersViewModel extends ViewModel {

    private static final String TAG = "UsersViewModel";

    @Inject
    public UsersViewModel() {
        Log.d(TAG, "UsersViewModel: working");
    }

}
