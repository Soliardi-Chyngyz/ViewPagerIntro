package com.example.taskapp.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.Task;

import java.util.ArrayList;
import java.util.List;

public class AdapterDashboardFr extends RecyclerView.Adapter<AdapterDashboardFr.ViewHolder> {
    private List<Task> list = new ArrayList<>();

    public void addData(Task task) {
        list.add(task);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textTime;
        private Task task;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle2);
            textTime = itemView.findViewById(R.id.textTime2);
        }

        public void bind(Task task, int position) {
            if (task != null) {
                this.task = task;
                this.position = position;
                textTitle.setText(task.getTitle());
                textTime.setText(task.getCreatedAt());
            }
        }
    }
}
