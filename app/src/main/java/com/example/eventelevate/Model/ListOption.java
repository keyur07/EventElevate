package com.example.eventelevate.Model;

public class ListOption {
private int pen;
private String name;

    public int getPen() {
        return pen;
    }

    public void setPen(int pen) {
        this.pen = pen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListOption(int pen, String apple) {
        this.pen = pen;
        this.name = apple;
    }
}
