package com.example.kaisersentinel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.anil.kaiserkomputer.activity.DynamicLayoutActivity;


public class ConfigActivity extends Activity implements View.OnClickListener {
    private EditText channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        channel = (EditText) findViewById(R.id.lvEdittext);



        Button button = findViewById(R.id.moverooms);
//        button.setImageResource(R.drawable.arrow_right_24dp);
//        button.setBackgroundColor(getResources().getColor(R.color.white));
       button.setOnClickListener(this);


        channel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!channel.getText().toString().equals("")) {
                    int ss = Integer.valueOf(channel.getText().toString());

                    System.out.println("no of channel="+ss);
                }

            }
        });

    }

    @Override
    public void onClick(View v)
    {
        if(channel.getText().toString().trim().equalsIgnoreCase("") ) {
            channel.setError("This Field Cannot be empty");
        }

       else
           {
            if (channel.getError() != null || channel.getText().length() == 0) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

            }
            else
                {

                Intent intent = new Intent(ConfigActivity.this,SigninActivity.class);
                intent.putExtra("channel", channel.getText().toString());

                 startActivity(intent);
            }


//           }

        }
// else {
//            int s = Integer.valueOf(lvrooms.getText().toString());
//            String s1 = roomName.getText().toString();
//            String[] s2 = s1.split(",",10);
//            Log.e("eroredit", "onClick: " + Arrays.toString(s2));
//            if(s == s2.length) {
//                for (String aS2 : s2) {
//                    if (aS2.equals("")) {
//                        roomName.setError("Field not matching with no of rooms given");
//                    }
//                }
////                Intent intent = new Intent(ConfigActivity.this, DynamicLayoutActivity.class);
////                intent.putExtra("roomcount1", lvrooms.getText().toString());
////                intent.putExtra("roomname", roomName.getText().toString());
////                startActivity(intent);
//            }else{
//                roomName.setError("error not matching");
//            }
//        }
    //switch (v.getId()){


//        case R.id.moverooms:
//            switch (lvrooms.getText().toString()) {
//                case "3": {
//                    Intent intent = new Intent(ConfigActivity.this, appliActivity.class);
//                    intent.putExtra("roomname", roomName.getText().toString());
//                    startActivity(intent);
//                    break;
//                }
//                case "7": {
//                    Intent intent = new Intent(ConfigActivity.this, room4Activity.class);
//                    intent.putExtra("roomname", roomName.getText().toString());
//                    startActivity(intent);
//                    break;
//                }
//                case "8": {
//                    Intent intent = new Intent(ConfigActivity.this, DynamicLayoutActivity.class);
//                    intent.putExtra("roomcount1",lvrooms.getText().toString());
//                    intent.putExtra("roomname", roomName.getText().toString());
//                    startActivity(intent);
//                    break;
//                }
//                default:
//                    Intent intent = new Intent(ConfigActivity.this, DynamicLayoutActivity.class);
//                    intent.putExtra("roomcount1",lvrooms.getText().toString());
//                    intent.putExtra("roomname", roomName.getText().toString());
//                    startActivity(intent);
//                    break;
//            }
//            break;
  //  }
    }
}
