package com.example.kaisersentinel;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

//import com.example.anil.kaiserkomputer.other.ToastError;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends Activity {
    EditText mEditCode,mEditdsign;
    FirebaseAuth mAuth;
    String codesent;
    private EditText mSysCode,mSysSign;
    private static int REQUEST_CODE=1;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        mEditCode = (EditText)findViewById(R.id.editText);
        mEditdsign = (EditText)findViewById(R.id.editText2);
        mSysCode = (EditText)findViewById(R.id.editText3);
        mSysSign = (EditText)findViewById(R.id.editText4);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);
        if(mAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

        progressDialog = new ProgressDialog(this);




        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mSysCode.getText().toString().equals("sentinel@ahdynamics.com") && mSysSign.getText().toString().equals("sentinel1234")){
                    callMac();

                }else{
                    Toast.makeText(SigninActivity.this, "Invalid Credetials", Toast.LENGTH_SHORT).show();
                }

            }
        });

            findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*try
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String email = mEditCode.getText().toString().trim();
                String password = mEditdsign.getText().toString().trim();
                //checking if email and passwords are empty
                if (TextUtils.isEmpty(email) ) {
                    Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
                    mEditCode.setError("Please enter email");
                }else if(!email.matches(emailPattern)){
                    Toast.makeText(SigninActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    mEditCode.setError("Please enter valid email");
                }else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
                    mEditCode.setError("Please enter valid password");
                }else {
                    progressDialog.setMessage("Authenticating Please Wait...");
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if (task.isSuccessful()) {
                                //start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            } else {
                                ToastError toastError = new ToastError();
                                toastError.showError("Authenticatio failed!",getApplicationContext());
                                //Toast.makeText(SigninActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }*/

                if(mEditCode.getText().toString().equals("sentinel@ahdynamics.com") && mEditdsign.getText().toString().equals("sentinel1234"))
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }else{
                    Toast.makeText(SigninActivity.this, "Invalid Credetials", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void callMac()
    {
        Intent intent = new Intent(SigninActivity.this, EnterMac.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
