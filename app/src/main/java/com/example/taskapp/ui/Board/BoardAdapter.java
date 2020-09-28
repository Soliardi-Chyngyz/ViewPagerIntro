package com.example.taskapp.ui.Board;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.taskapp.R;

public class BoardAdapter extends PagerAdapter {
    private String[] titles = new String[]{"Fast", "Free", "Power"};
    private String[] descs = new String[]{"Telegram delivers", "Free Telegram deliversblalblalbl", "Telegram delivers Power bank"};
    private int[] images = new int[]{
            R.drawable.aab,
            R.drawable.aav,
            R.drawable.aav};

//    private String [] buttons = new String[] {"", "", "open"};

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.page_board, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDesc = view.findViewById(R.id.textDesc);

//        Button btn = view.findViewById(R.id.btn);

        textTitle.setText(titles[position]);
        textDesc.setText(descs[position]);
        imageView.setImageResource(images[position]);
//        btn.setText(buttons[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
