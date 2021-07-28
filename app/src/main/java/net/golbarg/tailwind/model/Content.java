package net.golbarg.tailwind.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Content {
    private int id;
    private int categoryId;
    private String title;
    private String linkAddress;
    private String description;
    private String tags;

    public Content(int id, int categoryId, String title, String linkAddress, String description, String tags) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.linkAddress = linkAddress;
        this.description = description;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", title='" + title + '\'' +
                ", linkAddress='" + linkAddress + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }

    public static Content createFromJson(JSONObject json) throws JSONException {
        return new Content(json.getInt("id"), json.getInt("category_id"),json.getString("title"),
                json.getString("link_address"), json.getString("description"), json.getString("tags"));
    }
}
