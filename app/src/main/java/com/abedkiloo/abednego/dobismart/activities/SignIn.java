package com.abedkiloo.abednego.dobismart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.abedkiloo.abednego.dobismart.R;

public class SignIn extends AppCompatActivity {
    AppCompatTextView sign_up_here;

    TextInputEditText edit_text_login_phone_number;
    TextInputEditText edit_text_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        xml_elements();
        if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("phone_number")) {
            edit_text_login_phone_number.setText(getIntent().getExtras().getString("phone_number"));
        }
        /**
         * referencing all xml elements
         */

        //clicked sign up here
        sign_up_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }

    private void xml_elements() {
        sign_up_here = findViewById(R.id.text_view_sign_up_here);
        edit_text_login_phone_number = findViewById(R.id.edit_text_login_phone_number);
        edit_text_login_password = findViewById(R.id.edit_text_login_password);
    }

    public void btnSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
