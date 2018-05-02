package edu.utep.cs.cs4330.automatedapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // test2
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void volume(View view){
        VolumeCommand vol = new VolumeCommand("-", getApplicationContext());
        vol.start();

    }
}
