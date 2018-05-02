package edu.utep.cs.cs4330.automatedapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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


            View v1 = getWindow().getDecorView().getRootView();
            //View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

