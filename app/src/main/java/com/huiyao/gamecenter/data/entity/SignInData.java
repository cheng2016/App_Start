package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/7/15 11:37
 * @描述:
 */
public class SignInData {


    /**
     * status : 0
     * message : ok
     * url :
     * data : {"list":[{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第1天","date":"2022-07-18"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第2天","date":"2022-07-19"},{"status":1,"icon":"http://res.huiyaohuyu.com//picture//20220715//62d0d5f2bc795.png","top_content":"+8积分","bottom_content":"第3天","date":"2022-07-20"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第4天","date":"2022-07-21"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第5天","date":"2022-07-22"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第6天","date":"2022-07-23"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第7天","date":"2022-07-24"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第8天","date":"2022-07-25"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第9天","date":"2022-07-26"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第10天","date":"2022-07-27"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第11天","date":"2022-07-28"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第12天","date":"2022-07-29"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第13天","date":"2022-07-30"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第14天","date":"2022-07-31"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第15天","date":"2022-08-01"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第16天","date":"2022-08-02"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第17天","date":"2022-08-03"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第18天","date":"2022-08-04"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第19天","date":"2022-08-05"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第20天","date":"2022-08-06"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第21天","date":"2022-08-07"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第22天","date":"2022-08-08"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第23天","date":"2022-08-09"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第24天","date":"2022-08-10"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第25天","date":"2022-08-11"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第26天","date":"2022-08-12"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第27天","date":"2022-08-13"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第28天","date":"2022-08-14"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第29天","date":"2022-08-15"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第30天","date":"2022-08-16"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第31天","date":"2022-08-17"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第32天","date":"2022-08-18"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第33天","date":"2022-08-19"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第34天","date":"2022-08-20"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第35天","date":"2022-08-21"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第36天","date":"2022-08-22"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第37天","date":"2022-08-23"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第38天","date":"2022-08-24"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第39天","date":"2022-08-25"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第40天","date":"2022-08-26"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第41天","date":"2022-08-27"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第42天","date":"2022-08-28"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第43天","date":"2022-08-29"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第44天","date":"2022-08-30"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第45天","date":"2022-08-31"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第46天","date":"2022-09-01"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第47天","date":"2022-09-02"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第48天","date":"2022-09-03"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第49天","date":"2022-09-04"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第50天","date":"2022-09-05"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第51天","date":"2022-09-06"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第52天","date":"2022-09-07"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第53天","date":"2022-09-08"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第54天","date":"2022-09-09"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第55天","date":"2022-09-10"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第56天","date":"2022-09-11"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第57天","date":"2022-09-12"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第58天","date":"2022-09-13"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第59天","date":"2022-09-14"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第60天","date":"2022-09-15"}],"banner":"http:\\/\\/res.hyhygame.com\\/picture\\/20220715\\/62d0d6bf52c21.jpg","rule":"APP签到规则（新）\r\n\r\n一：每日签到\r\n1.每天只能签到一次\r\n2.每次签到可以获得8积分（后台可灵活配置）\r\n3.VIP3及以上每个月可以补签3次，最早可补签前20天内的任意一天\r\n\r\n二：连续签到\r\n1.连续签到7天可成为VIP1\r\n2.连续签到30天可成为VIP2\r\n3.连续签到60天可成为VIP3\r\n4.连续签到120天可成为VIP4\r\n5.连续签到300天可成为VIP5\r\n6.连续签到500天可成为VIP6\r\n\r\n三：断签\r\n连续签到被漏签后，重新开始计算连续签到天数"}
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
         * list : [{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第1天","date":"2022-07-18"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第2天","date":"2022-07-19"},{"status":1,"icon":"http://res.huiyaohuyu.com//picture//20220715//62d0d5f2bc795.png","top_content":"+8积分","bottom_content":"第3天","date":"2022-07-20"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第4天","date":"2022-07-21"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第5天","date":"2022-07-22"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第6天","date":"2022-07-23"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第7天","date":"2022-07-24"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第8天","date":"2022-07-25"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第9天","date":"2022-07-26"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第10天","date":"2022-07-27"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第11天","date":"2022-07-28"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第12天","date":"2022-07-29"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第13天","date":"2022-07-30"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第14天","date":"2022-07-31"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第15天","date":"2022-08-01"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第16天","date":"2022-08-02"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第17天","date":"2022-08-03"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第18天","date":"2022-08-04"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第19天","date":"2022-08-05"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第20天","date":"2022-08-06"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第21天","date":"2022-08-07"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第22天","date":"2022-08-08"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第23天","date":"2022-08-09"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第24天","date":"2022-08-10"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第25天","date":"2022-08-11"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第26天","date":"2022-08-12"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第27天","date":"2022-08-13"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第28天","date":"2022-08-14"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第29天","date":"2022-08-15"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第30天","date":"2022-08-16"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第31天","date":"2022-08-17"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第32天","date":"2022-08-18"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第33天","date":"2022-08-19"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第34天","date":"2022-08-20"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第35天","date":"2022-08-21"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第36天","date":"2022-08-22"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第37天","date":"2022-08-23"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第38天","date":"2022-08-24"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第39天","date":"2022-08-25"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第40天","date":"2022-08-26"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第41天","date":"2022-08-27"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第42天","date":"2022-08-28"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第43天","date":"2022-08-29"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第44天","date":"2022-08-30"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第45天","date":"2022-08-31"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第46天","date":"2022-09-01"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第47天","date":"2022-09-02"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第48天","date":"2022-09-03"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第49天","date":"2022-09-04"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第50天","date":"2022-09-05"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第51天","date":"2022-09-06"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第52天","date":"2022-09-07"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第53天","date":"2022-09-08"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第54天","date":"2022-09-09"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第55天","date":"2022-09-10"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第56天","date":"2022-09-11"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第57天","date":"2022-09-12"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第58天","date":"2022-09-13"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第59天","date":"2022-09-14"},{"status":0,"icon":"http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png","top_content":"+8积分","bottom_content":"第60天","date":"2022-09-15"}]
         * banner : http:\/\/res.hyhygame.com\/picture\/20220715\/62d0d6bf52c21.jpg
         * rule : APP签到规则（新）

         一：每日签到
         1.每天只能签到一次
         2.每次签到可以获得8积分（后台可灵活配置）
         3.VIP3及以上每个月可以补签3次，最早可补签前20天内的任意一天

         二：连续签到
         1.连续签到7天可成为VIP1
         2.连续签到30天可成为VIP2
         3.连续签到60天可成为VIP3
         4.连续签到120天可成为VIP4
         5.连续签到300天可成为VIP5
         6.连续签到500天可成为VIP6

         三：断签
         连续签到被漏签后，重新开始计算连续签到天数
         */

        private String banner;
        private String rule;
        private List<ListBean> list;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * status : 0
             * icon : http://res.huiyaohuyu.com//picture//20220720//62d771562639a.png
             * top_content : +8积分
             * bottom_content : 第1天
             * date : 2022-07-18
             */

            private int status;
            private String icon;
            private String top_content;
            private String bottom_content;
            private String date;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getTop_content() {
                return top_content;
            }

            public void setTop_content(String top_content) {
                this.top_content = top_content;
            }

            public String getBottom_content() {
                return bottom_content;
            }

            public void setBottom_content(String bottom_content) {
                this.bottom_content = bottom_content;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
