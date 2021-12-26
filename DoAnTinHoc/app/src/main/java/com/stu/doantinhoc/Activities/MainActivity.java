package com.stu.doantinhoc.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.stu.doantinhoc.Controller.LoginController;
import com.stu.doantinhoc.R;
import com.stu.doantinhoc.databinding.ActivityMainBinding;

import org.json.JSONArray;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        controller = new LoginController(MainActivity.this);
        LoadAllData();
        //login action
        login();
    }

    private void LoadAllData() {
        controller.loadDataFromDB();
    }


    private void login() {
        username = binding.edtUsername;
        password = binding.edtPassword;
        btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(v -> controller.actionLogin(
                username.getText().toString().trim(),
                password.getText().toString().trim())
        );
    }

}