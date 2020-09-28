package com.example.taskapp.ui.home;


import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.bottomnav.R;
//import com.example.bottomnav.Task;

import com.example.taskapp.R;
import com.example.taskapp.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

// добавления extends Rec..  1.2
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    // 1.3
    private List<Task> list;
    private int selectedPosition = 0;
    private ItemClickListener listener;

    // 1.4
    public TaskAdapter(List<Task> list, ItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // применить к одной вьюшке другую вьюшку, ViewHolder приюавляет новый лайаут list_task
        // для отображение списка, каждыый раз он создает новый item recycler
        // и количество его столько сколько весит getItemCount()
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // этот мемонт для заполнения каждого item recycler данными
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), position);
        holder.itemView.setSelected(selectedPosition == position);
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.GRAY);
        else
            holder.itemView.setBackgroundColor(Color.WHITE);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItem(Task task) {
        // каждый раз теперь он будет добавлять item на нулевой индекс (то есть вверх)
//        list.set(selectedPosition, task);
        list.add(0, task);
        notifyItemChanged(0);
        //        notifyItemChanged(list.indexOf(task));
//        notifyItemChanged(list.size() - 1);
    }

    public void update(int position, Task task) {
        list.remove(position);
        list.add(position, task);
        notifyDataSetChanged();

    }


    // порядок заполнения Адаптера 1
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView textTitle, textTime;
        private Task task;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textTime = itemView.findViewById(R.id.textTime);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Task task, int position) {
            if (task != null) {
                this.task = task;
                this.position = position;
                textTitle.setText(task.getTitle());
                textTime.setText(task.getCreatedAt());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            selectedPosition = getAdapterPosition();
            notifyItemChanged(selectedPosition);
//            notifyItemChanged(selectedPosition);
            if (listener != null)
                listener.onItemClick(getAdapterPosition());
            return true;
        }
    }

    public void setOnClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
