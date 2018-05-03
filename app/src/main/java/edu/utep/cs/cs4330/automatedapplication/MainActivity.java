package edu.utep.cs.cs4330.automatedapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private int STORAGE_PERMISSION_CODE=23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, STORAGE_PERMISSION_CODE);
        // test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cmd(View view){
        Intent intent = new Intent(this, AutomatedService.class);
        startService(intent);
    }
}
