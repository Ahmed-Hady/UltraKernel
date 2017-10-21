package com.ultradevs.ultrakernel.adapters;

public class initList {
    public String title;
    public String summary;
    public Boolean checked;
    public int bg;

    public initList(String title, Boolean checked, String summary, Integer bg) {
        this.title = title;
        this.checked = checked;
        this.summary = summary;
        this.bg = bg;
    }
}
