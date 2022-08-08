package com.huiyao.gamecenter.data.entity;

import java.util.List;

public class HyCaseRedpackTaskData {


    private List<LevelTaskBean> level_task;
    private List<PayTaskBean> pay_task;
    private List<?> rank_task;

    public List<LevelTaskBean> getLevel_task() {
        return level_task;
    }

    public void setLevel_task(List<LevelTaskBean> level_task) {
        this.level_task = level_task;
    }

    public List<PayTaskBean> getPay_task() {
        return pay_task;
    }

    public void setPay_task(List<PayTaskBean> pay_task) {
        this.pay_task = pay_task;
    }

    public List<?> getRank_task() {
        return rank_task;
    }

    public void setRank_task(List<?> rank_task) {
        this.rank_task = rank_task;
    }

    public static class LevelTaskBean {
        /**
         * id : 31
         * activity_id : 4
         * hy_uid : 0
         * type : 0
         * level : 20
         * amount : 1
         * create_time : 1655211282
         * status : 0
         * pass_level : 1
         */

        private String id;
        private String activity_id;
        private String hy_uid;
        private int type;
        private String level;
        private String amount;
        private String create_time;
        //0未完成，1领取，2领取置灰，3已领取
        private int status;
        private String pass_level;



        private String describe;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getHy_uid() {
            return hy_uid;
        }

        public void setHy_uid(String hy_uid) {
            this.hy_uid = hy_uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPass_level() {
            return pass_level;
        }

        public void setPass_level(String pass_level) {
            this.pass_level = pass_level;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }

    public static class PayTaskBean {
        /**
         * id : 13
         * activity_id : 4
         * hy_uid : 0
         * type : 1
         * level : 2000
         * amount : 1
         * create_time : 1655211259
         * status : 0
         * pass_amount : 0
         */

        private String id;
        private String activity_id;
        private String hy_uid;
        private int type;
        private String level;
        private String amount;
        private String create_time;
        private int status;
        private String pass_amount;



        private String describe;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getHy_uid() {
            return hy_uid;
        }

        public void setHy_uid(String hy_uid) {
            this.hy_uid = hy_uid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPass_amount() {
            return pass_amount;
        }

        public void setPass_amount(String pass_amount) {
            this.pass_amount = pass_amount;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
