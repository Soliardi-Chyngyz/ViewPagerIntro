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
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.bottomnav.MainActivity;
//import com.example.bottomnav.R;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    private LinearLayout cont, img;
    private ImageView etImg, btnReduct;
    private TextView txtName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        txtName.setText(Prefs.instance.getName());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnReduct = view.findViewById(R.id.btn_reduct);

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
        txtName = view.findViewById(R.id.t1);
        btnReduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName();
            }
        });

        // проверка на ноль и на пустоту вместе
        if (!TextUtils.isEmpty(Prefs.instance.getName())) {
            readFromFB();
        }

        if (!Prefs.instance.getImage().isEmpty()){
            getImageFromFB();
        }
    }

    private void getImageFromFB() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("image")
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null) {
                            String name = documentSnapshot.getString("images");
                            Uri uri = Uri.parse(name);
                            Glide.with(getContext()).load(uri).circleCrop().into(etImg);
                            Glide.with(getContext()).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    img.setBackground(drawable);
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getImageFromSP() {
        if (!Prefs.instance.getImage().isEmpty()) {
            Uri uri = Uri.parse(Prefs.instance.getImage());
            Glide.with(this).load(uri).circleCrop().into(etImg);
            Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Drawable drawable = new BitmapDrawable(resource);
                    img.setBackground(drawable);
                }
            });
        }
    }

    private void readFromFB() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name = documentSnapshot.getString("name");
                        txtName.setText(name);
                    }
                });
    }

    private void etName() {
        textFile();
        Bundle bundle = new Bundle();
        bundle.putString("text", txtName.getText().toString());
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_profile_to_editFragment, bundle);
    }

    private void textFile() {
        getFragmentManager().setFragmentResultListener("id", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                txtName.setText(result.getString("id"));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100 && data != null)
            // glide работа с img
            Glide.with(this).load(data.getData()).circleCrop().into(etImg);
        // сохранили в SharedPref.
        String image = String.valueOf(data.getData());
        Prefs.instance.saveImage(image);
        Glide.with(this).asBitmap().load(data.getData()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(resource);
                img.setBackground(drawable);
            }
        });

        saveInFirestore(image);
    }

    private void saveInFirestore(String image) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> map = new HashMap<>();
        map.put("images", image);
        FirebaseFirestore.getInstance().collection("image")
                // вносим по id и отправляем через set по map
                .document(userId)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
