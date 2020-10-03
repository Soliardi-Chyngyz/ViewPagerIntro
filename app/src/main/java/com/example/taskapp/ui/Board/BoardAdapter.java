package com.example.taskapp.ui.Board;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.taskapp.R;

public class BoardAdapter extends PagerAdapter {
    private String[] titles = new String[]{"Fast", "Free", "Power"};
    private String[] descs = new String[]{"Telegram delivers", "Free Telegram deliversblalblalbl", "Telegram delivers Power bank"};
    private int[] images = new int[]{
            R.drawable.aab,
            R.drawable.aav,
            R.drawable.aav};
//    private String[] buttons = new String[]{"", "", "open"};

    private OnStartClickListener onStartClickListener;

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
        onBindPageAdater(view, position);
        container.addView(view);
        return view;
    }

    private void onBindPageAdater(View view, int position) {
        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartClickListener.onClick();
            }
        });

//        btn.setText(buttons[position]);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDesc = view.findViewById(R.id.textDesc);
        textTitle.setText(titles[position]);
        textDesc.setText(descs[position]);
        imageView.setImageResource(images[position]);
        if (position < 2)
            btn.setVisibility(View.GONE);
        else
            btn.setVisibility(View.VISIBLE);
    }


    @Override
    // если мы на 1странице 3ий еще не создан, если на 3ей удалит 1ую
    // сделано для оптимизации, на случай если viewPager будет огромным
    // держать в базе будет соседние с обеих сторон а удалит все остальные
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setOnStartClickListener(OnStartClickListener onNextClickListener) {
        this.onStartClickListener = onNextClickListener;
    }

    public interface OnStartClickListener {
        void onClick ();
    }

}
