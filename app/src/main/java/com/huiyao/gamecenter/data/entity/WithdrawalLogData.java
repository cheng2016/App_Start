package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * @Created by: chengzj
 * @创建时间: 2022/6/23 15:38
 * @描述:
 */
public class WithdrawalLogData {




    /**
     * status : 0
     * message : ok
     * url :
     * data : {"balance":10,"spending":[{"balance":9,"type":1,"role_name":"小王","zone_name":"先行3服","amount":1,"before_amount":10}],"income":[{"balance":8,"type":0,"role_name":"小王","zone_name":"先行3服","amount":1,"before_amount":7}]}
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
         * balance : 10
         * spending : [{"balance":9,"type":1,"role_name":"小王","zone_name":"先行3服","amount":1,"before_amount":10}]
         * income : [{"balance":8,"type":0,"role_name":"小王","zone_name":"先行3服","amount":1,"before_amount":7}]
         */

        private int balance;
        private List<SpendingBean> spending;
        private List<IncomeBean> income;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public List<SpendingBean> getSpending() {
            return spending;
        }

        public void setSpending(List<SpendingBean> spending) {
            this.spending = spending;
        }

        public List<IncomeBean> getIncome() {
            return income;
        }

        public void setIncome(List<IncomeBean> income) {
            this.income = income;
        }

        public static class SpendingBean {


            /**
             * balance : 9
             * type : 1
             * role_name : 小王
             * zone_name : 先行3服
             * amount : 1
             * before_amount : 10
             *              * create_time : 1655974264
             */

            private String balance;
            private int type;
            private String role_name;
            private String zone_name;
            private String amount;
            private String before_amount;
            private String create_time;


            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }

            public String getZone_name() {
                return zone_name;
            }

            public void setZone_name(String zone_name) {
                this.zone_name = zone_name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBefore_amount() {
                return before_amount;
            }

            public void setBefore_amount(String before_amount) {
                this.before_amount = before_amount;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class IncomeBean {
            /**
             * balance : 8
             * type : 0
             * role_name : 小王
             * zone_name : 先行3服
             * amount : 1
             * before_amount : 7
             */

            private String balance;
            private int type;
            private String role_name;
            private String zone_name;
            private String amount;
            private String before_amount;
            private String create_time;



            private String desc;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getRole_name() {
                return role_name;
            }

            public void setRole_name(String role_name) {
                this.role_name = role_name;
            }

            public String getZone_name() {
                return zone_name;
            }

            public void setZone_name(String zone_name) {
                this.zone_name = zone_name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getBefore_amount() {
                return before_amount;
            }

            public void setBefore_amount(String before_amount) {
                this.before_amount = before_amount;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
