package com.boating.vpn.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.boating.vpn.R;
import com.boating.vpn.SharedPreference;
import com.boating.vpn.model.Server;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreference preference;
    ImageButton closeImageButton;
    Button saveAccountButton;
    EditText usernameEditText, passwordEditText;
    Intent intentToMainActivity;
    String usernameET, passwordET;
    Server server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preference = new SharedPreference(this);

        intentToMainActivity = new Intent(SettingsActivity.this, MainActivity.class);

        closeImageButton = findViewById(R.id.closeImageButton);
        closeImageButton.setOnClickListener(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        saveAccountButton = findViewById(R.id.saveAccountButton);
        saveAccountButton.setOnClickListener(this);

        if(preference.getServer() != null){
            server = preference.getServer();
            usernameEditText.setText(server.getOvpnUserName());
            passwordEditText.setText(server.getOvpnUserPassword());
        }
    }

    @Override
    public void onClick(View v) {
        usernameET = String.valueOf(usernameEditText.getText());
        passwordET = String.valueOf(passwordEditText.getText());
        switch (v.getId()) {
            case R.id.closeImageButton:
                startActivity(intentToMainActivity);
                break;
            case R.id.saveAccountButton:
                if("".equals(usernameET)){
                    Toast.makeText(this, "Username can't be empty!",
                            Toast.LENGTH_LONG).show();
                    usernameEditText.requestFocus();
                } else if("".equals(passwordET)){
                    Toast.makeText(this, "Password can't be empty!",
                            Toast.LENGTH_LONG).show();
                    passwordEditText.requestFocus();
                } else {
                    server = new Server("Main",
                            "main.ovpn",
                            String.valueOf(usernameET),
                            String.valueOf(passwordET)
                    );
                    preference.saveServer(server);
                    startActivity(intentToMainActivity);
                }
                break;
            default:
                break;
        }
    }
}
