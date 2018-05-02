package edu.utep.cs.cs4330.automatedapplication;

import java.io.DataOutputStream;

public class TextCommand extends Command {
    public TextCommand(String options) {
        super(options);
    }

    @Override
    public void start() {
        String text = options;
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            String cmd = "/system/bin/input text " + text + "\n";
            os.writeBytes(cmd);
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
