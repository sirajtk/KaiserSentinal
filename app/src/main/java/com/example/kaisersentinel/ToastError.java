package com.example.kaisersentinel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastError {

     public ToastError(){

     }
    public void showError(String message, Context context){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);

        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dissatisfied, 0, 0, 0);

        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        toast.show();
    }




}
