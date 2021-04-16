package com.example.test_showcard;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.test_showcard.Dynamic.DynamicView;
import com.example.test_showcard.Dynamic.ViewHolder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject jsonObject;
       // setContentView(R.layout.activity_main);
        try {

            jsonObject = new JSONObject(readFile("TextIT.json", this));

        } catch (JSONException je) {
            je.printStackTrace();
            jsonObject = null;
        }

        if (jsonObject != null) {
            View sampleView = DynamicView.createView(this, jsonObject, ViewHolder.class);
            sampleView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            setContentView(sampleView);

        } else {
            Log.e("cx", "Could not load valid json file");
        }
    }

    private String readFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public void writeToLocalFile(String filePath , String fileName){
        //写入本地文件
        InputStream is = null;
        try {
            is = getAssets().open(filePath);
            int len = 0;
            byte[] data = new byte[1024];
            File appDir = new File(Environment.getExternalStorageDirectory(), "IZD");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File file = new File(appDir, fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            Log.e("cx" , "filePath : " + file.getPath());
            FileOutputStream outputStream = new FileOutputStream(file);
            while((len = is.read(data)) > 0){
                outputStream.write(data,0,len);
                outputStream.flush();
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}