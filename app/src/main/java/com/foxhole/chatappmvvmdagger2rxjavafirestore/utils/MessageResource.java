package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessageResource <T> {

    @NonNull
    public final MessageStatus status;

    @Nullable
    public final T data;

    public MessageResource(@NonNull MessageStatus status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }


    public static <T> MessageResource<T> oldMessage (@Nullable T data) {
        return new MessageResource<>(MessageStatus.OLD_MESSAGE, data);
    }

    public static <T> MessageResource<T> newMessage( @Nullable T data) {
        return new MessageResource<>(MessageStatus.NEW_MESSAGE, data);
    }


    public enum MessageStatus { OLD_MESSAGE, NEW_MESSAGE}
}
