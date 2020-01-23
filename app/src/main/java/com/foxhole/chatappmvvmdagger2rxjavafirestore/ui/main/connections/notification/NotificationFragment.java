package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.notification;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter.RequestRecyclerAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Request;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.viewModels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends DaggerFragment implements RequestRecyclerAdapter.onButtonClickListener {

    private NotificationViewModel notificationViewModel;

    @Inject
    RequestRecyclerAdapter requestRecyclerAdapter;
    @Inject
    ViewModelProviderFactory providerFactory;
    private RecyclerView recyclerView;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(requestRecyclerAdapter);
        requestRecyclerAdapter.setOnClickListener(this);
        requestRecyclerAdapter.startListening();
        notificationViewModel = new ViewModelProvider(getViewModelStore(),providerFactory).get(NotificationViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requestRecyclerAdapter.clearDisposable();
    }

    @Override
    public void onStop() {
        super.onStop();
        requestRecyclerAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        requestRecyclerAdapter.startListening();
    }

    @Override
    public void onAcceptAction(String uid) {
        acceptFriendRequest(uid);
    }

    @Override
    public void onDeclineAction(String uid) {
        declineFriendRequest(uid);
    }

    private void acceptFriendRequest(String uid) {
        notificationViewModel.acceptFriendRequest(uid);
    }

    private void declineFriendRequest(String uid) {
        notificationViewModel.declineFriendRequest(uid);
    }
}
