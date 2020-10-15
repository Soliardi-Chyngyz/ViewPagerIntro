package com.example.taskapp.ui.Board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class BoardFragment extends Fragment {
    private ViewPager viewPager;
    private TextView back, skip;
    private DotsIndicator dotsIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_boarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
        viewPager = view.findViewById(R.id.view_pager);
        BoardAdapter boardAdapter = new BoardAdapter();
        viewPager.setAdapter(boardAdapter);
        dotsIndicator.setViewPager(viewPager);


        boardAdapter.setOnStartClickListener(new BoardAdapter.OnStartClickListener() {
            @Override
            public void onClick() {
                openPhoneFr();
            }
        });

        back = view.findViewById(R.id.back);
        skip = view.findViewById(R.id.skip);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                if (viewPager.getCurrentItem() > 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
//                if (viewPager.getCurrentItem() < (viewPager.getChildCount() - 1))
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                openPhoneFr();
            }
        });


        // это выход из приложения
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // вызываем активити, так как в проекте у нас одно активити
                // он закроет только его .finish();  но если бы были еще мы бы закрыли все
                // активити
                // requireActivity()  - родительское активити
                requireActivity().finish();
            }
        });
    }

    private void openPhoneFr() {
        // сохранение состояния
        Prefs.instance.saveShowState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//        navController.navigate(R.id.action_boardFragment_to_phoneFragment);
        navController.navigateUp();
    }
}
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

//        switch (view.getId()) {
//            case (R.id.back):
//                if (viewPager.getCurrentItem() > 0)
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//                break;
//            case (R.id.skip):
//                if (viewPager.getCurrentItem() < (viewPager.getChildCount() - 1))
//                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                break;
//        }

