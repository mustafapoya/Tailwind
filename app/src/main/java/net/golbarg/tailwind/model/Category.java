package net.golbarg.tailwind.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {
    private int id;
    private String title;

    public Category(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public static Category createFromJson(JSONObject json) throws JSONException {
        return new Category(json.getInt("id"),json.getString("title"));
    }
}
