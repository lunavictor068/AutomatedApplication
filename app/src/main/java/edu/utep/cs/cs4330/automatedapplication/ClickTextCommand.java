package edu.utep.cs.cs4330.automatedapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class ClickTextCommand extends Command {
    public ClickTextCommand(String options) {
        super(options);
    }

    @Override
    public void start() {
        try {
            Thread.sleep(2000);
            new CoordinateCommand("1276 2369").start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click screenshot 1296 2348
        // wait 1 second
        // get file path
        File lastmodified = lastFileModified("/sdcard/ScreenshotTouch/Screenshot/");
            new Thread(()->{
        try {
            Thread.sleep(5000);
                String response = sendPost(true, lastmodified, "eng");
                System.out.println("RES: " + response);
            JSONObject obj = new JSONObject(response);
            JSONArray words = obj.getJSONArray("ParsedResults").getJSONObject(0).getJSONObject("TextOverlay").getJSONArray("Lines");
            System.out.println(words.toString());
            for (int i = 0; i < words.length(); i++) {
                JSONObject jsonObject = words.getJSONObject(i);
                JSONArray singlewords = jsonObject.getJSONArray("Words");
                System.out.println("SINGLEWORDS: " + singlewords.toString());
                for (int j = 0; j < singlewords.length(); j++) {
                    System.out.println("");
                    System.out.println("WORD: "+singlewords.getJSONObject(0).getString("WordText"));
                    String wrd = singlewords.getJSONObject(0).getString("WordText");
                    if (options.compareTo(wrd) == 0) {
                        System.out.println("found!");
                        int left = singlewords.getJSONObject(0).getInt("Left");
                        int top = singlewords.getJSONObject(0).getInt("Top");
                        int height = singlewords.getJSONObject(0).getInt("Height");
                        int width = singlewords.getJSONObject(0).getInt("Width");
                        int randomX = ThreadLocalRandom.current().nextInt(left, (left + width) + 1);
                        int randomY = ThreadLocalRandom.current().nextInt(top, (top+height) + 1);
                        new CoordinateCommand(randomX + " " + randomY).start();
                        return;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
            }).start();
//        System.out.println(lastmodified.getName());
//        // Convert image to base 64
//        Bitmap bm = BitmapFactory.decodeFile(lastmodified.getPath());
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
//        byte[] b = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//        new CallAPI().execute("https://api.ocr.space/parse/image", "");

        // post to website

        // search for string
        // get random x y from range
        // touch x y
    }

    private static File lastFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    private String sendPost(boolean isOverlayRequired, File imageUrl, String language) throws Exception {

        StringBuffer responseString = new StringBuffer();
        try {
            MultipartUtility multipart = new MultipartUtility("https://api.ocr.space/parse/image", "UTF-8");

            multipart.addFormField("isOverlayRequired", Boolean.toString(isOverlayRequired));
            multipart.addFilePart("file", imageUrl);
            List<String> response = multipart.finish();

            for (String line : response) {
                responseString.append(line);
            }
        } catch (IOException ex) {
            Log.v("OCR Exception",ex.getMessage());
        }

        //return result
        return String.valueOf(responseString);
    }

}
