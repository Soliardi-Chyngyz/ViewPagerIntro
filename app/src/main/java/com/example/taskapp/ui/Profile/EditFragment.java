package com.example.taskapp.ui.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskapp.Prefs;
import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// этот EditFragment работает как метод, его можно переиспользовать
// для всех вьюшек что мы хотим изменить
public class EditFragment extends Fragment {

    private EditText editText;
    public boolean knopka = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_text);
        // получаем ключ по bundle из ProfileFragment
        String text = requireArguments().getString("text");
        editText.setText(text);
        editText.setSelection(text.length());

        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
                saveToProfile();
                saveToFirestore();
            }
        });

    }

    private void saveToProfile() {
        if (!editText.getText().toString().trim().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString("id", editText.getText().toString());
            getFragmentManager().setFragmentResult("id", bundle);
        } else {
            Toast.makeText(getContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        Prefs.instance.saveName(editText.getText().toString().trim());
    }

    private void saveToFirestore() {
        // к userId внесли функцию .getUid()
        // метод .getUid() - это id юзера
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String textName = editText.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("name", textName);
        FirebaseFirestore.getInstance().collection("users")
                // вносим по id и отправляем через set по map
                .document(userId)
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            close();
                        } else {
                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void close() {
        if (!knopka) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
            knopka = true;
        }
    }
}