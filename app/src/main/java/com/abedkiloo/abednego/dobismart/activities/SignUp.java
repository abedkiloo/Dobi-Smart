package com.abedkiloo.abednego.dobismart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.abedkiloo.abednego.dobismart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    String artisan_specialization;
    private DatabaseReference databaseReference;
    private TextInputEditText reg_edit_text_phone_number;
    private TextInputEditText reg_edit_text_confirm_code;
    private TextInputEditText reg_edit_first_name;
    private TextInputEditText reg_edit_other_name;
    private TextInputEditText reg_edit_locality_name;
    private TextInputEditText reg_edit_password;
    private AppCompatTextView sign_in_here;
    private AppCompatButton btn_sign_in;
    private Spinner specialization_spinner;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private LinearLayoutCompat linear_layout_phone_number_input;
    private LinearLayoutCompat linear_layout_user_details;
    private LinearLayoutCompat linear_layout_verification_code;
    private Artisan artisan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
//        databaseReference=Firebase
        mAuth = FirebaseAuth.getInstance();
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
        specialization_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                artisan_specialization = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.saka_fundi_artisans_tbl));

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.e("PHONE SIGN IN", "Verification complete " + phoneAuthCredential);
                mVerificationInProgress = false;


                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, phoneAuthCredential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e("PHONE SIGN IN", "Verification failed " + e);
                mVerificationInProgress = false;


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    reg_edit_text_phone_number.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(SignUp.this, "Qouta Exceeded", Toast.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }
                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]


            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.e(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatButton btn = (AppCompatButton) view;
                String str_btn_value = btn.getText().toString();
                if (str_btn_value.equals(getString(R.string.get_verification_code))) {
                    if (!validatePhoneNumber()) {
                        return;
                    }
                    startPhoneNumberVerification(reg_edit_text_phone_number.getText().toString());
                } else if (str_btn_value.equals(getString(R.string.resend_verification_code))) {
                    resendVerificationCode(reg_edit_text_phone_number.getText().toString(), mResendToken);
                } else if (str_btn_value.equals(getString(R.string.confirm_verification_code))) {
                    String code = reg_edit_text_confirm_code.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        reg_edit_text_confirm_code.setError("Cannot be empty.");
                        return;
                    }

                    verifyPhoneNumberWithCode(mVerificationId, code);
                } else if (str_btn_value.equals(getString(R.string.sign_up))) {
                    // !TODO empty field checking
                    String f_name = reg_edit_first_name.getText().toString();
                    String ot_name = reg_edit_other_name.getText().toString();
                    String password = reg_edit_password.getText().toString();
                    String ph_no = reg_edit_text_phone_number.getText().toString();
                    String local = reg_edit_locality_name.getText().toString();

                    String id = databaseReference.push().getKey();
                    artisan = new Artisan("1", f_name, ot_name, ph_no, artisan_specialization, local, password);

                    databaseReference.child(id).setValue(artisan);
                    startActivity(new Intent(getApplicationContext(), SignIn.class));

                }
            }
        });


    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void xml_elements() {
        reg_edit_text_confirm_code = findViewById(R.id.reg_edit_text_confirm_code);
        reg_edit_text_phone_number = findViewById(R.id.reg_phone_number);
        sign_in_here = findViewById(R.id.text_view_sign_in_here);
        specialization_spinner = findViewById(R.id.reg_specialization_spiiner);
        btn_sign_in = findViewById(R.id.btn_user_sign_up);
        linear_layout_phone_number_input = findViewById(R.id.phone_number_input);
        linear_layout_user_details = findViewById(R.id.reg_user_details_linear);
        linear_layout_verification_code = findViewById(R.id.verification_code_layout);
        reg_edit_first_name = findViewById(R.id.reg_artisan_first_name_edit_text);
        reg_edit_other_name = findViewById(R.id.reg_artisan_other_name_edit_text);
        reg_edit_locality_name = findViewById(R.id.reg_artisan_locality_edit_text);
        reg_edit_password = findViewById(R.id.reg_artisan_password_edit_text);
    }
    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            intent.putExtra("phone_number", currentUser.getPhoneNumber());
            Toast.makeText(this, currentUser.getPhoneNumber(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        updateUI(currentUser);


        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(reg_edit_text_phone_number.getText().toString());
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]


    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:

                // Initialized state, show only the phone number field and start button
                linear_layout_verification_code.setVisibility(View.GONE);
                linear_layout_phone_number_input.setVisibility(View.VISIBLE);
                linear_layout_user_details.setVisibility(View.GONE);
                btn_sign_in.setText(getString(R.string.confirm_verification_code));
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                btn_sign_in.setText(getString(R.string.confirm_verification_code));
                linear_layout_verification_code.setVisibility(View.VISIBLE);
                linear_layout_phone_number_input.setVisibility(View.GONE);
                linear_layout_user_details.setVisibility(View.GONE);
//                mDetailText.setText(R.string.status_code_sent);
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                linear_layout_verification_code.setVisibility(View.VISIBLE);
                linear_layout_phone_number_input.setVisibility(View.GONE);
                linear_layout_user_details.setVisibility(View.GONE);
                btn_sign_in.setText(R.string.resend_verification_code);
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                linear_layout_verification_code.setVisibility(View.GONE);
                linear_layout_phone_number_input.setVisibility(View.GONE);
                linear_layout_user_details.setVisibility(View.VISIBLE);
                btn_sign_in.setText(R.string.sign_up);

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
//                        mVerificationField.setText(cred.getSmsCode());
                        Toast.makeText(this, cred.getSmsCode(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Instant Verification Successful", Toast.LENGTH_SHORT).show();

//                        mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                linear_layout_verification_code.setVisibility(View.GONE);
                linear_layout_phone_number_input.setVisibility(View.GONE);
                linear_layout_user_details.setVisibility(View.VISIBLE);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out

        } else {
            // Signed in

        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = reg_edit_text_phone_number.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            reg_edit_text_phone_number.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                Toast.makeText(SignUp.this, "Invalid code", Toast.LENGTH_SHORT).show();
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]
}
