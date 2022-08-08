package com.huiyao.gamecenter.data.entity;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/30 14:52
 * @描述:
 */
public class AppEvent {
    public String recommend_id;
    public int product_id;
    public int type;

    public AppEvent(String recommend_id, int product_id, int type) {
        this.recommend_id = recommend_id;
        this.product_id = product_id;
        this.type = type;
    }
}
