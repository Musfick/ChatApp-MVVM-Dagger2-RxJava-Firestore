package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.MainActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.StateResource;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.LoadingDialog;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.utils.RxBindingHelper;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.viewModels.ViewModelProviderFactory;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DisposableObserver;

public class RegisterActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity_Tag";
    private RegisterViewModel registerViewModel;

    private EditText displayNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button createAccountBtn;

    Observable<Boolean> formObservable;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intToolbar();
        intView();
        formValidation();

        registerViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(RegisterViewModel.class);

        subscribeObservers();

    }

    private void subscribeObservers() {
        registerViewModel.observeRegister().observe(this, new Observer<StateResource>() {
            @Override
            public void onChanged(StateResource stateResource) {
                if (stateResource != null) {
                    switch (stateResource.status) {
                        case LOADING:
                            Log.d(TAG, "onChanged: Loading");
                            loadingDialog.show(getSupportFragmentManager(), "loadingDialog");
                            break;
                        case SUCCESS:
                            Log.d(TAG, "onChanged: Success");
                            loadingDialog.dismiss();
                            moveToHomeActivity();
                            break;
                        case ERROR:
                            Log.d(TAG, "onChanged: Error" + stateResource.message);
                            loadingDialog.dismiss();
                            showSnackBar(stateResource.message);
                            break;
                    }
                }

            }
        });
    }

    private void moveToHomeActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }


    private void formValidation() {
        Observable<String> display_name_observable = RxBindingHelper.getObservableFrom(displayNameInput);
        Observable<String> email_observable = RxBindingHelper.getObservableFrom(emailInput);
        Observable<String> password_observable = RxBindingHelper.getObservableFrom(passwordInput);


        formObservable = Observable.combineLatest(email_observable, password_observable, display_name_observable, new Function3<String, String, String, Boolean>() {
            @Override
            public Boolean apply(String email, String password, String displayName) throws Exception {
                return isValidForm(email.trim(), password.trim(), displayName.trim());
            }
        });

        formObservable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                createAccountBtn.setEnabled(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Boolean isValidForm(String email, String password, String displayName) {

        boolean isDisplayName = !displayName.isEmpty();
        if (!isDisplayName) {
            displayNameInput.setError("Please enter valid name");
        }

        boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty();
        if (!isEmail) {
            emailInput.setError("Please enter valid email");
        }

        boolean isPassword = password.length() > 6 && !password.isEmpty();
        if (!isPassword) {
            passwordInput.setError("Password must be greater then 6 digit");
        }

        return isDisplayName && isEmail && isPassword;
    }

    private void intView() {
        displayNameInput = findViewById(R.id.status_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        createAccountBtn = findViewById(R.id.create_account_btn);
        createAccountBtn.setOnClickListener(this);
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Account");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_account_btn:
                perform_register();
                break;
        }
    }

    private void perform_register() {
        String name = displayNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        registerViewModel.register(email, password,name);
    }

    private void showSnackBar(String msg) {
        View contextView = findViewById(android.R.id.content);
        Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG).show();
    }
}
