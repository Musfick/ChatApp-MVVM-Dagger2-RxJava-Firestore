package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.chat.ChatFragment;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.notification.NotificationFragment;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.users.UsersFragment;

public class ConnectionsPageAdapter extends FragmentStateAdapter {


    public ConnectionsPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ChatFragment();
            case 1:
                return new NotificationFragment();
            default:
                return new UsersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
