package com.example.kaisersentinel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

public class EnterMac extends Activity {

    private EditText mEditMac;
    private TextView mOldID;
    FirebaseAuth mAuth;
    private String s;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mac);
        mAuth = FirebaseAuth.getInstance();

        mEditMac = (EditText) findViewById(R.id.editText6);
        mOldID = (TextView) findViewById(R.id.textView3);
        openView();
        tinyDB = new TinyDB(EnterMac.this);



        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                okview();


            }
        });
    }

    private void openView()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(EnterMac.this,"You clicked yes button", Toast.LENGTH_LONG).show();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EnterMac.this, ConfigActivity.class);
                startActivity(intent);            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void okview()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        s = mEditMac.getText().toString();
        tinyDB.putString("macc",s);

        alertDialogBuilder.setMessage("Are you sure, You wanted to change ID" +s);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(EnterMac.this,"You clicked yes button", Toast.LENGTH_LONG).show();
                        tinyDB.putString("IdSystem",s);

                        Log.e("mactiny", "onClick: "+ tinyDB.getString("macc") );
                        mOldID.setText("New ID" + tinyDB.getString("macc"));

                        mEditMac.setText("");
                        Toast.makeText(EnterMac.this, s, Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        Intent intent = new Intent(EnterMac.this, ConfigActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOldID.setText("OLD ID" + tinyDB.getString("macc"));
    }
}
