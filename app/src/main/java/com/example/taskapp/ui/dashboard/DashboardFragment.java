package com.example.taskapp.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private AdapterDashboardFr adapter = new AdapterDashboardFr(list);
    private FirebaseFirestore fb;
    private static List<Task> list = new ArrayList<>();
    private RecyclerView recyclerView;

//    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fb = FirebaseFirestore.getInstance();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragmentDasha);
        recyclerView = view.findViewById(R.id.recycler_view_fragmentDasha);
        recyclerView.setAdapter(adapter);

        fb
                .collection("Tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Task> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task myTask = new Task(document.getString("title"),document.getString("time"));
                                list.add(myTask);
//                                adapter.addData(new Task(document.getString("time"), document.getString("title")));
                            }
                            adapter.updateList(list);
                        } else {
                            Log.e("TAG", "onComplete: FAILED");
                        }
                    }
                });

        adapter.setOnItemListener(new AdapterDashboardFr.ItemClickListener() {
            @Override
            public void onLongClick(final String title, final int position) {
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteFB(title,position);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Уверен?")
                        .setPositiveButton("Yes", dialog)
                        .setNegativeButton("No", dialog).show();
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
//                deleteFB();

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteFB(String title, int position) {
        adapter.deleteItem(position);
        fb.collection("Tasks")
                .document(title)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        adapter.deleteList();
                    }
                });
    }

}