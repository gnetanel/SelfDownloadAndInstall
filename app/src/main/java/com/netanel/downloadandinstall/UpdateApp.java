package com.netanel.downloadandinstall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateApp extends AsyncTask<String,Void,Void> {
    private static final String TAG = UpdateApp.class.getSimpleName();
    private Context context;

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            String appUrl = arg0[0];
            Log.i(TAG, "Application to download and install is located at URL " + arg0[0]);
            URL url = new URL(appUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            File path=new File(context.getFilesDir(),"");
            File outputFile = new File (path, "update.apk");

            if(outputFile.exists()){
                Log.i(TAG, "Old app with same file exist in directory , deleting...");
                outputFile.delete();
            } else {
                Log.i(TAG, "No app with same file name exist on directory, continue...");
            }

            Log.i(TAG, "Output file full name is " + outputFile.toURI().toString());
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();

            Log.i(TAG, "Start downloading application...");
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            Log.i(TAG, "Download completed...");
            Log.i(TAG, "About to install package...");
            Intent installIntent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, outputFile);
            installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(installIntent);
        } catch (Exception e) {
            Log.e("UpdateAPP", "Update error! " + e.getMessage());
        }
        return null;
    }}
