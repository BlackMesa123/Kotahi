package io.mesalabs.kotahi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.drinkless.tdlib.TdApi;

import io.mesalabs.kotahi.activity.oobe.OOBEActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KotahiApp.getUser(object -> {
            if (object.getConstructor() == TdApi.Error.CONSTRUCTOR && ((TdApi.Error) object).code == 401) {
                startActivity(new Intent(MainActivity.this, OOBEActivity.class));
                finish();
            }
        });
    }
}