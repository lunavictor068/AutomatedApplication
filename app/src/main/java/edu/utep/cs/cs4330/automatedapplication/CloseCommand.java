package edu.utep.cs.cs4330.automatedapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CloseCommand extends Command {
    private Context context;
    public CloseCommand(String options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    public void start() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + options));
            context.startActivity(intent);
            Thread.sleep(2000);
            new CoordinateCommand("1000-700").start();
            Thread.sleep(2000);
            new CoordinateCommand("1090-1428").start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
