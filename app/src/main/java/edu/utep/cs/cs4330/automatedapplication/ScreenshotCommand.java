package edu.utep.cs.cs4330.automatedapplication;

import android.content.Context;
import android.graphics.Bitmap;

import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class ScreenShotCommand extends Command {

    public Context context;

    public ScreenShotCommand(String options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    public void start() {
        String text = options;

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";


            //View v1 = getWindow().getDecorView().getRootView();
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

           // openScreenshot(imageFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

