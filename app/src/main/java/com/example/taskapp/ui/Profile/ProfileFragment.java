package com.example.taskapp.ui.Profile;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.example.bottomnav.MainActivity;
//import com.example.bottomnav.R;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.taskapp.R;

import static com.daimajia.androidanimations.library.Techniques.RubberBand;
import static com.daimajia.androidanimations.library.Techniques.Tada;

public class ProfileFragment extends Fragment {
    private LinearLayout cont;
    private ImageView etImg, btn_plus, btn_com;
    private LinearLayout img;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etImg = view.findViewById(R.id.etImg);
        etImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.BounceIn).duration(700).repeat(1).playOn(etImg);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
        img = view.findViewById(R.id.profile);
        cont = view.findViewById(R.id.cont);
        view.findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(1)
                        .playOn(cont);
            }
        });
        view.findViewById(R.id.btn_com).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.FadeOutDown)
                        .duration(700)
                        .repeat(1)
                        .playOn(btn_com);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data != null)
            // glide работа с img
            Glide.with(this).load(data.getData()).circleCrop().into(etImg);
            Glide.with(this).asBitmap().load(data.getData()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Drawable drawable = new BitmapDrawable(resource);
                    img.setBackground(drawable);
                }
            });
    }
}
