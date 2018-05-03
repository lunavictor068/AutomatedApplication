package edu.utep.cs.cs4330.automatedapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import okhttp3.WebSocket;


public class AutomatedService extends IntentService {
    Socket socket;
    BufferedReader is;
    PrintStream os;
    public AutomatedService() {
        super("AutomatedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
            new Thread(()-> {
                try {
                ServerSocket serverSocket = new ServerSocket(8000);
                System.out.println("accepting");
                socket = serverSocket.accept();
                System.out.println("accepted");
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = new PrintStream(socket.getOutputStream());
                    System.out.println("receiveing");
                    String line = null;
                        while ((line = is.readLine()) != null) {
                            JSONObject msg = new JSONObject(line);
                            String event = msg.getJSONObject("event").getString("name");
                            String eventoptions = msg.getJSONObject("event").getString("options");
                            JSONArray commands = msg.getJSONArray("commands");
                            switch (event){
                                case "now":
                                    new Thread(()->{

                                    }).start();
                                    break;
                                case "after":
                                    new Thread(()->{
                                        try {
                                            Thread.sleep(Integer.parseInt(eventoptions) * 1000);
                                            for (int i = 0; i < commands.length(); i++) {
                                                JSONObject commandJSON = commands.getJSONObject(i);
                                                String commandName = commandJSON.getString("name");
                                                String commandOptions = commandJSON.getString("options");
                                                System.out.println(commandName + " " + commandOptions);
                                                switch (commandName){
                                                    case "coordinate":
                                                        new CoordinateCommand(commandOptions).start();
                                                        break;
                                                    case "text":
                                                        new TextCommand(commandOptions).start();
                                                        break;
                                                    case "volume":
                                                        break;
                                                    case "open":
                                                        break;
                                                    case "click_text":
                                                        new ClickTextCommand(commandOptions).start();
                                                        break;
                                                    case "close":
                                                        new CloseCommand(commandOptions, getApplicationContext()).start();
                                                        break;
                                                }
                                            }
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }).start();
                                    break;
                            }

                        }
                    System.out.println("disconnected");
            } catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
    }
}
