package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton config;
    private EditText edit_login_id, edit_login_pw;
    //로그인
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        config = (ImageButton) findViewById(R.id.in_config);
        edit_login_id = (EditText) findViewById(R.id.in_id_et) ;
        edit_login_pw = (EditText) findViewById(R.id.in_pw_et);

        config.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String id, pw;

        id = edit_login_id.getText().toString();
        pw = edit_login_pw.getText().toString();

        Log.d("helper", LoginActivity.helper.print_user());

        if(LoginActivity.helper.user_check(id, pw)) {
            Toast.makeText(getApplicationContext(), "로그인 완료",  Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(SignInActivity.this, TotalActivity.class);

            startActivity(mainIntent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "로그인 실패.",  Toast.LENGTH_LONG).show();
        }
    }
}
