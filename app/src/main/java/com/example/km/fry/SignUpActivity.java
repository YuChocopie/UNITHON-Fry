package com.example.km.fry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton config;
    private EditText edit_join_id, edit_join_pw;

    //회원 가입
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        config = (ImageButton) findViewById(R.id.up_config);
        edit_join_id = (EditText) findViewById(R.id.up_id_et);
        edit_join_pw = (EditText) findViewById(R.id.up_pw_et);

        config.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String id, pw;

        id = edit_join_id.getText().toString();
        pw = edit_join_pw.getText().toString();

        Toast.makeText(this, "회원 가입 완료", Toast.LENGTH_LONG).show();
        LoginActivity.helper.insert("insert into user_table values(null, '" + id + "', '" + pw + "')");

        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
