package com.technologies.pittu.videokenassignment.model;

/**
 * Copyright (c) on 26/07/17
 * All this files are belongs to Pittu Harish Reddy (harish)
 */

public class Content {

    private int id;
    private String title;
    private long duration;

    public Content(int id, String title, long duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
