package com.foxhole.chatappmvvmdagger2rxjavafirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerAdapter extends FirestoreRecyclerAdapter<User, UsersRecyclerAdapter.UserViewHolder> {

    private RequestManager requestManager;
    private UserListener userListener;


    public UsersRecyclerAdapter(@NonNull FirestoreRecyclerOptions<User> options , RequestManager requestManager ) {
        super(options);
        this.requestManager = requestManager;
    }

    public void setClickListener(UserListener userListener){
        this.userListener = userListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        holder.nameView.setText(model.getDisplayName());

        if (!model.getStatus().equals("default")) {
            holder.statusView.setText(model.getStatus());
        }
        if (!model.getImage().equals("default")) {
            requestManager.load(model.getImage()).into(holder.profileImage);
        }
        if (model.isOnline()) {
            //holder.onlineView.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.users_view_item, parent, false);
        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ConstraintLayout fullLayout;
        public TextView nameView;
        public TextView statusView;
        public CircleImageView profileImage;
        public CircleImageView onlineView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            fullLayout = itemView.findViewById(R.id.full_layout);
            nameView = itemView.findViewById(R.id.display_name);
            statusView = itemView.findViewById(R.id.status);
            profileImage = itemView.findViewById(R.id.profile_image);
            onlineView = itemView.findViewById(R.id.online);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());
            userListener.onUserClick(snapshot);
        }
    }

    public interface UserListener {
        void onUserClick(DocumentSnapshot documentSnapshot);
    }

}


