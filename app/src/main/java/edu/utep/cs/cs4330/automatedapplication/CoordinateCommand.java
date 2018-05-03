package edu.utep.cs.cs4330.automatedapplication;

import java.io.DataOutputStream;

public class CoordinateCommand extends Command {
    public CoordinateCommand(String options) {
        super(options);
    }

    @Override
    public void start() {
        String[] optionsarr = this.options.split("-");
        String x = optionsarr[0];
        String y = optionsarr[1];
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            String cmd = "/system/bin/input tap " + x + " " + y + "\n";
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
