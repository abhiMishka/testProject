package com.abhishek.testproject;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by abhishekkumar on 17/11/16.
 */

public class UtilFunctions {

    public static String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = Global.getInstance().getAssets().open(Constants.JSON_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, Global.getInstance().getString(R.string.utf_8));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
