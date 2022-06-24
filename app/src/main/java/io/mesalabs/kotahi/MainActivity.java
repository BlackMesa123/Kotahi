package io.mesalabs.kotahi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.mesalabs.kotahi.activity.oobe.OOBEActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean userAdded = false; // TODO check if users are added
        if (!userAdded) {
            startActivity(new Intent(this, OOBEActivity.class));
            finish();
        }
    }
}