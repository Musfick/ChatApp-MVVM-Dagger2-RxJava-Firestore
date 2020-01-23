package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StateResource<T> {

    @NonNull
    public final RegisterStatus status;

    @Nullable
    public final String message;


    public StateResource(@NonNull RegisterStatus status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> StateResource<T> success () {
        return new StateResource<>(RegisterStatus.SUCCESS, null);
    }

    public static <T> StateResource<T> error(@NonNull String msg) {

        return new StateResource<>(RegisterStatus.ERROR, msg);
    }

    public static <T> StateResource<T> loading() {
        return new StateResource<>(RegisterStatus.LOADING, null);
    }

    public enum RegisterStatus {
        SUCCESS,
        ERROR,
        LOADING
    }
}
