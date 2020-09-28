package com.example.taskapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextClock;

//import com.example.bottomnav.ui.home.TaskAdapter;
import com.example.taskapp.R;
import com.example.taskapp.Task;

public class FormFragment extends Fragment{

    private EditText editText;
    private TextClock textClock;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.etText);
        textClock = view.findViewById(R.id.textClock);

        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        // принимаю на редактирование
        if (getArguments() != null) {
            Task task = (Task) getArguments().getSerializable("task");
            if (task != null) {
                editText.setText(task.getTitle());
                textClock.setText(task.getCreatedAt());
            }
        }
    }

    private void save() {

        String itemTitle = editText.getText().toString();
        String itemClock = textClock.getText().toString();

        Task task = new Task(itemTitle, itemClock);
        Bundle bundle = new Bundle();


        if (getArguments() != null){
            bundle.putSerializable("result", task);
            getParentFragmentManager().setFragmentResult("updateForm", bundle);
        } else {
            bundle.putSerializable("task", task);
            getParentFragmentManager().setFragmentResult("form", bundle);
        }

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}
