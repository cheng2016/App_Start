package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/15 18:01
 * @描述: 首页分页列表
 */
public class FirstGameListData {

    /**
     * status : 0
     * message : ok
     * url :
     * data : [{"id":17,"icon":"http://res.huiyaohuyu.com//picture//20220630//62bd1814583b8.jpg","name":"超强射手","type":"休闲射击","recommend_id":"recommend_17"},{"id":16,"icon":"http://res.huiyaohuyu.com//picture//20220628//62bac8783e937.jpg","name":"神元游仙","type":"玄幻仙侠","recommend_id":"recommend_16"},{"id":15,"icon":"https://res.huiyaohuyu.com/picture/20220628/62ba739953486.png","name":"苍空物语","type":"放置卡牌","recommend_id":"recommend_15"},{"id":14,"icon":"http://res.hyhygame.com/picture/20220628/62ba68d694ced.png","name":"弓箭猎人","type":"休闲射击","recommend_id":"recommend_14"}]
     * data_list : []
     */

    private int status;
    private String message;
    private String url;
    private List<DataBean> data;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
         * id : 17
         * icon : http://res.huiyaohuyu.com//picture//20220630//62bd1814583b8.jpg
         * name : 超强射手
         * type : 休闲射击
         * recommend_id : recommend_17
         */

        private int id;
        private String icon;
        private String name;
        private String type;
        private String recommend_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }
    }
}
