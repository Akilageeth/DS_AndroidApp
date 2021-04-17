package com.comsats.restaurantandroid.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;


import android.content.Context;
import android.util.Log;

public class Installation {

 private static String sID;
 private static final String INSTALLATION = "INSTALLATION";

 public synchronized static String id(Context context) {
     if (sID == null) {
         final File installation = new File(context.getFilesDir(), INSTALLATION);
         try {
             if (!installation.exists()) {
                 writeInstallationFile(installation);
                 Log.d("idnew", "new");
             }
             sID = readInstallationFile(installation);
             Log.d("id", sID);
         } catch (IOException e) {
            Log.w("LOG_TAG", "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
             return "Couldn't retrieve InstallationId";
         } catch (RuntimeException e) {
             Log.w("LOG_TAG", "Couldn't retrieve InstallationId for " + context.getPackageName(), e);
             return "Couldn't retrieve InstallationId";
         }
     }
     return sID;
 }

 private static String readInstallationFile(File installation) throws IOException {
     final RandomAccessFile f = new RandomAccessFile(installation, "r");
     final byte[] bytes = new byte[(int) f.length()];
     try {
         f.readFully(bytes);
     } finally {
         f.close();
     }
     return new String(bytes);
 }

 private static void writeInstallationFile(File installation) throws IOException {
     final FileOutputStream out = new FileOutputStream(installation);
     try {
         final String id = UUID.randomUUID().toString();
         out.write(id.getBytes());
     } finally {
         out.close();
     }
 }
}

 
  
       