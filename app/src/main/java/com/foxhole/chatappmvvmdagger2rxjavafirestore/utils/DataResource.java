package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataResource<T> {

    @NonNull
    public final DataStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;


    public DataResource(@NonNull DataStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> DataResource<T> DataStatus (@Nullable T data) {
        return new DataResource<>(DataStatus.SUCCESS, data, null);
    }

    public static <T> DataResource<T> DataStatus(@NonNull String msg) {

        return new DataResource<>(DataStatus.ERROR,null, msg);
    }

    public static <T> DataResource<T> loading() {
        return new DataResource<>(DataStatus.LOADING, null, null);
    }
    public enum DataStatus {
        SUCCESS,
        ERROR,
        LOADING
    }
}
