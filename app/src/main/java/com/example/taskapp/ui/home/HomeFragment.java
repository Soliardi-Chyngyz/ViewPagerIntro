package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.bottomnav.R;
//import com.example.bottomnav.Task;
import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab; // это плюсик
    private RecyclerView recyclerView;
    private static List<Task> list = new ArrayList<>();  // 2
    private static TaskAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(list);
        list.clear();
        List<Task> list = App.instance.getAppDataBase().taskDao().getAll();
        // list - содержит результат всех действий 57строки
        // list равно значению которое 57 возвращают
        adapter.setList(list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycler_view);
        initListeners();
        initList();

        adapter.setOnItemListener(new TaskAdapter.ItemClickListener() {
            @Override
            public void onItemClick(final int position) {
//                clickAdapter(position);
                getParentFragmentManager().setFragmentResultListener("updateForm", HomeFragment.this,
                        new FragmentResultListener() {
                            @Override
                            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                                Log.i("TAG", "onFragmentResult: " + result.getString("task"));
                                adapter.update(position, ((Task) result.getSerializable("result")));
                            }
                        });

                Bundle bundle = new Bundle();
                bundle.putSerializable("task", list.get(position));
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_formFragment, bundle);

            }

            @Override
            public void onLongClick(final int position) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                adapter.deleteItem(position);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Уверен?")
                        .setPositiveButton("Точно", dialogClickListener)
                        .setNegativeButton("Не уверен", dialogClickListener).show();

                // отправляет позицию -Ю-

                // это на случай если мы хотим просто удалить из базы -_-
//                delete(adapter.getItem(position));
            }
        });
    }

    // это на случай если мы хотим просто ужалить из базы -_-
    //    private void delete(Task task) {
    //        App.instance.getAppDataBase().taskDao().delete(task);
    //    }

    // метод для отображения списка
    private void initList() {
        recyclerView.setAdapter(adapter);
        // divider
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


    }

    private void initListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_formFragment);
            }
        });

        // на ключ "form" устанавливаем слушателя, и по нему получим любые данные из другого
        // фрагмента
        getParentFragmentManager().setFragmentResultListener("form", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.i("TAG", "onFragmentResult: " + result.getString("task"));
                adapter.addItem((Task) result.getSerializable("task"));
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove:
                list.clear();
                App.getInstance().getAppDataBase().taskDao().nukeTable();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.action_sort:
                sides();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean flag = true;
    private ArrayList<Task> oldList = new ArrayList<>();

    private void sides() {
        if (flag) {
            oldList.addAll(list);
            Collections.sort(list, new Comparator<Task>() {
                @Override
                public int compare(Task task, Task t1) {
                    return task.getTitle().compareTo(t1.getTitle());
                }
            });
            adapter.notifyDataSetChanged();
//            adapter.setList(list);
        } else {
            list.clear();
            adapter.setList(oldList);
            oldList.clear();
        }
        // создали бесконечный цикл true - false - true...
        flag = !flag;
    }
}