package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/21 15:06
 * @描述:
 */
public class GameDetailBean {
    /**
     * status : 0
     * message : ok
     * url :
     * data : {"id":26,"package_name":"com.dgz.lgz.hy","name":"零界战区","icon":"http://res.huiyaohuyu.com//picture//20220714//62cfbb850df6d.png","status":0,"direction":1,"url":null,"desc":"《零界战区》是一款以古今中外历史名将乱斗争霸为背景的放置挂机卡牌游戏，轻松挂机，获取资源，收集英雄，装备阵容随心搭配，称霸三国！","introduction":"三国放置卡牌","picture":null,"type":"三国放置卡牌","size":0,"create_time":1657781487,"app_id":2058,"game_type":1,"game_param":"?ext_info=509537","game_code":"gh_3e6f9c88a2ab"}
     * data_list : []
     */

    private int status;
    private String message;
    private String url;
    private DataBean data;
    private List<?> data_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public List<?> getData_list() {
        return data_list;
    }

    public void setData_list(List<?> data_list) {
        this.data_list = data_list;
    }

    public static class DataBean {
        /**
         * id : 26
         * package_name : com.dgz.lgz.hy
         * name : 零界战区
         * icon : http://res.huiyaohuyu.com//picture//20220714//62cfbb850df6d.png
         * status : 0
         * direction : 1
         * url : null
         * desc : 《零界战区》是一款以古今中外历史名将乱斗争霸为背景的放置挂机卡牌游戏，轻松挂机，获取资源，收集英雄，装备阵容随心搭配，称霸三国！
         * introduction : 三国放置卡牌
         * picture : null
         * type : 三国放置卡牌
         * size : 0
         * create_time : 1657781487
         * app_id : 2058
         * game_type : 1
         * game_param : ?ext_info=509537
         * game_code : gh_3e6f9c88a2ab
         */

        private int id;
        private String package_name;
        private String name;
        private String icon;
        private int status;
        private int direction;
        private String url;
        private String desc;
        private String introduction;
        private List<String> picture;
        private String type;
        private int size;
        private int create_time;
        private int app_id;
        private int game_type;
        private String game_param;
        private String game_code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public String getGame_param() {
            return game_param;
        }

        public void setGame_param(String game_param) {
            this.game_param = game_param;
        }

        public String getGame_code() {
            return game_code;
        }

        public void setGame_code(String game_code) {
            this.game_code = game_code;
        }
    }
}
