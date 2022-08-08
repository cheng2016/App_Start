package com.huiyao.gamecenter.data.entity;

import java.util.List;

/**
 * 现金红包详情 页面返回data 数据模版
 */
public class HyCaseRedpackData {


    /**
     * icon : http://dabao.huiyaohuyu.com/upload/package/20220125/2022012516564061efbb4825f05.png
     * product_name : 勇者养成记
     * type : 射击闯关
     * package_name : com.cqss1.hyhy
     * amount : 1600
     * id : 4
     * start_time : 1640966400
     * end_time : 1656086400
     * download_url : https://apk1.hyhygame.com/2013/default/yongzheyangchengji.422113.apk
     * role_data : [{"uid_role_id":"c36b494b181ae404dc5223c076a49e99","userid":"2aec6341563749261b03ca1b6b3a117d","guid":"eb97fe6b1f101e53a3002c356617ca58","cp_id":281,"app_id":2013,"channel_id":154,"role_id":"867331896954783144","role_name":"亚当","zone_id":"1","zone_name":"1服","is_role_create":0,"party_name":"无","role_level":"75","vip":"0","imei":"","create_time":1643340700,"ip":"","update_time":1653388474,"left_coin":"","total_pay":0,"ext":"{\"capability\":\"\"}"}]
     */

    private String icon;
    private String product_name;
    private String type;
    private String package_name;
    private String amount;
    private String id;
    private long start_time;
    private long end_time;
    private String download_url;
    private List<RoleDataBean> role_data;
    //活动剩余天数
    private String extra_day;
    //角色信息为空时候提示
    private String role_status;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public List<RoleDataBean> getRole_data() {
        return role_data;
    }

    public void setRole_data(List<RoleDataBean> role_data) {
        this.role_data = role_data;
    }

    public String getExtra_day() {
        return extra_day;
    }

    public void setExtra_day(String extra_day) {
        this.extra_day = extra_day;
    }

    public String getRole_status() {
        return role_status;
    }

    public void setRole_status(String role_status) {
        this.role_status = role_status;
    }


    public static class RoleDataBean {
        /**
         * uid_role_id : c36b494b181ae404dc5223c076a49e99
         * userid : 2aec6341563749261b03ca1b6b3a117d
         * guid : eb97fe6b1f101e53a3002c356617ca58
         * cp_id : 281
         * app_id : 2013
         * channel_id : 154
         * role_id : 867331896954783144
         * role_name : 亚当
         * zone_id : 1
         * zone_name : 1服
         * is_role_create : 0
         * party_name : 无
         * role_level : 75
         * vip : 0
         * imei :
         * create_time : 1643340700
         * ip :
         * update_time : 1653388474
         * left_coin :
         * total_pay : 0
         * ext : {"capability":""}
         */

        private String uid_role_id;
        private String userid;
        private String guid;
        private String cp_id;
        private String app_id;
        private String channel_id;
        private String role_id;
        private String role_name;
        private String zone_id;
        private String zone_name;
        private int is_role_create;
        private String party_name;
        private String role_level;
        private String vip;
        private String imei;
        private long create_time;
        private String ip;
        private long update_time;
        private String left_coin;
        private String total_amount;
        private String ext;



        private boolean isChecked;

        public String getUid_role_id() {
            return uid_role_id;
        }

        public void setUid_role_id(String uid_role_id) {
            this.uid_role_id = uid_role_id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getCp_id() {
            return cp_id;
        }

        public void setCp_id(String cp_id) {
            this.cp_id = cp_id;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getZone_id() {
            return zone_id;
        }

        public void setZone_id(String zone_id) {
            this.zone_id = zone_id;
        }

        public String getZone_name() {
            return zone_name;
        }

        public void setZone_name(String zone_name) {
            this.zone_name = zone_name;
        }

        public int getIs_role_create() {
            return is_role_create;
        }

        public void setIs_role_create(int is_role_create) {
            this.is_role_create = is_role_create;
        }

        public String getParty_name() {
            return party_name;
        }

        public void setParty_name(String party_name) {
            this.party_name = party_name;
        }

        public String getRole_level() {
            return role_level;
        }

        public void setRole_level(String role_level) {
            this.role_level = role_level;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getLeft_coin() {
            return left_coin;
        }

        public void setLeft_coin(String left_coin) {
            this.left_coin = left_coin;
        }



        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }
    }
}
