package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    ImageButton signUp;
    ImageButton signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.login_up);
        signIn = findViewById(R.id.login_in);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupIntent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            /*TODO: 메인->sign In 으로*/
            @Override
            public void onClick(View view) {
                Intent signinIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(signinIntent);
                finish();
            }
        });
    }
}
