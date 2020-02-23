package com.example.shortcutsstudy;

import java.io.Serializable;

public class ShortResource implements Serializable {
    private int shortLabel;
    private int longLabel;

    public int getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(int shortLabel) {
        this.shortLabel = shortLabel;
    }

    public int getLongLabel() {
        return longLabel;
    }

    public void setLongLabel(int longLabel) {
        this.longLabel = longLabel;
    }
}
