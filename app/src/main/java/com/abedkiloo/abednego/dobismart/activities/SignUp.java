package com.abedkiloo.abednego.dobismart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.abedkiloo.abednego.dobismart.R;

public class SignUp extends AppCompatActivity {
    AppCompatTextView sign_in_here;
    Spinner specialization_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        /**
         * referencing all xml elements
         */
        xml_elements();

        //clicked sign up here
        sign_in_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });


        ArrayAdapter<CharSequence> specialization_adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.specilization_list, R.layout.spinner_items);
        specialization_adapter.setDropDownViewResource(R.layout.spinner_items);
        specialization_spinner.setAdapter(specialization_adapter);

    }

    private void xml_elements() {

        sign_in_here = (AppCompatTextView) findViewById(R.id.text_view_sign_in_here);
        specialization_spinner = (Spinner) findViewById(R.id.reg_specialization_spiiner);
    }
}
