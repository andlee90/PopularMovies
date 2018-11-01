package com.example.android.popularmovies.models;

public class Trailer {

    private String id;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public Trailer(String id, String key, String name, String site, String size, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
