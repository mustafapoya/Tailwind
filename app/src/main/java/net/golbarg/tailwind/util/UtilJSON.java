package net.golbarg.tailwind.util;

import android.content.Context;

import net.golbarg.tailwind.model.Category;
import net.golbarg.tailwind.model.Content;
import net.golbarg.tailwind.model.ContentValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UtilJSON {

    public static String loadJSONFileAsset(Context context, String fileName) {
        String jsonData = null;

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonData;
    }

    public static JSONArray getJSONCategories(Context context) {
        JSONArray categories = null;

        try {
            JSONObject obj = new JSONObject(loadJSONFileAsset(context, "categories.json"));
            categories = obj.getJSONArray("categories");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static ArrayList<Category> mapCategoriesFromJSON(JSONArray categories) {
        ArrayList<Category> result = new ArrayList<>();

        try {
            for (int i = 0; i < categories.length(); i++) {
                JSONObject jsonObject = categories.getJSONObject(i);
                result.add(Category.createFromJson(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static JSONArray getJSONContents(Context context) {
        JSONArray contents = null;

        try {
            JSONObject obj = new JSONObject(loadJSONFileAsset(context, "contents.json"));
            contents = obj.getJSONArray("contents");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contents;
    }

    public static ArrayList<Content> mapContentsFromJSON(JSONArray contents) {
        ArrayList<Content> result = new ArrayList<>();

        try {
            for (int i = 0; i < contents.length(); i++) {
                JSONObject jsonObject = contents.getJSONObject(i);
                result.add(Content.createFromJson(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static JSONArray getJSONContentValues(Context context) {
        JSONArray contentValues = null;

        try {
            JSONObject obj = new JSONObject(loadJSONFileAsset(context, "content_values.json"));
            contentValues = obj.getJSONArray("content_values");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contentValues;
    }

    public static ArrayList<ContentValue> mapContentValuesFromJSON(JSONArray contentValues) {
        ArrayList<ContentValue> result = new ArrayList<>();

        try {
            for (int i = 0; i < contentValues.length(); i++) {
                JSONObject jsonObject = contentValues.getJSONObject(i);
                result.add(ContentValue.createFromJson(jsonObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
