package com.huiyao.gamecenter.data.entity;

public class CheckAppUpdateEntity {
    /**
     * Code : 0
     * url :
     * Message : {"version_no":1,"version_name":"LOL-0.0.1","download_url":"http://res.huiyaohuyu.com//picture//20220622//62b2c79851670.jpg","force_update":1,"describe":"版本更新的功能有如下：1、界面优化  2、英雄技能调整  3、部分活动修改"}
     */

    private int Code;
    private String url;
    private MessageBean Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageBean getMessage() {
        return Message;
    }

    public void setMessage(MessageBean Message) {
        this.Message = Message;
    }

    public static class MessageBean {
        /**
         * version_no : 1
         * version_name : LOL-0.0.1
         * download_url : http://res.huiyaohuyu.com//picture//20220622//62b2c79851670.jpg
         * force_update : 1
         * describe : 版本更新的功能有如下：1、界面优化  2、英雄技能调整  3、部分活动修改
         */

        private int version_no;
        private String version_name;
        private String download_url;
        // 0.更新 1.强更
        private int force_update;
        private String describe;

        public int getVersion_no() {
            return version_no;
        }

        public void setVersion_no(int version_no) {
            this.version_no = version_no;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public int getForce_update() {
            return force_update;
        }

        public void setForce_update(int force_update) {
            this.force_update = force_update;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
