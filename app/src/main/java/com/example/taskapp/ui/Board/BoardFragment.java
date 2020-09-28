package com.example.taskapp.ui.Board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskapp.R;

public class BoardFragment extends Fragment {
    private ViewPager viewPager;
    private TextView back, skip;
    private Button openPage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_boarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new BoardAdapter());
//        viewPager.addOnPageChangeListener();

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // вызываем активити
                requireActivity().finish();
            }
        });
        back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BACK", "onClick: back" + view);
                if (viewPager.getCurrentItem() > 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        skip = view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SKIP", "onClick: skip" + view);
                if (viewPager.getCurrentItem() < (viewPager.getChildCount() - 1))
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        openPage = view.findViewById(R.id.btn);
        if (viewPager.getCurrentItem() == 2) {
            openPage.setVisibility(View.VISIBLE);
        }
        openPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });



//        switch (view.getId()) {
//            case (R.id.back) :
//                if (viewPager.getCurrentItem() > 0)
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//                break;
//            case (R.id.skip) :
//                if (viewPager.getCurrentItem() < (viewPager.getChildCount() - 1))
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                break;
//        }
    }
}