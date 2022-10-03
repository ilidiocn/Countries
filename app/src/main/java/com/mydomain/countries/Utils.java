package com.mydomain.countries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class Utils {

    Activity activity;

    public Utils(Activity activity) {
        this.activity = activity;
    }

    public void createCSV(StringBuilder data) {


        Calendar calendar = Calendar.getInstance();
        long time= calendar.getTimeInMillis();
        try {
            //
            FileOutputStream out = activity.openFileOutput("CSV_Data_"+time+".csv", Context.MODE_PRIVATE);

            //store the data in CSV file by passing String Builder data
            out.write(data.toString().getBytes());
            out.close();
            Context context = activity.getApplicationContext();
            final File newFile = new File(Environment.getExternalStorageDirectory(),"SimpleCVS");
            if(!newFile.exists())
            {
                newFile.mkdir();
            }
            File file = new File(context.getFilesDir(),"CSV_Data_"+time+".csv");
            Uri path = FileProvider.getUriForFile(context,"com.example.dataintocsvformat",file);
            //once the file is ready a share option will pop up using which you can share
            // the same CSV from via Gmail or store in Google Drive
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            intent.putExtra(Intent.EXTRA_STREAM, path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(intent,"Excel Data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
