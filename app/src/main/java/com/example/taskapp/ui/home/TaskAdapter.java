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

import com.example.taskapp.App;
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
    // создали переменную
    private ItemClickListener listener;

    // 1.4
    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // применить к одной вьюшке другую вьюшку, ViewHolder прибавляет новый лайаут list_task
        // для отображение списка, каждыый раз он создает новый item recycler
        // и количество его столько сколько весит getItemCount()
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // этот мемонт для заполнения каждого item recycler данными
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), position);
        holder.itemView.setSelected(selectedPosition == position);          // ?
        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.GRAY);
        else
            holder.itemView.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // добавляем item
    public void addItem(Task task) {
        // каждый раз теперь он будет добавлять item на нулевой индекс (то есть вверх)
        list.add(0, task);
        notifyItemChanged(0);
    }

    // устанавливает весь лист
    public void setList(List<Task> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * этот метод обновляет item по позиции
     */
    public void update(int position, Task task) {
        list.remove(position);
        list.add(position, task);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Task task = list.get(position);
        // здесь удалили из базы
        App.instance.getAppDataBase().taskDao().delete(task);
        // а здесь уже из списка
        list.remove(task);
        // исчезнит на глазах
        notifyItemRemoved(position);
    }


    // порядок заполнения Адаптера 1
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textTime;
        private Task task;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textTime = itemView.findViewById(R.id.textTime);
            // откуда он знает что нужно открыть FormFragment
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // связан с itemView.setOnLongClickListener(this);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    if (listener != null)
                        listener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
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

    public void setList2(List<Task> list) {
        this.list.retainAll(list);
        notifyDataSetChanged();
    }


    // конструктор для этой переменной чтобы обращаться 1.3
    public void setOnItemListener(ItemClickListener listener) {
        this.listener = listener;
    }

    // создаем первым 1.1
    public interface ItemClickListener {
        void onItemClick(int position);
        void onLongClick(int position);
    }
}
