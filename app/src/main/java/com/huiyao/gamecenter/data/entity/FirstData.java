package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/15 17:58
 * @描述: 首页数据
 */
public class FirstData {


    /**
     * status : 0
     * message : ok
     * url :
     * data : {"banner":[{"id":24,"name":"邀请好友","img":"http://res.huiyaohuyu.com/picture/20220628/62badc8811d34.jpg","link_type":0,"product_id":null,"url":null,"type":0,"sort_index":2,"hy_uid":0,"create_time":1656413926,"update_time":1656413926,"recommend_id":"recommend_24","product_name":"","introduction":""},{"id":25,"name":"神元仙游","img":"https://res.hyhygame.com/picture/20220628/62bac71c06e36.jpg","link_type":0,"product_id":16,"url":null,"type":0,"sort_index":1,"hy_uid":0,"create_time":1656413998,"update_time":1656413998,"recommend_id":"recommend_25","product_name":"神元游仙","introduction":"强大的对手和十分美妙的人生故事，每一次的冒险都会在这里让你挑战自己的极限。"}],"hot_game":[{"id":12,"name":"弓箭猎人","img":"http://res.hyhygame.com/picture/20220628/62ba68d694ced.png","link_type":0,"product_id":14,"url":null,"type":1,"sort_index":1,"hy_uid":0,"create_time":1656385866,"update_time":1656385866,"recommend_id":"recommend_12","product_name":"弓箭猎人","introduction":"原始部落的原始生活"},{"id":16,"name":"苍空物语","img":"https://res.huiyaohuyu.com/picture/20220628/62ba739953486.png","link_type":0,"product_id":15,"url":null,"type":1,"sort_index":2,"hy_uid":0,"create_time":1656386984,"update_time":1656386984,"recommend_id":"recommend_16","product_name":"苍空物语","introduction":"萌幻画风炫酷打斗"},{"id":26,"name":"超强射手","img":"http://res.hyhygame.com//picture//20220630//62bd1814583b8.jpg","link_type":0,"product_id":17,"url":null,"type":1,"sort_index":2,"hy_uid":0,"create_time":1656560515,"update_time":1656560515,"recommend_id":"recommend_26","product_name":"超强射手","introduction":"精美的游戏画面，卡通的游戏画风，可爱的角色形象，有趣射击玩法。"}],"hot_banner":[{"id":11,"name":"弓箭猎人","img":"http://res.huiyaohuyu.com/picture/20220628/62ba6fca6554b.jpg","link_type":0,"product_id":14,"url":null,"type":2,"sort_index":1,"hy_uid":0,"create_time":1656385548,"update_time":1656385548,"recommend_id":"recommend_11","product_name":"弓箭猎人","introduction":"原始部落的原始生活"},{"id":15,"name":"苍空物语","img":"https://res.huiyaohuyu.com/picture/20220628/62ba7565744d0.png","link_type":0,"product_id":15,"url":null,"type":2,"sort_index":2,"hy_uid":0,"create_time":1656386962,"update_time":1656386962,"recommend_id":"recommend_15","product_name":"苍空物语","introduction":"萌幻画风炫酷打斗"}]}
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
        private List<BannerBean> banner;
        private List<HotGameBean> hot_game;
        private List<HotBannerBean> hot_banner;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<HotGameBean> getHot_game() {
            return hot_game;
        }

        public void setHot_game(List<HotGameBean> hot_game) {
            this.hot_game = hot_game;
        }

        public List<HotBannerBean> getHot_banner() {
            return hot_banner;
        }

        public void setHot_banner(List<HotBannerBean> hot_banner) {
            this.hot_banner = hot_banner;
        }

        public static class BannerBean {
            /**
             * id : 24
             * name : 邀请好友
             * img : http://res.huiyaohuyu.com/picture/20220628/62badc8811d34.jpg
             * link_type : 0
             * product_id : null
             * url : null
             * type : 0
             * sort_index : 2
             * hy_uid : 0
             * create_time : 1656413926
             * update_time : 1656413926
             * recommend_id : recommend_24
             * product_name :
             * introduction :
             */

            private int id;
            private String name;
            private String img;
            private int link_type;
            private int product_id;
            private String url;
            private int type;
            private int sort_index;
            private int hy_uid;
            private int create_time;
            private int update_time;
            private String recommend_id;
            private String product_name;
            private String introduction;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLink_type() {
                return link_type;
            }

            public void setLink_type(int link_type) {
                this.link_type = link_type;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSort_index() {
                return sort_index;
            }

            public void setSort_index(int sort_index) {
                this.sort_index = sort_index;
            }

            public int getHy_uid() {
                return hy_uid;
            }

            public void setHy_uid(int hy_uid) {
                this.hy_uid = hy_uid;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getRecommend_id() {
                return recommend_id;
            }

            public void setRecommend_id(String recommend_id) {
                this.recommend_id = recommend_id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }

        public static class HotGameBean {
            /**
             * id : 12
             * name : 弓箭猎人
             * img : http://res.hyhygame.com/picture/20220628/62ba68d694ced.png
             * link_type : 0
             * product_id : 14
             * url : null
             * type : 1
             * sort_index : 1
             * hy_uid : 0
             * create_time : 1656385866
             * update_time : 1656385866
             * recommend_id : recommend_12
             * product_name : 弓箭猎人
             * introduction : 原始部落的原始生活
             */

            private int id;
            private String name;
            private String img;
            private int link_type;
            private int product_id;
            private Object url;
            private int type;
            private int sort_index;
            private int hy_uid;
            private int create_time;
            private int update_time;
            private String recommend_id;
            private String product_name;
            private String introduction;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLink_type() {
                return link_type;
            }

            public void setLink_type(int link_type) {
                this.link_type = link_type;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSort_index() {
                return sort_index;
            }

            public void setSort_index(int sort_index) {
                this.sort_index = sort_index;
            }

            public int getHy_uid() {
                return hy_uid;
            }

            public void setHy_uid(int hy_uid) {
                this.hy_uid = hy_uid;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getRecommend_id() {
                return recommend_id;
            }

            public void setRecommend_id(String recommend_id) {
                this.recommend_id = recommend_id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }

        public static class HotBannerBean {
            /**
             * id : 11
             * name : 弓箭猎人
             * img : http://res.huiyaohuyu.com/picture/20220628/62ba6fca6554b.jpg
             * link_type : 0
             * product_id : 14
             * url : null
             * type : 2
             * sort_index : 1
             * hy_uid : 0
             * create_time : 1656385548
             * update_time : 1656385548
             * recommend_id : recommend_11
             * product_name : 弓箭猎人
             * introduction : 原始部落的原始生活
             */

            private int id;
            private String name;
            private String img;
            private int link_type;
            private int product_id;
            private String url;
            private int type;
            private int sort_index;
            private int hy_uid;
            private int create_time;
            private int update_time;
            private String recommend_id;
            private String product_name;
            private String introduction;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getLink_type() {
                return link_type;
            }

            public void setLink_type(int link_type) {
                this.link_type = link_type;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSort_index() {
                return sort_index;
            }

            public void setSort_index(int sort_index) {
                this.sort_index = sort_index;
            }

            public int getHy_uid() {
                return hy_uid;
            }

            public void setHy_uid(int hy_uid) {
                this.hy_uid = hy_uid;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getRecommend_id() {
                return recommend_id;
            }

            public void setRecommend_id(String recommend_id) {
                this.recommend_id = recommend_id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
    }
}
