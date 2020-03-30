package com.example.hieudo.phuocnguyenintern.others.models.networkModels;

public class Navigation {
    private String icon;
    private String title;
    //todo: add icon contructor
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Navigation(String title) {
        this.title = title;
    }
}
