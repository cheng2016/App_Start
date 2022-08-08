package com.huiyao.gamecenter.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/8/2 11:41
 * @描述:
 */
public class VipInfo {


    /**
     * status : 0
     * message : ok
     * url :
     * data : {"grouth":0,"vip":0,"vip_img":"http://res.huiyaohuyu.com//picture//20220729//62e39549e57b0.png","detail":{"vip":0,"vip_img":"http://res.huiyaohuyu.com//picture//20220729//62e39549e57b0.png","current_score":0,"sum_score":0,"start_grouth":0,"end_grouth":1000,"middle_url":"http://res.huiyaohuyu.com//picture//20220729//62e3948429fa2.png","down_url":"http://res.huiyaohuyu.com//picture//20220729//62e37c3741c15.jpg"},"list":[{"id":1,"title":"游戏特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9a584526.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea10f0a0faf.jpg"},{"id":2,"title":"专属礼包","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9c1d0fd8.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea1115db992.jpg"},{"id":3,"title":"生日特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9de90610.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea112edd497.jpg"},{"id":4,"title":"活动特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9f81440f.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea11484b2da.jpg"},{"id":5,"title":"平台特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7aa2cc308d.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea115faf6d8.jpg"},{"id":6,"title":"客服特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7aa4a33728.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea1181be5b6.jpg"}]}
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

    public static class DataBean  implements Serializable{
        /**
         * grouth : 0
         * vip : 0
         * vip_img : http://res.huiyaohuyu.com//picture//20220729//62e39549e57b0.png
         * detail : {"vip":0,"vip_img":"http://res.huiyaohuyu.com//picture//20220729//62e39549e57b0.png","current_score":0,"sum_score":0,"start_grouth":0,"end_grouth":1000,"middle_url":"http://res.huiyaohuyu.com//picture//20220729//62e3948429fa2.png","down_url":"http://res.huiyaohuyu.com//picture//20220729//62e37c3741c15.jpg"}
         * list : [{"id":1,"title":"游戏特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9a584526.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea10f0a0faf.jpg"},{"id":2,"title":"专属礼包","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9c1d0fd8.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea1115db992.jpg"},{"id":3,"title":"生日特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9de90610.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea112edd497.jpg"},{"id":4,"title":"活动特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7a9f81440f.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea11484b2da.jpg"},{"id":5,"title":"平台特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7aa2cc308d.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea115faf6d8.jpg"},{"id":6,"title":"客服特权","img":"http://res.huiyaohuyu.com//picture//20220801//62e7aa4a33728.png","detail_img":"http://res.huiyaohuyu.com//picture//20220803//62ea1181be5b6.jpg"}]
         */

        private int grouth;
        private int vip;
        private String vip_img;
        private DetailBean detail;
        private List<ListBean> list;
        private String customer_service;

        public String getCustomer_service() {
            return customer_service;
        }

        public void setCustomer_service(String customer_service) {
            this.customer_service = customer_service;
        }

        public DataBean(String customer_service) {
            this.customer_service = customer_service;
        }

        public int getGrouth() {
            return grouth;
        }

        public void setGrouth(int grouth) {
            this.grouth = grouth;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public String getVip_img() {
            return vip_img;
        }

        public void setVip_img(String vip_img) {
            this.vip_img = vip_img;
        }

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class DetailBean implements Serializable {
            /**
             * vip : 0
             * vip_img : http://res.huiyaohuyu.com//picture//20220729//62e39549e57b0.png
             * current_score : 0
             * sum_score : 0
             * start_grouth : 0
             * end_grouth : 1000
             * middle_url : http://res.huiyaohuyu.com//picture//20220729//62e3948429fa2.png
             * down_url : http://res.huiyaohuyu.com//picture//20220729//62e37c3741c15.jpg
             */

            private int vip;
            private String vip_img;
            private int current_score;
            private int sum_score;
            private String start_grouth;
            private String end_grouth;
            private String middle_url;
            private String down_url;

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public String getVip_img() {
                return vip_img;
            }

            public void setVip_img(String vip_img) {
                this.vip_img = vip_img;
            }

            public int getCurrent_score() {
                return current_score;
            }

            public void setCurrent_score(int current_score) {
                this.current_score = current_score;
            }

            public int getSum_score() {
                return sum_score;
            }

            public void setSum_score(int sum_score) {
                this.sum_score = sum_score;
            }

            public String getStart_grouth() {
                return start_grouth;
            }

            public void setStart_grouth(String start_grouth) {
                this.start_grouth = start_grouth;
            }

            public String getEnd_grouth() {
                return end_grouth;
            }

            public void setEnd_grouth(String end_grouth) {
                this.end_grouth = end_grouth;
            }

            public String getMiddle_url() {
                return middle_url;
            }

            public void setMiddle_url(String middle_url) {
                this.middle_url = middle_url;
            }

            public String getDown_url() {
                return down_url;
            }

            public void setDown_url(String down_url) {
                this.down_url = down_url;
            }
        }

        public static class ListBean  implements Serializable{
            /**
             * id : 1
             * title : 游戏特权
             * img : http://res.huiyaohuyu.com//picture//20220801//62e7a9a584526.png
             * head_img : http://res.huiyaohuyu.com//picture//20220803//62ea10f0a0faf.jpg
             */

            private int id;
            private String title;
            private String img;
            private String head_img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }
        }
    }
}
