package edu.utep.cs.cs4330.automatedapplication;


import android.content.Context;
import android.content.Intent;

public class OpenCommand extends Command{

    private Context context;

    public OpenCommand(String options, Context context){
        super(options);
        this.context = context;
    }

    @Override
    public void start() {
        String text = options;

        try{
            Intent intent = new Intent(options);
            context.startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}