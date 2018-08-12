package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    ImageButton signUp;
    ImageButton signIn;
    public static MySQLiteOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new MySQLiteOpenHelper(getApplicationContext(), "fry6.db", null, 1);

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
            @Override
            public void onClick(View view) {
                Intent signinIntent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(signinIntent);
                finish();
            }
        });
    }
}
