package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;

import androidx.annotation.NonNull;

public class RequestResource <T> {

    @NonNull
    public final RequestStatus status;


    public RequestResource(@NonNull RequestStatus status) {
        this.status = status;
    }

    public static <T> RequestResource<T> received () {
        return new RequestResource<>(RequestStatus.RECEIVED);
    }

    public static <T> RequestResource<T> sender() {

        return new RequestResource<>(RequestStatus.SENDER);
    }

    public static <T> RequestResource<T> friend() {
        return new RequestResource<>(RequestStatus.FRIEND);
    }
    public static <T> RequestResource<T> not_friend() {
        return new RequestResource<>(RequestStatus.NOT_FRIEND);
    }

    public enum RequestStatus {
        RECEIVED,
        SENDER,
        FRIEND,
        NOT_FRIEND
    }
}
