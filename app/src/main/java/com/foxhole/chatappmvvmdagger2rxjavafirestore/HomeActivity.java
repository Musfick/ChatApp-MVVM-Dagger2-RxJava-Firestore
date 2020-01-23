package com.foxhole.chatappmvvmdagger2rxjavafirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.login.LoginActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.register.RegisterActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loginBtn = findViewById(R.id.login_btn);
        Button createAccountBtn = findViewById(R.id.create_account_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
            }
        });
    }
}
