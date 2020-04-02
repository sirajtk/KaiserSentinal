package com.example.kaisersentinel.ui.configure;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.kaisersentinel.ImageActivity;
import com.example.kaisersentinel.R;

public class ConfigureFragment extends Fragment {

    private ConfigureViewModel configureViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configureViewModel =
                ViewModelProviders.of(this).get(ConfigureViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configure, container, false);

        Button b3=(Button)root.findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), ImageActivity.class);
                in.putExtra("some","some data");
                startActivity(in);

            }
        });




//       final TextView textView = root.findViewById(R.id.text_configure);
//        configureViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//
//            }
//        });
//


        return root;
    }
}
