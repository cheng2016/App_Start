package com.huiyao.gamecenter.data.entity;

/**
 * 存放用户账号认证状态 余额 等信息
 */
public class UserAccountStateInfoData {


    /**
     * balance : 9
     * mobile : true
     * is_wx : false
     * is_id : true
     * customer_service : https://wpa1.qq.com/wcwVf8tl?_type=wpa&qidian=true
     */
    private String score;
    private String balance;
    private boolean mobile;
    private boolean is_wx;
    private boolean is_id;
    private String customer_service;
    private ImgBean img;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isIs_wx() {
        return is_wx;
    }

    public void setIs_wx(boolean is_wx) {
        this.is_wx = is_wx;
    }

    public boolean isIs_id() {
        return is_id;
    }

    public void setIs_id(boolean is_id) {
        this.is_id = is_id;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public void setCustomer_service(String customer_service) {
        this.customer_service = customer_service;
    }

    public ImgBean getImg() {
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }


    public static class ImgBean{

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
         * product_name :
         * introduction :
         */

        private String id;
        private String name;
        private String img;
        private int link_type;
        private String product_id;
        private String url;
        private int type;
        private int sort_index;
        private String hy_uid;
        private String create_time;
        private String update_time;
        private String product_name;
        private String introduction;
        private String recommend_id;


        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
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

        public String getHy_uid() {
            return hy_uid;
        }

        public void setHy_uid(String hy_uid) {
            this.hy_uid = hy_uid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
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

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }
    }

}
