package com.example.quizzen.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;

public class GPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPopupDialog();
    }

    private void showPopupDialog() {
        // Create a pop-up dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
        builder.setTitle("GPS positioning")
                .setMessage("Request to access the user location and enable the GPS service.")
                .setPositiveButton("Yes", (dialog, which) -> {
//                    startGPS()
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Click the Cancel button
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

