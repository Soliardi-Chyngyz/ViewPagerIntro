package com.example.taskapp.ui.Profile;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.example.bottomnav.MainActivity;
//import com.example.bottomnav.R;
import com.example.taskapp.R;

public class Profile extends Fragment {
    private TextView putText, txt;
    private EditText etText;
    private ImageView etImg;
    private Button button;
    private Uri imgData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        putText = view.findViewById(R.id.putTxtResult);
        txt = view.findViewById(R.id.txt);
        etText = view.findViewById(R.id.etTxtAcc);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putText.setText(etText.getText().toString());
            }
        });
        etImg = view.findViewById(R.id.etImg);
        etImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data != null) {
            Glide.with(this).load(data.getData()).circleCrop().into(etImg);
//            imgData = data.getData();
//            etImg.setImageURI(imgData);
//                or
//            etImg.setImageURI(data.getData());
        }
    }
}
