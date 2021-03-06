
package com.technologies.pittu.videokenassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    @Expose
    public Default _default;
    @SerializedName("medium")
    @Expose
    public Medium medium;
    @SerializedName("high")
    @Expose
    public High high;

    public Default get_default() {
        return _default;
    }

    public void set_default(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }
}
