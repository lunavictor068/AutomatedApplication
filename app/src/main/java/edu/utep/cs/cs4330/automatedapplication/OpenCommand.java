package edu.utep.cs.cs4330.automatedapplication;


import android.content.Context;
import android.content.Intent;

public class OpenCommand extends Command{
// 172.19.152.20
    private Context context;

    public OpenCommand(String options, Context context){
        super(options);
        this.context = context;
    }

    @Override
    public void start() {
        String text = options;

        try{
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(options);
            context.startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}