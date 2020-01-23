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
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.Request;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.repository.DatabaseRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<Request, ChatRecyclerAdapter.ChatViewModel> {

    private RequestManager requestManager;
    private UserListener userListener;
    private DatabaseRepository databaseRepository;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Request> options , DatabaseRepository databaseRepository, RequestManager requestManager) {
        super(options);
        this.databaseRepository = databaseRepository;
        this.requestManager = requestManager;
    }

    public void setClickListener(UserListener userListener){
        this.userListener = userListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatViewModel holder, int position, @NonNull final Request model) {
        databaseRepository.getUserinfo(getSnapshots().getSnapshot(position).getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<DocumentSnapshot>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }
                    @Override
                    public void onNext(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        holder.nameView.setText(user.getDisplayName());

                        if (!user.getStatus().equals("default")) {
                            holder.statusView.setText(user.getStatus());
                        }
                        if (!user.getImage().equals("default")) {
                            requestManager.load(user.getImage()).into(holder.profileImage);
                        }
                        if (user.isOnline()) {
                            holder.onlineView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @NonNull
    @Override
    public ChatViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.users_view_item, parent, false);
        return new ChatViewModel(view);
    }

    public class ChatViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ConstraintLayout fullLayout;
        public TextView nameView;
        public TextView statusView;
        public CircleImageView profileImage;
        public CircleImageView onlineView;

        public ChatViewModel(@NonNull View itemView) {
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
            userListener.onUserClick(getSnapshots().getSnapshot(getAdapterPosition()).getId());
        }
    }

    public interface UserListener {
        void onUserClick(String uid);
    }

    public void clearDisposable(){
        if(disposable != null){
            disposable.clear();
        }
    }
}
