package net.golbarg.tailwind.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ContentValue {
    private int id;
    private int contentId;
    private String key;
    private String value;

    public ContentValue(int id, int contentId, String key, String value) {
        this.id = id;
        this.contentId = contentId;
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ContentValue{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static ContentValue createFromJson(JSONObject json) throws JSONException {
        return new ContentValue(json.getInt("id"), json.getInt("content_id"),json.getString("key"),
                                json.getString("value"));
    }

}
