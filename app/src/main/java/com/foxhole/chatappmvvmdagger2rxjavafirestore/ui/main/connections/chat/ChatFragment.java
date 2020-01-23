package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.chat;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter.ChatRecyclerAdapter;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.message.MessageActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.viewModels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends DaggerFragment implements ChatRecyclerAdapter.UserListener {

    private static final String TAG = "ChatFragment";

    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    ChatRecyclerAdapter chatRecyclerAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        chatViewModel = new ViewModelProvider(getViewModelStore(),providerFactory).get(ChatViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatRecyclerAdapter);
        chatRecyclerAdapter.setClickListener(this);
        chatRecyclerAdapter.startListening();
    }

    @Override
    public void onUserClick(String uid) {
        Log.d(TAG, "onUserClick: "+uid);
        moveToMessageActivity(uid);
    }

    private void moveToMessageActivity(String uid) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("key_uid",uid);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        chatRecyclerAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        chatRecyclerAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatRecyclerAdapter.clearDisposable();
    }
}
