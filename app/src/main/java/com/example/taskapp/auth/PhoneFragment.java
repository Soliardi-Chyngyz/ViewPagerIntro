package com.example.taskapp.auth;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {

    private EditText editPhone, etCode, etNumber;
    private OnVerificationStateChangedCallbacks callBacks;
    private Button btnConfirm, btnCarry, btnOpen;
    private String verificationId;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliSeconds = 10000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // переопределили OnBackPressedCallback чтобы из приложения выходило
        // при возвращении назад
        OnBackPressedCallback onBackPressedCallback =  new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.etPhone);
        etCode = view.findViewById(R.id.et_code);
        etNumber = view.findViewById(R.id.etNumber);

        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId, etCode.getText().toString().trim());
                signIn(phoneAuthCredential);
            }
        });

        initCallBacks();
        btnCarry = view.findViewById(R.id.btnCarry);
        btnCarry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireSms();
                startTimer();
            }
        });

        // по нажатию кнопки назад, он выходит
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        clickOPenMenu(view);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSeconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliSeconds = l;
                updateTimer();
            }
            @Override
            public void onFinish() {
                editPhone.setVisibility(View.VISIBLE);
                btnOpen.setVisibility(View.VISIBLE);
                btnCarry.setVisibility(View.VISIBLE);
                etNumber.setVisibility(View.VISIBLE);
                etCode.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.GONE);
            }
        }.start();
    }

    private void updateTimer() {
        int seconds = (int) (timeLeftInMilliSeconds % 60000 / 1000);
        btnConfirm.setText(String.valueOf(seconds));

    }

    private void clickOPenMenu(View view) {
        btnOpen = view.findViewById(R.id.btnOpenMenu);
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });
    }

    private void initCallBacks() {
        callBacks = new OnVerificationStateChangedCallbacks() {
            @Override
            // автозаполнение смс кода
            // phoneAuthCredential хранится смс, номер телефона
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
                String smsCode = phoneAuthCredential.getSmsCode();
                if (smsCode != null) {
                    etCode.setText(smsCode);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
            }

            @Override
            // по истечению 60сек срабаотывает он
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            // код отправлен
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }
        };
    }

    // для создания и авторизации
    // .addOnCompleteListener эта часть кода когда успешно авторизовался юзер и открывается далее какое
    // нибудь окно
    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigateUp();
                } else {
                    //Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requireSms() {
        String phone = etNumber.getText().toString().trim() + editPhone.getText().toString().trim();

        if (!phone.isEmpty()) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    10,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    callBacks);
            editPhone.setVisibility(View.GONE);
            btnOpen.setVisibility(View.GONE);
            btnCarry.setVisibility(View.GONE);
            etNumber.setVisibility(View.GONE);
            etCode.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
        }
    }
}