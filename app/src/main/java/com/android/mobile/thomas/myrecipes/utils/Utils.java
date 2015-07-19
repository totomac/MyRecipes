package com.android.mobile.thomas.myrecipes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Thomas on 19/07/2015.
 */
public class Utils {
    public static Bitmap convertUritoBitMap(String source, Context context) {
        Uri selectedImage = Uri.parse(source);

        InputStream imageStream;
        Bitmap yourSelectedImage = null;
        try {

            imageStream = context.getContentResolver().openInputStream(selectedImage);
            yourSelectedImage = BitmapFactory.decodeStream(imageStream);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return yourSelectedImage;
    }

    public static void copyBDDAction() {

        File f1 = new File("/data/data/com.main.bonappetit/databases/bonAppetit.db");
        File f2 = new File("/sdcard/bonappetit.db");

        if (!f1.exists()) {
            Log.v("copyBDDAction", "File '" + f1.getPath() + "' does not exists");
            return;
        }

        try {
            InputStream in = new FileInputStream(f1);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            Log.v("copyBDDAction", "Copy debug db failed");
            return;
        }

        return;
    }
}
