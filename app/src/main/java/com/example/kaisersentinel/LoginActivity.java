package com.example.kaisersentinel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText uname;
    EditText pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.button);
        uname=findViewById(R.id.editText2);
        pass=findViewById(R.id.editText4);

        SentinalDatabase db=new SentinalDatabase(this);
        SQLiteDatabase my =db.getReadableDatabase();
//        String projection[]={"mac_id,username,password"};
//        Cursor c=my.query("login",projection,null,null,null,null,null);
//        c.moveToFirst();
//        String mac=c.getString(0).toString();
//        String username=c.getString(1).toString();
//        final String password=c.getString(2).toString();
//        System.out.println("user mac= "+mac);
//        System.out.println("username= "+username);
//        System.out.println("pwd="+password);


   login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

//           String user = uname.getText().toString();
//           final String pwd = pass.getText().toString();
//           System.out.println(pwd);
//           System.out.println(password);
//
//           if (pwd.equals(password)) {
//
//           } else {
           Intent i = new Intent(LoginActivity.this, MainActivity.class);
//               i.putExtra("r1",r1);
//               i.putExtra("r2",r2);
//               i.putExtra("r3",r3);
//               i.putExtra("r4",r4);

           startActivity(i);
//               Toast toast = Toast.makeText(getApplicationContext(), "user name and password is wrong", Toast.LENGTH_SHORT);
//               // toast.setMargin(50,50);
//               toast.show();
//           }
       }
   });


       }
    }




