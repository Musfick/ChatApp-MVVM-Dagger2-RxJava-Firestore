package com.foxhole.chatappmvvmdagger2rxjavafirestore.utils;


import android.widget.EditText;

import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class RxBindingHelper {

    public static Observable<String> getObservableFrom(EditText editText) {
        return RxTextView.textChanges(editText).skip(1).map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return charSequence.toString();
                    }
        });
    }
}
