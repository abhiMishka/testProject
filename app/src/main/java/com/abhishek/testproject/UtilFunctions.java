package com.abhishek.testproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abhishek.testproject.model.Story;
import com.abhishek.testproject.model.User;
import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
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


    public static List<User> readUsersFromJson(String json){
        Map<String,User> userMap = new HashMap<>();
        List<User> userList = new ArrayList<>();

        Gson gson = new Gson();


        try {
            JSONArray reader = new JSONArray(json);

            for(int i=0;i<2;i++){
                JSONObject obj = reader.getJSONObject(i);
                User user = gson.fromJson(obj.toString(),User.class);
                userMap.put((user.getId()),user);
                userList.add(user);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userList;

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



        return storyMap;

    }

    public static List<Story> readStoriesListFromJson(String json) {

        List<Story> storyList = new ArrayList<>();
        Gson gson = new Gson();


        try {
            JSONArray reader = new JSONArray(json);

            int length = reader.length();

            for (int i = 2; i < length; i++) {
                JSONObject obj = reader.getJSONObject(i);
                Story story = gson.fromJson(obj.toString(), Story.class);
                storyList.add(story);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return storyList;

    }


    public static void saveUsersInSharedPreference(Object users) {
        SharedPreferences.Editor editor = getEditor();

        editor.putString(Constants.KEY_USERS, jsonify(users));
        editor.commit();

    }

    public static void saveStoriesInSharedPreference(Object stories) {
        SharedPreferences.Editor editor = getEditor();

        editor.putString(Constants.KEY_STORIES, jsonify(stories));
        editor.commit();

    }

    private static SharedPreferences.Editor getEditor() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = Global.getInstance().getSharedPreferences(Global.getInstance().getString(R.string.sharedPreference), Context.MODE_PRIVATE);
        editor = settings.edit();
        return editor;
    }

    public static String jsonify(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T objectify(String pJson, Type pType) {
        if (pJson == null || pJson.trim().length() == 0) {
            return null;
        }
        try {
            return new Gson().fromJson(pJson, pType);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getData(String key) {
        SharedPreferences settings;
        settings = Global.getInstance().getSharedPreferences(Global.getInstance().getString(R.string.sharedPreference), Context.MODE_PRIVATE);

        return settings.getString(key, null);
    }

    public static final Type TYPE_ARRAY_LIST_STORIES = new TypeToken<ArrayList<Story>>() {
    }.getType();

    public static final Type TYPE_ARRAY_LIST_USERS = new TypeToken<ArrayList<User>>() {
    }.getType();

    public static boolean shouldShowFavoriteInfo() {
        int count;
        count = Global.getInstance().getSharedPreferences
                (Global.getInstance().getString(R.string.sharedPreference),
                        Context.MODE_PRIVATE).getInt(Constants.KEY_APP_OPENED, 0);

        if (count == 0) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt(Constants.KEY_APP_OPENED, 1);
            editor.apply();
            return true;
        }

        return false;
    }
}
