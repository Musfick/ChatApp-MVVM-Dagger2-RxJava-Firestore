package com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder> {

    private int SENDER = 0;
    private int RECEIVER = 1;

    private String currentUid;
    private String imageUrl;
    private RequestManager requestManager;
    private List<Message> messageList = new ArrayList<>();

    public MessageRecyclerAdapter() {
    }

    public void setUserInfo(String currentUid, String imageUrl, RequestManager requestManager) {
        this.currentUid = currentUid;
        this.imageUrl = imageUrl;
        this.requestManager = requestManager;
    }

    public void setNewMessageAdd(Message message){
        messageList.add(message);
        notifyItemInserted(messageList.size()-1);
    }

    public void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        if(messageList.get(position).getSenderUid().equals(currentUid)){
            return SENDER;
        }
        else {
            return RECEIVER;
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType==SENDER){
            view = inflater.inflate(R.layout.sender_chat_view_item,parent,false);
            return new MessageViewHolder(view,SENDER);
        }
        else {
            view = inflater.inflate(R.layout.receiver_chat_view_item,parent,false);
            return new MessageViewHolder(view,RECEIVER);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if(messageList.get(position).getSenderUid().equals(currentUid)){
            holder.senderText.setText(messageList.get(position).getMessage());
        }
        else {
            holder.receiverText.setText(messageList.get(position).getMessage());
            requestManager.load(imageUrl).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView senderText;
        TextView receiverText;
        CircleImageView imageView;

        public MessageViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if(viewType == 0){
                senderText = itemView.findViewById(R.id.message);
            }
            else {
                receiverText = itemView.findViewById(R.id.message);
                imageView = itemView.findViewById(R.id.profile_image);
            }
        }
    }

}
