package com.example.taskapp.ui.notifications;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.taskapp.R;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.acl.Permission;

public class NotificationsFragment extends Fragment {
//    private NotificationsViewModel notificationsViewModel;

    private EditText editText;
    final String SEND_TEXT = "saved_text";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.etTxt);

        save();
        openText();
    }


    private void save() {
        Permissions.check(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                createFile();
            }

            private void createFile() {

                File folder = new File(Environment.getExternalStorageState(), "TaskApp");
//                // этот метод создает папку
                folder.mkdir();

                // folder.list(); дастнет все файлы этой папки
                // folder.listFiles(); достанет в виде файлов
                // folder.mkdirs(); для создании директории
                // folder.isFile(); проверка это файл       или
                // file.exists();
                // file.length() / 1024 / 1024 получим биты / мгб
                // file.ge      tNa// когда были сделаны последние изменения
                // file.lastModified();
                File file = new File("note.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openText() {
        FileInputStream fin = null;
        File file = new File(Environment.getExternalStorageDirectory(), "note.txt");

        try {
            fin = new FileInputStream(file);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            editText.setText(text);
        } catch (IOException ex) {
            Toast.makeText(requireActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(requireActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveText(String save_text) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "note.txt"));
            fos.write(save_text.getBytes());
        } catch (IOException ex) {
            Toast.makeText(requireActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(requireActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveText(editText.getText().toString());
    }
}
