package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.data.model.User;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.RequestResource;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.viewModels.ViewModelProviderFactory;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ProfileActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProfileActivity_Tag";
    private int REQUEST_TYPE = 0;

    private ProfileViewModel profileViewModel;
    private ImageView coverImage;
    private CircleImageView displayImage;
    private TextView displayName;
    private TextView displayStatus;
    private Button actionBtn;
    private Button declineBtn;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intToolbar();
        intView();
        profileViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(ProfileViewModel.class);
        getUserInfo();
        observeUserInfo();
        observeRequestState();
    }


    private void getUserInfo() {
        Intent intent = getIntent();
        if (intent.hasExtra("key_uid")) {
            String uid = intent.getStringExtra("key_uid");
            profileViewModel.setProfileUid(uid);
            profileViewModel.loadUserInfo();
            profileViewModel.requestState();
            getIntent().removeExtra("key_uid");
        }
    }

    private void observeUserInfo() {
        profileViewModel.observeOnUserInfo().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (!user.getImage().equals("default")) {
                    requestManager.load(user.getImage()).into(displayImage);
                    requestManager.load(user.getImage()).apply(bitmapTransform(new BlurTransformation(30))).into(coverImage);
                }
                if (!user.getStatus().equals("default")) {
                    displayStatus.setText(user.getStatus());
                }
                displayName.setText(user.getDisplayName());
            }
        });
    }

    private void observeRequestState() {
        profileViewModel.observeRequestState().observe(this, new Observer<RequestResource>() {
            @Override
            public void onChanged(RequestResource requestResource) {
                if(requestResource!=null){
                    switch (requestResource.status){
                        case NOT_FRIEND:
                            Log.d(TAG, "onChanged: Not friend..");
                            REQUEST_TYPE = 0;
                            updateBtn();
                            break;
                        case SENDER:
                            Log.d(TAG, "onChanged: Already request send..");
                            REQUEST_TYPE = 1;
                            updateBtn();
                            break;
                        case RECEIVED:
                            Log.d(TAG, "onChanged: Request received..");
                            REQUEST_TYPE = 2;
                            updateBtn();
                            break;
                        case FRIEND:
                            Log.d(TAG, "onChanged: Already friend..");
                            REQUEST_TYPE = 3;
                            updateBtn();
                            break;
                    }
                }
            }
        });
    }

    private void updateBtn() {
        switch (REQUEST_TYPE){
            case 0:
                actionBtn.setText("Send friend request");
                actionBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                actionBtn.setVisibility(View.VISIBLE);
                declineBtn.setVisibility(View.GONE);
                break;
            case 1:
                actionBtn.setText("Cancel friend request");
                actionBtn.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                actionBtn.setVisibility(View.VISIBLE);
                break;
            case 2:
                actionBtn.setText("Accept friend request");
                actionBtn.setVisibility(View.VISIBLE);
                declineBtn.setVisibility(View.VISIBLE);
                break;
            case 3:
                actionBtn.setText("unFriend");
                actionBtn.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                declineBtn.setVisibility(View.GONE);
                actionBtn.setVisibility(View.VISIBLE);

        }
    }


    private void intView() {
        coverImage = findViewById(R.id.cover_image);
        displayImage = findViewById(R.id.display_image);
        displayName = findViewById(R.id.display_name);
        displayStatus = findViewById(R.id.display_status);
        actionBtn = findViewById(R.id.action_btn);
        declineBtn = findViewById(R.id.decline_btn);
        actionBtn.setOnClickListener(this);
        declineBtn.setOnClickListener(this);
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_btn:
                switch (REQUEST_TYPE){
                    case 0:
                        sendFriendRequest();
                        break;
                    case 1:
                        cancelFriendRequest();
                        break;
                    case 2:
                        acceptFriendRequest();
                        break;
                    case 3:
                        unFriend();
                        break;
                }
                break;
            case R.id.decline_btn:
                cancelFriendRequest();
                break;
        }
    }

    private void unFriend() {
        profileViewModel.cancelFriendRequest();
    }

    private void acceptFriendRequest() {
        profileViewModel.acceptFriendRequest();
    }

    private void cancelFriendRequest() {
        profileViewModel.cancelFriendRequest();
    }

    private void sendFriendRequest() {
        profileViewModel.sendFriendRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
