package com.example.kaisersentinel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
  ImageView imageView;

 // SentinalDatabase db;
 SentinalDatabase db=new SentinalDatabase(this);
  Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);



       imageView=(ImageView)findViewById(R.id.imageView2);

        b1=(Button) findViewById(R.id.button6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setImageBitmap(db.getImage(1));
            }
        });
}
}