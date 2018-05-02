package edu.utep.cs.cs4330.automatedapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class VolumeCommand extends Command {



    public Context context;

    public VolumeCommand(String options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    public void start() {

        AudioManager audioManager = (AudioManager)
                context.getSystemService(Context.AUDIO_SERVICE);

        String text = options;

        if (options.equals("+")) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        }
        else if (options.equals("-")) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        }
        try{
            Intent intent = new Intent(options);
            context.startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

