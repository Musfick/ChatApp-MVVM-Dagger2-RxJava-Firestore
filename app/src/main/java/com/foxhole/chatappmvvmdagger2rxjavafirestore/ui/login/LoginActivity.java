package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.login;

import android.content.Intent;
import android.os.Bundle;
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
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

public class LoginActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private LoginViewModel loginViewModel;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginBtn;

    Observable<Boolean> formObservable;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intToolbar();
        intView();
        formValidation();

        loginViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(LoginViewModel.class);

        subscribeObservers();


    }

    private void subscribeObservers() {
        loginViewModel.observeLogin().observe(this, new Observer<StateResource>() {
            @Override
            public void onChanged(StateResource stateResource) {
                if(stateResource != null){
                    switch (stateResource.status){
                        case LOADING:
                            loadingDialog.show(getSupportFragmentManager(),"loadingDialog");
                            break;
                        case SUCCESS:
                            loadingDialog.dismiss();
                            moveToHomeActivity();
                            break;
                        case ERROR:
                            loadingDialog.dismiss();
                            showSnackBar(stateResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void intView() {
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                performLogin();
                break;
        }
    }

    private void performLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        loginViewModel.login(email,password);
    }

    private void formValidation() {
        Observable<String> email_observable = RxBindingHelper.getObservableFrom(emailInput);
        Observable<String> password_observable = RxBindingHelper.getObservableFrom(passwordInput);

        formObservable = Observable.combineLatest(email_observable, password_observable, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String email, String password) throws Exception {
                return isValidForm(email,password);
            }
        });

        formObservable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                loginBtn.setEnabled(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private Boolean isValidForm(String email, String password) {

        boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty();
        if (!isEmail) {
            emailInput.setError("Please enter valid email");
        }

        boolean isPassword = password.length() > 6 && !password.isEmpty();
        if (!isPassword) {
            passwordInput.setError("Password must be greater then 6 digit");
        }

        return isEmail && isPassword;
    }

    private void showSnackBar(String msg) {
        View contextView = findViewById(android.R.id.content);
        Snackbar.make(contextView, msg, Snackbar.LENGTH_LONG).show();
    }

    private void moveToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}
