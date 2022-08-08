package com.huiyao.gamecenter.data.entity;

/**
 * 作者: chengzj
 * 时间: 2022/6/8
 */

public class GridViewData {
    private String id;
    private String icon;
    private String name;

    public GridViewData(String id, String icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
