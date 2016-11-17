package com.abhishek.testproject;

import android.content.Context;
import android.util.Log;

import com.abhishek.testproject.model.Story;
import com.abhishek.testproject.model.User;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.System.in;

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


    public static Map<String,User> readUsersFromJson(String json){
        Map<String,User> userMap = new HashMap<>();

        Gson gson = new Gson();


        try {
            JSONArray reader = new JSONArray(json);

            int length = reader.length();

            for(int i=0;i<2;i++){
                JSONObject obj = reader.getJSONObject(i);
                User user = gson.fromJson(obj.toString(),User.class);
                userMap.put((user.getId()),user);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("testParsing","userMap :  " +userMap.get("238bb4ca-606d-4817-afad-78bee2898264").toString());

        Log.i("testParsing","userMap :  " +userMap.get("cda99c24-955a-4c58-a6a8-c811938df530").toString());

        return userMap;

    }

    public static Map<String,Story> readStoriesFromJson(String json) {
        Map<String, Story> storyMap = new HashMap<>();

        Gson gson = new Gson();


        try {
            JSONArray reader = new JSONArray(json);

            int length = reader.length();

            for (int i = 2; i < length; i++) {
                JSONObject obj = reader.getJSONObject(i);
                Story story = gson.fromJson(obj.toString(), Story.class);
                storyMap.put((story.getId()), story);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Set<String> keySet = storyMap.keySet();

        Iterator<String> it = keySet.iterator();


        while (it.hasNext()){
            Log.i("testParsing", "userMap :  " + storyMap.get(it.next()).toString()+"\n");
        }

        return storyMap;

    }
}
