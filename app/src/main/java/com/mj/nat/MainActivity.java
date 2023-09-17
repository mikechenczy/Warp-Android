package com.mj.nat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mj.nat.client.Client;

public class MainActivity extends AppCompatActivity {
    public EditText wsEditText;
    public EditText portEditText;
    public EditText hostEditText;
    public Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wsEditText = findViewById(R.id.edit_text_ws);
        portEditText = findViewById(R.id.edit_text_port);
        hostEditText = findViewById(R.id.edit_text_host);
        startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(v -> {
            String[] args;
            if(hostEditText.getText().toString().replaceAll(" ", "").equals("")) {
                args = new String[]{wsEditText.getText().toString(), portEditText.getText().toString()};
            } else {
                args = new String[]{wsEditText.getText().toString(), portEditText.getText().toString(), hostEditText.getText().toString()};
            }
            new Thread(() -> Client.main(args)).start();
        });
    }
}
