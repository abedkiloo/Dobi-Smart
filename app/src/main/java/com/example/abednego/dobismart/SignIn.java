package com.example.abednego.dobismart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class SignIn extends AppCompatActivity {
AppCompatTextView sign_up_here;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        /**
         * referencing all xml elements
         */
        xml_elements();


        //clicked sign up here
        sign_up_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }

    private void xml_elements() {
        sign_up_here= (AppCompatTextView) findViewById(R.id.text_view_sign_up_here);

    }
}
