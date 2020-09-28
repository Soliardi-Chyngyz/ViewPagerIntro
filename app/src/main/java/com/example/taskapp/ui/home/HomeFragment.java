package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.bottomnav.R;
//import com.example.bottomnav.Task;
import com.example.taskapp.R;
import com.example.taskapp.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements TaskAdapter.ItemClickListener{

    private FloatingActionButton fab; // это плюсик
    private RecyclerView recyclerView;
    private ArrayList<Task> list = new ArrayList<>(); // 2
    private TaskAdapter adapter;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(list, this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recycler_view);
        initListeners();
        initList();

        adapter.setOnClickListener(this);
    }

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
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_formFragment);
            }
        });

        // на ключ "form" устанавливаем слушателя, и по нему получим любые данные из другого
        // фрагмента
        getParentFragmentManager().setFragmentResultListener("form", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.i("TAG", "onFragmentResult: " + result.getString("task"));
                adapter.setItem((Task) result.getSerializable("task"));
            }
        });


    }

    @Override
    public void onItemClick (final int position) {
        getParentFragmentManager().setFragmentResultListener("updateForm", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.i("TAG", "onFragmentResult: " + result.getString("task"));
                adapter.update(position, ((Task) result.getSerializable("result")));
            }
        });

        Bundle bundle = new Bundle();
        bundle.putSerializable("task", list.get(position));
        Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_formFragment,bundle);
    }
}